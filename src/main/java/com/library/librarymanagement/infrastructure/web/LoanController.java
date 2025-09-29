package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.RegisterLoanUseCase;
import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.web.dto.CreateLoanRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final RegisterLoanUseCase registerLoanUseCase;

    public LoanController(RegisterLoanUseCase registerLoanUseCase) {
        this.registerLoanUseCase = registerLoanUseCase;
    }

    @PostMapping
    public ResponseEntity<Loan> register(@RequestBody CreateLoanRequest request) {
        Loan newLoan = registerLoanUseCase.execute(request.memberId(), request.bookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
    }
}
