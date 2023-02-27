package com.example.bookapi.controller;

import com.example.bookapi.domain.Book;
import com.example.bookapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{ISBN}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String ISBN , @RequestBody final Book book){
        final Book savedBook = bookService.save(book);
        final boolean isBookExists = bookService.isBookExists(book);

        if (isBookExists){
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        else
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @GetMapping(path = "/books/{ISBN}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String ISBN){
        final Optional<Book> foundBook = bookService.findById(ISBN);
        return foundBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks(){
        return new ResponseEntity<>(bookService.listBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{ISBN}")
    public ResponseEntity deleteBook(@PathVariable final String ISBN){
        bookService.deleteBookById(ISBN);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
