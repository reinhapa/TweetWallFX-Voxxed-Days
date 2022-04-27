package org.tweetwallfx.voxxed.dto;

public class VSessionType {
    private int id;
    private String name;
    private int duration;
    private boolean isPause;
    private String description;
    private String cssColor;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCssColor(String cssColor) {
        this.cssColor = cssColor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }
}
