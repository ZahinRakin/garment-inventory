// import React from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';


import { LoginForm } from './components/auth/LoginForm';
import { RegistrationForm } from './components/auth/RegistrationForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { useAuth } from './hooks/useAuth';
import { ErrorPage } from './components/common/ErrorElement';
import { RegistrationForm } from './components/auth/RegistrationForm';



function App() {
  const { user, isAuthenticated, login, logout } = useAuth();
  const router = createBrowserRouter([
    {
      path: '/',
      element: <Layout />, // Layout wraps child routes
      children: [
        {
          index: true,
          element: <Dashboard />
        },{
          path: 'login',
          element: <LoginForm onLogin={login} onRegister={register} />,
          errorElement: <ErrorPage errorCode="401" errorMessage="Unauthorized access. Please log in." />
        }
      ]
    }
  ]);

  if (!isAuthenticated) {
    return <LoginForm onLogin={login} />;
  }

  return (
    <div>
      <RouterProvider router={router} >
        <Layout user={user} onLogout={logout}>
          <Dashboard />
        </Layout>
      </ RouterProvider>
    </div>
  );
}

export default App;