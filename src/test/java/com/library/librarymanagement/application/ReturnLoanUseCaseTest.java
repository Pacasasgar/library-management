package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReturnLoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepository;

    @InjectMocks
    private ReturnLoanUseCase returnLoanUseCase;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_printsLateReturnEvent_whenBookIsReturnedLate() {
        String loanId = "loan-123";
        LocalDate dueDate = LocalDate.now().minusDays(3);
        Loan lateLoan = new Loan();
        lateLoan.setLoanId(loanId);
        lateLoan.setDueDate(dueDate);


        when(loanRepository.findById(loanId)).thenReturn(Optional.of(lateLoan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        returnLoanUseCase.execute(loanId);

        assertTrue(outContent.toString().contains("PRE-KAFKA EVENT: Book returned late! LoanId: " + loanId));
    }
}
