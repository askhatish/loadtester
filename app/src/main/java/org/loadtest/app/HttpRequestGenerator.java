package org.loadtest.app;

import org.loadtest.api.ILoadtestRequestGenerable;
import org.loadtest.api.LoadtestConfig;
import org.loadtest.http.HttpRequest;

/**
 * A class that generates http request structure for the load test
 */
public class HttpRequestGenerator implements ILoadtestRequestGenerable {
    final static int SEC_TO_MILLIS = 1000;

    @Override
    public HttpRequest generate(final LoadtestConfig config) {
        final HttpRequest request = new HttpRequest(config.getUrl(), config.getMethod());
        request.setConnectionTimeoutMillis(1 * SEC_TO_MILLIS);
        // set headers and payloads here
        return request;
    }
}
