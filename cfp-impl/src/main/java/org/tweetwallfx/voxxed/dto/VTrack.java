package org.tweetwallfx.voxxed.dto;

import java.net.URL;

public class VTrack {
    private int id;
    private String name;
    private String description;
    private URL imageURL;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return name;
    }
}
