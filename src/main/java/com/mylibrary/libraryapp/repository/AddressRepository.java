package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<PostalAddress, Long> {
}
