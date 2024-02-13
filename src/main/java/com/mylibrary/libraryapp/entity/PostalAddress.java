package com.mylibrary.libraryapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "postal_addresses")
public class PostalAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String streetName;

    @Column(nullable = false)
    @NotNull
    private String streetNumber;

    @Column
    private String zipCode;

    @Column(nullable = false)
    @NotNull
    private String placeName;

    @Column(nullable = false)
    @NotNull
    private String country;

    @Column
    private String additionalInfo;
}
