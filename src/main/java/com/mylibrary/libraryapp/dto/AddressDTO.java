package com.mylibrary.libraryapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String placeName;
    private String country;
    private String additionalInfo;
}
