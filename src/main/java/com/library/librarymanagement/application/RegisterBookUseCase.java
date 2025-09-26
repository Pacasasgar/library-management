package com.library.librarymanagement.application;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.BookRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterBookUseCase {

    private final BookRepositoryPort bookRepository;

    public RegisterBookUseCase(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book execute(String title, String author, String isbn) {
        Book book = new Book();
        book.setBookId(UUID.randomUUID().toString());
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);

        return bookRepository.save(book);
    }
}
