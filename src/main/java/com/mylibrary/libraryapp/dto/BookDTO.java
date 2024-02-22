package com.mylibrary.libraryapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer yearOfPublication;
    private String placeOfPublication;
    private Integer noOfAvailableCopies;
    private String barcodeNumber;
}
