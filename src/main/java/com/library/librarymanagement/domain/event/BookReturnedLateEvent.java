package com.library.librarymanagement.domain.event;

import java.time.LocalDate;

public record BookReturnedLateEvent(
        String loanId,
        String memberId,
        String bookId,
        LocalDate dueDate,
        LocalDate returnDate
) {}