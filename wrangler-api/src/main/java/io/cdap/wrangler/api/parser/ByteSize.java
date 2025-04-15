package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String token) {
        this.bytes = parseByteSize(token);
    }

    public long getBytes() {
        return bytes;
    }

    private long parseByteSize(String token) {
        // Assuming token format like 10KB, 5MB, etc.
        long value = Long.parseLong(token.replaceAll("[^0-9]", ""));
        String unit = token.replaceAll("[^a-zA-Z]", "").toUpperCase();

        switch (unit) {
            case "KB":
                return value * 1024;
            case "MB":
                return value * 1024 * 1024;
            case "GB":
                return value * 1024 * 1024 * 1024;
            case "B":
            default:
                return value;
        }
    }
}
