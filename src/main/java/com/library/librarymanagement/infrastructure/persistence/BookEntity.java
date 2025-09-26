package com.library.librarymanagement.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BookEntity {

    @Id
    private String bookId;
    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;
}
