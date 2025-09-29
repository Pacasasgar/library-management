package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + loan.getMemberId()));

        BookEntity bookEntity = bookJpaRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + loan.getBookId()));

        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setLoanId(loan.getLoanId());
        loanEntity.setMember(memberEntity);
        loanEntity.setBook(bookEntity);
        loanEntity.setLoanDate(loan.getLoanDate());
        loanEntity.setDueDate(loan.getDueDate());

        LoanEntity savedEntity = loanJpaRepository.save(loanEntity);

        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Loan> findByBookId(String bookId) {
        return Optional.empty();
    }

    private Loan mapToDomain(LoanEntity entity) {
        Loan domainLoan = new Loan();
        domainLoan.setLoanId(entity.getLoanId());
        domainLoan.setMemberId(entity.getMember().getMemberId());
        domainLoan.setBookId(entity.getBook().getBookId());
        domainLoan.setLoanDate(entity.getLoanDate());
        domainLoan.setDueDate(entity.getDueDate());
        return domainLoan;
    }
}