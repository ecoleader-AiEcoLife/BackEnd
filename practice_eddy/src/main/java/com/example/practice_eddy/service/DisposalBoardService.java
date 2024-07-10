package com.example.practice_eddy.service;

import com.example.practice_eddy.model.disposalBoard.DisposalBoard;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.repository.DisposalBoardRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class DisposalBoardService {

    private final DisposalBoardRepository disposalBoardRepository;

    public DisposalBoardService(DisposalBoardRepository disposalBoardRepository) {
        this.disposalBoardRepository = disposalBoardRepository;
    }

    public void insertBoard(DisposalBoardDTO boardDTO) {
        disposalBoardRepository.save(boardDTO.toEntity());
    }

    public DisposalBoardDTO findBoardById(Long id){
        DisposalBoard disposalBoard = disposalBoardRepository.findById(id).get();
        return disposalBoard.toDisposalBoardDTO();
    }

    public List<DisposalBoardDTO> getBoardList() {
        List<DisposalBoard> list = disposalBoardRepository.findAll();
        return list.stream().map(DisposalBoard::toDisposalBoardDTO).collect(Collectors.toList());
    }

    public void updateDisposalBoard(DisposalBoardDTO boardDTO){
        disposalBoardRepository.save(boardDTO.toEntity());
    }

    public void deleteDisposalBoard(Long id){
        disposalBoardRepository.deleteById(id);
    }

}
