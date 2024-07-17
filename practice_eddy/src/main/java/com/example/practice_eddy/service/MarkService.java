package com.example.practice_eddy.service;

import com.example.practice_eddy.model.mark.Mark;
import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.repository.MarkRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    private final MarkRepository markRepository;

    @Autowired
    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Operation(summary = "모든 마커 조회", description = "저장된 모든 마커를 조회합니다.")
    public List<MarkDTO> getAllMarks() {
        return markRepository.findAll().stream()
            .map(Mark::toDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "마커 추가", description = "새로운 마커를 추가합니다.")
    @Transactional
    public Long addMark(@Parameter(description = "추가할 마커 정보") MarkDTO markDTO) {
        Mark mark = markDTO.toEntity();
        mark = markRepository.save(mark);
        return mark.getId();
    }

    @Operation(summary = "마커 수정", description = "기존 마커를 수정합니다.")
    @Transactional
    public Long updateMark(@Parameter(description = "수정할 마커 ID") Long id,
        @Parameter(description = "수정할 마커 정보") MarkDTO markDTO) {
        Mark existingMark = markRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mark not found with id: " + id));

        existingMark.update(
            markDTO.getTitle(),
            markDTO.getLat(),
            markDTO.getLng(),
            markDTO.getLocation(),
            markDTO.getImgUrl()
        );

        return existingMark.getId();
    }

    @Operation(summary = "마커 삭제", description = "지정된 ID의 마커를 삭제합니다.")
    @Transactional
    public Long deleteMark(@Parameter(description = "삭제할 마커 ID") Long id) {
        if (!markRepository.existsById(id)) {
            throw new RuntimeException("Mark not found with id: " + id);
        }
        markRepository.deleteById(id);
        return id;
    }
}