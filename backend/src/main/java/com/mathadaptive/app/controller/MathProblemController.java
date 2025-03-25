package com.mathadaptive.app.controller;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.MathProblem;
import com.mathadaptive.app.model.UserProgress;
import com.mathadaptive.app.service.MathProblemService;
import com.mathadaptive.app.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/math")
public class MathProblemController {
    
    @Autowired
    private UserProgressService userProgressService;
    
    @GetMapping("/problem")
    public ResponseEntity<Map<String, Object>> getRandomProblem(@RequestParam String userId) {
        // Get the current difficulty level for the user
        DifficultyLevel level = userProgressService.getCurrentLevel(userId);
        
        // Get a random problem for that level
        MathProblem problem = mathProblemService.getRandomProblemByLevel(level);
        
        // Create response with problem and user's current level
        Map<String, Object> response = new HashMap<>();
        response.put("problem", problem);
        response.put("currentLevel", level);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAnswer(
            @RequestParam String userId,
            @RequestParam Long problemId,
            @RequestParam String answer) {
        
        // Check if the answer is correct
        boolean isCorrect = mathProblemService.checkAnswer(problemId, answer);
        
        // Update user progress based on the result
        UserProgress updatedProgress = userProgressService.updateUserProgress(userId, isCorrect);
        
        // Create response with result and updated progress
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("userProgress", updatedProgress);
        
        // If the answer was correct, include a new problem
        if (isCorrect) {
            MathProblem newProblem = mathProblemService.getRandomProblemByLevel(updatedProgress.getCurrentLevel());
            response.put("nextProblem", newProblem);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reset")
    public ResponseEntity<UserProgress> resetProgress(@RequestParam String userId) {
        UserProgress resetProgress = userProgressService.resetUserProgress(userId);
        return ResponseEntity.ok(resetProgress);
    }
}
