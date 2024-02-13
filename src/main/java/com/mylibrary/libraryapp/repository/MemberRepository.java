package com.mylibrary.libraryapp.repository;

import com.mylibrary.libraryapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
