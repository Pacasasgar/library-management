package com.library.librarymanagement.domain;

import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(String bookId);
    Optional<Book> findByIsbn(String isbn);
    void deleteById(String bookId);
}
