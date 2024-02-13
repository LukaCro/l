package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.entity.Book;
import com.mylibrary.libraryapp.repository.BookRepository;
import com.mylibrary.libraryapp.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public Book getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.get();
    }
}
