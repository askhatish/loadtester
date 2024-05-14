package org.loadtest.generator;

import org.loadtest.http.HttpClient;
import org.loadtest.http.HttpRequest;
import org.loadtest.http.HttpResponse;
import org.loadtest.metrics.Aggregator;

import java.util.concurrent.*;


public class TrafficGenerator {
    final static int RUNTIME_ERROR_CODE = 500;
    final static int NANOS_PER_SEC = 1_000_000_000;

    private final ScheduledExecutorService scheduler;

    public TrafficGenerator(final int numThreads) {
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    public void start(final HttpRequest request, final int qps, final int duration) {
        final long interval = NANOS_PER_SEC / qps; // Nano seconds per request
        final Runnable task = () -> {
            try {
                final HttpResponse response = HttpClient.sendRequest(request);
                recordMetrics(response);
            } catch (Exception e) {
                // System.err.println(e.getMessage());
                recordMetrics(new HttpResponse(RUNTIME_ERROR_CODE, 0.0, ""));
            }
        };
        final ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.NANOSECONDS);
        scheduler.schedule(this::stop, duration, TimeUnit.SECONDS);
    }

    public void stop() {
        this.scheduler.shutdownNow();
        Aggregator.getInstance().printReport();
    }

    public void recordMetrics(final HttpResponse response) {
        // System.out.printf("recording response code: %d, latency: %.2f\n", response.getResponseCode(), response.getLatencyMillis());
        Aggregator.getInstance().record(response);
    }
}