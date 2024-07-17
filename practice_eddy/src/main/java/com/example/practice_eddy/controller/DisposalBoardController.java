package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.service.DisposalBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @ApiResponse(
        responseCode = "200",
        description = "성공적으로 목록을 조회함",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                type = "array",
                implementation = DisposalBoardDTO.class
            )
        )
    )
    @GetMapping("/list")
    ResponseEntity<?> getList() {
        List<DisposalBoardDTO> list = disposalBoardService.getBoardList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "특정 id의 Disposal board 조회")
    @GetMapping("/{id}")
    ResponseEntity<?> getBoard(
        @Parameter(description = "조회할 게시판의 id", example = "/disboard/1") @PathVariable("id") Long id) {
        DisposalBoardDTO boardDTO = disposalBoardService.findBoardById(id);
        return ResponseEntity.ok(boardDTO);
    }

    @Operation(summary = "Disposal board 추가")
    @PostMapping
    ResponseEntity<?> createBoard(
        @RequestBody DisposalBoardDTO boardDTO) {
        disposalBoardService.insertBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 id의 Disposal board 수정")
    @PutMapping
    ResponseEntity<?> updateBoard(
        @RequestBody DisposalBoardDTO boardDTO) {
        disposalBoardService.updateDisposalBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "특정 id의 Disposal board 삭제")
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteBoard(
        @Parameter(description = "삭제될 게시판의 id",example = "/disboard/1")
        @PathVariable("id") Long id) {
        disposalBoardService.deleteDisposalBoard(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
