package com.library.librarymanagement.infrastructure.web.dto;

public record CreateBookRequest(String title, String author, String isbn) {
}
