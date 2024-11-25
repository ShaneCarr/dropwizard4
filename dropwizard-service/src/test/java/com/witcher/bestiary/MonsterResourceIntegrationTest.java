package com.witcher.bestiary;

import com.witcher.bestiary.models.Monster;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.DefaultConfigurationFactoryFactory;
import io.dropwizard.jackson.Jackson;
import java.io.*;
import java.util.Arrays;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterResourceIntegrationTest {

    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14");

    public static DropwizardAppExtension<BestiaryConfiguration> APP
            = new DropwizardAppExtension<>(
            BestiaryApplication.class,
            ResourceHelpers.resourceFilePath("bestiary-test.yml")
    );

    private String baseUrl;

    private void testStart() throws ConfigurationException, IOException {
        try {
            postgresContainer.start();
            System.out.println("Postgres started");
            File configFile = new File(ResourceHelpers.resourceFilePath("bestiary-test.yml"));
            var mapper = Jackson.newObjectMapper();
            ConfigurationFactory<BestiaryConfiguration> factory
                    = new DefaultConfigurationFactoryFactory<BestiaryConfiguration>()
                    .create(BestiaryConfiguration.class, null, mapper, "dw");

            BestiaryConfiguration config = factory.build(configFile);
            config.getDatabase().setUrl(postgresContainer.getJdbcUrl());
            config.getDatabase().setUser(postgresContainer.getUsername());
            config.getDatabase().setPassword(postgresContainer.getPassword());
            System.out.println("Modified configuration: " + mapper.writeValueAsString(config));

            APP = new DropwizardAppExtension<>(BestiaryApplication.class, mapper.writeValueAsString(config));

            baseUrl = String.format("http://localhost:%d/monsters", APP.getLocalPort());
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
            Assertions.fail("Failed to set up the test environment: " + e.getMessage() + "trace " + Arrays.toString(e.getStackTrace()));
        }

    }

    @BeforeAll
    public void setup() throws ConfigurationException, IOException {
        testStart();
        // Read test configuration
        File configFile = new File(ResourceHelpers.resourceFilePath("bestiary-test.yml"));
        ObjectMapper mapper = Jackson.newObjectMapper();
        ConfigurationFactory<BestiaryConfiguration> factory =
                new DefaultConfigurationFactoryFactory<BestiaryConfiguration>()
                        .create(BestiaryConfiguration.class, null, mapper, "dw");
        BestiaryConfiguration testConfig = factory.build(configFile);

        // Initialize PostgreSQLContainer with values from the test config
        postgresContainer = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName(testConfig.getDatabaseName())
                .withUsername(testConfig.getDatabase().getUser())
                .withPassword(testConfig.getDatabase().getPassword());

        postgresContainer.start();

//
       // File configFile = new File(ResourceHelpers.resourceFilePath("bestiary-test.yml"));
//        ObjectMapper mapper = Jackson.newObjectMapper();
//        ConfigurationFactory<BestiaryConfiguration> factory =
//                new DefaultConfigurationFactoryFactory<BestiaryConfiguration>().create(BestiaryConfiguration.class, null, mapper, "dw");
//        BestiaryConfiguration config = factory.build(configFile);
//
//        System.out.println("Parsed Database Config: " + config.getDatabase().);
//
//        System.out.println("Database Config: " + APP.getConfiguration().getDatabase());
        // Override test configuration with Testcontainers' dynamic URL
//        String jdbcUrl = postgresContainer.getJdbcUrl();
//        APP.getConfiguration().getDatabase().setUrl(jdbcUrl);
//        APP.getConfiguration().getDatabase().setUser(postgresContainer.getUsername());
//        APP.getConfiguration().getDatabase().setPassword(postgresContainer.getPassword());

        postgresContainer = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName(testConfig.getDatabaseName())
                .withUsername(testConfig.getDatabase().getUser())
                .withPassword(testConfig.getDatabase().getPassword());
        postgresContainer.start();
        testConfig.getDatabase().setUrl(postgresContainer.getJdbcUrl());

//        APP = new DropwizardAppExtension<>(
//                BestiaryApplication.class,
//        baseUrl = String.format("http://localhost:%d/monsters", APP.getLocalPort()));
    }

    @AfterAll
    public void cleanup() {
        postgresContainer.stop();
    }

    @Test
    @Order(1)
    public void testAddMonster() {
        Monster newMonster = new Monster(0, "Griffin", "Beast", "Mountains", "Silver", "Strength", "Fierce creature", null, 5, "Lore about griffin");

        Response response = APP.client().target(baseUrl)
                .request()
                .post(Entity.json(newMonster));

        assertThat(response.getStatus()).isEqualTo(201); // HTTP Created
    }

    @Test
    @Order(2)
    public void testGetAllMonsters() {
        Response response = APP.client().target(baseUrl)
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200); // HTTP OK
        String responseBody = response.readEntity(String.class);
        assertThat(responseBody).contains("Griffin"); // Check that the monster is returned
    }

    @Test
    @Order(3)
    public void testUpdateMonster() {
        Monster updatedMonster = new Monster(1, "Griffin", "Beast", "Hills", "Fire", "Speed", "Updated description", null, 6, "Updated lore");

        Response response = APP.client().target(baseUrl + "/1")
                .request()
                .put(Entity.json(updatedMonster));

        assertThat(response.getStatus()).isEqualTo(200); // HTTP OK
    }

    @Test
    @Order(4)
    public void testDeleteMonster() {
        Response response = APP.client().target(baseUrl + "/1")
                .request()
                .delete();

        assertThat(response.getStatus()).isEqualTo(204); // HTTP No Content
    }
}