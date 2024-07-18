package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.disposalBoard.TypeDTO;
import com.example.practice_eddy.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disboard/type")
@Tag(name = "Disposal Board Type Controller", description = "폐기물 분류 타입 관리 API")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @Operation(summary = "타입 추가", description = "새로운 폐기물 분류 타입을 추가합니다.")
    @PostMapping
    public ResponseEntity<TypeDTO> addType(@Valid @RequestBody TypeDTO typeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.addType(typeDTO));
    }

    @Operation(summary = "타입 수정", description = "기존 폐기물 분류 타입을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> updateType(@PathVariable Long id,
        @Valid @RequestBody TypeDTO typeDTO) {
        return ResponseEntity.ok(typeService.updateType(id, typeDTO));
    }

    @Operation(summary = "타입 목록 조회", description = "모든 폐기물 분류 타입 목록을 반환합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        return ResponseEntity.ok(typeService.getAllTypes());
    }

    @Operation(summary = "타입 조회", description = "ID로 특정 폐기물 분류 타입을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(typeService.getTypeById(id));
    }

    @Operation(summary = "타입 이름으로 조회", description = "이름으로 특정 폐기물 분류 타입을 조회합니다.")
    @GetMapping("/by-name")
    public ResponseEntity<TypeDTO> getTypeByName(@RequestParam String name) {
        return ResponseEntity.ok(typeService.getTypeByName(name));
    }
}