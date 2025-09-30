package com.library.librarymanagement.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LoanEntity {

    @Id
    private String loanId;

    @ManyToOne
    private MemberEntity member;

    @ManyToOne
    private BookEntity book;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
