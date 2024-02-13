package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.entity.Book;
import com.mylibrary.libraryapp.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor  // to secure auto-injection of services
@RequestMapping("api/books")
public class BookController {

    private BookService bookService;

    // API to get book by id
    // e.g. http://localhost:8080/api/books/1
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId) {
        BookDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // get all books
    // http://localhost:8080/api/books/listAll
    @GetMapping("listAll")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    // search by title (show first)
    // e.g. http://localhost:8080/api/books/title/queryTitle=it
    @GetMapping("title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam(name = "queryTitle") String title) {
        List<BookDTO> books = bookService.findBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // search by number of parameters
    // e.g. http://localhost:8080/api/books/search?title=lord&author=tolk
    @GetMapping("search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author) {
        List<BookDTO> books = bookService.findBooksByCriteria(title, author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
