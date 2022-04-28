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

import org.tweetwallfx.devoxx.api.cfp.client.Talk;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.mapOf;
import static org.tweetwallfx.util.ToString.mapEntry;

public class VProposal {
    private int id;
    private String title;
    private String description;
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

    public Talk talk() {
        Talk talk = new Talk();
        talk.setId(Integer.toString(id));
        talk.setTitle(title);
        talk.setSummaryAsHtml(description);
        talk.setTrack(Optional.ofNullable(track).map(VTrack::toString).orElse(""));
        talk.setSpeakers(speakers.stream().map(VSpeaker::speakerReference).toList());
        return talk;
    }

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("title", title),
                mapEntry("description", description),
                mapEntry("afterVideoURL", afterVideoURL),
                mapEntry("audienceLevel", audienceLevel),
                mapEntry("track", track),
                mapEntry("sessionType", sessionType),
                mapEntry("speakers", speakers),
                mapEntry("tags", tags),
                mapEntry("timeSlots", timeSlots)
        ), super.toString());
    }
}
