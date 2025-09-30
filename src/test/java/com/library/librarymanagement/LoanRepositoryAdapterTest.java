package com.library.librarymanagement;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanRepositoryAdapterTest {

    @InjectMocks
    private LoanRepositoryAdapter loanRepositoryAdapter;

    @Mock
    private LoanJpaRepository loanJpaRepository;

    @Test
    void findByBookId_returnsSuccessfully_whenBookIsFound() {
        String bookId = "book-123";
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookId);

        MemberEntity memberEntity = new MemberEntity();
        LoanEntity fakeLoanEntity = new LoanEntity();
        fakeLoanEntity.setLoanId("loan-abc");
        fakeLoanEntity.setBook(bookEntity);
        fakeLoanEntity.setMember(memberEntity);

        when(loanJpaRepository.findByBook_BookId(bookId)).thenReturn(Optional.of(fakeLoanEntity));

        Optional<Loan> resultOptional = loanRepositoryAdapter.findByBookId(bookId);

        assertTrue(resultOptional.isPresent(), "Loan should not be empty.");

    }
}
