package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.mark.MarkDTO;
import com.example.practice_eddy.service.MarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @ApiResponse(responseCode = "200", description = "성공적으로 마커 목록을 조회함")
    @GetMapping("/marks")
    public ResponseEntity<List<MarkDTO>> getAllMarks() {
        return ResponseEntity.ok(markService.getAllMarks());
    }

    @Operation(summary = "마커 추가", description = "새로운 마커를 추가합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "마커가 성공적으로 추가됨"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/mark")
    public ResponseEntity<MarkDTO> addMark(
        @Parameter(description = "추가할 마커 정보") @Valid @RequestBody MarkDTO markDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(markService.addMark(markDTO));
    }

    @Operation(summary = "마커 수정", description = "기존 마커를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "마커가 성공적으로 수정됨"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음")
    })
    @PutMapping("/mark/{id}")
    public ResponseEntity<MarkDTO> updateMark(
        @Parameter(description = "수정할 마커 ID") @PathVariable Long id,
        @Parameter(description = "수정할 마커 정보") @Valid @RequestBody MarkDTO markDTO) {
        return ResponseEntity.ok(markService.updateMark(id, markDTO));
    }

    @Operation(summary = "마커 삭제", description = "지정된 ID의 마커를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "마커가 성공적으로 삭제됨"),
        @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음")
    })
    @DeleteMapping("/mark/{id}")
    public ResponseEntity<Void> deleteMark(
        @Parameter(description = "삭제할 마커 ID") @PathVariable Long id) {
        markService.deleteMark(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마커 조회", description = "지정된 ID의 마커를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "마커를 성공적으로 조회함"),
        @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음")
    })
    @GetMapping("/mark/{id}")
    public ResponseEntity<MarkDTO> getMarkById(
        @Parameter(description = "조회할 마커 ID") @PathVariable Long id) {
        return ResponseEntity.ok(markService.getMarkById(id));
    }
}