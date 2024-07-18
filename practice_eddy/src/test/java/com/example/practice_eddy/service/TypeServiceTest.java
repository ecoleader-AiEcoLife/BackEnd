package com.example.practice_eddy.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.Type;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.example.practice_eddy.repository.TypeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeService typeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addType_Success() {
        TypeDTO typeDTO = new TypeDTO(null, "일반쓰레기", "http://example.com/image.jpg");
        Type type = new Type(1L, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.existsByName(typeDTO.name())).thenReturn(false);
        when(typeRepository.save(any(Type.class))).thenReturn(type);

        TypeDTO result = typeService.addType(typeDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(typeDTO.name(), result.name());
        assertEquals(typeDTO.imgUrl(), result.imgUrl());

        verify(typeRepository).existsByName(typeDTO.name());
        verify(typeRepository).save(any(Type.class));
    }

    @Test
    void addType_DuplicateName() {
        TypeDTO typeDTO = new TypeDTO(null, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.existsByName(typeDTO.name())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> typeService.addType(typeDTO));

        verify(typeRepository).existsByName(typeDTO.name());
        verify(typeRepository, never()).save(any(Type.class));
    }

    @Test
    void getAllTypes() {
        List<Type> types = Arrays.asList(
            new Type(1L, "일반쓰레기", "http://example.com/image1.jpg"),
            new Type(2L, "재활용", "http://example.com/image2.jpg")
        );

        when(typeRepository.findAll()).thenReturn(types);

        List<TypeDTO> result = typeService.getAllTypes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("일반쓰레기", result.get(0).name());
        assertEquals("재활용", result.get(1).name());

        verify(typeRepository).findAll();
    }

    @Test
    void updateType_Success() {
        Long id = 1L;
        TypeDTO typeDTO = new TypeDTO(id, "일반쓰레기(수정)", "http://example.com/image_updated.jpg");
        Type existingType = new Type(id, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.findById(id)).thenReturn(Optional.of(existingType));
        when(typeRepository.existsByName(typeDTO.name())).thenReturn(false);
        when(typeRepository.save(any(Type.class))).thenReturn(existingType);

        TypeDTO result = typeService.updateType(id, typeDTO);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(typeDTO.name(), result.name());
        assertEquals(typeDTO.imgUrl(), result.imgUrl());

        verify(typeRepository).findById(id);
        verify(typeRepository).existsByName(typeDTO.name());
        verify(typeRepository).save(any(Type.class));
    }

    @Test
    void updateType_NotFound() {
        Long id = 1L;
        TypeDTO typeDTO = new TypeDTO(id, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> typeService.updateType(id, typeDTO));

        verify(typeRepository).findById(id);
        verify(typeRepository, never()).existsByName(anyString());
        verify(typeRepository, never()).save(any(Type.class));
    }

    @Test
    void updateType_DuplicateName() {
        Long id = 1L;
        TypeDTO typeDTO = new TypeDTO(id, "재활용", "http://example.com/image.jpg");
        Type existingType = new Type(id, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.findById(id)).thenReturn(Optional.of(existingType));
        when(typeRepository.existsByName(typeDTO.name())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> typeService.updateType(id, typeDTO));

        verify(typeRepository).findById(id);
        verify(typeRepository).existsByName(typeDTO.name());
        verify(typeRepository, never()).save(any(Type.class));
    }

    @Test
    void getTypeById_Success() {
        Long id = 1L;
        Type type = new Type(id, "일반쓰레기", "http://example.com/image.jpg");

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));

        TypeDTO result = typeService.getTypeById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(type.getName(), result.name());
        assertEquals(type.getImgUrl(), result.imgUrl());

        verify(typeRepository).findById(id);
    }

    @Test
    void getTypeById_NotFound() {
        Long id = 1L;

        when(typeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> typeService.getTypeById(id));

        verify(typeRepository).findById(id);
    }

    @Test
    void getTypeByName_Success() {
        String name = "일반쓰레기";
        Type type = new Type(1L, name, "http://example.com/image.jpg");

        when(typeRepository.findByName(name)).thenReturn(Optional.of(type));

        TypeDTO result = typeService.getTypeByName(name);

        assertNotNull(result);
        assertEquals(type.getId(), result.id());
        assertEquals(name, result.name());
        assertEquals(type.getImgUrl(), result.imgUrl());

        verify(typeRepository).findByName(name);
    }

    @Test
    void getTypeByName_NotFound() {
        String name = "존재하지 않는 타입";

        when(typeRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> typeService.getTypeByName(name));

        verify(typeRepository).findByName(name);
    }
}