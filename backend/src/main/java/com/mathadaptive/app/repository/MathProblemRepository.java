package com.mathadaptive.app.repository;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.MathProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathProblemRepository extends JpaRepository<MathProblem, Long> {
    
    List<MathProblem> findByDifficultyLevel(DifficultyLevel difficultyLevel);
    
    @Query(value = "SELECT * FROM math_problem WHERE difficulty_level = ?1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MathProblem findRandomProblemByDifficultyLevel(String difficultyLevel);
}
