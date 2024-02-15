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
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "barcodeNumber", required = false) String barcodeNumber,
            @RequestParam(value = "isbn", required = false) String isbn) {
        List<BookDTO> books = bookService.findBooksByCriteria(title, author, barcodeNumber, isbn);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("addBook")
    // http://localhost:8080/api/books/addBook
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @PatchMapping("updateBook/{id}")
    // this time I will use the same name for id
    // e.g. http://localhost:8080/api/books/updateBook/5
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id);
        BookDTO updatedBook = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


}
