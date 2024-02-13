package com.mylibrary.libraryapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    @NotNull
    private String author;

    @Column(nullable = false)
    @NotNull
    private String isbn;

    @Column(nullable = false)
    @NotNull
    private String publisher;

    @Column(nullable = false)
    @NotNull
    private int yearOfPublication;

    @Column(nullable = false)
    @NotNull
    private String placeOfPublication;

    @Column(nullable = false)
    @NotNull
    private int noOfAvailableCopies;

    @Column(nullable = false)
    @NotNull
    private String barcodeNumber;
}
