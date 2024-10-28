package com.witcher.bestiary;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BestiaryApplication extends Application<BestiaryConfiguration> {

    public static void main(String[] args) throws Exception {
        new BestiaryApplication().run(args);
    }

    @Override
    public String getName() {
        return "witcher-bestiary";
    }

    @Override
    public void initialize(Bootstrap<BestiaryConfiguration> bootstrap) {
        // Any initialization logic goes here
    }

    @Override
    public void run(BestiaryConfiguration configuration, Environment environment) {
        // Register resources, health checks, etc.
    }
}