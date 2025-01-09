package com.witcher.bestiary;

import com.witcher.bestiary.dao.MonsterDAO;
import com.witcher.bestiary.resources.MonsterResource;
import com.witcher.bestiary.service.MonsterService;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.database.jvm.JdbcConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class BestiaryApplication extends Application<BestiaryConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BestiaryApplication.class);

    public static void main(String[] args) throws Exception {
        new BestiaryApplication().run(args);
    }

    @Override
    public String getName() {
        return "witcher-bestiary";
    }

    @Override
    public void initialize(Bootstrap<BestiaryConfiguration> bootstrap) {
        // Optional: Add additional initialization here if needed
    }

    @Override
    public void run(BestiaryConfiguration configuration, Environment environment) {
        LOGGER.info("Starting application");

        // Run Liquibase migrations
        // move to bundle or some other means.
        try {
            DataSource dataSource = configuration.getDatabase().build(environment.metrics(), "liquibase-database");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(dataSource.getConnection())
            );
            Liquibase liquibase = new Liquibase(
                    "migrations/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts());
        } catch (Exception e) {
            LOGGER.error("Error running Liquibase migrations", e);
            throw new RuntimeException(e);
        }

        LOGGER.info("Liquibase migrations completed successfully");

        // Register resources
        JdbiFactory jdbiFactory = new JdbiFactory();
        var factory = jdbiFactory.build(environment, configuration.getDatabase(), "postgresql");
        var daoMonster = factory.onDemand(MonsterDAO.class);
        final MonsterService service = new MonsterService(daoMonster);
        final MonsterResource monsterResource = new MonsterResource(service);
        environment.jersey().register(monsterResource);

        LOGGER.info("Bestiary Application started successfully!");
    }
}
