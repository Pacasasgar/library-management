package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.BookRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindBookByIdUseCase {

    private final BookRepositoryPort bookRepository;

    public FindBookByIdUseCase(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> execute(String bookId) {
        return bookRepository.findById(bookId);
    }
}
