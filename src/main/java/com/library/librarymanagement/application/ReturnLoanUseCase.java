package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnLoanUseCase {

    private final LoanRepositoryPort loanRepository;

    public ReturnLoanUseCase(LoanRepositoryPort loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan execute(String loanId) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (LocalDate.now().isAfter(existingLoan.getDueDate())) {
            System.out.println("PRE-KAFKA EVENT: Book returned late! LoanId: " + loanId);
        }

        existingLoan.setReturnDate(LocalDate.now());

        return loanRepository.save(existingLoan);
    }
}
