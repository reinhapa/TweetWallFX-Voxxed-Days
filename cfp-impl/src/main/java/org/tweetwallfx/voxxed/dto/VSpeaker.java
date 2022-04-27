package org.tweetwallfx.voxxed.dto;

import org.tweetwallfx.devoxx.api.cfp.client.Speaker;
import org.tweetwallfx.devoxx.api.cfp.client.SpeakerReference;

import java.net.URL;

public class VSpeaker {
    private int id;
    private String firstName;
    private String lastName;
    private String bio;
    private String company;
    private String imageUrl;
    private String twitterHandle;
    private String userName;
    private URL ref;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRef(URL ref) {
        this.ref = ref;
    }

    public SpeakerReference speakerReference() {
        SpeakerReference speakerReference = new SpeakerReference();
        speakerReference.setName(String.format("%s %s", firstName, lastName));
        return speakerReference;
    }

    public Speaker speaker() {
        Speaker speaker = new Speaker();
        speaker.setFirstName(firstName);
        speaker.setLastName(lastName);
        speaker.setBio(bio);
        speaker.setCompany(company);
        speaker.setAvatarURL(imageUrl);
        return speaker;
    }
}
