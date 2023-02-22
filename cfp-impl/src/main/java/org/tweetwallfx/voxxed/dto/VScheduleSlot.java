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

import org.tweetwallfx.conference.api.DateTimeRange;
import org.tweetwallfx.conference.api.Room;
import org.tweetwallfx.conference.api.ScheduleSlot;
import org.tweetwallfx.conference.api.Talk;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.mapEntry;
import static org.tweetwallfx.util.ToString.mapOf;

public class VScheduleSlot implements ScheduleSlot {
    private int id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean overflow;
    private String reserved;
    private String remark;
    private int eventId;
    private String eventName;
    private VRoom room;
    private int streamId;
    private VSessionType sessionType;
    private VTrack track;
    private VProposal proposal;
    private String audienceLevel;
    private String langName;
    private ZoneId timezone;
    private List<VSpeaker> speakers;
    private List<VTag> tags;
    private int totalFavourites;

    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public void setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
    }

    public void setRoom(VRoom room) {
        this.room = room;
    }

    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

    public void setAudienceLevel(String audienceLevel) {
        this.audienceLevel = audienceLevel;
    }

    public void setSessionType(VSessionType sessionType) {
        this.sessionType = sessionType;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public void setProposal(VProposal proposal) {
        this.proposal = proposal;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public void setTrack(VTrack track) {
        this.track = track;
    }

    public void setSpeakers(List<VSpeaker> speakers) {
        this.speakers = speakers;
    }

    public void setTags(List<VTag> tags) {
        this.tags = tags;
    }

    public void setTotalFavourites(int totalFavourites) {
        this.totalFavourites = totalFavourites;
    }

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("fromDate", fromDate),
                mapEntry("toDate", toDate),
                mapEntry("overflow", overflow),
                mapEntry("reserved", reserved),
                mapEntry("remark", remark),
                mapEntry("eventId", eventId),
                mapEntry("eventName", eventName),
                mapEntry("room", room),
                mapEntry("streamId", streamId),
                mapEntry("sessionType", sessionType),
                mapEntry("track", track),
                mapEntry("proposal", proposal),
                mapEntry("audienceLevel", audienceLevel),
                mapEntry("langName", langName),
                mapEntry("timezone", timezone),
                mapEntry("speakers", speakers),
                mapEntry("tags", tags),
                mapEntry("totalFavourites", totalFavourites)
        ), super.toString());
    }

    @Override
    public String getId() {
        return Integer.toString(id);
    }

    @Override
    public DateTimeRange getDateTimeRange() {
        return new DateTimeRangeImpl(fromDate.toInstant(), toDate.toInstant());
    }

    @Override
    public OptionalInt getFavoriteCount() {
        return OptionalInt.of(totalFavourites);
    }

    @Override
    public boolean isOverflow() {
        return overflow;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public Optional<Talk> getTalk() {
        return Optional.ofNullable(proposal);
    }
}
