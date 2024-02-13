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
    public List<BookDTO> findBooksByCriteria(String title, String author) {
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

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Book> result = entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(BookMapper::mapToBookDTO) // Assuming mapToBookDTO is a static method in BookMapper
                .collect(Collectors.toList());
    }
}