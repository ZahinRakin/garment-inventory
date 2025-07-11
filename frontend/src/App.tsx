// import React from 'react';
import { RouterProvider, createBrowserRouter, Navigate } from 'react-router-dom';

import { LoginForm } from './components/auth/LoginForm';
import { RegistrationForm } from './components/auth/RegistrationForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { useAuth } from './hooks/useAuth';
import { ErrorPage } from './components/common/ErrorElement';

// Placeholder pages for routes not yet implemented
const Placeholder = ({ title }: { title: string }) => (
  <div className="p-8 text-2xl text-gray-700">{title} (Coming soon)</div>
);

function App() {
  const { user, isAuthenticated, login, logout, register } = useAuth();

  const requireRole = (roles: string[], element: React.ReactNode) => {
    if (!isAuthenticated || !user) return <Navigate to="/login" replace />;
    if (!roles.includes(user.role)) return <ErrorPage errorCode="403" errorMessage="Forbidden: You do not have access to this page." />;
    return element;
  };

  const router = createBrowserRouter([
    {
      path: '/',
      element: isAuthenticated ? (
        <Layout user={user} onLogout={logout}>
          <Dashboard user={user} />
        </Layout>
      ) : (
        <Navigate to="/login" replace />
      ),
      children: [
        {
          index: true,
          element: <Dashboard user={user} />
        },
        // --- ADMIN ---
        {
          path: 'products',
          element: requireRole(['ADMIN', 'PRODUCTION_OFFICER', 'SALES_OFFICER'], <Placeholder title="Products & Variants" />)
        },
        {
          path: 'raw-materials',
          element: requireRole(['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER'], <Placeholder title="Raw Materials" />)
        },
        {
          path: 'suppliers',
          element: requireRole(['ADMIN', 'STORE_MANAGER'], <Placeholder title="Suppliers & Purchases" />)
        },
        {
          path: 'production-orders',
          element: requireRole(['ADMIN', 'PRODUCTION_OFFICER'], <Placeholder title="Production Orders" />)
        },
        {
          path: 'sales-orders',
          element: requireRole(['ADMIN', 'SALES_OFFICER'], <Placeholder title="Sales Orders" />)
        },
        {
          path: 'stock',
          element: requireRole(['ADMIN', 'STORE_MANAGER'], <Placeholder title="Stock Management" />)
        },
        {
          path: 'reports',
          element: requireRole(['ADMIN'], <Placeholder title="Reports" />)
        },
        {
          path: 'reports/inventory',
          element: requireRole(['STORE_MANAGER'], <Placeholder title="Inventory Reports" />)
        },
        {
          path: 'reports/production',
          element: requireRole(['PRODUCTION_OFFICER'], <Placeholder title="Production Reports" />)
        },
        {
          path: 'reports/sales',
          element: requireRole(['SALES_OFFICER'], <Placeholder title="Sales Reports" />)
        },
        {
          path: 'users',
          element: requireRole(['ADMIN'], <Placeholder title="User Management" />)
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