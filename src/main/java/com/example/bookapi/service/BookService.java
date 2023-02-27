package com.example.bookapi.service;

import com.example.bookapi.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean isBookExists(Book book);

    Book save(Book book);

    Optional<Book> findById(final String ISBN);

    List<Book> listBooks();

    void deleteBookById(final String ISBN);
}
