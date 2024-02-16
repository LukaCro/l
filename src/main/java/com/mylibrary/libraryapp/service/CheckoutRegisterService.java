package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.CheckoutRegisterDTO;

import java.time.LocalDate;

public interface CheckoutRegisterService {

    public CheckoutRegisterDTO createCheckout(CheckoutRegisterDTO checkoutRegisterDTO);
    public void returnBook(Long checkoutRegisterId, LocalDate returnDate);


}
