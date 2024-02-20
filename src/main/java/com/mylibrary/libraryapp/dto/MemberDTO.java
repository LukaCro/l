package com.mylibrary.libraryapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberDTO {

    // validations are added later
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private AddressDTO postalAddress; // Assuming you have a PostalAddressDTO
    @Email
    private String email;
    private String phone;
    private String barcodeNumber;
    private LocalDate membershipStarted;
    private LocalDate membershipEnded;
    private Boolean isActive;
}
