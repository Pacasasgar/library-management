package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.FindBookByIdUseCase;
import com.library.librarymanagement.application.RegisterBookUseCase;
import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.infrastructure.web.dto.CreateBookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final FindBookByIdUseCase findBookByIdUseCase;

    public BookController(RegisterBookUseCase registerBookUseCase,
                          FindBookByIdUseCase findBookByIdUseCase) {
        this.registerBookUseCase = registerBookUseCase;
        this.findBookByIdUseCase = findBookByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Book> register(@RequestBody CreateBookRequest request) {
        Book newBook = registerBookUseCase.execute(request.title(), request.author(), request.isbn());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") String bookId) {
        Optional<Book> bookOptional = findBookByIdUseCase.execute(bookId);

        return bookOptional
                .map(book -> ResponseEntity.ok(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
