package com.witcher.bestiary;

import com.witcher.bestiary.dao.MonsterDAO;
import com.witcher.bestiary.models.Monster;
import com.witcher.bestiary.resources.MonsterResource;
import com.witcher.bestiary.service.MonsterService;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;

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
        JdbiFactory jdbiFactory = new JdbiFactory();
        var factory = jdbiFactory.build(environment, configuration.getDatabase(), "postgresql");
        var daoMonster = factory.onDemand(MonsterDAO.class);
        final MonsterService service = new MonsterService(daoMonster);
        final MonsterResource monsterResource = new MonsterResource(service);
        environment.jersey().register(monsterResource);
    }
}