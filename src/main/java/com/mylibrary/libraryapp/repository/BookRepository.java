package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
