package com.unir.template.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.unir.template.model.Book;
import com.unir.template.repository.BooksRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BooksServiceImplTest {

  @Mock private BooksRepository booksRepository;

  private BooksServiceImpl booksService;

  @BeforeEach
  void setUp() {
    booksService = new BooksServiceImpl(booksRepository);
  }

  @Test
  void givenValidBookId_whenGetBookById_thenReturnBook() {
    UUID bookId = UUID.randomUUID();
    Book book = Instancio.create(Book.class);
    given(booksRepository.findById(bookId)).willReturn(Optional.of(book));

    Book result = booksService.getBookById(bookId);

    assertNotNull(result);
    assertEquals(book.getId(), result.getId());
    assertEquals(book.getName(), result.getName());
  }

  @Test
  void givenInvalidBookId_whenGetBookById_thenExceptionThrown() {
    UUID bookId = UUID.randomUUID();
    given(booksRepository.findById(bookId)).willReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> booksService.getBookById(bookId));

    assertEquals("Book not found with id: " + bookId, exception.getMessage());
  }

  @Test
  void whenGetAllBooks_thenReturnListOfBooks() {
    List<Book> books = Instancio.ofList(Book.class).size(3).create();
    given(booksRepository.findAll()).willReturn(books);

    List<Book> result = booksService.getAllBooks();

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(books, result);
  }

  @Test
  void whenCreateBook_thenReturnSavedBook() {
    Book book = Instancio.create(Book.class);
    given(booksRepository.save(book)).willReturn(book);

    Book result = booksService.createBook(book);

    assertNotNull(result);
    assertEquals(book, result);
  }

  @Test
  void givenValidBookId_whenUpdateBook_thenReturnUpdatedBook() {
    UUID bookId = UUID.randomUUID();
    Book existingBook = Instancio.create(Book.class);
    existingBook.setId(bookId);
    Book updatedData = Instancio.create(Book.class);
    given(booksRepository.findById(bookId)).willReturn(Optional.of(existingBook));
    given(booksRepository.save(any(Book.class)))
        .willAnswer(invocation -> invocation.getArgument(0));

    Book result = booksService.updateBook(bookId, updatedData);

    assertNotNull(result);
    assertEquals(updatedData.getName(), result.getName());
    assertEquals(updatedData.getDescription(), result.getDescription());
    assertEquals(updatedData.getPrice(), result.getPrice());
    assertEquals(updatedData.getStock(), result.getStock());
  }

  @Test
  void givenInvalidBookId_whenUpdateBook_thenExceptionThrown() {
    UUID bookId = UUID.randomUUID();
    Book updatedData = Instancio.create(Book.class);
    given(booksRepository.findById(bookId)).willReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> booksService.updateBook(bookId, updatedData));

    assertEquals("Book not found with id: " + bookId, exception.getMessage());
  }

  @Test
  void givenValidBookId_whenDeleteBook_thenReturnDeletedBook() {
    UUID bookId = UUID.randomUUID();
    Book book = Instancio.create(Book.class);
    book.setId(bookId);
    given(booksRepository.findById(bookId)).willReturn(Optional.of(book));

    Book result = booksService.deleteBook(bookId);

    assertNotNull(result);
    assertEquals(book, result);
    verify(booksRepository).deleteById(bookId);
  }

  @Test
  void givenInvalidBookId_whenDeleteBook_thenExceptionThrown() {
    UUID bookId = UUID.randomUUID();
    given(booksRepository.findById(bookId)).willReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> booksService.deleteBook(bookId));

    assertEquals("Book not found with id: " + bookId, exception.getMessage());
  }
}
