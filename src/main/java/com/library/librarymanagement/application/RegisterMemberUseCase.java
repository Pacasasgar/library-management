package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;

import java.util.UUID;

public class RegisterMemberUseCase {

    private final MemberRepositoryPort memberRepository;

    public RegisterMemberUseCase(MemberRepositoryPort memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member execute(String name, String email) {
        Member member = new Member();
        member.setMemberId(UUID.randomUUID().toString());
        member.setName(name);
        member.setEmail(email);

        return memberRepository.save(member);
    }
}