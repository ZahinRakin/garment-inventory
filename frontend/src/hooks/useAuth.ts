import { useState, useEffect } from 'react';
import type { AuthState, User } from '../types';
import { getStoredAuth, storeAuth, clearAuth } from '../utils/auth';

export const useAuth = () => {
  const [authState, setAuthState] = useState<AuthState>(getStoredAuth());

  useEffect(() => {
    storeAuth(authState);
  }, [authState]);

  const login = (user: User, token: string) => {
    const newAuthState = {
      user,
      token,
      isAuthenticated: true
    };
    setAuthState(newAuthState);
  };

  const logout = () => {
    const newAuthState = {
      user: null,
      token: null,
      isAuthenticated: false
    };
    setAuthState(newAuthState);
    clearAuth();
  };

  return {
    ...authState,
    login,
    logout
  };
};
