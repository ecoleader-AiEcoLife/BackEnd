package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.service.DisposalBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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


@Tag(name = "Disboard", description = "Disposal Board를 위한 요청 처리")
@RestController
@RequestMapping("/disboard")
public class DisposalBoardController {

    private final DisposalBoardService disposalBoardService;

    public DisposalBoardController(DisposalBoardService disposalBoardService) {
        this.disposalBoardService = disposalBoardService;
    }

    @Operation(summary = "Disposal board 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공적으로 목록을 조회함",
        content = @Content(mediaType = "application/json",
            schema = @Schema(type = "array", implementation = DisposalBoardDTO.class)))
    @GetMapping("/list")
    public ResponseEntity<List<DisposalBoardDTO>> getList() {
        return ResponseEntity.ok(disposalBoardService.getBoardList());
    }

    @Operation(summary = "특정 id의 Disposal board 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 게시판을 조회함"),
        @ApiResponse(responseCode = "404", description = "해당 id의 게시판을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisposalBoardDTO> getBoard(
        @Parameter(description = "조회할 게시판의 id", example = "1") @PathVariable("id") Long id) {
        return ResponseEntity.ok(disposalBoardService.findBoardById(id));
    }

    @Operation(summary = "Disposal board 추가")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 게시판을 추가함"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "409", description = "중복된 제목")
    })
    @PostMapping
    public ResponseEntity<DisposalBoardDTO> createBoard(
        @Valid @RequestBody DisposalBoardDTO boardDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(disposalBoardService.insertBoard(boardDTO));
    }

    @Operation(summary = "특정 id의 Disposal board 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 게시판을 수정함"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "해당 id의 게시판을 찾을 수 없음"),
        @ApiResponse(responseCode = "409", description = "중복된 제목")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DisposalBoardDTO> updateBoard(
        @Parameter(description = "수정할 게시판의 id", example = "1") @PathVariable("id") Long id,
        @Valid @RequestBody DisposalBoardDTO boardDTO) {
        return ResponseEntity.ok(disposalBoardService.updateDisposalBoard(id, boardDTO));
    }

    @Operation(summary = "특정 id의 Disposal board 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 게시판을 삭제함"),
        @ApiResponse(responseCode = "404", description = "해당 id의 게시판을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
        @Parameter(description = "삭제될 게시판의 id", example = "1") @PathVariable("id") Long id) {
        disposalBoardService.deleteDisposalBoard(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "특정 type의 Disposal board 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공적으로 목록을 조회함",
        content = @Content(mediaType = "application/json",
            schema = @Schema(type = "array", implementation = DisposalBoardDTO.class)))
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<DisposalBoardDTO>> getBoardListByTypeId(
        @Parameter(description = "조회할 type의 id", example = "1") @PathVariable("typeId") Long typeId) {
        return ResponseEntity.ok(disposalBoardService.getBoardListByTypeId(typeId));
    }
}