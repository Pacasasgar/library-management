package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.DeleteBookByIdUseCase;
import com.library.librarymanagement.application.FindBookByIdUseCase;
import com.library.librarymanagement.application.RegisterBookUseCase;
import com.library.librarymanagement.application.UpdateBookUseCase;
import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.infrastructure.web.dto.CreateBookRequest;
import com.library.librarymanagement.infrastructure.web.dto.UpdateBookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final FindBookByIdUseCase findBookByIdUseCase;
    private final DeleteBookByIdUseCase deleteBookByIdUseCase;
    private final UpdateBookUseCase updateBookUseCase;

    public BookController(RegisterBookUseCase registerBookUseCase,
                          FindBookByIdUseCase findBookByIdUseCase,
                          DeleteBookByIdUseCase deleteBookByIdUseCase,
                          UpdateBookUseCase updateBookUseCase) {
        this.registerBookUseCase = registerBookUseCase;
        this.findBookByIdUseCase = findBookByIdUseCase;
        this.deleteBookByIdUseCase = deleteBookByIdUseCase;
        this.updateBookUseCase = updateBookUseCase;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String bookId) {
        deleteBookByIdUseCase.execute(bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateById(@PathVariable("id") String bookId,
                                           @RequestBody UpdateBookRequest request) {
        Book updatedBook = updateBookUseCase.execute(bookId, request);
        return ResponseEntity.ok(updatedBook);
    }
}
