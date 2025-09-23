package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import com.library.librarymanagement.infrastructure.web.dto.UpdateMemberRequest;
import org.springframework.stereotype.Service;

@Service
public class UpdateMemberUseCase {

    private final MemberRepositoryPort memberRepository;

    public UpdateMemberUseCase(MemberRepositoryPort memberRepositoryPort) {
        this.memberRepository = memberRepositoryPort;
    }

    public Member execute(String memberId, UpdateMemberRequest request) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        existingMember.setName(request.name());
        existingMember.setEmail(request.email());

        return memberRepository.save(existingMember);
    }
}
