import axios from 'axios';
import { getStoredAuth } from '../utils/auth';

// Create axios instance with default config
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8000',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to include auth token
api.interceptors.request.use(
  (config) => {
    // Skip auth header for public endpoints
    const publicEndpoints = ['/api/register', '/api/login'];
    const isPublicEndpoint = publicEndpoints.some(endpoint => config.url?.includes(endpoint));
    
    if (!isPublicEndpoint) {
      const auth = getStoredAuth();
      if (auth?.token) {
        config.headers.Authorization = `Bearer ${auth.token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      // Handle unauthorized access
      localStorage.removeItem('garment_inventory_auth');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
