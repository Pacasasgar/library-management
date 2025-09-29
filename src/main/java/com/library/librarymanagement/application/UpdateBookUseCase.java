package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.BookRepositoryPort;
import com.library.librarymanagement.infrastructure.web.dto.UpdateBookRequest;
import org.springframework.stereotype.Service;

@Service
public class UpdateBookUseCase {

    private final BookRepositoryPort bookRepository;

    public UpdateBookUseCase(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book execute(String bookId, UpdateBookRequest request) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setTitle(request.title());
        existingBook.setAuthor(request.author());
        existingBook.setIsbn(request.isbn());

        return bookRepository.save(existingBook);
    }
}
