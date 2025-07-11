// import React from 'react';
import { RouterProvider, createBrowserRouter, Navigate } from 'react-router-dom';

import { LoginForm } from './components/auth/LoginForm';
import { RegistrationForm } from './components/auth/RegistrationForm';
import { Layout } from './components/common/Layout';
import { Dashboard } from './components/dashboard/Dashboard';
import { useAuth } from './hooks/useAuth';
import { ErrorPage } from './components/common/ErrorElement';
import { ProductList } from './components/products/ProductList';
import { RawMaterialList } from './components/products/RawMaterialList';
import { SupplierList } from './components/products/SupplierList';
import { PurchaseList } from './components/products/PurchaseList';
import { ProductionOrderList } from './components/products/ProductionOrderList';
import { SalesOrderList } from './components/products/SalesOrderList';
import { StockManagementList } from './components/products/StockManagementList';
import { ReportPage } from './components/products/ReportPage';
import { InventoryReportPage } from './components/products/InventoryReportPage';
import { ProductionReportPage } from './components/products/ProductionReportPage';
import { SalesReportPage } from './components/products/SalesReportPage';

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
        <Layout user={user} onLogout={logout} />
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
          element: requireRole(['ADMIN', 'PRODUCTION_OFFICER', 'SALES_OFFICER'], <ProductList />)
        },
        {
          path: 'raw-materials',
          element: requireRole(['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER'], <RawMaterialList />)
        },
        {
          path: 'suppliers',
          element: requireRole(['ADMIN', 'STORE_MANAGER'], <SupplierList />)
        },
        {
          path: 'purchases',
          element: requireRole(['ADMIN', 'STORE_MANAGER'], <PurchaseList />)
        },
        {
          path: 'production-orders',
          element: requireRole(['ADMIN', 'PRODUCTION_OFFICER'], <ProductionOrderList />)
        },
        {
          path: 'sales-orders',
          element: requireRole(['ADMIN', 'SALES_OFFICER'], <SalesOrderList />)
        },
        {
          path: 'stock',
          element: requireRole(['ADMIN', 'STORE_MANAGER'], <StockManagementList />)
        },
        {
          path: 'reports',
          element: requireRole(['ADMIN'], <ReportPage />)
        },
        {
          path: 'reports/inventory',
          element: requireRole(['STORE_MANAGER'], <InventoryReportPage />)
        },
        {
          path: 'reports/production',
          element: requireRole(['PRODUCTION_OFFICER'], <ProductionReportPage />)
        },
        {
          path: 'reports/sales',
          element: requireRole(['SALES_OFFICER'], <SalesReportPage />)
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