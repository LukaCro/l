package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.CheckoutRegisterDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface CheckoutRegisterService {

    public CheckoutRegisterDTO createCheckoutRegister(CheckoutRegisterDTO checkoutRegisterDTO);

    CheckoutRegisterDTO getCheckoutRegisterById(Long registerId);

    List<CheckoutRegisterDTO> getAllRegisters();

    CheckoutRegisterDTO updateRegister(CheckoutRegisterDTO checkoutRegisterDTO);

    void deleteRegister(Long id);

}
