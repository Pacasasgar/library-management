package com.library.librarymanagement;

import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    void registerNewMember_throwsException_whenEmailIsInvalid() {
        String invalidEmail = "invalid-email";
        String name = "Jane Doe";

        assertThrows(IllegalArgumentException.class, () -> {
            registerMemberUseCase.execute(name, invalidEmail);
        });
    }

    @Test
    void registerNewMember_throwsException_whenEmailAlreadyExists() {
        String existingEmail = "john.doe@example.com";
        String name = "Johnathan Doe";

        Member existingMember = new Member();
        existingMember.setMemberId("some-id");
        existingMember.setName("John Doe");
        existingMember.setEmail(existingEmail);

        when(memberRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingMember));

        assertThrows(IllegalArgumentException.class, () -> {
            registerMemberUseCase.execute(name, existingEmail);
        });
    }
}