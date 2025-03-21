import axios from 'axios';

const API_URL = '/api/math';

// Fetch a random math problem based on user's current level
export const fetchProblem = async (userId) => {
  try {
    const response = await axios.get(`${API_URL}/problem`, {
      params: { userId }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching problem:', error);
    throw error;
  }
};

// Check if the user's answer is correct
export const checkAnswer = async (userId, problemId, answer) => {
  try {
    const response = await axios.post(`${API_URL}/check`, null, {
      params: { userId, problemId, answer }
    });
    return response.data;
  } catch (error) {
    console.error('Error checking answer:', error);
    throw error;
  }
};

// Reset the user's progress
export const resetProgress = async (userId) => {
  try {
    const response = await axios.post(`${API_URL}/reset`, null, {
      params: { userId }
    });
    return response.data;
  } catch (error) {
    console.error('Error resetting progress:', error);
    throw error;
  }
};
