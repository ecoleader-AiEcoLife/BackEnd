package com.example.practice_eddy.controller;

import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.service.MarkService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MarkController.class)
public class MarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarkService markService;

    @Autowired
    private ObjectMapper objectMapper;

    private MarkDTO markDTO;

    @BeforeEach
    void setUp() {
        markDTO = new MarkDTO(1L, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");
    }

    @Test
    void getAllMarks_Success() throws Exception {
        List<MarkDTO> marks = Arrays.asList(
            new MarkDTO(1L, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image1.jpg"),
            new MarkDTO(2L, "경복궁", 37.579617f, 126.977041f, "서울특별시 종로구 세종로 1-1", "http://example.com/image2.jpg")
        );

        when(markService.getAllMarks()).thenReturn(marks);

        mockMvc.perform(get("/map/marks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("서울타워"))
            .andExpect(jsonPath("$[1].title").value("경복궁"));
    }

    @Test
    void addMark_Success() throws Exception {
        when(markService.addMark(any(MarkDTO.class))).thenReturn(markDTO);

        mockMvc.perform(post("/map/mark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(markDTO)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("서울타워"));
    }

    @Test
    void updateMark_Success() throws Exception {
        when(markService.updateMark(anyLong(), any(MarkDTO.class))).thenReturn(markDTO);

        mockMvc.perform(put("/map/mark/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(markDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("서울타워"));
    }

    @Test
    void updateMark_NotFound() throws Exception {
        when(markService.updateMark(anyLong(), any(MarkDTO.class))).thenThrow(new ResourceNotFoundException("Mark", "id", 1L));

        mockMvc.perform(put("/map/mark/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(markDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteMark_Success() throws Exception {
        doNothing().when(markService).deleteMark(anyLong());

        mockMvc.perform(delete("/map/mark/{id}", 1))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteMark_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Mark", "id", 1L)).when(markService).deleteMark(anyLong());

        mockMvc.perform(delete("/map/mark/{id}", 1))
            .andExpect(status().isNotFound());
    }

    @Test
    void getMarkById_Success() throws Exception {
        when(markService.getMarkById(anyLong())).thenReturn(markDTO);

        mockMvc.perform(get("/map/mark/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("서울타워"));
    }

    @Test
    void getMarkById_NotFound() throws Exception {
        when(markService.getMarkById(anyLong())).thenThrow(new ResourceNotFoundException("Mark", "id", 1L));

        mockMvc.perform(get("/map/mark/{id}", 1))
            .andExpect(status().isNotFound());
    }
}