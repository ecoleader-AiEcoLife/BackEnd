package com.example.practice_eddy.controller;

import com.example.practice_eddy.model.disposalBoard.BoardForm;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.service.DisposalBoardService;
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

@RestController
@RequestMapping("/disboard")
public class DisposalBoardController {

    private final DisposalBoardService disposalBoardService;

    public DisposalBoardController(DisposalBoardService disposalBoardService) {
        this.disposalBoardService = disposalBoardService;
    }

    @GetMapping("/list")
    ResponseEntity<?> getList() {
        List<DisposalBoardDTO> list = disposalBoardService.getBoardList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    ResponseEntity<?> getBoard(@PathVariable("id") Long id) {
        DisposalBoardDTO boardDTO = disposalBoardService.findBoardById(id);
        return ResponseEntity.ok(boardDTO);
    }

    @PostMapping
    ResponseEntity<?> createBoard(@RequestBody BoardForm boardForm) {
        disposalBoardService.insertBoard(new DisposalBoardDTO(boardForm.getItemName(),
            boardForm.getContent()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateBoard(@RequestBody BoardForm boardForm, @PathVariable("id") Long id) {
        disposalBoardService.updateDisposalBoard(new DisposalBoardDTO(id, boardForm.getItemName(),
            boardForm.getContent(), null, null));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        disposalBoardService.deleteDisposalBoard(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
