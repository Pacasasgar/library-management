package com.library.librarymanagement.domain;

import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Optional<Loan> findById(String loanId);
    Optional<Loan> findByBookId(String bookId);
}
