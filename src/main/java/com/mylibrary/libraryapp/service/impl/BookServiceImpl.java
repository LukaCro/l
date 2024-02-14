package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.entity.Book;
import com.mylibrary.libraryapp.mapper.BookMapper;
import com.mylibrary.libraryapp.repository.BookRepository;
import com.mylibrary.libraryapp.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookDTO getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookMapper::mapToBookDTO) // convert each book to BookDTO
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByCriteria(String title, String author, String barcodeNumber, String isbn) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (author != null && !author.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("author")), "%" + author.toLowerCase() + "%"));
        }
        if (barcodeNumber != null && !barcodeNumber.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }
        if (isbn != null && !isbn.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Book> result = entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = BookMapper.mapToBookEntity(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        // add as the last
        if (bookDTO.getId() == null) {
            throw new IllegalArgumentException("Book ID cannot be null for update operation");
        }

        // 1. find existing book by id
        Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("Book with ID " + bookDTO.getId() + " not found");
        }

        // 2. do partial update of the book (only non-null fields)
        Book bookToUpdate = bookOptional.get();
        updateBookEntityFromDTO(bookToUpdate, bookDTO);

        // 3. save updated book to database
        bookRepository.save(bookToUpdate);

        // 4. return bookDTO using mapper
        return BookMapper.mapToBookDTO(bookToUpdate);
    }

    private void updateBookEntityFromDTO(Book book, BookDTO bookDTO) {
        if (bookDTO.getTitle() != null) book.setTitle(bookDTO.getTitle());
        if (bookDTO.getAuthor() != null) book.setAuthor(bookDTO.getAuthor());
        if (bookDTO.getIsbn() != null) book.setIsbn(bookDTO.getIsbn());
        if (bookDTO.getPublisher() != null) book.setPublisher(bookDTO.getPublisher());
        if (bookDTO.getYearOfPublication() != 0) book.setYearOfPublication(bookDTO.getYearOfPublication());
        if (bookDTO.getPlaceOfPublication() != null) book.setPlaceOfPublication(bookDTO.getPlaceOfPublication());
        if (bookDTO.getNoOfAvailableCopies() != 0) book.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        if (bookDTO.getBarcodeNumber() != null) book.setBarcodeNumber(bookDTO.getBarcodeNumber());
    }
}