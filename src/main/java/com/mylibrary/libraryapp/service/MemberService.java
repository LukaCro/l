package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO getMemberById(Long bookId);

    List<MemberDTO> getAllMembers();

    List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber);

    MemberDTO addMember(MemberDTO memberDTO);

    MemberDTO updateMember(MemberDTO MemberDTO);
}
