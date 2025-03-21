package com.mathadaptive.app.service;

import com.mathadaptive.app.model.DifficultyLevel;
import com.mathadaptive.app.model.MathProblem;
import com.mathadaptive.app.repository.MathProblemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MathProblemService {
    
    @Autowired
    private MathProblemRepository mathProblemRepository;
    
    private final Random random = new Random();
    
    public MathProblem getRandomProblemByLevel(DifficultyLevel level) {
        MathProblem problem = mathProblemRepository.findRandomProblemByDifficultyLevel(level.name());
        
        // If no problem found in the database, generate one on the fly
        if (problem == null) {
            problem = generateProblem(level);
            mathProblemRepository.save(problem);
        }
        
        return problem;
    }
    
    public boolean checkAnswer(Long problemId, String userAnswer) {
        MathProblem problem = mathProblemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        
        return problem.getAnswer().equals(userAnswer.trim());
    }
    
    private MathProblem generateProblem(DifficultyLevel level) {
        switch (level) {
            case GRADE_1:
                return generateGrade1Problem();
            case GRADE_2:
                return generateGrade2Problem();
            case GRADE_3:
                return generateGrade3Problem();
            case GRADE_4:
                return generateGrade4Problem();
            case GRADE_5:
                return generateGrade5Problem();
            case GRADE_6:
                return generateGrade6Problem();
            case GRADE_7:
                return generateGrade7Problem();
            case GRADE_8:
                return generateGrade8Problem();
            default:
                return generateGrade1Problem();
        }
    }
    
    private MathProblem generateGrade1Problem() {
        // Basic addition and subtraction with numbers 1-10
        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        boolean isAddition = random.nextBoolean();
        
        if (isAddition) {
            String question = num1 + " + " + num2 + " = ?";
            String answer = String.valueOf(num1 + num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_1, "Addition");
        } else {
            // Ensure subtraction result is positive
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            String question = num1 + " - " + num2 + " = ?";
            String answer = String.valueOf(num1 - num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_1, "Subtraction");
        }
    }
    
    private MathProblem generateGrade2Problem() {
        // Advanced addition and subtraction with numbers 1-20
        int num1 = random.nextInt(20) + 1;
        int num2 = random.nextInt(20) + 1;
        boolean isAddition = random.nextBoolean();
        
        if (isAddition) {
            String question = num1 + " + " + num2 + " = ?";
            String answer = String.valueOf(num1 + num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_2, "Addition");
        } else {
            // Ensure subtraction result is positive
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            String question = num1 + " - " + num2 + " = ?";
            String answer = String.valueOf(num1 - num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_2, "Subtraction");
        }
    }
    
    private MathProblem generateGrade3Problem() {
        // Basic multiplication and division with numbers 1-10
        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        boolean isMultiplication = random.nextBoolean();
        
        if (isMultiplication) {
            String question = num1 + " × " + num2 + " = ?";
            String answer = String.valueOf(num1 * num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_3, "Multiplication");
        } else {
            // Ensure division results in a whole number
            int product = num1 * num2;
            String question = product + " ÷ " + num1 + " = ?";
            String answer = String.valueOf(num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_3, "Division");
        }
    }
    
    private MathProblem generateGrade4Problem() {
        // Advanced multiplication and division with numbers 1-12
        int num1 = random.nextInt(12) + 1;
        int num2 = random.nextInt(12) + 1;
        boolean isMultiplication = random.nextBoolean();
        
        if (isMultiplication) {
            String question = num1 + " × " + num2 + " = ?";
            String answer = String.valueOf(num1 * num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_4, "Multiplication");
        } else {
            // Ensure division results in a whole number
            int product = num1 * num2;
            String question = product + " ÷ " + num1 + " = ?";
            String answer = String.valueOf(num2);
            return new MathProblem(question, answer, DifficultyLevel.GRADE_4, "Division");
        }
    }
    
    private MathProblem generateGrade5Problem() {
        // Fractions and decimals
        boolean isFraction = random.nextBoolean();
        
        if (isFraction) {
            // Simple fraction addition
            int denominator = random.nextInt(5) + 2; // 2-6
            int numerator1 = random.nextInt(denominator);
            int numerator2 = random.nextInt(denominator);
            
            String question = numerator1 + "/" + denominator + " + " + numerator2 + "/" + denominator + " = ?";
            int resultNumerator = numerator1 + numerator2;
            
            // Simplify the fraction if possible
            int gcd = gcd(resultNumerator, denominator);
            String answer = (resultNumerator / gcd) + "/" + (denominator / gcd);
            
            // If the result is a whole number
            if (resultNumerator % denominator == 0) {
                answer = String.valueOf(resultNumerator / denominator);
            }
            
            return new MathProblem(question, answer, DifficultyLevel.GRADE_5, "Fractions");
        } else {
            // Decimal addition
            double num1 = Math.round((random.nextDouble() * 10) * 10.0) / 10.0; // 0.0-10.0 with one decimal place
            double num2 = Math.round((random.nextDouble() * 10) * 10.0) / 10.0;
            
            String question = String.format("%.1f + %.1f = ?", num1, num2);
            String answer = String.format("%.1f", num1 + num2);
            
            return new MathProblem(question, answer, DifficultyLevel.GRADE_5, "Decimals");
        }
    }
    
    private MathProblem generateGrade6Problem() {
        // Percentages and ratios
        boolean isPercentage = random.nextBoolean();
        
        if (isPercentage) {
            // Find the percentage of a number
            int percentage = (random.nextInt(9) + 1) * 10; // 10%, 20%, ..., 90%
            int number = (random.nextInt(10) + 1) * 10; // 10, 20, ..., 100
            
            String question = "What is " + percentage + "% of " + number + "?";
            int answer = (percentage * number) / 100;
            
            return new MathProblem(question, String.valueOf(answer), DifficultyLevel.GRADE_6, "Percentages");
        } else {
            // Ratio problems
            int ratio1 = random.nextInt(5) + 1;
            int ratio2 = random.nextInt(5) + 1;
            int total = (ratio1 + ratio2) * (random.nextInt(5) + 1);
            
            String question = "If the ratio of A to B is " + ratio1 + ":" + ratio2 + 
                    " and the total is " + total + ", what is A?";
            int answer = (ratio1 * total) / (ratio1 + ratio2);
            
            return new MathProblem(question, String.valueOf(answer), DifficultyLevel.GRADE_6, "Ratios");
        }
    }
    
    private MathProblem generateGrade7Problem() {
        // Basic algebra
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(20) + 1;
        int x = random.nextInt(10) + 1;
        int result = a * x + b;
        
        String question = a + "x + " + b + " = " + result + ", what is x?";
        String answer = String.valueOf(x);
        
        return new MathProblem(question, answer, DifficultyLevel.GRADE_7, "Basic Algebra");
    }
    
    private MathProblem generateGrade8Problem() {
        // Advanced algebra
        boolean isQuadratic = random.nextBoolean();
        
        if (isQuadratic) {
            // Simple quadratic equation with integer solutions
            int x1 = random.nextInt(5) + 1;
            int x2 = random.nextInt(5) + 1;
            
            // (x - x1)(x - x2) = x^2 - (x1 + x2)x + x1*x2
            int b = -(x1 + x2);
            int c = x1 * x2;
            
            String question = "Solve for x: x² ";
            if (b > 0) {
                question += "+ " + b + "x ";
            } else if (b < 0) {
                question += "- " + Math.abs(b) + "x ";
            }
            
            if (c > 0) {
                question += "+ " + c + " = 0";
            } else if (c < 0) {
                question += "- " + Math.abs(c) + " = 0";
            } else {
                question += "= 0";
            }
            
            String answer = "x = " + x1 + " or x = " + x2;
            if (x1 == x2) {
                answer = "x = " + x1;
            }
            
            return new MathProblem(question, answer, DifficultyLevel.GRADE_8, "Quadratic Equations");
        } else {
            // System of linear equations
            int x = random.nextInt(10) - 5; // -5 to 4
            int y = random.nextInt(10) - 5; // -5 to 4
            
            int a1 = random.nextInt(5) + 1;
            int b1 = random.nextInt(5) + 1;
            int c1 = a1 * x + b1 * y;
            
            int a2 = random.nextInt(5) + 1;
            int b2 = random.nextInt(5) + 1;
            int c2 = a2 * x + b2 * y;
            
            String question = "Solve the system of equations:\n" +
                    a1 + "x + " + b1 + "y = " + c1 + "\n" +
                    a2 + "x + " + b2 + "y = " + c2;
            
            String answer = "x = " + x + ", y = " + y;
            
            return new MathProblem(question, answer, DifficultyLevel.GRADE_8, "Systems of Equations");
        }
    }
    
    // Helper method to find greatest common divisor (GCD)
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    
    @PostConstruct
    public void initializeProblems() {
        // Check if we already have problems in the database
        if (mathProblemRepository.count() > 0) {
            return;
        }
        
        // Generate some initial problems for each difficulty level
        List<MathProblem> initialProblems = new ArrayList<>();
        
        for (DifficultyLevel level : DifficultyLevel.values()) {
            for (int i = 0; i < 10; i++) {
                initialProblems.add(generateProblem(level));
            }
        }
        
        mathProblemRepository.saveAll(initialProblems);
    }
}
