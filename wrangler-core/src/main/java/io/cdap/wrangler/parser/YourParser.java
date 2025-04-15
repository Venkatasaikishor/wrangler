package io.cdap.wrangler;

import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

public class YourParser {
    // Method to visit byte size argument
    public Object visitByteSizeArg(ByteSizeContext ctx) {
        return new ByteSize(ctx.getText());
    }

    // Method to visit time duration argument
    public Object visitTimeDurationArg(TimeDurationContext ctx) {
        return new TimeDuration(ctx.getText());
    }
}
