package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.entity.Book;

import java.util.List;

public interface BookService {

    BookDTO getBookById(Long bookId);

    List<BookDTO> getAllBooks();

    List<BookDTO> findBooksByTitle(String title);

    List<BookDTO> findBooksByCriteria(String title, String author);
}
