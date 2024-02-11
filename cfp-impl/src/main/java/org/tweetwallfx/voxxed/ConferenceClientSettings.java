/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019-2022 TweetWallFX
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
package org.tweetwallfx.voxxed;

import java.util.Objects;
import org.tweetwallfx.config.ConfigurationConverter;
import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.map;

/**
 * POJO for reading Settings concerning Devoxx CFP Client.
 */
public final class ConferenceClientSettings {

    /**
     * Configuration key under which the data for this Settings object is stored
     * in the configuration data map.
     */
    public static final String CONFIG_KEY = "voxxedDayConferenceClient";
    private String eventUri;
    private String votingResultsUri;
    private String votingResultsToken;
    private String votingResultsEvent;

    /**
     * Returns the Event URI from where all standard calls are executed.
     *
     * @return the Event URI from where all standard calls are executed
     */
    public String eventUri() {
        return Objects.requireNonNull(eventUri, "eventUri must not be null!");
    }

    /**
     * Sets the Event URI from where all standard calls are executed.
     *
     * @param eventUri the Event URI from where all standard calls are executed
     */
    public void setEventUri(final String eventUri) {
        Objects.requireNonNull(eventUri, "eventUri must not be null!");
        this.eventUri = eventUri.endsWith("/") ? eventUri : eventUri + '/';
    }

    /**
     * Returns the Query Uri from where voting results are retrieved.
     *
     * @return the Query Uri from where voting results are retrieved
     */
    public String votingResultsUri() {
        return votingResultsUri;
    }

    /**
     * Sets the Query Uri from where voting results are retrieved.
     *
     * @param votingResultsUri the Query Uri from where voting results are
     * retrieved
     */
    public void setVotingResultsUri(final String votingResultsUri) {
        this.votingResultsUri = votingResultsUri;
    }

    /**
     * {@return the Token required to retrieve voting results.}
     */
    public String votingResultsToken() {
        return votingResultsToken;
    }

    /**
     * Sets the Token required to retrieve voting results.
     *
     * @param votingResultsToken the voting result token
     */
    public void setVotingResultsToken(String votingResultsToken) {
        Objects.requireNonNull(votingResultsToken, "votingResultsToken must not be null!");
        this.votingResultsToken = votingResultsToken;
    }

    /**
     * {@return the voting results event id.}
     */
    public String votingResultsEvent() {
        return votingResultsEvent;
    }

    /**
     * Sets the voting results event id.
     *
     * @param votingResultsEvent the voting result event id
     */
    public void setVotingResultsEvent(String votingResultsEvent) {
        Objects.requireNonNull(votingResultsEvent, "votingResultsEvent must not be null!");
        this.votingResultsEvent = votingResultsEvent;
    }

    @Override
    public String toString() {
        return createToString(this, map(
                "eventBaseUri", eventUri(),
                "votingResultsToken", votingResultsToken(),
                "votingResultsUri", votingResultsUri(),
                "votingResultsEvent", votingResultsEvent()
        ));
    }

    /**
     * Service implementation converting the configuration data of the root key
     * {@link ConferenceClientSettings#CONFIG_KEY} into {@link ConferenceClientSettings}.
     */
    public static class Converter implements ConfigurationConverter {

        @Override
        public String getResponsibleKey() {
            return ConferenceClientSettings.CONFIG_KEY;
        }

        @Override
        public Class<?> getDataClass() {
            return ConferenceClientSettings.class;
        }
    }
}
