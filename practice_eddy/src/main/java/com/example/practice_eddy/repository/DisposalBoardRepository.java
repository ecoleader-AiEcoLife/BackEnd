package com.example.practice_eddy.repository;

import com.example.practice_eddy.model.disposalBoard.DisposalBoard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposalBoardRepository extends JpaRepository<DisposalBoard,Long> {
    Optional<DisposalBoard> findByTitle(String title);
    void deleteById(Long id);
}