package com.unir.template.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.unir.template.model.Book;
import com.unir.template.service.BooksService;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class BooksControllerTest {

  private BooksService booksService;
  private BooksController booksController;

  @BeforeEach
  void setUp() {
    booksService = mock(BooksService.class);
    booksController = new BooksController(booksService);
  }

  @Test
  void givenValidId_whenGetBookById_thenReturnOkResponse() {
    Book book = Instancio.create(Book.class);
    UUID id = book.getId();
    when(booksService.getBookById(id)).thenReturn(book);

    ResponseEntity<Book> response = booksController.getBookById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(book, response.getBody());
  }

  @Test
  void givenInvalidId_whenGetBookById_thenReturnNotFound() {
    UUID id = UUID.randomUUID();
    when(booksService.getBookById(id)).thenThrow(new RuntimeException("Not found"));

    ResponseEntity<Book> response = booksController.getBookById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }

  @SuppressWarnings("null")
  @Test
  void whenGetAllBooks_thenReturnBooksList() {
    List<Book> books = Instancio.ofList(Book.class).size(2).create();
    when(booksService.getAllBooks()).thenReturn(books);

    ResponseEntity<List<Book>> response = booksController.getAllBooks();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals(books, response.getBody());
  }

  @Test
  void whenCreateBook_thenReturnCreatedBook() {
    Book book = Instancio.create(Book.class);
    when(booksService.createBook(any(Book.class))).thenReturn(book);

    Book result = booksController.createBook(book);

    assertEquals(book, result);
  }

  @Test
  void givenValidId_whenUpdateBook_thenReturnUpdatedBook() {
    Book book = Instancio.create(Book.class);
    UUID id = book.getId();
    when(booksService.updateBook(eq(id), any(Book.class))).thenReturn(book);

    ResponseEntity<Book> response = booksController.updateBook(id, book);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(book, response.getBody());
  }

  @Test
  void givenInvalidId_whenUpdateBook_thenReturnNotFound() {
    UUID id = UUID.randomUUID();
    Book book = Instancio.create(Book.class);
    when(booksService.updateBook(eq(id), any(Book.class)))
        .thenThrow(new RuntimeException("Not found"));

    ResponseEntity<Book> response = booksController.updateBook(id, book);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void givenValidId_whenDeleteBook_thenReturnDeletedBook() {
    Book book = Instancio.create(Book.class);
    UUID id = book.getId();
    when(booksService.deleteBook(id)).thenReturn(book);

    ResponseEntity<Book> response = booksController.deleteBook(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(book, response.getBody());
  }

  @Test
  void givenInvalidId_whenDeleteBook_thenReturnNotFound() {
    UUID id = UUID.randomUUID();
    when(booksService.deleteBook(id)).thenThrow(new RuntimeException("Not found"));

    ResponseEntity<Book> response = booksController.deleteBook(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }
}
