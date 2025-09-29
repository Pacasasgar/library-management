package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    public LoanRepositoryAdapter(LoanJpaRepository loanJpaRepository,
                                 MemberJpaRepository memberJpaRepository,
                                 BookJpaRepository bookJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public Loan save(Loan loan) {
        MemberEntity memberEntity = memberJpaRepository.findById(loan.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BookEntity bookEntity = bookJpaRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setLoanId(loan.getLoanId());
        loanEntity.setMember(memberEntity);
        loanEntity.setBook(bookEntity);
        loanEntity.setLoanDate(loan.getLoanDate());
        loanEntity.setDueDate(loan.getDueDate());

        LoanEntity savedEntity = loanJpaRepository.save(loanEntity);

        // We can skip mapping back for now as the controller doesn't need it
        // but in a real app, you would map savedEntity back to a Loan domain object.
        return loan;
    }
}