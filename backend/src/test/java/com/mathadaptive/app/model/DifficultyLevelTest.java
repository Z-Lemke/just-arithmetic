package com.mathadaptive.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void getLevel_ReturnsCorrectLevel() {
        assertEquals(1, DifficultyLevel.GRADE_1.getLevel());
        assertEquals(2, DifficultyLevel.GRADE_2.getLevel());
        assertEquals(3, DifficultyLevel.GRADE_3.getLevel());
        assertEquals(4, DifficultyLevel.GRADE_4.getLevel());
        assertEquals(5, DifficultyLevel.GRADE_5.getLevel());
        assertEquals(6, DifficultyLevel.GRADE_6.getLevel());
        assertEquals(7, DifficultyLevel.GRADE_7.getLevel());
        assertEquals(8, DifficultyLevel.GRADE_8.getLevel());
    }

    @Test
    void getDescription_ReturnsCorrectDescription() {
        assertTrue(DifficultyLevel.GRADE_1.getDescription().contains("Grade 1"));
        assertTrue(DifficultyLevel.GRADE_2.getDescription().contains("Grade 2"));
        assertTrue(DifficultyLevel.GRADE_3.getDescription().contains("Grade 3"));
        assertTrue(DifficultyLevel.GRADE_4.getDescription().contains("Grade 4"));
        assertTrue(DifficultyLevel.GRADE_5.getDescription().contains("Grade 5"));
        assertTrue(DifficultyLevel.GRADE_6.getDescription().contains("Grade 6"));
        assertTrue(DifficultyLevel.GRADE_7.getDescription().contains("Grade 7"));
        assertTrue(DifficultyLevel.GRADE_8.getDescription().contains("Grade 8"));
    }

    @Test
    void fromLevel_WithValidLevel_ReturnsCorrectEnum() {
        assertEquals(DifficultyLevel.GRADE_1, DifficultyLevel.fromLevel(1));
        assertEquals(DifficultyLevel.GRADE_2, DifficultyLevel.fromLevel(2));
        assertEquals(DifficultyLevel.GRADE_3, DifficultyLevel.fromLevel(3));
        assertEquals(DifficultyLevel.GRADE_4, DifficultyLevel.fromLevel(4));
        assertEquals(DifficultyLevel.GRADE_5, DifficultyLevel.fromLevel(5));
        assertEquals(DifficultyLevel.GRADE_6, DifficultyLevel.fromLevel(6));
        assertEquals(DifficultyLevel.GRADE_7, DifficultyLevel.fromLevel(7));
        assertEquals(DifficultyLevel.GRADE_8, DifficultyLevel.fromLevel(8));
    }

    @Test
    void fromLevel_WithInvalidLevel_ReturnsLowestLevel() {
        assertEquals(DifficultyLevel.GRADE_1, DifficultyLevel.fromLevel(0));
        assertEquals(DifficultyLevel.GRADE_1, DifficultyLevel.fromLevel(-1));
        assertEquals(DifficultyLevel.GRADE_1, DifficultyLevel.fromLevel(9));
        assertEquals(DifficultyLevel.GRADE_1, DifficultyLevel.fromLevel(100));
    }
}
