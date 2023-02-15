/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2018 TweetWallFX
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
package org.tweetwallfx.vdz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.tweetwallfx.devoxx.api.cfp.client.CFPClient;
import org.tweetwallfx.devoxx.api.cfp.client.Rooms;
import org.tweetwallfx.devoxx.api.cfp.client.Schedule;
import org.tweetwallfx.devoxx.api.cfp.client.ScheduleSlot;

import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;

@Disabled
class ScheduleTest {
    private static final Logger LOG = LogManager.getLogger(ScheduleTest.class);

    @Test
    void testReadEvents() {
        LOG.info("reading event..");
        var client = CFPClient.getClient();
        client.getRooms().ifPresent(ScheduleTest::rooms);
        client.getSchedule("thursday").ifPresent(ScheduleTest::schedule);
    }


    private static void rooms(Rooms rooms) {
        LOG.info("rooms: {}", rooms);
    }

    private static void schedule(Schedule schedule) {
        final ScheduleSlot slot = schedule.getSlots().get(0);

        ZoneId zoneId = ZoneId.of("Europe/Zurich");
        OffsetTime now = OffsetTime.parse("09:00:00.0+02:00");

        Instant instant = Instant.ofEpochMilli(slot.getToTimeMillis());
        OffsetTime offsetTime = OffsetTime.ofInstant(instant, zoneId);
        boolean after = offsetTime.isAfter(now.plusMinutes(10));
        LOG.info("slot: {} {}", slot, after);
    }
}
