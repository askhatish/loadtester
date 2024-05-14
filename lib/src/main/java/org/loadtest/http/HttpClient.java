package org.loadtest.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class sends http requests via network and returns a parsed response
 */

public class HttpClient {
    public static final int NANO_TO_MILLIS = 1_000_000;

    public static HttpResponse sendRequest(final HttpRequest request) throws Exception {
        final URL url = new URL(request.getUrl());
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getMethod());
        if (request.getConnectionTimeout() > 0) {
            connection.setConnectTimeout(request.getConnectionTimeout());
        }
        final int responseCode = connection.getResponseCode();
        final long startTime = System.nanoTime();
        final String response = getResponseContent(connection);
        final long endTime = System.nanoTime();
        final long latencyNano = endTime - startTime;
        final double latencyMillis = latencyNano * 1.0 / NANO_TO_MILLIS;

        return new HttpResponse(responseCode, latencyMillis, response);
    }

    private static String getResponseContent(final HttpURLConnection connection) throws Exception {
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder responseContent = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            responseContent.append(line);
        }
        in.close();
        return responseContent.toString();
    }
}
