package com.mylibrary.libraryapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String firstName;

    @Column(nullable = false)
    @NotNull
    private String lastName;

    @Column(nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "postal_address_id")
    private PostalAddress postalAddress;

    @Column(nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String phone;

    @Column(nullable = false)
    @NotNull
    private String barcodeNumber;

    @Column(nullable = false)
    @NotNull
    private LocalDate membershipStarted;

    @Column
    private LocalDate membershipEnded;
}
