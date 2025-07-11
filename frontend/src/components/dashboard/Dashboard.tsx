import React from 'react';
import { DashboardAdmin } from './DashboardAdmin';
import { DashboardStoreManager } from './DashboardStoreManager';
import { DashboardProductionOfficer } from './DashboardProductionOfficer';
import { DashboardSalesOfficer } from './DashboardSalesOfficer';
import type { User } from '../../types';

interface DashboardProps {
  user: User | null;
}

export const Dashboard: React.FC<DashboardProps> = ({ user }) => {
  if (!user) {
    return <div className="text-center text-red-500">No user found. Please log in.</div>;
  }
  switch (user.role) {
    case 'ADMIN':
      return <DashboardAdmin />;
    case 'STORE_MANAGER':
      return <DashboardStoreManager />;
    case 'PRODUCTION_OFFICER':
      return <DashboardProductionOfficer />;
    case 'SALES_OFFICER':
      return <DashboardSalesOfficer />;
    default:
      return <div className="text-center text-red-500">Unknown role: {user.role}</div>;
  }
};
