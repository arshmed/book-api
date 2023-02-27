package com.example.bookapi;

import com.example.bookapi.domain.Book;
import com.example.bookapi.domain.BookEntity;

public final class TestData {

    private TestData(){
    }

    public static Book testBook(){
        return Book.builder()
                .ISBN("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();
    }

    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .ISBN("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();
    }

}
