package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindMemberByIdUseCase {

    private final MemberRepositoryPort memberRepository;

    public FindMemberByIdUseCase(MemberRepositoryPort memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(String memberId){
        return memberRepository.findById(memberId);
    }
}
