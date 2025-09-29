package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegisterLoanUseCase {

    private final LoanRepositoryPort loanRepository;

    public RegisterLoanUseCase(LoanRepositoryPort loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan execute(String memberId, String bookId) {
        Loan loan = new Loan();
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setMemberId(memberId);
        loan.setBookId(bookId);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));

        return loanRepository.save(loan);
    }
}
