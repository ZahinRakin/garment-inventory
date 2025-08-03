import type { AuthState, User } from '../types/index';

const AUTH_STORAGE_KEY = 'garment_inventory_auth';

const getStoredAuth = (): AuthState => {
  try {
    const stored = localStorage.getItem(AUTH_STORAGE_KEY);
    if (stored) {
      const parsedAuth = JSON.parse(stored);
      // Validate that the stored auth has all required properties and is still valid
      if (parsedAuth.user && parsedAuth.token && parsedAuth.isAuthenticated) {
        return parsedAuth;
      }
    }
  } catch (error) {
    console.error('Error parsing stored auth:', error);
    // Clear invalid auth data
    localStorage.removeItem(AUTH_STORAGE_KEY);
  }
  
  return {
    user: null,
    token: null,
    isAuthenticated: false
  };
};

const storeAuth = (auth: AuthState): void => {
  try {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(auth));
  } catch (error) {
    console.error('Error storing auth:', error);
  }
};

const clearAuth = (): void => {
  try {
    localStorage.removeItem(AUTH_STORAGE_KEY);
  } catch (error) {
    console.error('Error clearing auth:', error);
  }
};

const hasPermission = (user: User | null, requiredRoles: string[]): boolean => {
  if (!user) return false;
  return requiredRoles.includes(user.role);
};

const formatUserName = (user: User | null): string => {
  if (!user) return '';
  return `${user.firstName} ${user.lastName}`;
};

const getRoleDisplayName = (role: string): string => {
  switch (role) {
    case 'ADMIN':
      return 'Administrator';
    case 'STORE_MANAGER':
      return 'Store Manager';
    case 'PRODUCTION_OFFICER':
      return 'Production Officer';
    case 'SALES_OFFICER':
      return 'Sales Officer';
    default:
      return role;
  }
};


export {
  getStoredAuth,
  storeAuth,
  clearAuth,
  hasPermission,
  formatUserName,
  getRoleDisplayName
}