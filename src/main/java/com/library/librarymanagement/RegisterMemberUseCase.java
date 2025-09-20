package com.library.librarymanagement;

import java.util.UUID;

public class RegisterMemberUseCase {
    public Member execute(String name, String email) {
        Member member = new Member();
        member.setMemberId(UUID.randomUUID().toString());
        member.setName(name);
        member.setEmail(email);
        return member;
    }
}
