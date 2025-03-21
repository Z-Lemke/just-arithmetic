package com.mathadaptive.app.service;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.UserProgress;
import com.mathadaptive.app.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProgressService {
    
    @Autowired
    private UserProgressRepository userProgressRepository;
    
    public UserProgress getUserProgress(String userId) {
        Optional<UserProgress> userProgressOptional = userProgressRepository.findByUserId(userId);
        
        return userProgressOptional.orElseGet(() -> {
            UserProgress newUserProgress = new UserProgress(userId);
            return userProgressRepository.save(newUserProgress);
        });
    }
    
    public UserProgress updateUserProgress(String userId, boolean isCorrect) {
        UserProgress userProgress = getUserProgress(userId);
        userProgress.recordAttempt(isCorrect);
        return userProgressRepository.save(userProgress);
    }
    
    public DifficultyLevel getCurrentLevel(String userId) {
        return getUserProgress(userId).getCurrentLevel();
    }
    
    public UserProgress resetUserProgress(String userId) {
        UserProgress userProgress = getUserProgress(userId);
        userProgress.setCurrentLevel(DifficultyLevel.GRADE_1);
        userProgress.setCorrectAnswers(0);
        userProgress.setTotalAttempts(0);
        userProgress.setConsecutiveCorrectAnswers(0);
        return userProgressRepository.save(userProgress);
    }
}
