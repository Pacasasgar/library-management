package com.library.librarymanagement.infrastructure.web.dto;

public record CreateLoanRequest(String memberId, String bookId) {
}
