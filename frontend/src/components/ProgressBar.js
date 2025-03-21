import React from 'react';
import './ProgressBar.css';

const ProgressBar = ({ correctAnswers, totalAttempts, consecutiveCorrect }) => {
  const accuracy = totalAttempts > 0 ? (correctAnswers / totalAttempts) * 100 : 0;
  
  return (
    <div className="progress-container">
      <div className="progress-stats">
        <div className="stat-item">
          <span className="stat-label">Accuracy</span>
          <span className="stat-value">{accuracy.toFixed(1)}%</span>
        </div>
        <div className="stat-item">
          <span className="stat-label">Correct</span>
          <span className="stat-value">{correctAnswers}</span>
        </div>
        <div className="stat-item">
          <span className="stat-label">Total</span>
          <span className="stat-value">{totalAttempts}</span>
        </div>
        <div className="stat-item">
          <span className="stat-label">Streak</span>
          <span className="stat-value">{consecutiveCorrect}</span>
        </div>
      </div>
      
      <div className="progress-bar-wrapper">
        <div className="progress-label">
          <span>Progress to next level</span>
          <span>{consecutiveCorrect}/3</span>
        </div>
        <div className="progress-bar">
          <div 
            className="progress-fill" 
            style={{ width: `${(consecutiveCorrect / 3) * 100}%` }}
          ></div>
        </div>
      </div>
    </div>
  );
};

export default ProgressBar;
