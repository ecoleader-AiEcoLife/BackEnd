package com.example.practice_eddy.service;


import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.Type;
import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.example.practice_eddy.repository.TypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Transactional
    public TypeDTO addType(TypeDTO typeDTO) {
        if (typeRepository.existsByName(typeDTO.name())) {
            throw new DuplicateResourceException("Type", "name", typeDTO.name());
        }
        Type type = typeRepository.save(typeDTO.toEntity());
        return type.toDTO();
    }

    @Transactional(readOnly = true)
    public List<TypeDTO> getAllTypes() {
        return typeRepository.findAll().stream()
            .map(Type::toDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public TypeDTO updateType(Long id, TypeDTO typeDTO) {
        Type type = typeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id));

        if (!type.getName().equals(typeDTO.name()) && typeRepository.existsByName(typeDTO.name())) {
            throw new DuplicateResourceException("Type", "name", typeDTO.name());
        }

        type.update(typeDTO.name(), typeDTO.imgUrl());
        return typeRepository.save(type).toDTO();
    }

    @Transactional(readOnly = true)
    public boolean isTypeNameDuplicate(String name) {
        return typeRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public TypeDTO getTypeById(Long id) {
        return typeRepository.findById(id)
            .map(Type::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id));
    }

    @Transactional(readOnly = true)
    public TypeDTO getTypeByName(String name) {
        return typeRepository.findByName(name)
            .map(Type::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Type", "name", name));
    }
}