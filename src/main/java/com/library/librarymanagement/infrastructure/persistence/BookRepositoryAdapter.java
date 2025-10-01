package com.library.librarymanagement.infrastructure.persistence;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.BookRepositoryPort;
import com.library.librarymanagement.infrastructure.persistence.mapper.BookMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final BookJpaRepository bookJpaRepository;
    private final BookMapper bookMapper;

    public BookRepositoryAdapter(BookJpaRepository bookJpaRepository,
                                 BookMapper bookMapper) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = bookMapper.toEntity(book);
        BookEntity savedEntity = bookJpaRepository.save(bookEntity);
        return bookMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Book> findById(String bookId) {
        Optional<BookEntity> bookEntityOptional = bookJpaRepository.findById(bookId);
        return bookEntityOptional.map(bookMapper::toDomain);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Optional<BookEntity> bookEntityOptional = bookJpaRepository.findByIsbn(isbn);
        return bookEntityOptional.map(bookMapper::toDomain);
    }

    @Override
    public void deleteById(String bookId) {
        bookJpaRepository.deleteById(bookId);
    }
}
