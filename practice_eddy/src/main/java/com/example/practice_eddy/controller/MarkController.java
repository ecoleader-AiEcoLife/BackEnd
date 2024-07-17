package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.service.MarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
@Tag(name = "마커 관리", description = "마커 CRUD API")
public class MarkController {

    private final MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @Operation(summary = "모든 마커 조회", description = "저장된 모든 마커를 조회합니다.")
    @GetMapping("/marks")
    public ResponseEntity<List<MarkDTO>> getAllMarks() {
        List<MarkDTO> marks = markService.getAllMarks();
        return ResponseEntity.ok(marks);
    }

    @Operation(summary = "마커 추가", description = "새로운 마커를 추가합니다.")
    @PostMapping("/mark")
    public ResponseEntity<Long> addMark(
        @Parameter(description = "추가할 마커 정보") @RequestBody MarkDTO markDTO) {
        Long id = markService.addMark(markDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Operation(summary = "마커 수정", description = "기존 마커를 수정합니다.")
    @PutMapping("/mark")
    public ResponseEntity<Long> updateMark(
        @Parameter(description = "수정할 마커 정보") @RequestBody MarkDTO markDTO) {
        if (markDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Long id = markService.updateMark(markDTO.getId(), markDTO);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "마커 삭제", description = "지정된 ID의 마커를 삭제합니다.")
    @DeleteMapping("/mark/{id}")
    public ResponseEntity<Long> deleteMark(
        @Parameter(description = "삭제할 마커 ID") @PathVariable Long id) {
        Long deletedId = markService.deleteMark(id);
        return ResponseEntity.ok(deletedId);
    }
}