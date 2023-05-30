package com.testcontainers.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@JooqTest
@TestPropertySource(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:15.3-alpine:///db"
        })
@Sql("/test-data.sql")
@Testcontainers
class UserRepositoryJooqTest {

    /*
    // If you are using Spring Boot version 3.1.0+
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine");
    */

    /*
    // If you are using Spring Boot version < 3.1.0
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    */

    @Autowired
    DSLContext dsl;

    UserRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new UserRepository(dsl);
    }

    @Test
    void shouldGetUserByEmail() {
        User user = repository.getUserByEmail("siva@gmail.com").orElseThrow();

        assertThat(user.id()).isEqualTo(1L);
        assertThat(user.name()).isEqualTo("Siva");
        assertThat(user.email()).isEqualTo("siva@gmail.com");
    }
}
