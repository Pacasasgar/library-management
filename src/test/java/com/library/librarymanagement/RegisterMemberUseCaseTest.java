package com.library.librarymanagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterMemberUseCaseTest {

    @Test
    void registerNewMember_successfully() {
        // 1. Arrange
        // We set up the "Use Case" or the class we want to test.
        var registerMemberUseCase = new RegisterMemberUseCase();

        // Input data for our use case.
        String name = "John Doe";
        String email = "john.doe@example.com";

        // 2. Act
        // We execute the method we want to test.
        Member newMember = registerMemberUseCase.execute(name, email);

        // 3. Assert
        // We verify that the outcome is what we expected.
        assertNotNull(newMember, "The member should not be null.");
        assertNotNull(newMember.getMemberId(), "The member ID should not be null after registration.");
        assertEquals(name, newMember.getName(), "The member's name does not match.");
        assertEquals(email, newMember.getEmail(), "The member's email does not match.");
    }
}