package com.mylibrary.libraryapp.mapper;

import com.mylibrary.libraryapp.dto.MemberDTO;
import com.mylibrary.libraryapp.entity.Member;
import com.mylibrary.libraryapp.entity.PostalAddress;

public class MemberMapper {

    public static MemberDTO mapToMemberDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setDateOfBirth(member.getDateOfBirth());
        if (member.getPostalAddress() != null) {
            dto.setPostalAddress(AddressMapper.mapToAddressDTO(member.getPostalAddress()));
        }
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setBarcodeNumber(member.getBarcodeNumber());
        dto.setMembershipStarted(member.getMembershipStarted());
        dto.setMembershipEnded(member.getMembershipEnded());
        return dto;
    }

    public static Member mapToMemberEntity(MemberDTO dto) {
        Member member = new Member();
        member.setId(dto.getId()); // Be cautious with setting ID when creating new entities
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getPostalAddress() != null) {
            member.setPostalAddress(new PostalAddress());
        }
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setBarcodeNumber(dto.getBarcodeNumber());
        member.setMembershipStarted(dto.getMembershipStarted());
        member.setMembershipEnded(dto.getMembershipEnded());
        return member;
    }
}
