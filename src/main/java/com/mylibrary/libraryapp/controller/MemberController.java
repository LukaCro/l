package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.BookDTO;
import com.mylibrary.libraryapp.dto.MemberDTO;
import com.mylibrary.libraryapp.service.BookService;
import com.mylibrary.libraryapp.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor  // to secure auto-injection of services
@RequestMapping("api/members")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private MemberService memberService;

    // API to get book by id
    // e.g. http://localhost:8080/api/members/1
    @GetMapping("{id}")
    public ResponseEntity<MemberDTO> getBookById(@PathVariable("id") Long memberId) {
        logger.info("Fetching member with id: {}", memberId);
        MemberDTO memberDTO = memberService.getMemberById(memberId);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    // get all members
    // http://localhost:8080/api/members/listAll
    @GetMapping("listAll")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        logger.info("Fetching all members...");
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
        logger.info("Searching members with criteria - cardNumber: {}, firstName: {}, lastName: {}, barcodeNumber: {}",
                cardNumber, firstName, lastName, barcodeNumber);
        List<MemberDTO> members = memberService.findMembersByCriteria(cardNumber, firstName, lastName, barcodeNumber);
        if (members.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("addMember")
    // http://localhost:8080/api/members/addMember
    public ResponseEntity<MemberDTO> addBook(@Valid @RequestBody MemberDTO memberDTO) {
        logger.info("Adding new member: {}", memberDTO);
        try {
            MemberDTO savedMemberDTO = memberService.addMember(memberDTO);
            return new ResponseEntity<>(savedMemberDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Assuming your service method throws IllegalArgumentException for invalid input
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            // Catch-all for other exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @PatchMapping("updateMember/{id}")
    // e.g. http://localhost:8080/api/members/updateMember/5
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTO memberDTO) {
        logger.info("Updating member with id: {}", id);
        memberDTO.setId(id);
        MemberDTO updatedMember = memberService.updateMember(memberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("deleteMember/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>("Member successfully deleted", HttpStatus.OK);
    }
}
