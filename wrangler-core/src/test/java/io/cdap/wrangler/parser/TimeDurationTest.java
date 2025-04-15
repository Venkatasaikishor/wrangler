package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimeDurationTest {
    @Test
    public void testTimeDurationParsing() {
        TimeDuration timeDuration = new TimeDuration("150ms");
        assertEquals(150_000_000, timeDuration.getNanos());
    }

    @Test
    public void testSecondsParsing() {
        TimeDuration timeDuration = new TimeDuration("2s");
        assertEquals(2_000_000_000L, timeDuration.getNanos());
    }
}
