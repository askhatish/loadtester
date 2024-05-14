package org.loadtest.api;

import java.net.URL;

/**
 * This is the class contains settings for the load test. All the tweaks and params should go here
 */
public class LoadtestConfig {

    private URL url;
    private int qps;
    private int duration;
    private String method;

    // Constructor to initialize with all fields
    public LoadtestConfig(final URL url,
                          final int qps,
                          final int duration,
                          final String method
    ) {
        this.url = url;
        this.qps = qps;
        this.duration = duration;
        this.method = method;
    }

    // Getter for URL
    public URL getUrl() {
        return url;
    }

    // Setter for URL
    public void setUrl(URL url) {
        this.url = url;
    }

    // Getter for QPS
    public int getQps() {
        return qps;
    }

    // Setter for QPS
    public void setQps(int qps) {
        this.qps = qps;
    }

    // Getter for Duration
    public int getDuration() {
        return duration;
    }

    // Setter for Duration
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Getter for HTTP Method
    public String getMethod() {
        return method;
    }

    // Setter for HTTP Method
    public void setMethod(String method) {
        this.method = method;
    }

    // Optional toString method for debugging or logging
    @Override
    public String toString() {
        return "LoadTestConfig{" +
                "url=" + url +
                ", qps=" + qps +
                ", duration=" + duration +
                ", method='" + method + '\'' +
                '}';
    }
}
