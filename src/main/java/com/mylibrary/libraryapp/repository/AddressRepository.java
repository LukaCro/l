package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<PostalAddress, Long> {
}
