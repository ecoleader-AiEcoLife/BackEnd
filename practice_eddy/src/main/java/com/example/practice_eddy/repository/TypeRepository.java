package com.example.practice_eddy.repository;

import com.example.practice_eddy.model.disposalBoard.Type;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    boolean existsByName(String name);

    Optional<Type> findByName(String name);
}