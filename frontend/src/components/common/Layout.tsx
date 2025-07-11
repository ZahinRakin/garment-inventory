import React from 'react';
import type { User } from '../../types';
import { Sidebar } from './Sidebar';
import { Header } from './Header';
import { Outlet } from 'react-router-dom';

interface LayoutProps {
  user: User | null;
  onLogout: () => void;
}

export const Layout: React.FC<LayoutProps> = ({ user, onLogout }) => {
  return (
    <div className="min-h-screen bg-gray-50">
      <Sidebar user={user} />
      <div className="ml-64">
        <Header user={user} onLogout={onLogout} />
        <main className="p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
};
