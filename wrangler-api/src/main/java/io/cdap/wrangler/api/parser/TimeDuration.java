package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long nanos;

    public TimeDuration(String token) {
        this.nanos = parseTimeDuration(token);
    }

    public long getNanos() {
        return nanos;
    }

    private long parseTimeDuration(String token) {
        long value = Long.parseLong(token.replaceAll("[^0-9]", ""));
        String unit = token.replaceAll("[^a-zA-Z]", "").toUpperCase();

        switch (unit) {
            case "MS":
                return value * 1000000; // ms to nanoseconds
            case "S":
                return value * 1000000000; // seconds to nanoseconds
            case "M":
                return value * 60000000000L; // minutes to nanoseconds
            default:
                return value;
        }
    }
}
