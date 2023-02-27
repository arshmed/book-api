package com.example.bookapi.Impl;

import com.example.bookapi.domain.Book;
import com.example.bookapi.domain.BookEntity;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired // not needed
    public BookServiceImpl(final BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getISBN());
    }

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(String ISBN) {

        final Optional<BookEntity> foundBook = bookRepository.findById(ISBN);

        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        List<BookEntity> bookList =  bookRepository.findAll();
        return bookList.stream().map(this::bookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(String ISBN) {
        try {
            bookRepository.deleteById(ISBN);
        }
        catch (final EmptyResultDataAccessException e){
            log.debug("Attempted to delete non-existing book", e);
        }
    }

    private BookEntity bookToBookEntity(final Book book){
        return BookEntity.builder()
                .ISBN(book.getISBN())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
    }

    private Book bookEntityToBook(final BookEntity bookEntity){
        return Book.builder()
                .ISBN(bookEntity.getISBN())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .build();
    }

}
