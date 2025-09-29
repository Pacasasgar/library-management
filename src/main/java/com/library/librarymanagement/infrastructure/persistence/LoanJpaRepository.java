package com.library.librarymanagement.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanJpaRepository extends JpaRepository<LoanEntity, String> {
    Optional<LoanEntity> findByBook_BookId(String bookId);
}
