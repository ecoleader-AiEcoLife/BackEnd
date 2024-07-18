package com.example.practice_eddy.service;

import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.mark.Mark;
import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.repository.MarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkServiceTest {

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private MarkService markService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMarks() {
        List<Mark> marks = Arrays.asList(
            new Mark(1L, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image1.jpg"),
            new Mark(2L, "경복궁", 37.579617f, 126.977041f, "서울특별시 종로구 세종로 1-1", "http://example.com/image2.jpg")
        );

        when(markRepository.findAll()).thenReturn(marks);

        List<MarkDTO> result = markService.getAllMarks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("서울타워", result.get(0).getTitle());
        assertEquals("경복궁", result.get(1).getTitle());

        verify(markRepository).findAll();
    }

    @Test
    void addMark_Success() {
        MarkDTO markDTO = new MarkDTO(null, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");
        Mark mark = new Mark(1L, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");

        when(markRepository.save(any(Mark.class))).thenReturn(mark);

        MarkDTO result = markService.addMark(markDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(markDTO.getTitle(), result.getTitle());
        assertEquals(markDTO.getLat(), result.getLat());
        assertEquals(markDTO.getLng(), result.getLng());
        assertEquals(markDTO.getLocation(), result.getLocation());
        assertEquals(markDTO.getImgUrl(), result.getImgUrl());

        verify(markRepository).save(any(Mark.class));
    }

    @Test
    void updateMark_Success() {
        Long id = 1L;
        MarkDTO markDTO = new MarkDTO(id, "서울타워(수정)", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동(수정)", "http://example.com/image_updated.jpg");
        Mark existingMark = new Mark(id, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");

        when(markRepository.findById(id)).thenReturn(Optional.of(existingMark));
        when(markRepository.save(any(Mark.class))).thenReturn(existingMark);

        MarkDTO result = markService.updateMark(id, markDTO);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(markDTO.getTitle(), result.getTitle());
        assertEquals(markDTO.getLat(), result.getLat());
        assertEquals(markDTO.getLng(), result.getLng());
        assertEquals(markDTO.getLocation(), result.getLocation());
        assertEquals(markDTO.getImgUrl(), result.getImgUrl());

        verify(markRepository).findById(id);
        verify(markRepository).save(any(Mark.class));
    }

    @Test
    void updateMark_NotFound() {
        Long id = 1L;
        MarkDTO markDTO = new MarkDTO(id, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");

        when(markRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> markService.updateMark(id, markDTO));

        verify(markRepository).findById(id);
        verify(markRepository, never()).save(any(Mark.class));
    }

    @Test
    void deleteMark_Success() {
        Long id = 1L;

        when(markRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> markService.deleteMark(id));

        verify(markRepository).existsById(id);
        verify(markRepository).deleteById(id);
    }

    @Test
    void deleteMark_NotFound() {
        Long id = 1L;

        when(markRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> markService.deleteMark(id));

        verify(markRepository).existsById(id);
        verify(markRepository, never()).deleteById(any());
    }

    @Test
    void getMarkById_Success() {
        Long id = 1L;
        Mark mark = new Mark(id, "서울타워", 37.551176f, 126.988449f, "서울특별시 용산구 용산2가동", "http://example.com/image.jpg");

        when(markRepository.findById(id)).thenReturn(Optional.of(mark));

        MarkDTO result = markService.getMarkById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(mark.getTitle(), result.getTitle());
        assertEquals(mark.getLat(), result.getLat());
        assertEquals(mark.getLng(), result.getLng());
        assertEquals(mark.getLocation(), result.getLocation());
        assertEquals(mark.getImgUrl(), result.getImgUrl());

        verify(markRepository).findById(id);
    }

    @Test
    void getMarkById_NotFound() {
        Long id = 1L;

        when(markRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> markService.getMarkById(id));

        verify(markRepository).findById(id);
    }
}