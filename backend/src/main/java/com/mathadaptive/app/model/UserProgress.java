package com.mathadaptive.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userId;
    
    @Enumerated(EnumType.STRING)
    private DifficultyLevel currentLevel;
    
    private int correctAnswers;
    private int totalAttempts;
    private LocalDateTime lastAttemptTime;
    
    // Threshold for advancing to the next level (e.g., 3 correct answers in a row)
    private int consecutiveCorrectAnswers;
    
    // Constructor for new user
    public UserProgress(String userId) {
        this.userId = userId;
        this.currentLevel = DifficultyLevel.GRADE_1;
        this.correctAnswers = 0;
        this.totalAttempts = 0;
        this.consecutiveCorrectAnswers = 0;
        this.lastAttemptTime = LocalDateTime.now();
    }
    
    public void recordAttempt(boolean correct) {
        this.totalAttempts++;
        this.lastAttemptTime = LocalDateTime.now();
        
        if (correct) {
            this.correctAnswers++;
            this.consecutiveCorrectAnswers++;
            
            // Check if user should advance to the next level
            if (this.consecutiveCorrectAnswers >= 3) {
                if (this.currentLevel.ordinal() < DifficultyLevel.values().length - 1) {
                    this.currentLevel = DifficultyLevel.values()[this.currentLevel.ordinal() + 1];
                }
                this.consecutiveCorrectAnswers = 0; // Reset consecutive correct answers regardless of level
            }
        } else {
            this.consecutiveCorrectAnswers = 0; // Reset consecutive correct answers
            
            // Check if user should drop to a lower level
            if (this.currentLevel.ordinal() > 0 && this.totalAttempts >= 5 && 
                    (double) this.correctAnswers / this.totalAttempts < 0.6) {
                this.currentLevel = DifficultyLevel.values()[this.currentLevel.ordinal() - 1];
            }
        }
    }
}
