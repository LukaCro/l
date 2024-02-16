package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.dto.MemberDTO;
import com.mylibrary.libraryapp.service.BookService;
import com.mylibrary.libraryapp.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor  // to secure auto-injection of services
@RequestMapping("api/members")
public class MemberController {

    private MemberService memberService;

    // API to get book by id
    // e.g. http://localhost:8080/api/members/1
    @GetMapping("{id}")
    public ResponseEntity<MemberDTO> getBookById(@PathVariable("id") Long memberId) {
        MemberDTO member = memberService.getMemberById(memberId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    // get all members
    // http://localhost:8080/api/members/listAll
    @GetMapping("listAll")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> allMembers = memberService.getAllMembers();
        return new ResponseEntity<>(allMembers, HttpStatus.OK);
    }

    // search by number of parameters
    // e.g. http://localhost:8080/api/members/search?firstName=Jerry&lastName=sein
    @GetMapping("search")
    public ResponseEntity<List<MemberDTO>> searchBooks(
            @RequestParam(value = "cardNumber", required = false) Long cardNumber,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "barcodeNumber", required = false) String barcodeNumber) {
        List<MemberDTO> members = memberService.findMembersByCriteria(cardNumber, firstName, lastName, barcodeNumber);
        if (members.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("addMember")
    // http://localhost:8080/api/members/addMember
    public ResponseEntity<MemberDTO> addBook(@RequestBody MemberDTO memberDTO) {
        MemberDTO savedMemberDTO = memberService.addMember(memberDTO);
        return new ResponseEntity<>(savedMemberDTO, HttpStatus.CREATED);
    }

    @PatchMapping("updateMember/{id}")
    // e.g. http://localhost:8080/api/members/updateMember/5
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        memberDTO.setId(id);
        MemberDTO updatedMember = memberService.updateMember(memberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }


}
