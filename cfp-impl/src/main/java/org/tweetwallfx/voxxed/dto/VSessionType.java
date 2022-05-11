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

import static org.tweetwallfx.util.ToString.*;
import static org.tweetwallfx.util.ToString.mapEntry;

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

    @Override
    public String toString() {
        return createToString(this, mapOf(
                mapEntry("id", id),
                mapEntry("name", name),
                mapEntry("duration", duration),
                mapEntry("isPause", isPause),
                mapEntry("description", description),
                mapEntry("cssColor", cssColor)
        ), super.toString());
    }
}