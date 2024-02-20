package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO getPostalAddressById(Long id);

    List<AddressDTO> getAllAddresses();

    AddressDTO createAddress(AddressDTO postalAddressDTO);

    AddressDTO updateAddress(AddressDTO postalAddressDTO);

    void deleteAddress(Long id);

}
