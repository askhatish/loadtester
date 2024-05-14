package org.loadtest.api;

import org.loadtest.http.HttpRequest;
import org.loadtest.metrics.LoadtesMetrics;

import java.util.concurrent.ScheduledFuture;

/**
 * This is the API entry point to initiate the load test process
 */
public class Loadtest {
    public ScheduledFuture<LoadtesMetrics> run(final LoadtestConfig config,
                                               final ILoadtestRequestGenerable requestGenerable) {
        final HttpRequest request = requestGenerable.generate(config);
        final TrafficGenerator trafficGenerator = new TrafficGenerator();
        System.out.println("Starting load test with config: " + config);
        return trafficGenerator.start(request, config.getQps(), config.getDuration());
    }
}
