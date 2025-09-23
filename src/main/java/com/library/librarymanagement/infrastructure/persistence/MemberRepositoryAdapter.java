package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Override
    public Optional<Member> findByEmail(String email) {
        Optional<MemberEntity> memberEntityOptional = memberJpaRepository.findByEmail(email);
        return memberEntityOptional.map(entity -> {
            Member member = new Member();
            member.setMemberId(entity.getMemberId());
            member.setName(entity.getName());
            member.setEmail(entity.getEmail());
            return member;
        });
    }

    @Override
    public Optional<Member> findById(String memberId) {
        Optional<MemberEntity> memberEntityOptional = memberJpaRepository.findById(memberId);
        return memberEntityOptional.map(entity -> {
            Member member = new Member();
            member.setMemberId(entity.getMemberId());
            member.setName(entity.getName());
            member.setEmail(entity.getEmail());
            return member;
        });
    }

    @Override
    public void deleteById(String memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}