package com.mylibrary.libraryapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "checkout_register") // can be omitted in this case
public class CheckoutRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private Book book;

    @Column(nullable = false)
    @NotNull
    private LocalDate checkoutDate;

    @Column(nullable = false)
    @NotNull
    private LocalDate returnDate;

    @Column(nullable = false)
    @NotNull
    private Boolean active;
}
