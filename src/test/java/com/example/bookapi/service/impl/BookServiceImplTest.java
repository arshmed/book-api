package com.example.bookapi.service.impl;

import com.example.bookapi.Impl.BookServiceImpl;
import com.example.bookapi.TestData;
import com.example.bookapi.domain.Book;
import com.example.bookapi.domain.BookEntity;
import com.example.bookapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved(){

        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
        final Book result = underTest.save(book);

        assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){

        final String ISBN = "12345";
        when(bookRepository.findById(eq(ISBN))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(ISBN);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){

        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();
        when(bookRepository.findById(eq(book.getISBN()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getISBN());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBookExist(){
        final List<Book> result = underTest.listBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsWhenExist(){
        final BookEntity bookEntity = TestData.testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(List.of(new Book(bookEntity.getISBN(),bookEntity.getAuthor(),bookEntity.getTitle())), result);
    }

    @Test
    public void testBookExistsReturnsFalseWhenBookDoesntExist(){
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(TestData.testBook());
        assertEquals(false, result);
    }

    @Test
    public void testBookExistsReturnsTrueWhenBookDoesExist(){
        when(bookRepository.existsById(any())).thenReturn(true);
        final boolean result = underTest.isBookExists(TestData.testBook());
        assertEquals(true, result);
    }

    @Test
    public void testDeleteBookByIdDeletesBook(){
        final String ISBN = "132312";
        underTest.deleteBookById(ISBN);
        verify(bookRepository, times(1)).deleteById(eq(ISBN));
    }

}
