package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AggregateStatsDirectiveTest {

    private static class MockExecutorContext extends ExecutorContext {
        private final List<Row> input;
        private final List<Row> output = new ArrayList<>();
        private final java.util.Map<String, String> args;

        public MockExecutorContext(List<Row> input, java.util.Map<String, String> args) {
            this.input = input;
            this.args = args;
        }

        @Override
        public List<Row> getInput() {
            return input;
        }

        @Override
        public List<Row> getOutput() {
            return output;
        }

        @Override
        public String getArgument(String key) {
            return args.get(key);
        }
    }

    @Test
    public void testAggregateStats() {
        // Create sample input rows
        Row row1 = Row.of("data_transfer_size", new ByteSize("10KB"), "response_time", new TimeDuration("100ms"));
        Row row2 = Row.of("data_transfer_size", new ByteSize("20KB"), "response_time", new TimeDuration("200ms"));
        List<Row> inputRows = Arrays.asList(row1, row2);

        // Arguments passed to the directive
        java.util.Map<String, String> args = new java.util.HashMap<>();
        args.put("byteSizeColumn", "data_transfer_size");
        args.put("timeDurationColumn", "response_time");
        args.put("totalSizeColumn", "total_size_mb");
        args.put("totalTimeColumn", "total_time_sec");

        // Instantiate directive and initialize with mock context
        AggregateStatsDirective directive = new AggregateStatsDirective();
        directive.initialize(new MockExecutorContext(inputRows, args));

        // Execute directive
        MockExecutorContext executionContext = new MockExecutorContext(inputRows, args);
        directive.execute(executionContext);

        // Get output
        List<Row> output = executionContext.getOutput();
        Row result = output.get(0);

        // 10KB + 20KB = 30KB = 30 * 1024 = 30720 bytes
        // 30720 bytes = 0.029296875 MB
        // 100ms + 200ms = 300ms = 0.3s

        double expectedMB = 30 * 1024 / (1024.0 * 1024);  // ~0.029296875
        double expectedSec = 300 / 1000.0;                // 0.3 seconds

        assertEquals(expectedMB, result.getValue("total_size_mb"), 0.0001);
        assertEquals(expectedSec, result.getValue("total_time_sec"), 0.0001);
    }
}
