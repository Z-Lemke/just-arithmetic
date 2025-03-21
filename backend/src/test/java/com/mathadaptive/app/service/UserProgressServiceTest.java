package com.mathadaptive.app.service;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.UserProgress;
import com.mathadaptive.app.repository.UserProgressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserProgressServiceTest {

    @MockBean
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserProgressService userProgressService;

    @Test
    void getUserProgress_WhenUserExists_ReturnsUserProgress() {
        // Arrange
        String userId = "test-user";
        UserProgress expectedProgress = new UserProgress(userId);
        expectedProgress.setCurrentLevel(DifficultyLevel.GRADE_2);
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.of(expectedProgress));

        // Act
        UserProgress result = userProgressService.getUserProgress(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProgress, result);
        verify(userProgressRepository).findByUserId(userId);
    }

    @Test
    void getUserProgress_WhenUserDoesNotExist_CreatesAndReturnsNewUserProgress() {
        // Arrange
        String userId = "test-user";
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> {
            UserProgress savedProgress = invocation.getArgument(0);
            savedProgress.setId(1L);
            return savedProgress;
        });

        // Act
        UserProgress result = userProgressService.getUserProgress(userId);

        // Assert
        assertNotNull(result);
        assertEquals(DifficultyLevel.GRADE_1, result.getCurrentLevel());
        assertEquals(0, result.getCorrectAnswers());
        assertEquals(0, result.getTotalAttempts());
        verify(userProgressRepository).save(any(UserProgress.class));
    }

    @Test
    void getCurrentLevel_WhenUserExists_ReturnsCurrentLevel() {
        // Arrange
        String userId = "test-user";
        UserProgress userProgress = new UserProgress(userId);
        userProgress.setCurrentLevel(DifficultyLevel.GRADE_2);
        
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.of(userProgress));

        // Act
        DifficultyLevel result = userProgressService.getCurrentLevel(userId);

        // Assert
        assertEquals(DifficultyLevel.GRADE_2, result);
        verify(userProgressRepository).findByUserId(userId);
    }

    @Test
    void getCurrentLevel_WhenUserDoesNotExist_CreatesNewUserAndReturnsGrade1() {
        // Arrange
        String userId = "new-user";
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> {
            UserProgress savedProgress = invocation.getArgument(0);
            savedProgress.setId(1L);
            return savedProgress;
        });

        // Act
        DifficultyLevel result = userProgressService.getCurrentLevel(userId);

        // Assert
        assertEquals(DifficultyLevel.GRADE_1, result);
        verify(userProgressRepository).save(any(UserProgress.class));
    }

    @Test
    void updateUserProgress_WhenUserExists_UpdatesAndReturnsUserProgress() {
        // Arrange
        String userId = "test-user";
        boolean isCorrect = true;
        
        UserProgress existingProgress = new UserProgress(userId);
        existingProgress.setId(1L);
        existingProgress.setCurrentLevel(DifficultyLevel.GRADE_1);
        existingProgress.setCorrectAnswers(2);
        existingProgress.setTotalAttempts(5);
        existingProgress.setConsecutiveCorrectAnswers(1);
        
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.of(existingProgress));
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserProgress result = userProgressService.updateUserProgress(userId, isCorrect);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getCorrectAnswers());
        assertEquals(6, result.getTotalAttempts());
        assertEquals(2, result.getConsecutiveCorrectAnswers());
        verify(userProgressRepository).save(existingProgress);
    }

    @Test
    void updateUserProgress_WhenUserDoesNotExist_CreatesNewUserAndReturnsIt() {
        // Arrange
        String userId = "new-user";
        boolean isCorrect = true;
        
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> {
            UserProgress savedProgress = invocation.getArgument(0);
            savedProgress.setId(1L);
            return savedProgress;
        });

        // Act
        UserProgress result = userProgressService.updateUserProgress(userId, isCorrect);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCorrectAnswers());
        assertEquals(1, result.getTotalAttempts());
        assertEquals(1, result.getConsecutiveCorrectAnswers());
        verify(userProgressRepository, times(2)).save(any(UserProgress.class));
    }

    @Test
    void resetUserProgress_WhenUserExists_ResetsAndReturnsUserProgress() {
        // Arrange
        String userId = "test-user";
        
        UserProgress existingProgress = new UserProgress(userId);
        existingProgress.setId(1L);
        existingProgress.setCurrentLevel(DifficultyLevel.GRADE_3);
        existingProgress.setCorrectAnswers(10);
        existingProgress.setTotalAttempts(20);
        existingProgress.setConsecutiveCorrectAnswers(2);
        
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.of(existingProgress));
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserProgress result = userProgressService.resetUserProgress(userId);

        // Assert
        assertNotNull(result);
        assertEquals(DifficultyLevel.GRADE_1, result.getCurrentLevel());
        assertEquals(0, result.getCorrectAnswers());
        assertEquals(0, result.getTotalAttempts());
        assertEquals(0, result.getConsecutiveCorrectAnswers());
        verify(userProgressRepository).save(existingProgress);
    }

    @Test
    void resetUserProgress_WhenUserDoesNotExist_CreatesNewUserAndReturnsIt() {
        // Arrange
        String userId = "new-user";
        
        when(userProgressRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userProgressRepository.save(any(UserProgress.class))).thenAnswer(invocation -> {
            UserProgress savedProgress = invocation.getArgument(0);
            savedProgress.setId(1L);
            return savedProgress;
        });

        // Act
        UserProgress result = userProgressService.resetUserProgress(userId);

        // Assert
        assertNotNull(result);
        assertEquals(DifficultyLevel.GRADE_1, result.getCurrentLevel());
        assertEquals(0, result.getCorrectAnswers());
        assertEquals(0, result.getTotalAttempts());
        assertEquals(0, result.getConsecutiveCorrectAnswers());
        verify(userProgressRepository, times(2)).save(any(UserProgress.class));
    }
}
