package org.tweetwallfx.voxxed.dto;

import org.tweetwallfx.devoxx.api.cfp.client.Talk;

import java.util.*;
import java.net.URL;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
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
}
