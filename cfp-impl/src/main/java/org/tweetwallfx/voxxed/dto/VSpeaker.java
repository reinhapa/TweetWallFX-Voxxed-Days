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

import org.tweetwallfx.conference.api.Speaker;
import org.tweetwallfx.conference.api.Talk;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.mapEntry;
import static org.tweetwallfx.util.ToString.mapOf;

public class VSpeaker implements Speaker {
    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("firstName", firstName),
                mapEntry("lastName", lastName),
                mapEntry("fullName", fullName),
                mapEntry("bio", bio),
                mapEntry("company", company),
                mapEntry("imageUrl", imageUrl),
                mapEntry("twitterHandle", twitterHandle),
                mapEntry("userName", userName),
                mapEntry("ref", ref)
        ), super.toString());
    }

    @Override
    public String getAvatarURL() {
        return imageUrl;
    }

    @Override
    public String getId() {
        return Integer.toString(id);
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Map<String, String> getSocialMedia() {
        return Map.of("twitter", twitterHandle);
    }

    @Override
    public List<Talk> getTalks() {
        return null;
    }

    @Override
    public Optional<Speaker> reload() {
        return Optional.of(this);
    }

    @Override
    public Optional<String> getCompany() {
        return Optional.ofNullable(company);
    }
}
