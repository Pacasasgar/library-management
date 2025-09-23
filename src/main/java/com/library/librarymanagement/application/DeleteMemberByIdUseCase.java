package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteMemberByIdUseCase {

    private final MemberRepositoryPort memberRepository;

    public DeleteMemberByIdUseCase(MemberRepositoryPort memberRepositoryPort) {
        this.memberRepository = memberRepositoryPort;
    }

    public void execute(String memberId) {
        memberRepository.deleteById(memberId);
    }
}
