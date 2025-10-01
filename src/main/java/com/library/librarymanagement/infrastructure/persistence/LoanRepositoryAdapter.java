package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.LoanRepositoryPort;
import com.library.librarymanagement.infrastructure.persistence.mapper.LoanMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final BookJpaRepository bookJpaRepository;
    private final LoanMapper loanMapper;

    public LoanRepositoryAdapter(LoanJpaRepository loanJpaRepository,
                                 MemberJpaRepository memberJpaRepository,
                                 BookJpaRepository bookJpaRepository,
                                 LoanMapper loanMapper) {
        this.loanJpaRepository = loanJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public Loan save(Loan loan) {
        MemberEntity memberEntity = memberJpaRepository.findById(loan.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + loan.getMemberId()));

        BookEntity bookEntity = bookJpaRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + loan.getBookId()));

        LoanEntity loanEntity = loanMapper.toEntity(loan);
        loanEntity.setMember(memberEntity);
        loanEntity.setBook(bookEntity);

        LoanEntity savedEntity = loanJpaRepository.save(loanEntity);
        return loanMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Loan> findById(String loanId) {
        Optional<LoanEntity> loanEntityOptional = loanJpaRepository.findById(loanId);
        return loanEntityOptional.map(loanMapper::toDomain);
    }

    @Override
    public Optional<Loan> findByBookId(String bookId) {
        Optional<LoanEntity> loanEntityOptional = loanJpaRepository.findByBook_BookId(bookId);
        return loanEntityOptional.map(loanMapper::toDomain);
    }
}