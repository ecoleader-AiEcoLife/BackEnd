package com.example.practice_eddy.service;

import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.mark.Mark;
import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.repository.MarkRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarkService {

    private final MarkRepository markRepository;

    @Autowired
    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Transactional(readOnly = true)
    public List<MarkDTO> getAllMarks() {
        return markRepository.findAll().stream()
            .map(Mark::toDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public MarkDTO addMark(MarkDTO markDTO) {
        Mark mark = markDTO.toEntity();
        mark = markRepository.save(mark);
        return mark.toDTO();
    }

    @Transactional
    public MarkDTO updateMark(Long id, MarkDTO markDTO) {
        Mark existingMark = markRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mark", "id", id));

        existingMark.update(
            markDTO.getTitle(),
            markDTO.getLat(),
            markDTO.getLng(),
            markDTO.getLocation(),
            markDTO.getImgUrl()
        );

        Mark updatedMark = markRepository.save(existingMark);
        return updatedMark.toDTO();
    }

    @Transactional
    public void deleteMark(Long id) {
        if (!markRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mark", "id", id);
        }
        markRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MarkDTO getMarkById(Long id) {
        return markRepository.findById(id)
            .map(Mark::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Mark", "id", id));
    }
}