package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.AddressDTO;
import com.mylibrary.libraryapp.entity.PostalAddress;
import com.mylibrary.libraryapp.mapper.AddressMapper;
import com.mylibrary.libraryapp.repository.AddressRepository;
import com.mylibrary.libraryapp.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;

    @Override
    public AddressDTO getPostalAddressById(Long id) {
        Optional<PostalAddress> optionalPostalAddress = addressRepository.findById(id);
        PostalAddress postalAddress = optionalPostalAddress.get();
        return AddressMapper.mapToAddressDTO(postalAddress);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<PostalAddress> postalAddresses = addressRepository.findAll();
        return postalAddresses.stream()
                .map(AddressMapper::mapToAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO createAddress(AddressDTO postalAddressDTO) {
        PostalAddress postalAddress = AddressMapper.mapToAddressEntity(postalAddressDTO);
        postalAddress = addressRepository.save(postalAddress);
        return AddressMapper.mapToAddressDTO(postalAddress);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO postalAddressDTO) {
        // 1. find existing address by id
        Optional<PostalAddress> addressOptional = addressRepository.findById(postalAddressDTO.getId());

        // 2. do partial update of the address (only non-null fields)
        PostalAddress addressToUpdate = addressOptional.get();
        updateAddressEntityFromDTO(addressToUpdate, postalAddressDTO);

        // 3. save updated book to database
        addressRepository.save(addressToUpdate);

        // 4. return bookDTO using mapper
        return AddressMapper.mapToAddressDTO(addressToUpdate);
    }

    public void updateAddressEntityFromDTO(PostalAddress addressToUpdate, AddressDTO postalAddressDTO) {
        if (postalAddressDTO.getStreetName() != null) {
            addressToUpdate.setStreetName(postalAddressDTO.getStreetName());
        }
        if (postalAddressDTO.getStreetNumber() != null) {
            addressToUpdate.setStreetNumber(postalAddressDTO.getStreetNumber());
        }
        if (postalAddressDTO.getZipCode() != null) {
            addressToUpdate.setZipCode(postalAddressDTO.getZipCode());
        }
        if (postalAddressDTO.getPlaceName() != null) {
            addressToUpdate.setPlaceName(postalAddressDTO.getPlaceName());
        }
        if (postalAddressDTO.getCountry() != null) {
            addressToUpdate.setCountry(postalAddressDTO.getCountry());
        }
        if (postalAddressDTO.getAdditionalInfo() != null) {
            addressToUpdate.setAdditionalInfo(postalAddressDTO.getAdditionalInfo());
        }
    }


}
