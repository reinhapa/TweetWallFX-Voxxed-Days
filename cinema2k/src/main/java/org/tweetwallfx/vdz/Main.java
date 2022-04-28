/*
 * The MIT License
 *
 * Copyright 2015-2018 TweetWallFX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.tweetwallfx.vdz;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.tweetwallfx.config.Configuration;
import org.tweetwallfx.config.TweetwallSettings;
import org.tweetwallfx.tweet.StringPropertyAppender;
import org.tweetwallfx.tweet.api.Tweeter;
import org.tweetwallfx.twod.TagTweets;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    private final AtomicReference<Integer> exitCode = new AtomicReference<>();

    @Override
    public void start(Stage primaryStage) {
        LOG.info("starting...");
        new Thread(this::mqttLoop).start();

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1920, 1280);
        borderPane.getStyleClass().add("splash");

        final TweetwallSettings tweetwallSettings = Configuration.getInstance()
                .getConfigTyped(TweetwallSettings.CONFIG_KEY, TweetwallSettings.class);

        Optional.ofNullable(tweetwallSettings.stylesheetResource())
                .map(ClassLoader.getSystemClassLoader()::getResource).map(java.net.URL::toExternalForm)
                .ifPresent(scene.getStylesheets()::add);
        Optional.ofNullable(tweetwallSettings.stylesheetFile()).ifPresent(scene.getStylesheets()::add);

        org.apache.logging.log4j.core.config.Configuration config = LoggerContext.getContext().getConfiguration();
        StringPropertyAppender spa = new StringPropertyAppender();
        spa.start();
        LoggerConfig rootLogger = config.getRootLogger();

        HBox statusLineHost = new HBox();
        Text statusLineText = new Text();
        statusLineText.getStyleClass().addAll("statusline");
        statusLineText.textProperty().bind(spa.stringProperty());
        statusLineHost.getChildren().add(statusLineText);

        final TagTweets tweetsTask = new TagTweets(borderPane);
        Platform.runLater(tweetsTask::start);

        scene.setOnKeyTyped((KeyEvent event) -> {
            if (event.isMetaDown()) {
                switch (event.getCharacter()) {
                case "d": {
                    if (null == statusLineHost.getParent()) {
                        borderPane.setBottom(statusLineHost);
                        rootLogger.addAppender(spa, Level.TRACE, null);
                    } else {
                        borderPane.getChildren().remove(statusLineHost);
                        rootLogger.removeAppender(spa.getName());
                    }
                    break;
                }
                case "x": {
                    exitCode.set(Integer.valueOf(0));
                    return;
                }
                default:
                }
            }
        });

        primaryStage.setTitle(tweetwallSettings.title());
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setFullScreen(!Boolean.getBoolean("org.tweetwallfx.disable-full-screen"));
    }

    @Override
    public void stop() {
        LOG.info("closing...");
        Tweeter.getInstance().shutdown();
    }

    void mqttLoop() {
        Properties systemProperties = System.getProperties();
        for (;;) {
            String broker = systemProperties.getProperty("mqtt.url", "tcp://127.0.0.1:1883");
            String clientId = systemProperties.getProperty("mqtt.client.id", UUID.randomUUID().toString());
            try (MqttClientPersistence persistence = new MemoryPersistence();
                    MqttClient sampleClient = new MqttClient(broker, clientId, persistence)) {
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                connOpts.setConnectionTimeout(0);
                connOpts.setKeepAliveInterval(30);
                connOpts.setAutomaticReconnect(true);
                Optional.ofNullable(systemProperties.getProperty("mqtt.username")).ifPresent(connOpts::setUserName);
                Optional.ofNullable(systemProperties.getProperty("mqtt.password"))
                        .ifPresent(pw -> connOpts.setPassword(pw.toCharArray()));

                LOG.info("Connect to {}", broker);
                sampleClient.connect(connOpts);
                LOG.info("Connection established");

                sampleClient.subscribe("tweetwall/action/#", (t, m) -> handleMqttMessage(clientId, t, m));
                for (;;) {
                    sampleClient.publish("tweetwall/state/" + clientId, message("alive"));
                    Integer rc = exitCode.getAndSet(null);
                    if (rc != null) {
                        sampleClient.publish("tweetwall/state/" + clientId, message("stopping"));
                        LOG.warn("Going to exit with rc {}", rc);
                        sampleClient.disconnect();
                        Platform.exit();
                        return;
                    }

                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (MqttException e) {
                LOG.error("Failure while handling MQTT", e);
            } catch (InterruptedException e) {
                LOG.error("Interrupted while waiting", e);
                return;
            }
        }
    }

    static MqttMessage message(String messageContent) {
        MqttMessage message = new MqttMessage(messageContent.getBytes(StandardCharsets.UTF_8));
        message.setQos(2);
        return message;
    }

    void handleMqttMessage(String clientId, String topic, MqttMessage message) {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        if (topic.equals("tweetwall/action/" + clientId)) {
            switch (payload) {
            case "stop": {
                exitCode.set(Integer.valueOf(0));
                break;
            }
            case "restart": {
                exitCode.set(Integer.valueOf(42));
                break;
            }
            default:
            }
        } else {
            LOG.warn("Unkown payload '{}' for topic {}", payload, topic);
        }
    }

    /**
     * @param args
     *                 the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
