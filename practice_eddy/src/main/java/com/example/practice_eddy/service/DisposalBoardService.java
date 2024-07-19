package com.example.practice_eddy.service;

import com.example.practice_eddy.exception.customException.DuplicateResourceException;
import com.example.practice_eddy.exception.customException.ResourceNotFoundException;
import com.example.practice_eddy.model.disposalBoard.DisposalBoard;
import com.example.practice_eddy.model.disposalBoard.DisposalBoardDTO;
import com.example.practice_eddy.model.disposalBoard.Type;
import com.example.practice_eddy.repository.DisposalBoardRepository;
import com.example.practice_eddy.repository.TypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisposalBoardService {

    private final DisposalBoardRepository disposalBoardRepository;
    private final TypeRepository typeRepository;

    public DisposalBoardService(DisposalBoardRepository disposalBoardRepository,
        TypeRepository typeRepository) {
        this.disposalBoardRepository = disposalBoardRepository;
        this.typeRepository = typeRepository;
    }

    @Transactional
    public DisposalBoardDTO insertBoard(DisposalBoardDTO boardDTO) {
        checkDuplicateTitle(boardDTO.title());
        Type type = findTypeById(boardDTO.typeId());
        DisposalBoard board = new DisposalBoard(null, boardDTO.title(), boardDTO.content(),
            boardDTO.subContent(), type);
        return disposalBoardRepository.save(board).toDisposalBoardDTO();
    }

    @Transactional(readOnly = true)
    public DisposalBoardDTO findBoardById(Long id) {
        return disposalBoardRepository.findById(id)
            .map(DisposalBoard::toDisposalBoardDTO)
            .orElseThrow(() -> new ResourceNotFoundException("DisposalBoard", "id", id));
    }

    @Transactional(readOnly = true)
    public List<DisposalBoardDTO> getBoardList() {
        return disposalBoardRepository.findAll().stream()
            .map(DisposalBoard::toDisposalBoardDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DisposalBoardDTO> getBoardListByTypeId(Long typeId) {
        return disposalBoardRepository.findAllByTypeId(typeId).stream()
            .map(DisposalBoard::toDisposalBoardDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public DisposalBoardDTO updateDisposalBoard(Long id, DisposalBoardDTO boardDTO) {
        DisposalBoard board = disposalBoardRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("DisposalBoard", "id", id));
        if (!board.getTitle().equals(boardDTO.title())) {
            checkDuplicateTitle(boardDTO.title());
        }
        Type type = findTypeById(boardDTO.typeId());
        board.update(boardDTO.title(), boardDTO.content(), boardDTO.subContent(), type);
        return board.toDisposalBoardDTO();
    }

    @Transactional
    public void deleteDisposalBoard(Long id) {
        if (!disposalBoardRepository.existsById(id)) {
            throw new ResourceNotFoundException("DisposalBoard", "id", id);
        }
        disposalBoardRepository.deleteById(id);
    }

    private void checkDuplicateTitle(String title) {
        if (disposalBoardRepository.findByTitle(title).isPresent()) {
            throw new DuplicateResourceException("DisposalBoard", "title", title);
        }
    }

    private Type findTypeById(Long id) {
        return typeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id));
    }
}