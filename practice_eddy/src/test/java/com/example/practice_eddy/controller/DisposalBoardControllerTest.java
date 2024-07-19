package com.example.practice_eddy.controller;

import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardForm;
import com.example.practice_eddy.service.DisposalBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisposalBoardController.class)
public class DisposalBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisposalBoardService disposalBoardService;

    @Autowired
    private ObjectMapper objectMapper;

    private DisposalBoardDTO boardDTO;
    private DisposalBoardForm boardForm;

    @BeforeEach
    void setUp() {
        boardDTO = new DisposalBoardDTO(1L, "제목", "내용", "부내용", 1L);
        boardForm = new DisposalBoardForm("제목", "내용", "부내용", 1L);
    }

    @Test
    void getList_Success() throws Exception {
        List<DisposalBoardDTO> boards = Arrays.asList(
            new DisposalBoardDTO(1L, "제목1", "내용1", "부내용1", 1L),
            new DisposalBoardDTO(2L, "제목2", "내용2", "부내용2", 1L)
        );

        when(disposalBoardService.getBoardList()).thenReturn(boards);

        mockMvc.perform(get("/disboard/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("제목1"))
            .andExpect(jsonPath("$[1].title").value("제목2"));
    }

    @Test
    void getBoard_Success() throws Exception {
        when(disposalBoardService.findBoardById(anyLong())).thenReturn(boardDTO);

        mockMvc.perform(get("/disboard/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    void getBoard_NotFound() throws Exception {
        when(disposalBoardService.findBoardById(anyLong())).thenThrow(new ResourceNotFoundException("DisposalBoard", "id", 1L));

        mockMvc.perform(get("/disboard/{id}", 1))
            .andExpect(status().isNotFound());
    }

    @Test
    void createBoard_Success() throws Exception {
        when(disposalBoardService.insertBoard(any(DisposalBoardDTO.class))).thenReturn(boardDTO);

        mockMvc.perform(post("/disboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardForm)))
            .andExpect(status().isCreated());
    }

    @Test
    void createBoard_DuplicateTitle() throws Exception {
        when(disposalBoardService.insertBoard(any(DisposalBoardDTO.class))).thenThrow(new DuplicateResourceException("DisposalBoard", "title", boardForm.getTitle()));

        mockMvc.perform(post("/disboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardForm)))
            .andExpect(status().isConflict());
    }

    @Test
    void updateBoard_Success() throws Exception {
        when(disposalBoardService.updateDisposalBoard(anyLong(), any(DisposalBoardDTO.class))).thenReturn(boardDTO);

        mockMvc.perform(put("/disboard/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardForm)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    void updateBoard_NotFound() throws Exception {
        when(disposalBoardService.updateDisposalBoard(anyLong(), any(DisposalBoardDTO.class))).thenThrow(new ResourceNotFoundException("DisposalBoard", "id", 1L));

        mockMvc.perform(put("/disboard/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardForm)))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteBoard_Success() throws Exception {
        mockMvc.perform(delete("/disboard/{id}", 1))
            .andExpect(status().isOk());
    }

    @Test
    void deleteBoard_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("DisposalBoard", "id", 1L)).when(disposalBoardService).deleteDisposalBoard(anyLong());

        mockMvc.perform(delete("/disboard/{id}", 1))
            .andExpect(status().isNotFound());
    }
}