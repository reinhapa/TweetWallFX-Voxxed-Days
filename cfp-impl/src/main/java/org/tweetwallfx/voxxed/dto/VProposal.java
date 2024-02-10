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
package org.tweetwallfx.voxxed.dto;

import org.tweetwallfx.conference.api.ScheduleSlot;
import org.tweetwallfx.conference.api.SessionType;
import org.tweetwallfx.conference.api.Speaker;
import org.tweetwallfx.conference.api.Talk;
import org.tweetwallfx.conference.api.Track;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.mapOf;
import static org.tweetwallfx.util.ToString.mapEntry;
import static org.tweetwallfx.voxxed.RestCallHelper.cast;

public class VProposal implements Talk {
    private int id;
    private String title;
    private String description;
    private String summary;
    private URL afterVideoURL;
    private String audienceLevel;
    private VTrack track;
    private VSessionType sessionType;
    private List<VSpeaker> speakers;
    private List<VTag> tags;
    private List<VTimeSlot> timeSlots;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTags(List<VTag> tags) {
        this.tags = tags;
    }

    public void setSpeakers(List<VSpeaker> speakers) {
        this.speakers = speakers;
    }

    public void setTrack(VTrack track) {
        this.track = track;
    }

    public void setSessionType(VSessionType sessionType) {
        this.sessionType = sessionType;
    }

    public void setAudienceLevel(String audienceLevel) {
        this.audienceLevel = audienceLevel;
    }

    public void setAfterVideoURL(URL afterVideoURL) {
        this.afterVideoURL = afterVideoURL;
    }

    public void setTimeSlots(List<VTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("title", title),
                mapEntry("description", description),
                mapEntry("summary", summary),
                mapEntry("afterVideoURL", afterVideoURL),
                mapEntry("audienceLevel", audienceLevel),
                mapEntry("track", track),
                mapEntry("sessionType", sessionType),
                mapEntry("speakers", speakers),
                mapEntry("tags", tags),
                mapEntry("timeSlots", timeSlots)
        ), super.toString());
    }

    @Override
    public String getId() {
        return Integer.toString(id);
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getAudienceLevel() {
        return audienceLevel;
    }

    @Override
    public SessionType getSessionType() {
        return sessionType;
    }

    @Override
    public OptionalInt getFavoriteCount() {
        return OptionalInt.empty();
    }

    @Override
    public Locale getLanguage() {
        return null;
    }

    @Override
    public List<ScheduleSlot> getScheduleSlots() {
        return cast(timeSlots, ScheduleSlot.class);
    }

    @Override
    public List<Speaker> getSpeakers() {
        return cast(speakers, Speaker.class);
    }

    @Override
    public List<String> getTags() {
        return tags.stream().map(VTag::toString).toList();
    }

    @Override
    public Track getTrack() {
        return track;
    }

    @Override
    public Optional<Talk> reload() {
        return Optional.of(this);
    }
}
