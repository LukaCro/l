package com.mylibrary.libraryapp.mapper;

import com.mylibrary.libraryapp.dto.CheckoutRegisterDTO;
import com.mylibrary.libraryapp.entity.Book;
import com.mylibrary.libraryapp.entity.CheckoutRegister;
import com.mylibrary.libraryapp.entity.Member;
import com.mylibrary.libraryapp.repository.BookRepository;
import com.mylibrary.libraryapp.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutRegisterMapper {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookRepository bookRepository;

    // not static for consistency
    public CheckoutRegisterDTO mapToCheckoutRegisterDTO(CheckoutRegister checkoutRegister) {
        CheckoutRegisterDTO dto = new CheckoutRegisterDTO();
        dto.setId(checkoutRegister.getId());
        dto.setMemberId(checkoutRegister.getMember().getId());
        dto.setBookId(checkoutRegister.getBook().getId());
        dto.setCheckoutDate(checkoutRegister.getCheckoutDate());
        dto.setDueDate(checkoutRegister.getDueDate());
        dto.setReturnDate(checkoutRegister.getReturnDate());
        dto.setOverdueFine(checkoutRegister.getOverdueFine());
        return dto;
    }

    // I am using memberRepository, so it cannot be static (show what happens if it's static)
    public CheckoutRegister mapToCheckoutRegisterEntity(CheckoutRegisterDTO checkoutRegisterDTO) {
        CheckoutRegister checkoutRegister = new CheckoutRegister();
        Member member = memberRepository.findById(checkoutRegisterDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + checkoutRegisterDTO.getMemberId()));
        Book book = bookRepository.findById(checkoutRegisterDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + checkoutRegisterDTO.getBookId()));
        checkoutRegister.setMember(member);
        checkoutRegister.setBook(book);
        checkoutRegister.setCheckoutDate(checkoutRegisterDTO.getCheckoutDate());
        checkoutRegister.setDueDate(checkoutRegisterDTO.getDueDate());
        checkoutRegister.setReturnDate(checkoutRegisterDTO.getReturnDate());
        checkoutRegister.setOverdueFine(checkoutRegisterDTO.getOverdueFine());

        return checkoutRegister;
    }


}
