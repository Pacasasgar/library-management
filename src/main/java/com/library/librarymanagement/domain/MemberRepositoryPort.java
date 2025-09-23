package com.library.librarymanagement.domain;

import java.util.Optional;

public interface MemberRepositoryPort {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(String memberId);
    void deleteById(String memberId);
}