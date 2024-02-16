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

    public static Member mapToMemberEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId()); // Be cautious with setting ID when creating new entities
        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());
        member.setDateOfBirth(memberDTO.getDateOfBirth());
        if (memberDTO.getPostalAddress() != null) {
            member.setPostalAddress(new PostalAddress());
        }
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setBarcodeNumber(memberDTO.getBarcodeNumber());
        member.setMembershipStarted(memberDTO.getMembershipStarted());
        member.setMembershipEnded(memberDTO.getMembershipEnded());
        return member;
    }
}
