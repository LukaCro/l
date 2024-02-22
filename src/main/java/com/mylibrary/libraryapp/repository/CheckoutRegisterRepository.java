package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.CheckoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRegisterRepository extends JpaRepository<CheckoutRegister, Long> {

    // Find all checkout records by member's ID
    List<CheckoutRegister> findByMemberId(Long memberId);

    // Find all checkout records by book's ID
    List<CheckoutRegister> findByBookId(Long bookId);

}
