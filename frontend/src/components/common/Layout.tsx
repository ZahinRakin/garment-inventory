import React from 'react';
import type { User } from '../../types';
import { Sidebar } from './Sidebar';
import { Header } from './Header';

interface LayoutProps {
  children: React.ReactNode;
  user: User | null;
  onLogout: () => void;
}

export const Layout: React.FC<LayoutProps> = ({ children, user, onLogout }) => {
  return (
    <div className="min-h-screen bg-gray-50">
      <Sidebar user={user} />
      <div className="ml-64">
        <Header user={user} onLogout={onLogout} />
        <main className="p-8">
          {children}
        </main>
      </div>
    </div>
  );
};
