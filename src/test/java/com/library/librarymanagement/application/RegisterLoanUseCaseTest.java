package com.library.librarymanagement.application;

import com.library.librarymanagement.application.RegisterLoanUseCase;
import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterLoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepository;

    @InjectMocks
    private RegisterLoanUseCase registerLoanUseCase;

    @Test
    void execute_throwsException_whenBookIsAlreadyOnLoan() {
        String memberId = "member-123";
        String bookId = "book-456";
        Loan existingLoan = new Loan();

        when(loanRepository.findByBookId(bookId)).thenReturn(Optional.of(existingLoan));

        assertThrows(IllegalStateException.class, () -> {
            registerLoanUseCase.execute(memberId, bookId);
        });
    }
}