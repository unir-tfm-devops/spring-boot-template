package com.unir.template.controller;

import com.unir.template.model.Book;
import com.unir.template.service.BooksService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BooksController {

  private final BooksService booksService;

  public BooksController(BooksService booksService) {
    this.booksService = booksService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> getBookById(@PathVariable("id") UUID id) {
    try {
      return ResponseEntity.ok(booksService.getBookById(id));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = booksService.getAllBooks();
    return ResponseEntity.ok(books);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Book createBook(@RequestBody Book book) {
    return booksService.createBook(book);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> updateBook(@PathVariable("id") UUID id, @RequestBody Book book) {
    try {
      return ResponseEntity.ok(booksService.updateBook(id, book));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> deleteBook(@PathVariable("id") UUID id) {
    try {
      return ResponseEntity.ok(booksService.deleteBook(id));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }
}
