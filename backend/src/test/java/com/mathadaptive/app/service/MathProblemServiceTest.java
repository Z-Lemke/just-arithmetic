package com.mathadaptive.app.service;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.MathProblem;
import com.mathadaptive.app.repository.MathProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MathProblemServiceTest {

    @MockBean
    private MathProblemRepository mathProblemRepository;

    @Autowired
    private MathProblemService mathProblemService;

    @Test
    void getRandomProblemByLevel_WhenProblemExists_ReturnsProblem() {
        // Arrange
        MathProblem expectedProblem = new MathProblem(
                "5 + 3 = ?",
                "8",
                DifficultyLevel.GRADE_1,
                "Addition"
        );
        when(mathProblemRepository.findRandomProblemByDifficultyLevel(anyString()))
                .thenReturn(expectedProblem);

        // Act
        MathProblem result = mathProblemService.getRandomProblemByLevel(DifficultyLevel.GRADE_1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProblem, result);
        verify(mathProblemRepository).findRandomProblemByDifficultyLevel(DifficultyLevel.GRADE_1.name());
    }

    @Test
    void getRandomProblemByLevel_WhenNoProblemExists_GeneratesAndSavesNewProblem() {
        // Arrange
        when(mathProblemRepository.findRandomProblemByDifficultyLevel(anyString()))
                .thenReturn(null);
        when(mathProblemRepository.save(any(MathProblem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MathProblem result = mathProblemService.getRandomProblemByLevel(DifficultyLevel.GRADE_1);

        // Assert
        assertNotNull(result);
        assertEquals(DifficultyLevel.GRADE_1, result.getDifficultyLevel());
        verify(mathProblemRepository).save(any(MathProblem.class));
    }

    @Test
    void checkAnswer_WhenAnswerIsCorrect_ReturnsTrue() {
        // Arrange
        Long problemId = 1L;
        String userAnswer = "8";
        MathProblem problem = new MathProblem(
                "5 + 3 = ?",
                "8",
                DifficultyLevel.GRADE_1,
                "Addition"
        );
        when(mathProblemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act
        boolean result = mathProblemService.checkAnswer(problemId, userAnswer);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkAnswer_WhenAnswerIsIncorrect_ReturnsFalse() {
        // Arrange
        Long problemId = 1L;
        String userAnswer = "9";
        MathProblem problem = new MathProblem(
                "5 + 3 = ?",
                "8",
                DifficultyLevel.GRADE_1,
                "Addition"
        );
        when(mathProblemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act
        boolean result = mathProblemService.checkAnswer(problemId, userAnswer);

        // Assert
        assertFalse(result);
    }

    @Test
    void checkAnswer_WhenProblemNotFound_ThrowsException() {
        // Arrange
        Long problemId = 1L;
        String userAnswer = "8";
        when(mathProblemRepository.findById(problemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> mathProblemService.checkAnswer(problemId, userAnswer));
    }

    @Test
    void generateGrade1Problem_ReturnsValidProblem() {
        // This test uses reflection to access the private method
        // We're testing the public method that calls it instead
        
        // Act
        MathProblem result = mathProblemService.getRandomProblemByLevel(DifficultyLevel.GRADE_1);
        
        // Assert
        assertNotNull(result);
        assertEquals(DifficultyLevel.GRADE_1, result.getDifficultyLevel());
        assertTrue(result.getOperationType().equals("Addition") || 
                   result.getOperationType().equals("Subtraction"));
    }
}
