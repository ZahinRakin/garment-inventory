import React from 'react';
import {
  Package,
  ShoppingCart,
  Users,
  Factory,
  Warehouse,
  TrendingUp,
  BarChart3,
  Settings,
  Home,
  Lock
} from 'lucide-react';
import type { User } from '../../types';
import { hasPermission } from '../../utils/auth';

interface SidebarProps {
  user: User | null;
}

interface NavItem {
  icon: React.ReactNode;
  label: string;
  href: string;
  roles: string[];
  readOnly?: boolean;
}

const navItems: NavItem[] = [
  {
    icon: <Home className="w-5 h-5" />,
    label: 'Dashboard',
    href: '/',
    roles: ['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER', 'SALES_OFFICER']
  },
  {
    icon: <Package className="w-5 h-5" />,
    label: 'Products & Variants',
    href: '/products',
    roles: ['ADMIN'],
  },
  {
    icon: <Warehouse className="w-5 h-5" />,
    label: 'Raw Materials',
    href: '/raw-materials',
    roles: ['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER'],
    readOnly: false
  },
  {
    icon: <Users className="w-5 h-5" />,
    label: 'Suppliers & Purchases',
    href: '/suppliers',
    roles: ['ADMIN', 'STORE_MANAGER']
  },
  {
    icon: <Factory className="w-5 h-5" />,
    label: 'Production Orders',
    href: '/production-orders',
    roles: ['ADMIN', 'PRODUCTION_OFFICER']
  },
  {
    icon: <ShoppingCart className="w-5 h-5" />,
    label: 'Sales Orders',
    href: '/sales-orders',
    roles: ['ADMIN', 'SALES_OFFICER']
  },
  {
    icon: <TrendingUp className="w-5 h-5" />,
    label: 'Stock Management',
    href: '/stock',
    roles: ['ADMIN', 'STORE_MANAGER']
  },
  {
    icon: <BarChart3 className="w-5 h-5" />,
    label: 'Reports',
    href: '/reports',
    roles: ['ADMIN'],
  },
  {
    icon: <BarChart3 className="w-5 h-5" />,
    label: 'Reports (Inventory)',
    href: '/reports/inventory',
    roles: ['STORE_MANAGER']
  },
  {
    icon: <BarChart3 className="w-5 h-5" />,
    label: 'Reports (Production)',
    href: '/reports/production',
    roles: ['PRODUCTION_OFFICER']
  },
  {
    icon: <BarChart3 className="w-5 h-5" />,
    label: 'Reports (Sales)',
    href: '/reports/sales',
    roles: ['SALES_OFFICER']
  },
  {
    icon: <Package className="w-5 h-5" />,
    label: 'Product Variants',
    href: '/products',
    roles: ['PRODUCTION_OFFICER'],
    readOnly: true
  },
  {
    icon: <Package className="w-5 h-5" />,
    label: 'Products',
    href: '/products',
    roles: ['SALES_OFFICER'],
    readOnly: true
  },
  {
    icon: <Warehouse className="w-5 h-5" />,
    label: 'Raw Materials',
    href: '/raw-materials',
    roles: ['PRODUCTION_OFFICER'],
    readOnly: true
  },
  {
    icon: <Settings className="w-5 h-5" />,
    label: 'User Management',
    href: '/users',
    roles: ['ADMIN'],
  },
];

export const Sidebar: React.FC<SidebarProps> = ({ user }) => {
  const [activeItem, setActiveItem] = React.useState('/');

  // Remove duplicates by label+href, keep the first match
  const uniqueNavItems = navItems.filter((item, idx, arr) =>
    arr.findIndex(i => i.label === item.label && i.href === item.href) === idx
  );

  const filteredNavItems = uniqueNavItems.filter(item =>
    user && item.roles.includes(user.role)
  );

  return (
    <div className="fixed inset-y-0 left-0 z-50 w-64 bg-slate-800 shadow-lg">
      <div className="flex items-center justify-center h-16 bg-slate-900">
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 bg-gradient-to-br from-emerald-400 to-cyan-500 rounded-lg flex items-center justify-center">
            <Package className="w-5 h-5 text-white" />
          </div>
          <span className="text-xl font-bold text-white">GIS</span>
        </div>
      </div>
      <nav className="mt-8">
        <div className="px-4 space-y-2">
          {filteredNavItems.map((item) => (
            <button
              key={item.href + item.label}
              onClick={() => setActiveItem(item.href)}
              className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                activeItem === item.href
                  ? 'bg-emerald-600 text-white'
                  : 'text-gray-300 hover:bg-slate-700 hover:text-white'
              } ${item.readOnly ? 'opacity-60 cursor-not-allowed' : ''}`}
              disabled={!!item.readOnly}
            >
              {item.icon}
              <span className="font-medium">{item.label}</span>
              {item.readOnly && <Lock className="w-4 h-4 ml-2 text-gray-400" />}
            </button>
          ))}
        </div>
      </nav>
    </div>
  );
};
