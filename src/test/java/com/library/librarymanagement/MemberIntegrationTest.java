package com.library.librarymanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.infrastructure.web.dto.CreateMemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @DirtiesContext elimina el contexto después de cada test (en este caso) para evitar dependencias entre ellos
class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerMember_successfully_returns201Created() throws Exception {
        // 1. Arrange
        CreateMemberRequest request = new CreateMemberRequest("Jane Doe", "jane.doe@example.com");
        String requestJson = objectMapper.writeValueAsString(request);

        // 2. Act & 3. Assert
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId", notNullValue()))
                .andExpect(jsonPath("$.name", is("Jane Doe")))
                .andExpect(jsonPath("$.email", is("jane.doe@example.com")));
    }

    @Test
    void findMemberById_successfully_returns200Ok() throws Exception {
        CreateMemberRequest createRequest = new CreateMemberRequest("Jane Doe", "jane.doe@example.com");
        String createRequestJson = objectMapper.writeValueAsString(createRequest);

        String createdMemberJson = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Member createdMember = objectMapper.readValue(createdMemberJson, Member.class);
        String memberId = createdMember.getMemberId();

        mockMvc.perform(get("/members/" + memberId))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(jsonPath("$.memberId", is(memberId)))
                .andExpect(jsonPath("$.name", is("Jane Doe")));
    }

    @Test
    void registerMember_returns409Conflict_whenEmailIsDuplicated() throws Exception {
        CreateMemberRequest request = new CreateMemberRequest("Jane Doe", "jane.doe@example.com");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }
}