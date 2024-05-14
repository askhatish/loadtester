package org.loadtest.metrics;

import org.loadtest.http.HttpResponse;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public class Aggregator {
    private final AtomicInteger totalRequests;
    private final AtomicInteger errorCount;
    private final DoubleAdder totalLatency;

    private static volatile Aggregator instance;

    private Aggregator() {
        totalLatency = new DoubleAdder();
        errorCount = new AtomicInteger();
        totalRequests = new AtomicInteger();
    }

    public static Aggregator getInstance() {
        if (instance == null) {
            synchronized (Aggregator.class) {
                if (instance == null) {
                    instance = new Aggregator();
                }
            }
        }
        return instance;
    }

    public synchronized void record(HttpResponse response) {
        totalRequests.incrementAndGet();
        totalLatency.add(response.getLatencyMillis());
        if (response.getResponseCode() >= 400) {
            errorCount.incrementAndGet();
        }
    }

    public synchronized void printReport() {
        double errorRate = (double) errorCount.get() / totalRequests.get() * 100;
        double avgLatency = totalLatency.sum() / totalRequests.get();
        System.out.println("Total Requests: " + totalRequests.get());
        System.out.println("Error Rate: " + String.format("%.2f%%", errorRate));
        System.out.println("Average Latency: " + String.format("%.2f ms", avgLatency));
    }
}
