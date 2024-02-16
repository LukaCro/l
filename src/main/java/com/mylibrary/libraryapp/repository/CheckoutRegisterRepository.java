package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.CheckoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRegisterRepository extends JpaRepository<CheckoutRegister, Long> {
}
