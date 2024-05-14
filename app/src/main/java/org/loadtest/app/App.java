/*
 * This is the main class that launches the loadtest
 */
package org.loadtest.app;

import org.loadtest.generator.TrafficGenerator;
import org.loadtest.http.HttpRequest;

import java.net.URL;

public class App {
    final static int MAX_QPS = 100;
    final static int MAX_THREADS_POOL = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: ./gradlew :app:run --args=\"<url> <qps> <duration> <num_threads>\"");
            System.exit(1);
        }

        final String url = args[0];
        validateURL(url);
        // for safety reasons, we don't want too high QPS, and non-positive numbers
        final int qps = Math.max(1, Math.min(Integer.parseInt(args[1]), MAX_QPS));
        final int duration = Integer.parseInt(args[2]);
        // for technical reasons, we have to limit number of threads in the pool,
        // and we don't want non-positive numbers
        final int numThreads = Math.max(1, Math.min(Integer.parseInt(args[3]), MAX_THREADS_POOL));

        final HttpRequest req = new HttpRequest("GET", url);

        final TrafficGenerator generator = new TrafficGenerator(numThreads);
        generator.start(req, qps, duration);
    }

    private static void validateURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
        } catch (Exception e) {
            System.out.printf("Invalid URL: %s, error message: %s%n", urlString, e.getMessage());
            System.exit(1);
        }
    }
}
