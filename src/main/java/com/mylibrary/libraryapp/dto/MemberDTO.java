package com.mylibrary.libraryapp.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private AddressDTO postalAddress; // Assuming you have a PostalAddressDTO
    private String email;
    private String phone;
    private String barcodeNumber;
    private LocalDate membershipStarted;
    private LocalDate membershipEnded;
    private Boolean isActive;
}
