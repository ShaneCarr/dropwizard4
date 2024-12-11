package com.witcher.bestiary;

import com.witcher.bestiary.models.Monster;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import java.io.*;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BestiaryIntegrationTest {

    private static final PostgreSQLContainer<?> postgresContainer;
    private static final DropwizardAppExtension<BestiaryConfiguration> APP;
    private static final String base_url;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:14");
        postgresContainer.start();
        APP = new DropwizardAppExtension<>(
                BestiaryApplication.class,
                ResourceHelpers.resourceFilePath("bestiary-test.yml"), // Base configuration file
                ConfigOverride.config("database.url", postgresContainer.getJdbcUrl()),
                ConfigOverride.config("database.user", postgresContainer.getUsername()),
                ConfigOverride.config("database.password", postgresContainer.getPassword())
        );

        try {
            APP.before();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        base_url = String.format("http://localhost:%d/Groups", APP.getLocalPort());
    }

    @BeforeAll
    public void setup() throws ConfigurationException, IOException {
        File configFile = new File(ResourceHelpers.resourceFilePath("grouper-test.yml"));
        ObjectMapper mapper = Jackson.newObjectMapper();
    }

    @AfterAll
    public void cleanup() {
        postgresContainer.stop();
    }

    @Test
    @Order(1)
    public void testAddGroup() {
        Monster newGroup = new Monster(0, "Griffin", "Beast", "Mountains", "Silver", "Strength", "Fierce creature", null, 5, "Lore about griffin");

        Response response = APP.client().target(base_url)
                .request()
                .post(Entity.json(newGroup));

        assertThat(response.getStatus()).isEqualTo(201); // HTTP Created
    }

    @Test
    @Order(2)
    public void testGetAllGroups() {
        Response response = APP.client().target(base_url)
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200); // HTTP OK
        String responseBody = response.readEntity(String.class);
        assertThat(responseBody).contains("Griffin"); // Check that the Group is returned
    }

    @Test
    @Order(3)
    public void testUpdateGroup() {
        Monster updatedGroup = new Monster(1, "Griffin", "Beast", "Hills", "Fire", "Speed", "Updated description", null, 6, "Updated lore");

        Response response = APP.client().target(base_url + "/1")
                .request()
                .put(Entity.json(updatedGroup));

        assertThat(response.getStatus()).isEqualTo(200); // HTTP OK
    }

    @Test
    @Order(4)
    public void testDeleteGroup() {
        Response response = APP.client().target(base_url + "/1")
                .request()
                .delete();

        assertThat(response.getStatus()).isEqualTo(204); // HTTP No Content
    }
}