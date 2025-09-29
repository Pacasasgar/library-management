package com.library.librarymanagement.domain;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Loan {
    private String loanId;
    private String bookId;
    private String memberId;
    private LocalDate loanDate;
    private LocalDate dueDate;
}