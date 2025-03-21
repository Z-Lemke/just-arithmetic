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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathProblem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String question;
    private String answer;
    
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    
    private String operationType; // Addition, Subtraction, Multiplication, Division, etc.
    
    // Constructor without id for creating new problems
    public MathProblem(String question, String answer, DifficultyLevel difficultyLevel, String operationType) {
        this.question = question;
        this.answer = answer;
        this.difficultyLevel = difficultyLevel;
        this.operationType = operationType;
    }
}
