package org.tweetwallfx.vdz.steps;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FlipInTweetsTest {
    @Test
    public void testFixup() {
        assertThat(FlipInTweets.fixup("Join\nX")).isEqualTo("Join|X");
        assertThat(FlipInTweets.fixup("Join\rX")).isEqualTo("Join|X");
        assertThat(FlipInTweets.fixup("Join\r\nX")).isEqualTo("Join|X");
        assertThat(FlipInTweets.fixup("Join\uD83D\uDC69\u200DX")).isEqualTo("Join\uD83D\uDC69X");
    }
}
