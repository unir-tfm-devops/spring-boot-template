package com.unir.template.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.unir.template.e2e.common.E2EContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BooksE2ETest extends E2EContainer {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    // Set the base URI for Rest Assured
    RestAssured.port = port;
    RestAssured.baseURI = "http://localhost";
  }

  @Test
  void givenValidBookData_whenCreatingBook_thenBookIsCreatedAndCanBeRetrieved() {
    // Given
    String bookJson =
        """
        {
          "name": "The Great Gatsby",
          "description": "A classic American novel",
          "price": 19.99,
          "stock": 10
        }
        """;

    // When & Then - Create book
    String bookId =
        given()
            .contentType(ContentType.JSON)
            .body(bookJson)
            .when()
            .post("/api/books")
            .then()
            .statusCode(200)
            .body("name", equalTo("The Great Gatsby"))
            .body("description", equalTo("A classic American novel"))
            .body("price", equalTo(19.99f))
            .body("stock", equalTo(10))
            .body("id", notNullValue())
            .extract()
            .path("id");

    // When & Then - Retrieve the created book
    given()
        .when()
        .get("/api/books/" + bookId)
        .then()
        .statusCode(200)
        .body("name", equalTo("The Great Gatsby"))
        .body("description", equalTo("A classic American novel"))
        .body("price", equalTo(19.99f))
        .body("stock", equalTo(10));
  }

  @Test
  void givenNonExistentBookId_whenRetrievingBook_thenReturns404() {
    // Given
    UUID nonExistentId = UUID.randomUUID();

    // When & Then
    given().when().get("/api/books/" + nonExistentId).then().statusCode(404);
  }

  @Test
  void givenMultipleBooks_whenGettingAllBooks_thenReturnsAllBooks() {
    // Given - Create multiple books
    String book1Json =
        """
        {
          "name": "Book 1",
          "description": "First book",
          "price": 15.99,
          "stock": 5
        }
        """;

    String book2Json =
        """
        {
          "name": "Book 2",
          "description": "Second book",
          "price": 25.99,
          "stock": 8
        }
        """;

    // Create first book
    given()
        .contentType(ContentType.JSON)
        .body(book1Json)
        .when()
        .post("/api/books")
        .then()
        .statusCode(200);

    // Create second book
    given()
        .contentType(ContentType.JSON)
        .body(book2Json)
        .when()
        .post("/api/books")
        .then()
        .statusCode(200);

    // When & Then - Get all books
    given()
        .when()
        .get("/api/books")
        .then()
        .statusCode(200)
        .body("size()", greaterThanOrEqualTo(2))
        .body("name", hasItems("Book 1", "Book 2"));
  }

  @Test
  void givenExistingBook_whenUpdatingBook_thenBookIsUpdated() {
    // Given - Create a book first
    String createBookJson =
        """
        {
          "name": "Original Name",
          "description": "Original description",
          "price": 10.99,
          "stock": 3
        }
        """;

    String bookId =
        given()
            .contentType(ContentType.JSON)
            .body(createBookJson)
            .when()
            .post("/api/books")
            .then()
            .statusCode(200)
            .extract()
            .path("id");

    // Update book
    String updateBookJson =
        """
        {
          "name": "Updated Name",
          "description": "Updated description",
          "price": 20.99,
          "stock": 7
        }
        """;

    // When & Then - Update the book
    given()
        .contentType(ContentType.JSON)
        .body(updateBookJson)
        .when()
        .put("/api/books/" + bookId)
        .then()
        .statusCode(200)
        .body("name", equalTo("Updated Name"))
        .body("description", equalTo("Updated description"))
        .body("price", equalTo(20.99f))
        .body("stock", equalTo(7));

    // Verify the update by retrieving the book
    given()
        .when()
        .get("/api/books/" + bookId)
        .then()
        .statusCode(200)
        .body("name", equalTo("Updated Name"))
        .body("description", equalTo("Updated description"));
  }

  @Test
  void givenExistingBook_whenDeletingBook_thenBookIsDeleted() {
    // Given - Create a book first
    String bookJson =
        """
        {
          "name": "Book to Delete",
          "description": "This book will be deleted",
          "price": 12.99,
          "stock": 2
        }
        """;

    String bookId =
        given()
            .contentType(ContentType.JSON)
            .body(bookJson)
            .when()
            .post("/api/books")
            .then()
            .statusCode(200)
            .extract()
            .path("id");

    // When & Then - Delete the book
    given()
        .when()
        .delete("/api/books/" + bookId)
        .then()
        .statusCode(200)
        .body("name", equalTo("Book to Delete"));

    // Verify the book is deleted
    given().when().get("/api/books/" + bookId).then().statusCode(404);
  }

  @Test
  void givenNonExistentBookId_whenUpdatingBook_thenReturns404() {
    // Given
    UUID nonExistentId = UUID.randomUUID();
    String updateBookJson =
        """
        {
          "name": "Updated Name",
          "description": "Updated description",
          "price": 20.99,
          "stock": 7
        }
        """;

    // When & Then
    given()
        .contentType(ContentType.JSON)
        .body(updateBookJson)
        .when()
        .put("/api/books/" + nonExistentId)
        .then()
        .statusCode(404);
  }

  @Test
  void givenNonExistentBookId_whenDeletingBook_thenReturns404() {
    // Given
    UUID nonExistentId = UUID.randomUUID();

    // When & Then
    given().when().delete("/api/books/" + nonExistentId).then().statusCode(404);
  }
}
