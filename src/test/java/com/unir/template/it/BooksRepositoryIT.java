package com.unir.template.it;

import static org.assertj.core.api.Assertions.assertThat;

import com.unir.template.model.Book;
import com.unir.template.repository.BooksRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class BooksRepositoryIT {

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16.8-alpine")
          .withDatabaseName("test_db")
          .withUsername("test_user")
          .withPassword("test_password");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired private BooksRepository booksRepository;

  @BeforeEach
  void setUp() {
    booksRepository.deleteAll();
  }

  @Test
  void givenBook_whenSave_thenBookIsSaved() {
    // Given
    Book book =
        Book.builder()
            .name("Test Book")
            .description("Test Description")
            .price(29.99)
            .stock(10)
            .build();

    // When
    Book savedBook = booksRepository.save(book);

    // Then
    assertThat(savedBook).isNotNull();
    assertThat(savedBook.getId()).isNotNull();
    assertThat(savedBook.getName()).isEqualTo("Test Book");
    assertThat(savedBook.getDescription()).isEqualTo("Test Description");
    assertThat(savedBook.getPrice()).isEqualTo(29.99);
    assertThat(savedBook.getStock()).isEqualTo(10);
  }

  @Test
  void givenSavedBook_whenFindById_thenBookIsFound() {
    // Given
    Book book =
        Book.builder()
            .name("Test Book")
            .description("Test Description")
            .price(29.99)
            .stock(10)
            .build();
    Book savedBook = booksRepository.save(book);

    // When
    Optional<Book> foundBook = booksRepository.findById(savedBook.getId());

    // Then
    assertThat(foundBook).isPresent();
    assertThat(foundBook.get().getName()).isEqualTo("Test Book");
  }

  @Test
  void givenMultipleBooks_whenFindAll_thenAllBooksAreReturned() {
    // Given
    Book book1 =
        Book.builder().name("Book 1").description("Description 1").price(19.99).stock(5).build();

    Book book2 =
        Book.builder().name("Book 2").description("Description 2").price(39.99).stock(15).build();

    booksRepository.save(book1);
    booksRepository.save(book2);

    // When
    List<Book> allBooks = booksRepository.findAll();

    // Then
    assertThat(allBooks).hasSize(2);
    assertThat(allBooks).extracting("name").containsExactlyInAnyOrder("Book 1", "Book 2");
  }

  @Test
  void givenSavedBook_whenUpdate_thenBookIsUpdated() {
    // Given
    Book book =
        Book.builder()
            .name("Original Name")
            .description("Original Description")
            .price(25.00)
            .stock(5)
            .build();
    Book savedBook = booksRepository.save(book);

    // When
    savedBook.setName("Updated Name");
    savedBook.setPrice(35.00);
    Book updatedBook = booksRepository.save(savedBook);

    // Then
    assertThat(updatedBook.getName()).isEqualTo("Updated Name");
    assertThat(updatedBook.getPrice()).isEqualTo(35.00);
    assertThat(updatedBook.getId()).isEqualTo(savedBook.getId());
  }

  @Test
  void givenSavedBook_whenDeleteById_thenBookIsDeleted() {
    // Given
    Book book =
        Book.builder()
            .name("Book to Delete")
            .description("Description")
            .price(20.00)
            .stock(3)
            .build();
    Book savedBook = booksRepository.save(book);

    // When
    booksRepository.deleteById(savedBook.getId());

    // Then
    Optional<Book> deletedBook = booksRepository.findById(savedBook.getId());
    assertThat(deletedBook).isEmpty();
  }

  @Test
  void givenNonExistentBookId_whenFindById_thenEmptyIsReturned() {
    // When
    Optional<Book> notFoundBook = booksRepository.findById(UUID.randomUUID());

    // Then
    assertThat(notFoundBook).isEmpty();
  }

  @Test
  void givenMultipleBooks_whenCount_thenCorrectCountIsReturned() {
    // Given
    Book book1 =
        Book.builder().name("Book 1").description("Description 1").price(19.99).stock(5).build();

    Book book2 =
        Book.builder().name("Book 2").description("Description 2").price(39.99).stock(15).build();

    booksRepository.save(book1);
    booksRepository.save(book2);

    // When
    long count = booksRepository.count();

    // Then
    assertThat(count).isEqualTo(2);
  }

  @Test
  void givenSavedBook_whenExistsById_thenReturnsTrue() {
    // Given
    Book book =
        Book.builder()
            .name("Test Book")
            .description("Test Description")
            .price(29.99)
            .stock(10)
            .build();
    Book savedBook = booksRepository.save(book);

    // When & Then
    assertThat(booksRepository.existsById(savedBook.getId())).isTrue();
    assertThat(booksRepository.existsById(UUID.randomUUID())).isFalse();
  }
}
