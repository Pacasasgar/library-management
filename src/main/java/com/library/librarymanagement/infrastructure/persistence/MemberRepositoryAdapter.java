package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class MemberRepositoryAdapter implements MemberRepositoryPort {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryAdapter(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(member.getMemberId());
        memberEntity.setName(member.getName());
        memberEntity.setEmail(member.getEmail());

        MemberEntity savedEntity = memberJpaRepository.save(memberEntity);

        Member result = new Member();
        result.setMemberId(savedEntity.getMemberId());
        result.setName(savedEntity.getName());
        result.setEmail(savedEntity.getEmail());

        return result;
    }
}