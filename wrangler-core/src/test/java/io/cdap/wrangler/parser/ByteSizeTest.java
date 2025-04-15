package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ByteSizeTest {
    @Test
    public void testByteSizeParsing() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(10 * 1024, byteSize.getBytes());
    }

    @Test
    public void testMBParsing() {
        ByteSize byteSize = new ByteSize("2MB");
        assertEquals(2 * 1024 * 1024, byteSize.getBytes());
    }
}
