package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.entity.Book;

public interface BookService {

    BookDTO getBookById(Long bookId);
}
