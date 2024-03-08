package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.dto.CheckoutRegisterDTO;
import com.mylibrary.libraryapp.entity.Book;
import com.mylibrary.libraryapp.entity.CheckoutRegister;
import com.mylibrary.libraryapp.exception.ResourceNotFoundException;
import com.mylibrary.libraryapp.mapper.BookMapper;
import com.mylibrary.libraryapp.mapper.CheckoutRegisterMapper;
import com.mylibrary.libraryapp.repository.CheckoutRegisterRepository;
import com.mylibrary.libraryapp.service.CheckoutRegisterService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutRegisterServiceImpl implements CheckoutRegisterService {

    @Value("${library.overdueFineRate}")
    private double overdueFineRate;

    @Value("${library.loanPeriodInDays}")
    private int loanPeriodInDays;

    private final CheckoutRegisterRepository checkoutRegisterRepository;
    private final CheckoutRegisterMapper checkoutRegisterMapper;

    @Override
    public CheckoutRegisterDTO createCheckoutRegister(CheckoutRegisterDTO checkoutRegisterDTO) {
        CheckoutRegister checkoutRegister = checkoutRegisterMapper.mapToCheckoutRegisterEntity(checkoutRegisterDTO);
        // set dueDate
        LocalDate dueDate = calculateDueDate(checkoutRegister.getCheckoutDate());
        checkoutRegister.setDueDate(dueDate);
        checkoutRegister = checkoutRegisterRepository.save(checkoutRegister);
        return checkoutRegisterMapper.mapToCheckoutRegisterDTO(checkoutRegister);
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate) {
        return checkoutDate.plusDays(loanPeriodInDays);
    }

    @Override
    public CheckoutRegisterDTO getCheckoutRegisterById(Long registerId) {
        Optional<CheckoutRegister> optionalCheckoutRegister = checkoutRegisterRepository.findById(registerId);
        CheckoutRegister checkoutRegister = optionalCheckoutRegister.get();
        return checkoutRegisterMapper.mapToCheckoutRegisterDTO(checkoutRegister);
    }

    @Override
    public List<CheckoutRegisterDTO> getAllRegisters() {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findAll();
        return checkoutRegisters.stream()
                .map(checkoutRegisterMapper::mapToCheckoutRegisterDTO) // convert each book to BookDTO
                .collect(Collectors.toList());
    }

    @Override
    public CheckoutRegisterDTO updateRegister(CheckoutRegisterDTO checkoutRegisterDTO) {
            // find existing register by id
            Optional<CheckoutRegister> checkoutRegisterOptional = checkoutRegisterRepository.findById(checkoutRegisterDTO.getId());

            // do partial update of the book (only non-null fields)
            CheckoutRegister registerToUpdate = checkoutRegisterOptional.get();
            updateRegisterEntityFromDTO(registerToUpdate, checkoutRegisterDTO);

            // calculate overdue fine if neccessary
            calculateOverdueFine(registerToUpdate);

            // save updated book to database
            checkoutRegisterRepository.save(registerToUpdate);

            // return bookDTO using mapper
            return checkoutRegisterMapper.mapToCheckoutRegisterDTO(registerToUpdate);
    }

    @Override
    public void deleteRegister(Long id) {
        checkoutRegisterRepository.deleteById(id);
    }

    @Override
    public List<CheckoutRegisterDTO> getCheckoutsByMemberId(Long id) {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findByMemberId(id);
        // if non found
        if (checkoutRegisters.isEmpty()) {
            throw new ResourceNotFoundException("Checkout register", "Member ID", id);
        }
        return checkoutRegisters.stream()
                .map(checkoutRegisterMapper::mapToCheckoutRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CheckoutRegisterDTO> getCheckoutsByBookId(Long id) {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findByBookId(id);
        return checkoutRegisters.stream()
                .map(checkoutRegisterMapper::mapToCheckoutRegisterDTO)
                .collect(Collectors.toList());
    }

    private void updateRegisterEntityFromDTO(CheckoutRegister registerToUpdate, CheckoutRegisterDTO checkoutRegisterDTO) {

        // if someone wants to prolong
        if (checkoutRegisterDTO.getDueDate() != null) {
            registerToUpdate.setDueDate(checkoutRegisterDTO.getDueDate());
        }
        // when the book is returned
        if (checkoutRegisterDTO.getReturnDate() != null) {
            registerToUpdate.setReturnDate(checkoutRegisterDTO.getReturnDate());
        }
    }

    private void calculateOverdueFine(CheckoutRegister checkoutRegister) {
        if (checkoutRegister.getReturnDate() != null
                && checkoutRegister.getReturnDate().isAfter(checkoutRegister.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(checkoutRegister.getDueDate(), checkoutRegister.getReturnDate());
            checkoutRegister.setOverdueFine(daysOverdue * overdueFineRate);
        } else {
            checkoutRegister.setOverdueFine(null);
        }
    }
}
