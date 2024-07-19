package com.example.practice_eddy.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardForm;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    public void testBoardLifecycle() throws Exception {
        TypeDTO createdType = createType();

        // Create
        DisposalBoardForm boardForm = new DisposalBoardForm("테스트 게시글", "내용", "부내용", createdType.id());
        MvcResult result = mockMvc.perform(post("/disboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardForm)))
            .andExpect(status().isCreated())
            .andReturn();

        DisposalBoardDTO createdBoard = objectMapper.readValue(result.getResponse().getContentAsString(), DisposalBoardDTO.class);
        assertNotNull(createdBoard.id());

        // Read
        mockMvc.perform(get("/disboard/{id}", createdBoard.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("테스트 게시글"))
            .andExpect(jsonPath("$.typeId").value(createdType.id()));

        // Update
        DisposalBoardForm updatedBoardForm = new DisposalBoardForm("수정된 게시글", "수정된 내용", "수정된 부내용", createdType.id());
        mockMvc.perform(put("/disboard/{id}", createdBoard.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBoardForm)))
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
