package com.witcher.bestiary;

import com.witcher.bestiary.BestiaryApplication;
import com.witcher.bestiary.BestiaryConfiguration;
import com.witcher.bestiary.resources.MonsterResource;
import com.witcher.bestiary.service.MonsterService;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.mockito.Mockito.mock;
//import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;



@ExtendWith(DropwizardExtensionsSupport.class)
public class MonsterResourceTest {

    // Initializing PostgreSQL container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new MonsterResource(mock(MonsterService.class)))
            .build();

    @RegisterExtension
    public static final DropwizardAppExtension<BestiaryConfiguration> EXT =
            new DropwizardAppExtension<>(BestiaryApplication.class, "config.yml");

    @BeforeAll
    public static void startContainer() {
        postgresContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        postgresContainer.stop();
    }

    @Test
    public void testGetActivityItems() {
        String response = RESOURCES.target("/activity")
                .request()
                .get(String.class);
        Assertions.assertThat(response).contains("Item 1", "Item 2");
    }
}