package com.mathadaptive.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProgressTest {

    @Test
    void constructor_InitializesCorrectly() {
        // Act
        UserProgress userProgress = new UserProgress("test-user");
        
        // Assert
        assertEquals("test-user", userProgress.getUserId());
        assertEquals(DifficultyLevel.GRADE_1, userProgress.getCurrentLevel());
        assertEquals(0, userProgress.getCorrectAnswers());
        assertEquals(0, userProgress.getTotalAttempts());
        assertEquals(0, userProgress.getConsecutiveCorrectAnswers());
        assertNotNull(userProgress.getLastAttemptTime());
    }

    @Test
    void recordAttempt_WhenCorrect_UpdatesProgressCorrectly() {
        // Arrange
        UserProgress userProgress = new UserProgress("test-user");
        
        // Act
        userProgress.recordAttempt(true);
        
        // Assert
        assertEquals(1, userProgress.getCorrectAnswers());
        assertEquals(1, userProgress.getTotalAttempts());
        assertEquals(1, userProgress.getConsecutiveCorrectAnswers());
        assertEquals(DifficultyLevel.GRADE_1, userProgress.getCurrentLevel());
    }

    @Test
    void recordAttempt_WhenThreeConsecutiveCorrect_AdvancesLevel() {
        // Arrange
        UserProgress userProgress = new UserProgress("test-user");
        
        // Act
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        
        // Assert
        assertEquals(3, userProgress.getCorrectAnswers());
        assertEquals(3, userProgress.getTotalAttempts());
        assertEquals(0, userProgress.getConsecutiveCorrectAnswers()); // Reset after level advancement
        assertEquals(DifficultyLevel.GRADE_2, userProgress.getCurrentLevel());
    }

    @Test
    void recordAttempt_WhenIncorrect_ResetsConsecutiveCorrect() {
        // Arrange
        UserProgress userProgress = new UserProgress("test-user");
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        
        // Act
        userProgress.recordAttempt(false);
        
        // Assert
        assertEquals(2, userProgress.getCorrectAnswers());
        assertEquals(3, userProgress.getTotalAttempts());
        assertEquals(0, userProgress.getConsecutiveCorrectAnswers());
        assertEquals(DifficultyLevel.GRADE_1, userProgress.getCurrentLevel());
    }

    @Test
    void recordAttempt_WhenLowAccuracyAndHigherLevel_DropsLevel() {
        // Arrange
        UserProgress userProgress = new UserProgress("test-user");
        userProgress.setCurrentLevel(DifficultyLevel.GRADE_2);
        
        // Act - Add 5 attempts with only 2 correct (40% accuracy)
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(false);
        userProgress.recordAttempt(false);
        userProgress.recordAttempt(false);
        
        // Assert
        assertEquals(2, userProgress.getCorrectAnswers());
        assertEquals(5, userProgress.getTotalAttempts());
        assertEquals(0, userProgress.getConsecutiveCorrectAnswers());
        assertEquals(DifficultyLevel.GRADE_1, userProgress.getCurrentLevel()); // Dropped to lower level
    }

    @Test
    void recordAttempt_WhenAtMaxLevel_DoesNotAdvanceFurther() {
        // Arrange
        UserProgress userProgress = new UserProgress("test-user");
        userProgress.setCurrentLevel(DifficultyLevel.GRADE_8); // Max level
        
        // Act
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        userProgress.recordAttempt(true);
        
        // Assert
        assertEquals(3, userProgress.getCorrectAnswers());
        assertEquals(3, userProgress.getTotalAttempts());
        assertEquals(0, userProgress.getConsecutiveCorrectAnswers()); // Resets after 3 consecutive correct answers
        assertEquals(DifficultyLevel.GRADE_8, userProgress.getCurrentLevel()); // Stays at max level
    }
}
