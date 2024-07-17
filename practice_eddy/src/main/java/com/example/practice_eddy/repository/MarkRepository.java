package com.example.practice_eddy.repository;

import com.example.practice_eddy.model.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

}
