/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 TweetWallFX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.tweetwallfx.mqtt;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetwallfx.config.Configuration;
import org.tweetwallfx.mqtt.config.MqttSettings;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MqttProcess implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MqttProcess.class);

    private final BooleanProperty stopProperty = new SimpleBooleanProperty();
    private final BooleanProperty runningProperty = new SimpleBooleanProperty();
    private final List<EventHandler<MqttEvent>> handlers = new ArrayList<>();

    private void fire(MqttEvent mqttEvent) {
        handlers.forEach(h -> h.handle(mqttEvent));
    }

    public void addMqttEventHandler(EventHandler<MqttEvent> handler) {
        handlers.add(Objects.requireNonNull(handler));
    }

    public ReadOnlyBooleanProperty runningProperty() {
        return ReadOnlyBooleanProperty.readOnlyBooleanProperty(runningProperty);
    }

    public void stop() {
        stopProperty.set(true);
        while (runningProperty.get()) {
            waitFor(MILLISECONDS, 500);
        }
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName("MQTT-Command-Dispatcher");
            final MqttSettings mqttSettings = Configuration.getInstance()
                    .getConfigTyped(MqttSettings.CONFIG_KEY, MqttSettings.class);
            long lastHeartbeat = 0;
            while (!stopProperty.get()) {
                final String broker = mqttSettings.brokerUrl();
                final String clientId = mqttSettings.clientId();
                try (MqttClientPersistence persistence = new MemoryPersistence();
                     MqttClient mqttClient = new MqttClient(broker, clientId, persistence)) {
                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setCleanSession(true);
                    connOpts.setConnectionTimeout(0);
                    connOpts.setKeepAliveInterval(30);
                    connOpts.setAutomaticReconnect(true);
                    final MqttSettings.Auth auth = mqttSettings.auth();
                    Optional.ofNullable(auth.userName()).ifPresent(connOpts::setUserName);
                    Optional.ofNullable(auth.secret()).ifPresent(pw -> connOpts.setPassword(pw.toCharArray()));
                    LOG.info("Connect to {}", broker);
                    mqttClient.connect(connOpts);
                    stopProperty.addListener((observableValue, oldValue, newValue) -> {
                        if (newValue) {
                            try {
                                mqttClient.publish("tweetwall/state/" + clientId, message(State.stopping()));
                                LOG.info("Disconnecting");
                                mqttClient.disconnect();
                            } catch (MqttException e) {
                                LOG.error("Failed to send stop notification", e);
                            }
                        }
                    });
                    runningProperty.set(true);
                    mqttClient.publish("tweetwall/state/" + clientId, message(State.starting(SystemInfo.info())));
                    LOG.info("Connection established");
                    mqttClient.subscribe("tweetwall/action/#", (t, m) -> handleActionMessage(clientId, t, m));
                    while (!stopProperty.get()) {
                        // heart beat task
                        long currentTime = System.currentTimeMillis();
                        long duration = MILLISECONDS.toSeconds(currentTime - lastHeartbeat);
                        if (duration >= mqttSettings.heartbeatSeconds()) {
                            lastHeartbeat = currentTime;
                            LOG.debug("Sending heart beat message");
                            mqttClient.publish("tweetwall/state/" + clientId, message(State.alive()));
                        } else {
                            waitFor(MILLISECONDS, 500);
                        }
                    }
                } catch (MqttException e) {
                    LOG.error("Failure while handling MQTT", e);
                }
            }
        } finally {
            runningProperty.set(false);
        }
    }

    private static void waitFor(TimeUnit unit, long amount) {
        try {
            unit.sleep(amount);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("Interrupted while waiting", e);
        }
    }

    static MqttMessage message(Object messageObject) throws MqttException {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            MqttMessage message = new MqttMessage(jsonb.toJson(messageObject).getBytes(StandardCharsets.UTF_8));
            message.setQos(2);
            return message;
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    void handleActionMessage(String clientId, String topic, MqttMessage message) {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        if (topic.equals("tweetwall/action/" + clientId)) {
            switch (payload) {
                case "stop" -> fire(new MqttEvent(this, MqttEvent.STOP));
                case "restart" -> fire(new MqttEvent(this, MqttEvent.RESTART));
                default -> LOG.warn("Unknown action payload: {}", payload);
            }
        } else {
            LOG.warn("Unknown payload '{}' for topic {}", payload, topic);
        }
    }
}
