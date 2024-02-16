package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.CheckoutRegisterDTO;
import com.mylibrary.libraryapp.entity.CheckoutRegister;
import com.mylibrary.libraryapp.mapper.CheckoutRegisterMapper;
import com.mylibrary.libraryapp.repository.CheckoutRegisterRepository;
import com.mylibrary.libraryapp.service.CheckoutRegisterService;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CheckoutRegisterServiceImpl implements CheckoutRegisterService {
    @Value("${library.overdueFineRate}")
    private double overdueFineRate;

    private CheckoutRegisterRepository checkoutRegisterRepository;

    @Override
    public CheckoutRegisterDTO createCheckout(CheckoutRegisterDTO checkoutRegisterDTO) {
        CheckoutRegisterMapper checkoutRegisterMapper = new CheckoutRegisterMapper();
        CheckoutRegister checkoutRegister = checkoutRegisterMapper.mapToCheckoutRegisterEntity(checkoutRegisterDTO);
        checkoutRegister = checkoutRegisterRepository.save(checkoutRegister);
        return checkoutRegisterMapper.mapToCheckoutRegisterDTO(checkoutRegister);
    }

    @Override
    public void returnBook(Long checkoutRegisterId, LocalDate returnDate) {
    }

    public void calculateOverdueFine(CheckoutRegister checkoutRegister) {
        if (checkoutRegister.getReturnDate() != null && checkoutRegister.getDueDate() != null
                && checkoutRegister.getReturnDate().isAfter(checkoutRegister.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(checkoutRegister.getDueDate(), checkoutRegister.getReturnDate());
            checkoutRegister.setOverdueFine(daysOverdue * overdueFineRate);
        } else {
            checkoutRegister.setOverdueFine(0.0); // or null, depending on how you want to handle no fines
        }
    }


    /*
    @Override
    public void returnBook(Long checkoutRegisterId, LocalDate returnDate) {
        CheckoutRegister checkoutRegister = checkoutRegisterRepository.findById(checkoutRegisterId)
                .orElseThrow(() -> new EntityNotFoundException("CheckoutRegister not found"));

        checkoutRegister.setReturnDate(returnDate);
        checkoutRegister.calculateOverdueFine(); // Calculate the overdue fine
        checkoutRegisterRepository.save(checkoutRegister);
    }

    */
}
