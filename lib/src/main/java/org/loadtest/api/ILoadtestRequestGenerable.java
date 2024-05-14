package org.loadtest.api;

import org.loadtest.http.HttpRequest;

/**
 * An interface that describes the class responsible to generate an endpoint compatible request
 */
public interface ILoadtestRequestGenerable {
    public HttpRequest generate(LoadtestConfig config);
}
