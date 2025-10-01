package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import com.library.librarymanagement.infrastructure.persistence.mapper.MemberMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberRepositoryAdapter implements MemberRepositoryPort {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;

    public MemberRepositoryAdapter(MemberJpaRepository memberJpaRepository,
                                   MemberMapper memberMapper) {
        this.memberJpaRepository = memberJpaRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = memberMapper.toEntity(member);
        MemberEntity savedEntity = memberJpaRepository.save(memberEntity);
        return memberMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Optional<MemberEntity> memberEntityOptional = memberJpaRepository.findByEmail(email);
        return memberEntityOptional.map(memberMapper::toDomain);
    }

    @Override
    public Optional<Member> findById(String memberId) {
        Optional<MemberEntity> memberEntityOptional = memberJpaRepository.findById(memberId);
        return memberEntityOptional.map(memberMapper::toDomain);
    }

    @Override
    public void deleteById(String memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}