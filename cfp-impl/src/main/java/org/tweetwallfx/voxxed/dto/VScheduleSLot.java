package org.tweetwallfx.voxxed.dto;

import org.tweetwallfx.devoxx.api.cfp.client.Schedule;
import org.tweetwallfx.devoxx.api.cfp.client.ScheduleSlot;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class VScheduleSLot {
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

    public ScheduleSlot scheduleSlot() {
        ScheduleSlot scheduleSlot = new ScheduleSlot();
        scheduleSlot.setSlotId(Integer.toString(id));
        Optional.ofNullable(fromDate).ifPresent(from -> {
            scheduleSlot.setFromTime(from.toString());
            scheduleSlot.setFromTimeMillis(from.toInstant().toEpochMilli());
        });
        Optional.ofNullable(toDate).ifPresent(to -> {
            scheduleSlot.setToTime(to.toString());
            scheduleSlot.setFromTimeMillis(to.toInstant().toEpochMilli());
        });
        Optional.ofNullable(room).map(VRoom::room).ifPresent(r -> {
            scheduleSlot.setRoomId(r.getId());
            scheduleSlot.setRoomName(r.getName());
            scheduleSlot.setRoomCapacity(r.getCapacity());
        });
        Optional.ofNullable(proposal).map(VProposal::talk).ifPresent(scheduleSlot::setTalk);
        return scheduleSlot;
    }

    public static Schedule schedule(List<VScheduleSLot> vSlots) {
        Schedule schedule = new Schedule();
        schedule.setSlots(vSlots.stream().map(VScheduleSLot::scheduleSlot).toList());
        return schedule;
    }
}
