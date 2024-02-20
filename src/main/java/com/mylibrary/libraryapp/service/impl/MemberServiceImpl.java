package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.AddressDTO;
import com.mylibrary.libraryapp.dto.MemberDTO;
import com.mylibrary.libraryapp.entity.Member;
import com.mylibrary.libraryapp.entity.PostalAddress;
import com.mylibrary.libraryapp.exception.ResourceNotFoundException;
import com.mylibrary.libraryapp.mapper.AddressMapper;
import com.mylibrary.libraryapp.mapper.MemberMapper;
import com.mylibrary.libraryapp.repository.AddressRepository;
import com.mylibrary.libraryapp.repository.MemberRepository;
import com.mylibrary.libraryapp.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private AddressRepository addressRepository; // when adding member
    private AddressServiceImpl addressService;

    @Override
    public MemberDTO getMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.get();
        return MemberMapper.mapToMemberDTO(member);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberMapper::mapToMemberDTO) // convert each book to BookDTO
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> memberRoot = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(memberRoot.get("id"), id));
        }
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(cb.lower(memberRoot.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(cb.lower(memberRoot.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (barcodeNumber != null && !barcodeNumber.isEmpty()) {
            predicates.add(cb.like(cb.lower(memberRoot.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Member> result = entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(MemberMapper::mapToMemberDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional  // last thing to add
    public MemberDTO addMember(MemberDTO memberDTO) {
        // First we have to deal with postal_address
        AddressDTO addressDTO = memberDTO.getPostalAddress();
        PostalAddress postalAddress = AddressMapper.mapToAddressEntity(addressDTO);
        postalAddress = addressRepository.save(postalAddress);

        // Convert MemberDTO to Member entity, associate with saved postal address, and save
        Member member = MemberMapper.mapToMemberEntity(memberDTO);
        member.setPostalAddress(postalAddress); // Associate the saved postal address with the member
        member = memberRepository.save(member);

        // Convert the saved Member entity back to MemberDTO and return
        return MemberMapper.mapToMemberDTO(member);
    }

    public MemberDTO updateMember(MemberDTO memberDTO) {
        // 1. find existing member by id
        Optional<Member> memberOptional = memberRepository.findById(memberDTO.getId());

        // 2. do partial update of the member (only non-null fields)
        Member memberToUpdate = memberOptional.get();
        updateMemberEntityFromDTO(memberToUpdate, memberDTO);

        // 3. save updated member to database
        memberRepository.save(memberToUpdate);

        // 4. return memberDTO using mapper
        return MemberMapper.mapToMemberDTO(memberToUpdate);
    }

    @Override
    public void deleteMember(Long memberId) {
        // it's a little complicated because we have to delete from postal_address table as well
        /* Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "ID", memberId));
        if (member.getPostalAddress() != null) {
            addressRepository.deleteById(member.getPostalAddress().getId());
        } */
        // add (cascade = CascadeType.ALL) to repository
        memberRepository.deleteById(memberId);
    }

    public void updateMemberEntityFromDTO(Member memberToUpdate, MemberDTO memberDTO) {
        // Check and update each field only if it's not null in the DTO
        if (memberDTO.getFirstName() != null) {
            memberToUpdate.setFirstName(memberDTO.getFirstName());
        }
        if (memberDTO.getLastName() != null) {
            memberToUpdate.setLastName(memberDTO.getLastName());
        }
        if (memberDTO.getDateOfBirth() != null) {
            memberToUpdate.setDateOfBirth(memberDTO.getDateOfBirth());
        }
        if (memberDTO.getEmail() != null) {
            memberToUpdate.setEmail(memberDTO.getEmail());
        }
        if (memberDTO.getPhone() != null) {
            memberToUpdate.setPhone(memberDTO.getPhone());
        }
        if (memberDTO.getBarcodeNumber() != null) {
            memberToUpdate.setBarcodeNumber(memberDTO.getBarcodeNumber());
        }
        if (memberDTO.getMembershipStarted() != null) {
            memberToUpdate.setMembershipStarted(memberDTO.getMembershipStarted());
        }
        if (memberDTO.getMembershipEnded() != null) {
            memberToUpdate.setMembershipEnded(memberDTO.getMembershipEnded());
        }
        // let's be fancy here
        memberToUpdate.setIsActive(memberDTO.getMembershipEnded() == null);

        // Handle PostalAddress update
        if (memberDTO.getPostalAddress() != null) {
            PostalAddress addressToUpdate;
            if (memberToUpdate.getPostalAddress() != null) {
                // If the member already has an address, update it
                addressToUpdate = memberToUpdate.getPostalAddress();
            } else {
                // Otherwise, create a new PostalAddress entity
                addressToUpdate = new PostalAddress();
            }
            // Use the existing service to update the address entity
            addressService.updateAddressEntityFromDTO(addressToUpdate, memberDTO.getPostalAddress());
            // Save the updated address entity
            addressRepository.save(addressToUpdate);
            // Associate the updated address with the member
            memberToUpdate.setPostalAddress(addressToUpdate);
        }
    }

}