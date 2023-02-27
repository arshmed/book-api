package com.example.bookapi.repository;

import com.example.bookapi.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // not needed
public interface BookRepository extends JpaRepository<BookEntity, String> {
}
