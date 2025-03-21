import React, { useRef, useEffect } from 'react';
import './MathProblem.css';

const MathProblem = ({ problem, userAnswer, setUserAnswer, handleSubmit, feedback }) => {
  const inputRef = useRef(null);

  useEffect(() => {
    // Focus on the input field when the component mounts or when problem changes
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [problem]);

  return (
    <div className="problem-container">
      <div className="problem-card">
        <div className="problem-header">
          <span className="problem-type">{problem.operationType}</span>
          <span className="problem-level">Level: {problem.difficultyLevel.level}</span>
        </div>
        
        <div className="problem-content">
          <h2 className="problem-question">{problem.question}</h2>
        </div>
        
        <form onSubmit={handleSubmit} className="answer-form">
          <div className="input-group">
            <label htmlFor="answer">Your Answer:</label>
            <input
              ref={inputRef}
              type="text"
              id="answer"
              value={userAnswer}
              onChange={(e) => setUserAnswer(e.target.value)}
              placeholder="Enter your answer"
              disabled={feedback && feedback.isCorrect}
            />
          </div>
          
          <button 
            type="submit" 
            className="button primary submit-btn"
            disabled={feedback && feedback.isCorrect}
          >
            Submit Answer
          </button>
        </form>
        
        {feedback && (
          <div className={`feedback ${feedback.isCorrect ? 'correct' : 'incorrect'}`}>
            {feedback.message}
          </div>
        )}
      </div>
    </div>
  );
};

export default MathProblem;
