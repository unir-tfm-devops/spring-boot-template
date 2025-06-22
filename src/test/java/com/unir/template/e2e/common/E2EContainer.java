package com.unir.template.e2e.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class E2EContainer {

  public static final String POSTGRESQL_VERSION = "postgres:16.8-alpine";

  protected static PostgreSQLContainer<?> postgreSQLContainer;

  static {
    postgreSQLContainer =
        new PostgreSQLContainer<>(POSTGRESQL_VERSION)
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_password")
            .withReuse(true);
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }
}
