package io.cdap.directives.aggregates;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.directive.Directive;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

public class AggregateStatsDirective implements Directive {
    private String byteSizeColumn;
    private String timeDurationColumn;
    private String totalSizeColumn;
    private String totalTimeColumn;

    @Override
    public void define() {
        // Depending on how define() works in CDAP Wrangler. Placeholder.
    }

    @Override
    public void initialize(ExecutorContext context) {
        byteSizeColumn = context.getArgument("byteSizeColumn");
        timeDurationColumn = context.getArgument("timeDurationColumn");
        totalSizeColumn = context.getArgument("totalSizeColumn");
        totalTimeColumn = context.getArgument("totalTimeColumn");
    }

    @Override
    public void execute(ExecutorContext context) {
        long totalSize = 0;
        long totalTime = 0;

        for (Row row : context.getInput()) {
            ByteSize byteSize = (ByteSize) row.getValue(byteSizeColumn);
            TimeDuration timeDuration = (TimeDuration) row.getValue(timeDurationColumn);

            totalSize += byteSize.getBytes();
            totalTime += timeDuration.getNanos();
        }

        double totalSizeInMB = totalSize / (1024.0 * 1024);
        double totalTimeInSec = totalTime / 1_000_000_000.0;

        Row outputRow = Row.of(totalSizeColumn, totalSizeInMB, totalTimeColumn, totalTimeInSec);
        context.getOutput().add(outputRow);
    }
}
