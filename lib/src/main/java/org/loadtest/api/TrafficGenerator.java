package org.loadtest.api;

import org.loadtest.http.HttpClient;
import org.loadtest.http.HttpRequest;
import org.loadtest.http.HttpResponse;
import org.loadtest.metrics.LoadtesMetrics;
import org.loadtest.metrics.Metrics;

import java.util.concurrent.*;

/**
 * This class is responsible for sending requests to the endpoint at fixed qps and
 * report the results back to metrics collector.
 */
class TrafficGenerator {
    final static int RUNTIME_ERROR_CODE = 500;
    final static int NANOS_PER_SEC = 1_000_000_000;
    final static int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private final ScheduledExecutorService scheduler;

    public TrafficGenerator() {
        this.scheduler = Executors.newScheduledThreadPool(NUM_THREADS);
    }

    public ScheduledFuture<LoadtesMetrics> start(final HttpRequest request, final int qps, final int duration) {
        final long interval = NANOS_PER_SEC / qps; // Nano seconds per request
        final Runnable task = () -> {
            try {
                final HttpResponse response = HttpClient.sendRequest(request);
                recordMetrics(response);
            } catch (Exception e) {
                System.err.println("Error during request: " + e.getMessage());
                recordMetrics(new HttpResponse(RUNTIME_ERROR_CODE, 0.0, e.getMessage()));
            }
        };
        final ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.NANOSECONDS);
        return scheduler.schedule(this::stop, duration, TimeUnit.SECONDS);
    }

    public LoadtesMetrics stop() {
        this.scheduler.shutdownNow();
        // Metrics.getInstance().printReport();
        return Metrics.getInstance().getLoadtestMetrics();
    }

    private void recordMetrics(final HttpResponse response) {
        // System.out.printf("recording response code: %d, latency: %.2f\n", response.getResponseCode(), response.getLatencyMillis());
        Metrics.getInstance().record(response);
    }
}