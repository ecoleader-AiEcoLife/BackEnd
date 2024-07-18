package com.example.practice_eddy.integration;

import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DisposalBoardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TypeDTO createType() throws Exception {
        TypeDTO typeDTO = new TypeDTO(null, "테스트 타입", "http://example.com/image.jpg");
        MvcResult result = mockMvc.perform(post("/disboard/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDTO)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), TypeDTO.class);
    }

    @Test
    public void testTypeLifecycle() throws Exception {
        // Create
        TypeDTO createdType = createType();
        assertNotNull(createdType.id());

        // Read
        mockMvc.perform(get("/disboard/type/{id}", createdType.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("테스트 타입"));

        // Update
        TypeDTO updatedTypeDTO = new TypeDTO(createdType.id(), "수정된 타입", "http://example.com/updated_image.jpg");
        mockMvc.perform(put("/disboard/type/{id}", createdType.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTypeDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("수정된 타입"));

        // List
        mockMvc.perform(get("/disboard/type/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("수정된 타입"));
    }

    @Test
    public void testBoardLifecycle() throws Exception {
        TypeDTO createdType = createType();

        // Create
        DisposalBoardDTO boardDTO = new DisposalBoardDTO(null, "테스트 게시글", "내용", "부내용", createdType);
        MvcResult result = mockMvc.perform(post("/disboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardDTO)))
            .andExpect(status().isCreated())
            .andReturn();

        DisposalBoardDTO createdBoard = objectMapper.readValue(result.getResponse().getContentAsString(), DisposalBoardDTO.class);
        assertNotNull(createdBoard.id());

        // Read
        mockMvc.perform(get("/disboard/{id}", createdBoard.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("테스트 게시글"))
            .andExpect(jsonPath("$.type.id").value(createdType.id()));

        // Update
        DisposalBoardDTO updatedBoardDTO = new DisposalBoardDTO(createdBoard.id(), "수정된 게시글", "수정된 내용", "수정된 부내용", createdType);
        mockMvc.perform(put("/disboard/{id}", createdBoard.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBoardDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("수정된 게시글"));

        // Delete
        mockMvc.perform(delete("/disboard/{id}", createdBoard.id()))
            .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/disboard/{id}", createdBoard.id()))
            .andExpect(status().isNotFound());
    }
}