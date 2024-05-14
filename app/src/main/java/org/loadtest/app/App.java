/*
 * This is the main class that launches the loadtest
 */
package org.loadtest.app;

import org.loadtest.api.Loadtest;
import org.loadtest.api.LoadtestConfig;

import java.net.URL;

public class App {
    final static int MAX_QPS = 100;

    public static void main(String[] args) {
        LoadtestConfig loadtestConfig = loadtestConfigFromArgs(args);
        Loadtest loadtest = new Loadtest();
        loadtest.run(loadtestConfig, new HttpRequestGenerator());
    }

    private static LoadtestConfig loadtestConfigFromArgs(String[] args) {
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

    private static URL validateURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return url;
        } catch (Exception e) {
            System.out.printf("Invalid URL: %s, error message: %s%n", urlString, e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static void validateMethod(String method) {
        assert method.equals("GET") || method.equals("POST");
    }
}
