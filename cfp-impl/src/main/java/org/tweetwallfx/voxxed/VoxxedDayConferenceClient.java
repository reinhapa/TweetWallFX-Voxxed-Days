/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 TweetWallFX
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

import jakarta.ws.rs.core.GenericType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetwallfx.conference.api.ConferenceClient;
import org.tweetwallfx.conference.api.RatingClient;
import org.tweetwallfx.conference.api.Room;
import org.tweetwallfx.conference.api.ScheduleSlot;
import org.tweetwallfx.conference.api.SessionType;
import org.tweetwallfx.conference.api.Speaker;
import org.tweetwallfx.conference.api.Talk;
import org.tweetwallfx.conference.api.Track;
import org.tweetwallfx.config.Configuration;
import org.tweetwallfx.voxxed.dto.VProposal;
import org.tweetwallfx.voxxed.dto.VRoom;
import org.tweetwallfx.voxxed.dto.VScheduleSlot;
import org.tweetwallfx.voxxed.dto.VSessionType;
import org.tweetwallfx.voxxed.dto.VSpeaker;
import org.tweetwallfx.voxxed.dto.VTrack;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.tweetwallfx.voxxed.RestCallHelper.cast;
import static org.tweetwallfx.voxxed.RestCallHelper.readOptionalFrom;

public class VoxxedDayConferenceClient implements ConferenceClient {
    private static final Logger LOG = LoggerFactory.getLogger(VoxxedDayConferenceClient.class);
    private static final GenericType<List<VRoom>> GT_ROOM = new GenericType<>() {
    };
    private static final GenericType<List<VScheduleSlot>> GT_SCHEDULE_SLOT = new GenericType<>() {
    };
    private static final GenericType<List<VSessionType>> GT_SESSION_TYPE = new GenericType<>() {
    };
    private static final GenericType<List<VSpeaker>> GT_SPEAKER = new GenericType<>() {
    };
    private static final GenericType<List<VTrack>> GT_TRACK = new GenericType<>() {
    };
    private static final GenericType<List<VProposal>> GT_TALK = new GenericType<>() {
    };

    private final String eventBaseUri;

    public VoxxedDayConferenceClient() {
        final ConferenceClientSettings cfpClientSettings = Configuration.getInstance().getConfigTyped(ConferenceClientSettings.CONFIG_KEY, ConferenceClientSettings.class);
        eventBaseUri = cfpClientSettings.eventUri();
        LOG.info("Initialized conference client for: {}", eventBaseUri);
    }

    private void notImplemented() {
        final var stackTrace = StackWalker
                .getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
                .walk(this::callerStack);
        final var uoe = new UnsupportedOperationException("Not implemented");
        uoe.setStackTrace(stackTrace);
        LOG.error("Called unimplemented operation", uoe);
        throw uoe;
    }

    private StackTraceElement[] callerStack(Stream<StackWalker.StackFrame> stackFrameStream) {
        return stackFrameStream.skip(1)
                                .map(StackWalker.StackFrame::toStackTraceElement)
                                .toArray(StackTraceElement[]::new);
    }

    @Override
    public String getName() {
        return "vdz";
    }

    @Override
    public List<SessionType> getSessionTypes() {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/proposalTypes", GT_SESSION_TYPE)
                .map(l -> cast(l, SessionType.class))
                .orElse(List.of());
    }

    @Override
    public List<Room> getRooms() {
        return readOptionalFrom(eventBaseUri + "/rooms/", GT_ROOM)
                .map(l -> cast(l, Room.class))
                .orElse(List.of());
    }

    @Override
    public List<ScheduleSlot> getSchedule(String conferenceDay) {
        return readOptionalFrom(eventBaseUri + "/schedules/" + conferenceDay, GT_SCHEDULE_SLOT)
                .map(l -> cast(l, ScheduleSlot.class))
                .orElse(List.of());
    }

    @Override
    public List<ScheduleSlot> getSchedule(String conferenceDay, String roomName) {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/rooms/" + roomName + "/" + conferenceDay, GT_SCHEDULE_SLOT)
                .map(l -> cast(l, ScheduleSlot.class))
                .orElse(List.of());
    }

    @Override
    public List<Speaker> getSpeakers() {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/speakers/", GT_SPEAKER)
                .map(l -> cast(l, Speaker.class))
                .orElse(List.of());
    }

    @Override
    public Optional<Speaker> getSpeaker(String speakerId) {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/speakers/" + speakerId, VSpeaker.class)
                .map(Speaker.class::cast);
    }

    @Override
    public List<Talk> getTalks() {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/tracks", GT_TALK)
                .map(l -> cast(l, Talk.class))
                .orElse(List.of());
    }

    @Override
    public Optional<Talk> getTalk(String talkId) {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/talks/" + talkId, VProposal.class)
                .map(Talk.class::cast);
    }

    @Override
    public List<Track> getTracks() {
        notImplemented();
        return readOptionalFrom(eventBaseUri + "/tracks", GT_TRACK).map(l -> cast(l, Track.class)).orElse(List.of());
    }

    @Override
    public Optional<RatingClient> getRatingClient() {
        return Optional.empty();
    }
}
