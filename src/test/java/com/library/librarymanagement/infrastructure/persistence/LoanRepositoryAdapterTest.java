package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.persistence.*;
import com.library.librarymanagement.infrastructure.persistence.mapper.LoanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanRepositoryAdapterTest {

    @InjectMocks
    private LoanRepositoryAdapter loanRepositoryAdapter;

    @Mock
    private LoanJpaRepository loanJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private BookJpaRepository bookJpaRepository;

    @Mock
    private LoanMapper loanMapper;

    @Test
    void findByBookId_returnsSuccessfully_whenBookIsFound() {
        String bookId = "book-123";
        String memberId = "member-abc";
        String loanId = "loan-xyz";

        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookId);

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberId);

        LoanEntity fakeLoanEntity = new LoanEntity();
        fakeLoanEntity.setLoanId(loanId);
        fakeLoanEntity.setBook(bookEntity);
        fakeLoanEntity.setMember(memberEntity);

        Loan expectedLoan = new Loan();
        expectedLoan.setLoanId(loanId);
        expectedLoan.setBookId(bookId);
        expectedLoan.setMemberId(memberId);

        when(loanJpaRepository.findByBook_BookId(bookId)).thenReturn(Optional.of(fakeLoanEntity));
        when(loanMapper.toDomain(fakeLoanEntity)).thenReturn(expectedLoan);

        Optional<Loan> resultOptional = loanRepositoryAdapter.findByBookId(bookId);

        assertTrue(resultOptional.isPresent(), "The returned Optional should not be empty.");

        Loan resultLoan = resultOptional.get();
        assertEquals(expectedLoan.getLoanId(), resultLoan.getLoanId());
        assertEquals(expectedLoan.getBookId(), resultLoan.getBookId());
        assertEquals(expectedLoan.getMemberId(), resultLoan.getMemberId());
    }
}
