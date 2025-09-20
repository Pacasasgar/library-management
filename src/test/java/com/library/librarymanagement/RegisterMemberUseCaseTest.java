package com.library.librarymanagement;

import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterMemberUseCaseTest {

    @Mock
    private MemberRepositoryPort memberRepository;

    @InjectMocks
    private RegisterMemberUseCase registerMemberUseCase;

    @Test
    void registerNewMember_successfully() {
        String name = "John Doe";
        String email = "john.doe@example.com";

        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member newMember = registerMemberUseCase.execute(name, email);

        assertNotNull(newMember, "The member should not be null.");
        assertNotNull(newMember.getMemberId(), "The member ID should not be null after registration.");
        assertEquals(name, newMember.getName(), "The member's name does not match.");
        assertEquals(email, newMember.getEmail(), "The member's email does not match.");
    }
}