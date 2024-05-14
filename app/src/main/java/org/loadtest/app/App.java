/*
 * This is the main class that launches the loadtest
 */
package org.loadtest.app;

import org.loadtest.api.Loadtest;
import org.loadtest.api.LoadtestConfig;
import org.loadtest.metrics.LoadtesMetrics;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

/**
 * Main class that launches the load test and reports results to the console
 */
public class App {
    final static int MAX_QPS = 1000;

    public static void main(String[] args) {
        final LoadtestConfig loadtestConfig = loadtestConfigFromArgs(args);
        final Loadtest loadtest = new Loadtest();
        final ScheduledFuture<LoadtesMetrics> futureMetrics = loadtest.run(loadtestConfig, new HttpRequestGenerator());
        LoadtesMetrics metrics;
        try {
            metrics = futureMetrics.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Loadtest has been failed with error message " + e.getMessage());
            return;
        }
        System.out.printf("The load test has completed successfully! Here are the results:\n" +
                "Total requests: %d\n" +
                "Error rate: %.2f\n" +
                "Average latency: %.2f\n" +
                "P50 latency: %.2f\n" +
                "P90 latency: %.2f\n", metrics.getTotalRequests(), metrics.getErrorPercentage(),
                metrics.getAverageLatency(), metrics.getLatencyPercentile(50), metrics.getLatencyPercentile(90));
    }

    public static LoadtestConfig loadtestConfigFromArgs(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: ./gradlew :app:run --args=\"<url> <method> <qps> <duration>\"");
            System.exit(1);
        }

        final URL url = validateURL(args[0]);
        final String method = args[1].toUpperCase();
        validateMethod(method);
        // for safety reasons, we don't want too high QPS, and non-positive numbers
        final int qps = Math.max(1, Math.min(Integer.parseInt(args[2]), MAX_QPS));
        final int duration = Integer.parseInt(args[3]);
        return new LoadtestConfig(url, qps, duration, method);
    }

    private static URL validateURL(final String urlString) {
        try {
            final URL url = new URL(urlString);
            url.toURI();
            return url;
        } catch (Exception e) {
            System.out.printf("Invalid URL: %s, error message: %s%n", urlString, e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static void validateMethod(final String method) {
        assert method.equals("GET") || method.equals("POST");
    }
}
