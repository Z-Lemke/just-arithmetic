import React, { useState, useEffect } from 'react';
import './App.css';
import MathProblem from './components/MathProblem';
import ProgressBar from './components/ProgressBar';
import { fetchProblem, checkAnswer, resetProgress } from './services/mathService';

function App() {
  const [userId, setUserId] = useState(localStorage.getItem('userId') || `user-${Date.now()}`);
  const [problem, setProblem] = useState(null);
  const [userAnswer, setUserAnswer] = useState('');
  const [feedback, setFeedback] = useState(null);
  const [userProgress, setUserProgress] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Store userId in localStorage for persistence
    localStorage.setItem('userId', userId);
    
    // Fetch the first problem
    getNewProblem();
  }, [userId]);

  const getNewProblem = async () => {
    setLoading(true);
    try {
      const data = await fetchProblem(userId);
      setProblem(data.problem);
      setUserProgress({
        currentLevel: data.currentLevel,
        levelDescription: data.currentLevel.description
      });
      setFeedback(null);
      setUserAnswer('');
    } catch (error) {
      console.error('Error fetching problem:', error);
      setFeedback({
        isCorrect: false,
        message: 'Error loading problem. Please try again.'
      });
    } finally {
      setLoading(false);
    }
  };

  const handleAnswerSubmit = async (e) => {
    e.preventDefault();
    
    if (!userAnswer.trim()) {
      setFeedback({
        isCorrect: false,
        message: 'Please enter an answer.'
      });
      return;
    }
    
    setLoading(true);
    try {
      const result = await checkAnswer(userId, problem.id, userAnswer);
      
      setFeedback({
        isCorrect: result.correct,
        message: result.correct ? 'Correct! Great job!' : 'Incorrect. Try again!'
      });
      
      setUserProgress(result.userProgress);
      
      if (result.correct) {
        // If correct, set the next problem after a short delay
        setTimeout(() => {
          setProblem(result.nextProblem);
          setUserAnswer('');
          setFeedback(null);
        }, 1500);
      }
    } catch (error) {
      console.error('Error checking answer:', error);
      setFeedback({
        isCorrect: false,
        message: 'Error checking answer. Please try again.'
      });
    } finally {
      setLoading(false);
    }
  };

  const handleReset = async () => {
    setLoading(true);
    try {
      await resetProgress(userId);
      getNewProblem();
    } catch (error) {
      console.error('Error resetting progress:', error);
      setFeedback({
        isCorrect: false,
        message: 'Error resetting progress. Please try again.'
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Adaptive Math Learning</h1>
        <p className="subtitle">Problems adapt to your skill level</p>
      </header>
      
      <main className="app-main">
        {userProgress && (
          <div className="level-info">
            <h2>Current Level: {userProgress.currentLevel.description}</h2>
            <ProgressBar 
              correctAnswers={userProgress.correctAnswers || 0} 
              totalAttempts={userProgress.totalAttempts || 0} 
              consecutiveCorrect={userProgress.consecutiveCorrectAnswers || 0}
            />
          </div>
        )}
        
        {loading ? (
          <div className="loading">Loading...</div>
        ) : problem ? (
          <MathProblem 
            problem={problem} 
            userAnswer={userAnswer} 
            setUserAnswer={setUserAnswer} 
            handleSubmit={handleAnswerSubmit}
            feedback={feedback}
          />
        ) : (
          <div className="error">No problem available. Please try again.</div>
        )}
        
        <div className="actions">
          <button 
            className="button secondary" 
            onClick={getNewProblem}
            disabled={loading}
          >
            Skip Problem
          </button>
          <button 
            className="button danger" 
            onClick={handleReset}
            disabled={loading}
          >
            Reset Progress
          </button>
        </div>
      </main>
      
      <footer className="app-footer">
        <p>© {new Date().getFullYear()} Math Adaptive Learning</p>
      </footer>
    </div>
  );
}

export default App;
