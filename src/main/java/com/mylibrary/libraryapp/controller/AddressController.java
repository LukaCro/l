package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.AddressDTO;
import com.mylibrary.libraryapp.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/addresses")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private AddressService addressService;

    @GetMapping("{id}")
    // e.g. http://localhost:8080/api/addresses/1
    public ResponseEntity<AddressDTO> getPostalAddressById(@PathVariable Long id) {
        logger.info("Fetching postal address with id: {}", id);
        AddressDTO postalAddressDTO = addressService.getPostalAddressById(id);
        logger.info("Fetched postal address: {}", postalAddressDTO);
        return new ResponseEntity<>(postalAddressDTO, HttpStatus.OK);
    }

    // get all books
    // http://localhost:8080/api/addresses/listAll
    @GetMapping("listAll")
    public ResponseEntity<List<AddressDTO>> getAllBooks() {
        logger.info("Fetching all postal addresses...");
        List<AddressDTO> allPostalAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allPostalAddresses, HttpStatus.OK);
    }

    @PostMapping("createAddress")
    // http://localhost:8080/api/addresses/createAddress
    public ResponseEntity<AddressDTO> addBook(@RequestBody AddressDTO addressDTO) {
        logger.info("Creating a new postal address: {}", addressDTO);
        AddressDTO savedPostalAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(savedPostalAddressDTO, HttpStatus.CREATED);
    }

    @PatchMapping("updateAddress/{id}")
    // e.g. http://localhost:8080/api/addresses/updateAddress/5
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        logger.info("Updating postal address: ", addressDTO);
        addressDTO.setId(id);
        AddressDTO updatedAddress = addressService.updateAddress(addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
}
