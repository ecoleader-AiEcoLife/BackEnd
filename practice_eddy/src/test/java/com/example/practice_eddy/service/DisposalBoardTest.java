package com.example.practice_eddy.service;

import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.DisposalBoard;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.model.disposalBoard.Type;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.example.practice_eddy.repository.DisposalBoardRepository;
import com.example.practice_eddy.repository.TypeRepository;
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

class DisposalBoardServiceTest {

    @Mock
    private DisposalBoardRepository disposalBoardRepository;

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private DisposalBoardService disposalBoardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertBoard_Success() {
        TypeDTO typeDTO = new TypeDTO(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoardDTO boardDTO = new DisposalBoardDTO(null, "제목", "내용", "부내용", typeDTO);
        Type type = new Type(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoard board = new DisposalBoard(1L, "제목", "내용", "부내용", type);

        when(disposalBoardRepository.findByTitle(boardDTO.title())).thenReturn(Optional.empty());
        when(typeRepository.findById(typeDTO.id())).thenReturn(Optional.of(type));
        when(disposalBoardRepository.save(any(DisposalBoard.class))).thenReturn(board);

        DisposalBoardDTO result = disposalBoardService.insertBoard(boardDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(boardDTO.title(), result.title());
        assertEquals(boardDTO.content(), result.content());
        assertEquals(boardDTO.subContent(), result.subContent());
        assertEquals(typeDTO.id(), result.type().id());

        verify(disposalBoardRepository).findByTitle(boardDTO.title());
        verify(typeRepository).findById(typeDTO.id());
        verify(disposalBoardRepository).save(any(DisposalBoard.class));
    }

    @Test
    void insertBoard_DuplicateTitle() {
        TypeDTO typeDTO = new TypeDTO(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoardDTO boardDTO = new DisposalBoardDTO(null, "제목", "내용", "부내용", typeDTO);

        when(disposalBoardRepository.findByTitle(boardDTO.title())).thenReturn(Optional.of(new DisposalBoard()));

        assertThrows(DuplicateResourceException.class, () -> disposalBoardService.insertBoard(boardDTO));

        verify(disposalBoardRepository).findByTitle(boardDTO.title());
        verify(typeRepository, never()).findById(any());
        verify(disposalBoardRepository, never()).save(any());
    }

    @Test
    void findBoardById_Success() {
        Long id = 1L;
        Type type = new Type(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoard board = new DisposalBoard(id, "제목", "내용", "부내용", type);

        when(disposalBoardRepository.findById(id)).thenReturn(Optional.of(board));

        DisposalBoardDTO result = disposalBoardService.findBoardById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(board.getTitle(), result.title());
        assertEquals(board.getContent(), result.content());
        assertEquals(board.getSubContent(), result.subContent());
        assertEquals(type.getId(), result.type().id());

        verify(disposalBoardRepository).findById(id);
    }

    @Test
    void findBoardById_NotFound() {
        Long id = 1L;

        when(disposalBoardRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> disposalBoardService.findBoardById(id));

        verify(disposalBoardRepository).findById(id);
    }

    @Test
    void getBoardList() {
        Type type1 = new Type(1L, "일반쓰레기", "http://example.com/image1.jpg");
        Type type2 = new Type(2L, "재활용", "http://example.com/image2.jpg");
        List<DisposalBoard> boards = Arrays.asList(
            new DisposalBoard(1L, "제목1", "내용1", "부내용1", type1),
            new DisposalBoard(2L, "제목2", "내용2", "부내용2", type2)
        );

        when(disposalBoardRepository.findAll()).thenReturn(boards);

        List<DisposalBoardDTO> result = disposalBoardService.getBoardList();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("제목1", result.get(0).title());
        assertEquals("제목2", result.get(1).title());

        verify(disposalBoardRepository).findAll();
    }

    @Test
    void updateDisposalBoard_Success() {
        Long id = 1L;
        TypeDTO typeDTO = new TypeDTO(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoardDTO boardDTO = new DisposalBoardDTO(id, "수정된 제목", "수정된 내용", "수정된 부내용", typeDTO);
        Type type = new Type(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoard existingBoard = new DisposalBoard(id, "원래 제목", "원래 내용", "원래 부내용", type);

        when(disposalBoardRepository.findById(id)).thenReturn(Optional.of(existingBoard));
        when(disposalBoardRepository.findByTitle(boardDTO.title())).thenReturn(Optional.empty());
        when(typeRepository.findById(typeDTO.id())).thenReturn(Optional.of(type));

        DisposalBoardDTO result = disposalBoardService.updateDisposalBoard(id, boardDTO);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(boardDTO.title(), result.title());
        assertEquals(boardDTO.content(), result.content());
        assertEquals(boardDTO.subContent(), result.subContent());
        assertEquals(typeDTO.id(), result.type().id());

        verify(disposalBoardRepository).findById(id);
        verify(disposalBoardRepository).findByTitle(boardDTO.title());
        verify(typeRepository).findById(typeDTO.id());
    }

    @Test
    void updateDisposalBoard_NotFound() {
        Long id = 1L;
        TypeDTO typeDTO = new TypeDTO(1L, "일반쓰레기", "http://example.com/image.jpg");
        DisposalBoardDTO boardDTO = new DisposalBoardDTO(id, "수정된 제목", "수정된 내용", "수정된 부내용", typeDTO);

        when(disposalBoardRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> disposalBoardService.updateDisposalBoard(id, boardDTO));

        verify(disposalBoardRepository).findById(id);
        verify(disposalBoardRepository, never()).findByTitle(any());
        verify(typeRepository, never()).findById(any());
    }

    @Test
    void deleteDisposalBoard_Success() {
        Long id = 1L;

        when(disposalBoardRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> disposalBoardService.deleteDisposalBoard(id));

        verify(disposalBoardRepository).existsById(id);
        verify(disposalBoardRepository).deleteById(id);
    }

    @Test
    void deleteDisposalBoard_NotFound() {
        Long id = 1L;

        when(disposalBoardRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> disposalBoardService.deleteDisposalBoard(id));

        verify(disposalBoardRepository).existsById(id);
        verify(disposalBoardRepository, never()).deleteById(any());
    }
}