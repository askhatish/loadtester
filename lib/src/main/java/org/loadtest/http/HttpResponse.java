package org.loadtest.http;

/*
 * This class wraps the response with its request context details, such as latency, response code.
 * Also, the response body is just a string due to it's lack of usage.
 */
public class HttpResponse {

    private final int responseCode;
    private final double latencyMillis;
    private final String responseBody;

    public HttpResponse(final int responseCode,
                        final double latencyMillis,
                        final String responseBody) {
        this.responseCode = responseCode;
        this.latencyMillis = latencyMillis;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public double getLatencyMillis() {
        return latencyMillis;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
