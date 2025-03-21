package com.mathadaptive.app.model;

public enum DifficultyLevel {
    GRADE_1(1, "Grade 1 - Basic Addition/Subtraction"),
    GRADE_2(2, "Grade 2 - Advanced Addition/Subtraction"),
    GRADE_3(3, "Grade 3 - Basic Multiplication/Division"),
    GRADE_4(4, "Grade 4 - Advanced Multiplication/Division"),
    GRADE_5(5, "Grade 5 - Fractions and Decimals"),
    GRADE_6(6, "Grade 6 - Percentages and Ratios"),
    GRADE_7(7, "Grade 7 - Basic Algebra"),
    GRADE_8(8, "Grade 8 - Advanced Algebra");

    private final int level;
    private final String description;

    DifficultyLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public static DifficultyLevel fromLevel(int level) {
        for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
            if (difficultyLevel.getLevel() == level) {
                return difficultyLevel;
            }
        }
        return GRADE_1; // Default to the lowest level if not found
    }
}
