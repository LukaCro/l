package com.mylibrary.libraryapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckoutRegisterDTO {
    private Long id;
    private Long memberId;
    private Long bookId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Double overdueFine;

    // Getters and Setters
}
