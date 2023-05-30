package com.testcontainers.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Sql("/test-data.sql")
@Testcontainers
class PostRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine");

    @Autowired
    PostRepository repository;

    @Test
    void shouldGetPostById() {
        Post post = repository.getPostById(1L).orElseThrow();

        assertThat(post.id()).isEqualTo(1L);
        assertThat(post.title()).isEqualTo("Post 1 Title");
        assertThat(post.content()).isEqualTo("Post 1 content");
        assertThat(post.createdBy().id()).isEqualTo(1L);
        assertThat(post.createdBy().name()).isEqualTo("Siva");
        assertThat(post.createdBy().email()).isEqualTo("siva@gmail.com");
        assertThat(post.comments()).hasSize(2);
    }
}
