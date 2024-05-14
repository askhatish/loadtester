package org.loadtest.metrics;

import java.util.ArrayList;
import java.util.List;

public class LoadtesMetrics {
    private final long totalRequests;
    private final long totalErrors;
    private List<Double> sortedLatencies = new ArrayList<>();
    private final double averageLatency;
    public LoadtesMetrics(long totalRequests, long totalErrors, List<Double> sortedLatencies) {
        this.totalRequests = totalRequests;
        this.totalErrors = totalErrors;
        this.sortedLatencies = sortedLatencies;
        this.averageLatency = sortedLatencies.stream().mapToDouble(Double::doubleValue).sum() / totalRequests;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public double getErrorPercentage() {
        return (double) totalErrors / totalRequests * 100;
    }

    public double getAverageLatency() {
        return averageLatency;
    }

    public List<Double> getSortedLatencies() {
        return sortedLatencies;
    }

    public Double getLatencyPercentile(final int percentile) {
        assert percentile >= 1 && percentile <= 100;
        final int index = percentileIndex(percentile);
        return sortedLatencies.get(index);
    }

    private int percentileIndex(final int percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * sortedLatencies.size());
        return index - 1;
    }
}
