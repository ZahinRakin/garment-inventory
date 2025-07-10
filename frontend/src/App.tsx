import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { LoginForm } from './components/auth/LoginForm';
import { RegistrationForm } from './components/auth/RegistrationForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { ProductList } from './components/products/ProductList';
import { useAuth } from './hooks/useAuth';

function App() {
  const { user, isAuthenticated, login, logout } = useAuth();

  const handleRegister = (user: any, token: string) => {
    // You may want to implement registration logic here
    login(user, token); // Log in after registration
  };

  return (
    <Router>
      <Routes>
        {!isAuthenticated ? (
          <>
            <Route path="/login" element={<LoginForm onLogin={login} />} />
            <Route path="/register" element={<RegistrationForm onRegister={handleRegister} />} />
            <Route path="*" element={<Navigate to="/login" replace />} />
          </>
        ) : (
          <Route
            path="/*"
            element={
              <Layout user={user} onLogout={logout}>
                <Dashboard />
              </Layout>
            }
          />
        )}
      </Routes>
    </Router>
  );
}

export default App;