package org.loadtest.api;

import org.loadtest.http.HttpRequest;

/**
 * This is the API entry point to initiate the load test process
 */
public class Loadtest {
    public void run(LoadtestConfig config, ILoadtestRequestGenerable requestGenerable) {
        HttpRequest request = requestGenerable.generate(config);
        TrafficGenerator trafficGenerator = new TrafficGenerator();
        trafficGenerator.start(request, config.getQps(), config.getDuration());
    }
}
