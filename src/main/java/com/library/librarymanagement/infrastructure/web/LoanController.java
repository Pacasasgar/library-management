package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.RegisterLoanUseCase;
import com.library.librarymanagement.application.ReturnLoanUseCase;
import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.web.dto.CreateLoanRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final RegisterLoanUseCase registerLoanUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;

    public LoanController(RegisterLoanUseCase registerLoanUseCase,
                          ReturnLoanUseCase returnLoanUseCase) {
        this.registerLoanUseCase = registerLoanUseCase;
        this.returnLoanUseCase = returnLoanUseCase;
    }

    @PostMapping
    public ResponseEntity<Loan> register(@RequestBody CreateLoanRequest request) {
        Loan newLoan = registerLoanUseCase.execute(request.memberId(), request.bookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnLoan(@PathVariable("id") String loanId) {
        Loan updatedLoan = returnLoanUseCase.execute(loanId);
        return ResponseEntity.ok(updatedLoan);
    }
}
