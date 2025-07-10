import React from 'react';
import { LoginForm } from './components/auth/LoginForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { ProductList } from './components/products/ProductList';
import { useAuth } from './hooks/useAuth';

function App() {
  const { user, isAuthenticated, login, logout } = useAuth();

  if (!isAuthenticated) {
    return <LoginForm onLogin={login} />;
  }

  return (
    <Layout user={user} onLogout={logout}>
      <Dashboard />
    </Layout>
  );
}

export default App;