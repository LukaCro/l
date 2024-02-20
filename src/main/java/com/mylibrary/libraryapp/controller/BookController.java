package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.exception.ErrorDetails;
import com.mylibrary.libraryapp.exception.ResourceNotFoundException;
import com.mylibrary.libraryapp.service.BookService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor  // to secure auto-injection of services
@RequestMapping("api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    // API to get book by id
    // e.g. http://localhost:8080/api/books/1
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId) {
        logger.info("Fetching book with id: {}", bookId);
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    // get all books
    // http://localhost:8080/api/books/listAll
    @GetMapping("listAll")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Fetching all books...");
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    // search by title (show first)
    // e.g. http://localhost:8080/api/books/title/queryTitle=it
    @GetMapping("title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam(name = "queryTitle") String title) {
        logger.info("Searching books by title: {}", title);
        List<BookDTO> books = bookService.findBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // search by number of parameters
    // e.g. http://localhost:8080/api/books/search?title=lord&author=tolk
    @GetMapping("search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "barcodeNumber", required = false) String barcodeNumber,
            @RequestParam(value = "isbn", required = false) String isbn) {
        logger.info("Searching books with criteria - title: {}, author: {}, barcodeNumber: {}, isbn: {}", title, author, barcodeNumber, isbn);
        List<BookDTO> books = bookService.findBooksByCriteria(title, author, barcodeNumber, isbn);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("addBook")
    // http://localhost:8080/api/books/addBook
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        logger.info("Adding new book: {}, {}", bookDTO.getTitle(), bookDTO.getAuthor());
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @PatchMapping("updateBook/{id}")
    // this time I will use the same name for id
    // e.g. http://localhost:8080/api/books/updateBook/5
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        logger.info("Updating book with id: {}", id);
        bookDTO.setId(id);
        BookDTO updatedBook = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book successfully deleted", HttpStatus.OK);
    }


}
