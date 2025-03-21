package com.mathadaptive.app.controller;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.MathProblem;
import com.mathadaptive.app.model.UserProgress;
import com.mathadaptive.app.repository.MathProblemRepository;
import com.mathadaptive.app.service.UserProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driverClassName=org.h2.Driver"
})
class MathProblemControllerTest {

    @Autowired
    private MathProblemController mathProblemController;

    @Autowired
    private UserProgressService userProgressService;
    
    @Autowired
    private MathProblemRepository mathProblemRepository;

    private final String userId = "test-user";
    
    @BeforeEach
    void setUp() {
        // Create a test math problem
        MathProblem problem = new MathProblem(
            "5 + 3 = ?", 
            "8", 
            DifficultyLevel.GRADE_1, 
            "Addition"
        );
        mathProblemRepository.save(problem);
    }

    @Test
    void getRandomProblem_ReturnsCorrectResponse() {
        // Act
        ResponseEntity<Map<String, Object>> response = mathProblemController.getRandomProblem(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.get("problem"));
        assertNotNull(responseBody.get("currentLevel"));
        assertTrue(responseBody.get("problem") instanceof MathProblem);
        assertTrue(responseBody.get("currentLevel") instanceof DifficultyLevel);
    }

    @Test
    void resetProgress_ReturnsResetUserProgress() {
        // First create some progress
        userProgressService.updateUserProgress(userId, true);
        userProgressService.updateUserProgress(userId, true);
        userProgressService.updateUserProgress(userId, true);
        
        // Act
        ResponseEntity<UserProgress> response = mathProblemController.resetProgress(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        
        UserProgress responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DifficultyLevel.GRADE_1, responseBody.getCurrentLevel());
        assertEquals(0, responseBody.getCorrectAnswers());
        assertEquals(0, responseBody.getTotalAttempts());
        assertEquals(0, responseBody.getConsecutiveCorrectAnswers());
    }
}
