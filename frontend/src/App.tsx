// import React from 'react';
import { RouterProvider, createBrowserRouter, Navigate } from 'react-router-dom';

import { LoginForm } from './components/auth/LoginForm';
import { RegistrationForm } from './components/auth/RegistrationForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { useAuth } from './hooks/useAuth';
import { ErrorPage } from './components/common/ErrorElement';

function App() {
  const { user, isAuthenticated, login, logout, register } = useAuth();

  const router = createBrowserRouter([
    {
      path: '/',
      element: isAuthenticated ? (
        <Layout user={user} onLogout={logout}>
          <Dashboard />
        </Layout>
      ) : (
        <Navigate to="/login" replace />
      ),
      children: [
        {
          index: true,
          element: <Dashboard />
        }
      ]
    },
    {
      path: '/login',
      element: isAuthenticated ? (
        <Navigate to="/" replace />
      ) : (
        <LoginForm onLogin={login} onRegister={() => window.location.href = '/register'} />
      ),
      errorElement: <ErrorPage errorCode="401" errorMessage="Unauthorized access. Please log in." />
    },
    {
      path: '/register',
      element: isAuthenticated ? (
        <Navigate to="/" replace />
      ) : (
        <RegistrationForm onRegister={register} />
      ),
      errorElement: <ErrorPage errorCode="400" errorMessage="Registration failed. Please try again." />
    }
  ]);

  return <RouterProvider router={router} />;
}

export default App;