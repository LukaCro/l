package com.mylibrary.libraryapp.dto;

import lombok.Data;

@Data
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int yearOfPublication;
    private String placeOfPublication;
    private int noOfAvailableCopies;
    private String barcodeNumber;
}
