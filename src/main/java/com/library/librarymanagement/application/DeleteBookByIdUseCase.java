package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.BookRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteBookByIdUseCase {

    private final BookRepositoryPort bookRepository;

    public DeleteBookByIdUseCase(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void execute(String bookId) {
        bookRepository.deleteById(bookId);
    }
}
