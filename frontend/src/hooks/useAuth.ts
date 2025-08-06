import { useState, useEffect } from 'react';
import type { User } from '../types';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
}

export const useAuth = () => {
  const [authState, setAuthState] = useState<AuthState>({
    user: null,
    isAuthenticated: false,
  });

  const login = (user: User) => {
    console.log('🔑 Login successful for user:', user.email);
    // Store user in localStorage (simple approach, no tokens)
    localStorage.setItem('garment_inventory_user', JSON.stringify(user));
    setAuthState({
      user,
      isAuthenticated: true,
    });
  };

  const register = (user: User) => {
    console.log('📝 Registration successful for user:', user.email);
    // Same as login - just store user
    localStorage.setItem('garment_inventory_user', JSON.stringify(user));
    setAuthState({
      user,
      isAuthenticated: true,
    });
  };

  const logout = () => {
    console.log('🚪 Logging out user');
    localStorage.removeItem('garment_inventory_user');
    setAuthState({
      user: null,
      isAuthenticated: false,
    });
  };

  // Check for stored user on app load
  useEffect(() => {
    const storedUser = localStorage.getItem('garment_inventory_user');
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        console.log('🔄 Restored user from localStorage:', user.email);
        setAuthState({
          user,
          isAuthenticated: true,
        });
      } catch (error) {
        console.error('❌ Error parsing stored user data:', error);
        localStorage.removeItem('garment_inventory_user');
      }
    }
  }, []);

  return {
    user: authState.user,
    isAuthenticated: authState.isAuthenticated,
    login,
    logout,
    register
  };
};
