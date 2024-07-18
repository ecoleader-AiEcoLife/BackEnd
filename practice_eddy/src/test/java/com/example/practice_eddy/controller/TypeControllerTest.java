package com.example.practice_eddy.controller;

import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.example.practice_eddy.service.TypeService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeController.class)
public class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TypeService typeService;

    @Autowired
    private ObjectMapper objectMapper;

    private TypeDTO typeDTO;

    @BeforeEach
    void setUp() {
        typeDTO = new TypeDTO(1L, "일반쓰레기", "http://example.com/image.jpg");
    }

    @Test
    void getAllTypes_Success() throws Exception {
        List<TypeDTO> types = Arrays.asList(
            new TypeDTO(1L, "일반쓰레기", "http://example.com/image1.jpg"),
            new TypeDTO(2L, "재활용", "http://example.com/image2.jpg")
        );

        when(typeService.getAllTypes()).thenReturn(types);

        mockMvc.perform(get("/disboard/type/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("일반쓰레기"))
            .andExpect(jsonPath("$[1].name").value("재활용"));
    }

    @Test
    void addType_Success() throws Exception {
        when(typeService.addType(any(TypeDTO.class))).thenReturn(typeDTO);

        mockMvc.perform(post("/disboard/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDTO)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("일반쓰레기"));
    }

    @Test
    void addType_DuplicateName() throws Exception {
        when(typeService.addType(any(TypeDTO.class))).thenThrow(new DuplicateResourceException("Type", "name", typeDTO.name()));

        mockMvc.perform(post("/disboard/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDTO)))
            .andExpect(status().isConflict());
    }

    @Test
    void updateType_Success() throws Exception {
        when(typeService.updateType(anyLong(), any(TypeDTO.class))).thenReturn(typeDTO);

        mockMvc.perform(put("/disboard/type/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("일반쓰레기"));
    }

    @Test
    void updateType_NotFound() throws Exception {
        when(typeService.updateType(anyLong(), any(TypeDTO.class))).thenThrow(new ResourceNotFoundException("Type", "id", 1L));

        mockMvc.perform(put("/disboard/type/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    void getTypeById_Success() throws Exception {
        when(typeService.getTypeById(anyLong())).thenReturn(typeDTO);

        mockMvc.perform(get("/disboard/type/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("일반쓰레기"));
    }

    @Test
    void getTypeById_NotFound() throws Exception {
        when(typeService.getTypeById(anyLong())).thenThrow(new ResourceNotFoundException("Type", "id", 1L));

        mockMvc.perform(get("/disboard/type/{id}", 1))
            .andExpect(status().isNotFound());
    }

    @Test
    void getTypeByName_Success() throws Exception {
        when(typeService.getTypeByName(any(String.class))).thenReturn(typeDTO);

        mockMvc.perform(get("/disboard/type/by-name").param("name", "일반쓰레기"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("일반쓰레기"));
    }

    @Test
    void getTypeByName_NotFound() throws Exception {
        when(typeService.getTypeByName(any(String.class))).thenThrow(new ResourceNotFoundException("Type", "name", "일반쓰레기"));

        mockMvc.perform(get("/disboard/type/by-name").param("name", "일반쓰레기"))
            .andExpect(status().isNotFound());
    }
}