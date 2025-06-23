package com.unir.template.controller;

import com.unir.template.model.Book;
import com.unir.template.service.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Books", description = "Books management API endpoints")
public class BooksController {

  private final BooksService booksService;

  public BooksController(BooksService booksService) {
    this.booksService = booksService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Get book by ID",
      description = "Retrieves a specific book by its unique identifier")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
      })
  public ResponseEntity<Book> getBookById(
      @Parameter(
              description = "Unique identifier of the book",
              example = "123e4567-e89b-12d3-a456-426614174000")
          @PathVariable("id")
          UUID id) {
    try {
      return ResponseEntity.ok(booksService.getBookById(id));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get all books", description = "Retrieves a list of all books in the system")
  @ApiResponse(
      responseCode = "200",
      description = "Books retrieved successfully",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Book.class)))
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = booksService.getAllBooks();
    return ResponseEntity.ok(books);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create a new book", description = "Creates a new book in the system")
  @ApiResponse(
      responseCode = "200",
      description = "Book created successfully",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Book.class)))
  public Book createBook(
      @Parameter(description = "Book object to be created", required = true) @RequestBody
          Book book) {
    return booksService.createBook(book);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Update an existing book",
      description = "Updates an existing book by its unique identifier")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book updated successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
      })
  public ResponseEntity<Book> updateBook(
      @Parameter(
              description = "Unique identifier of the book to update",
              example = "123e4567-e89b-12d3-a456-426614174000")
          @PathVariable("id")
          UUID id,
      @Parameter(description = "Updated book object", required = true) @RequestBody Book book) {
    try {
      return ResponseEntity.ok(booksService.updateBook(id, book));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Delete a book",
      description = "Deletes a book from the system by its unique identifier")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book deleted successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
      })
  public ResponseEntity<Book> deleteBook(
      @Parameter(
              description = "Unique identifier of the book to delete",
              example = "123e4567-e89b-12d3-a456-426614174000")
          @PathVariable("id")
          UUID id) {
    try {
      return ResponseEntity.ok(booksService.deleteBook(id));
    } catch (RuntimeException _) {
      return ResponseEntity.notFound().build();
    }
  }
}
