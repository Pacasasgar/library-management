package com.library.librarymanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.infrastructure.web.dto.CreateBookRequest;
import com.library.librarymanagement.infrastructure.web.dto.CreateLoanRequest;
import com.library.librarymanagement.infrastructure.web.dto.CreateMemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LoanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerLoan_successfully_returns201Created() throws Exception {
        Member createdMember = createMemberViaApi(new CreateMemberRequest("John Doe", "john.doe@example.com"));
        Book createdBook = createBookViaApi(new CreateBookRequest("Mistborn", "Brandon Sanderson", "12345"));

        CreateLoanRequest loanRequest = new CreateLoanRequest(createdMember.getMemberId(), createdBook.getBookId());
        String loanRequestJson = objectMapper.writeValueAsString(loanRequest);

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.loanId", notNullValue()))
                .andExpect(jsonPath("$.memberId", is(createdMember.getMemberId())))
                .andExpect(jsonPath("$.bookId", is(createdBook.getBookId())));
    }

    @Test
    void returnLoan_successfully_updatesReturnDateAndReturns200Ok() throws Exception {
        Member createdMember = createMemberViaApi(new CreateMemberRequest("John Doe", "john.doe@example.com"));
        Book createdBook = createBookViaApi(new CreateBookRequest("Mistborn", "Brandon Sanderson", "12345"));
        Loan createdLoan = createLoanViaApi(new CreateLoanRequest(createdMember.getMemberId(), createdBook.getBookId()));
        String loanId = createdLoan.getLoanId();

        mockMvc.perform(put("/loans/" + loanId + "/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId", is(loanId)))
                .andExpect(jsonPath("$.returnDate", notNullValue()));
    }

    private Member createMemberViaApi(CreateMemberRequest request) throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseJson, Member.class);
    }

    private Book createBookViaApi(CreateBookRequest request) throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseJson, Book.class);
    }

    private Loan createLoanViaApi(CreateLoanRequest request) throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseJson, Loan.class);
    }
}
