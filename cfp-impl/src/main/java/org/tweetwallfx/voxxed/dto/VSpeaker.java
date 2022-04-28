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

import org.tweetwallfx.devoxx.api.cfp.client.Speaker;
import org.tweetwallfx.devoxx.api.cfp.client.SpeakerReference;

import java.net.URL;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.mapEntry;
import static org.tweetwallfx.util.ToString.mapOf;

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

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("firstName", firstName),
                mapEntry("lastName", lastName),
                mapEntry("bio", bio),
                mapEntry("company", company),
                mapEntry("imageUrl", imageUrl),
                mapEntry("twitterHandle", twitterHandle),
                mapEntry("userName", userName),
                mapEntry("ref", ref)
        ), super.toString());
    }
}
