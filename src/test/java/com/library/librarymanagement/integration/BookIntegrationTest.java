package com.library.librarymanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.infrastructure.web.dto.CreateBookRequest;
import com.library.librarymanagement.infrastructure.web.dto.UpdateBookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerBook_successfully_returns201Created() throws Exception {
        CreateBookRequest request = new CreateBookRequest("Mistborn", "Brandon Sanderson", "0000");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Mistborn")))
                .andExpect(jsonPath("$.author", is("Brandon Sanderson")))
                .andExpect(jsonPath("$.isbn", is("0000")));
    }

    @Test
    void findBookById_successfully_returns200Ok() throws Exception {
        CreateBookRequest createRequest = new CreateBookRequest("Mistborn", "Brandon Sanderson", "0000");
        String createRequestJson = objectMapper.writeValueAsString(createRequest);

        String createdBookJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Book createdBook = objectMapper.readValue(createdBookJson, Book.class);
        String bookId = createdBook.getBookId();

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(bookId)))
                .andExpect(jsonPath("$.title", is("Mistborn")))
                .andExpect(jsonPath("$.author", is("Brandon Sanderson")))
                .andExpect(jsonPath("$.isbn", is("0000")));
    }

    @Test
    void registerBook_returns409Conflict_whenIsbnIsDuplicated() throws Exception {
        CreateBookRequest request = new CreateBookRequest("Mistborn", "Brandon Sanderson", "0000");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteBook_successfully_returns204() throws Exception {
        CreateBookRequest request = new CreateBookRequest("Mistborn", "Brandon Sanderson", "0000");
        String requestJson = objectMapper.writeValueAsString(request);

        String createdBookJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Book createdBook = objectMapper.readValue(createdBookJson, Book.class);
        String bookId = createdBook.getBookId();

        mockMvc.perform(delete("/books/" + bookId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateBookTitle_successfully_returns200Ok() throws Exception {
        CreateBookRequest request = new CreateBookRequest("Mistborn", "Brandon Sanderson", "0000");
        String requestJson = objectMapper.writeValueAsString(request);

        String createdBookJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Book createdBook = objectMapper.readValue(createdBookJson, Book.class);
        String bookId = createdBook.getBookId();
        String bookAuthor = createdBook.getAuthor();
        String bookIsbn = createdBook.getIsbn();

        UpdateBookRequest updateRequest = new UpdateBookRequest("El Imperio Final", bookAuthor, bookIsbn);
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(bookId)))
                .andExpect(jsonPath("$.title", is("El Imperio Final")))
                .andExpect(jsonPath("$.author", is(bookAuthor)))
                .andExpect(jsonPath("$.isbn", is(bookIsbn)));
    }
}
