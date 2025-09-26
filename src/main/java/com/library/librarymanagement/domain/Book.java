package com.library.librarymanagement.domain;

import lombok.Data;

@Data
public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
}
