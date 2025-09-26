package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.BookRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final BookJpaRepository bookJpaRepository;

    public BookRepositoryAdapter(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(book.getBookId());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());

        BookEntity savedEntity = bookJpaRepository.save(bookEntity);

        Book result = new Book();
        result.setBookId(savedEntity.getBookId());
        result.setTitle(savedEntity.getTitle());
        result.setAuthor(savedEntity.getAuthor());
        result.setIsbn(savedEntity.getIsbn());

        return result;
    }

    @Override
    public Optional<Book> findById(String bookId) {
        Optional<BookEntity> bookEntityOptional = bookJpaRepository.findById(bookId);
        return bookEntityOptional.map(entity -> {
            Book book = new Book();
            book.setBookId(entity.getBookId());
            book.setTitle(entity.getTitle());
            book.setAuthor(entity.getAuthor());
            book.setIsbn(entity.getIsbn());
            return book;
        });
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Optional<BookEntity> bookEntityOptional = bookJpaRepository.findByIsbn(isbn);
        return bookEntityOptional.map(entity -> {
            Book book = new Book();
            book.setBookId(entity.getBookId());
            book.setTitle(entity.getTitle());
            book.setAuthor(entity.getAuthor());
            book.setIsbn(entity.getIsbn());
            return book;
        });
    }
}
