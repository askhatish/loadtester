package org.loadtest.http;

import java.util.HashMap;
import java.util.Map;

/*
 * This class represents a http request object
 */
public class HttpRequest {
    private String method;
    private String url;
    private Map<String, String> headers;
    private String payload;
    private int connectionTimeout;

    public HttpRequest(final String method, final String url) {
        this.method = method;
        this.url = url;
        headers = new HashMap<>();
    }

    // Getters and Setters

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        if (headers != null) {
            this.headers = headers;
        }
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(final int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}