package com.unir.template.service;

import com.unir.template.model.Book;
import com.unir.template.repository.BooksRepository;
import java.util.List;
import java.util.UUID;

public class BooksServiceImpl implements BooksService {

  private static final String BOOK_NOT_FOUND_MESSAGE = "Book not found with id: ";

  private final BooksRepository booksRepository;

  public BooksServiceImpl(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  @Override
  public List<Book> getAllBooks() {
    return booksRepository.findAll();
  }

  @Override
  public Book getBookById(UUID id) {
    return booksRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(BOOK_NOT_FOUND_MESSAGE + id));
  }

  @Override
  public Book createBook(Book book) {
    return booksRepository.save(book);
  }

  @Override
  public Book updateBook(UUID id, Book book) {
    return booksRepository
        .findById(id)
        .map(
            existingBook -> {
              existingBook.setName(book.getName());
              existingBook.setDescription(book.getDescription());
              existingBook.setPrice(book.getPrice());
              existingBook.setStock(book.getStock());
              return booksRepository.save(existingBook);
            })
        .orElseThrow(() -> new RuntimeException(BOOK_NOT_FOUND_MESSAGE + id));
  }

  @Override
  public Book deleteBook(UUID id) {
    return booksRepository
        .findById(id)
        .map(
            existingBook -> {
              booksRepository.deleteById(id);
              return existingBook;
            })
        .orElseThrow(() -> new RuntimeException(BOOK_NOT_FOUND_MESSAGE + id));
  }
}
