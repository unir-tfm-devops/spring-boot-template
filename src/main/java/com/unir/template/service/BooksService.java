package com.unir.template.service;

import com.unir.template.model.Book;
import java.util.List;
import java.util.UUID;

public interface BooksService {

  List<Book> getAllBooks();

  Book getBookById(UUID id);

  Book createBook(Book book);

  Book updateBook(UUID id, Book book);

  Book deleteBook(UUID id);
}
