package org.loadtest.metrics;

import org.loadtest.http.HttpResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
    final static int MAX_CAPACITY = 500000;
    private final AtomicLong totalRequests;
    private final AtomicLong errorCount;
    private final LinkedBlockingDeque<Double> latencyQueue;

    private static volatile Metrics instance;

    private Metrics() {
        latencyQueue = new LinkedBlockingDeque<Double>(MAX_CAPACITY);
        errorCount = new AtomicLong();
        totalRequests = new AtomicLong();
    }

    public static Metrics getInstance() {
        if (instance == null) {
            synchronized (Metrics.class) {
                if (instance == null) {
                    instance = new Metrics();
                }
            }
        }
        return instance;
    }

    public synchronized void record(HttpResponse response) {
        totalRequests.incrementAndGet();
        latencyQueue.add(response.getLatencyMillis());
        if (latencyQueue.size() >= MAX_CAPACITY) {
            latencyQueue.poll();
        }
        if (response.getResponseCode() >= 400) {
            errorCount.incrementAndGet();
        }
    }

    public synchronized LoadtesMetrics getLoadtestMetrics() {
        System.out.println("Get loadtest metrics, size: " + latencyQueue.size() + " requests: " + totalRequests.get() + " errors: " + errorCount.get());
        List<Double> latencies = new ArrayList<Double>();
        latencyQueue.drainTo(latencies);
        Collections.sort(latencies);
        return new LoadtesMetrics(totalRequests.get(), errorCount.get(), latencies);
    }

    public synchronized void printReport() {
        LoadtesMetrics metrics = getLoadtestMetrics();
        System.out.println("Total Requests: " + metrics.getTotalRequests());
        System.out.println("Error Rate: " + String.format("%.2f%%", metrics.getErrorPercentage()));
        System.out.println("Average Latency: " + String.format("%.2f ms", metrics.getAverageLatency()));
    }
}
