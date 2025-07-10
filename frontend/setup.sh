cat <<'EOF' > src/types/index.ts
// Auth Types
export interface User {
  id: string;
  email: string;
  role: 'ADMIN' | 'STORE_MANAGER' | 'PRODUCTION_OFFICER' | 'SALES_OFFICER';
  firstName: string;
  lastName: string;
}

export interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
}

// Product Types
export interface Product {
  id: string;
  name: string;
  category: string;
  description?: string;
  createdAt: string;
  variants: Variant[];
}

export interface Variant {
  id: string;
  productId: string;
  size: string;
  color: string;
  fabric: string;
  quantity: number;
  sku: string;
  unitPrice: number;
  product?: Product;
}

// Raw Material Types
export interface RawMaterial {
  id: string;
  name: string;
  unit: string;
  currentStock: number;
  reorderLevel: number;
  category: string;
  unitPrice: number;
  supplierId?: string;
  supplier?: Supplier;
}

// Supplier Types
export interface Supplier {
  id: string;
  name: string;
  contactPerson: string;
  email: string;
  phone: string;
  address: string;
  purchases: Purchase[];
}

export interface Purchase {
  id: string;
  supplierId: string;
  status: 'PENDING' | 'DELIVERED' | 'CANCELLED';
  totalAmount: number;
  orderDate: string;
  deliveryDate?: string;
  supplier?: Supplier;
  purchaseItems: PurchaseItem[];
}

export interface PurchaseItem {
  id: string;
  purchaseId: string;
  materialId: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  material?: RawMaterial;
}

// Production Types
export interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: 'CREATED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
  startDate: string;
  completionDate?: string;
  variant?: Variant;
  materialConsumption: MaterialConsumption[];
}

export interface MaterialConsumption {
  id: string;
  productionOrderId: string;
  materialId: string;
  quantityUsed: number;
  material?: RawMaterial;
}

// Sales Types
export interface SalesOrder {
  id: string;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  status: 'PENDING' | 'DELIVERED' | 'CANCELLED';
  totalAmount: number;
  orderDate: string;
  deliveryDate?: string;
  salesItems: SalesItem[];
}

export interface SalesItem {
  id: string;
  salesOrderId: string;
  variantId: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  variant?: Variant;
}

// Stock Types
export interface StockLocation {
  id: string;
  name: string;
  address: string;
  type: 'WAREHOUSE' | 'STORE' | 'PRODUCTION';
}

export interface StockAdjustment {
  id: string;
  variantId: string;
  locationId: string;
  adjustmentType: 'INCREASE' | 'DECREASE';
  quantity: number;
  reason: string;
  adjustedBy: string;
  adjustedAt: string;
}

// Common Types
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}
EOF

cat <<'EOF' > src/types/auth.ts
import { AuthState, User } from '../types';

const AUTH_STORAGE_KEY = 'garment_inventory_auth';

export const getStoredAuth = (): AuthState => {
  try {
    const stored = localStorage.getItem(AUTH_STORAGE_KEY);
    if (stored) {
      return JSON.parse(stored);
    }
  } catch (error) {
    console.error('Error parsing stored auth:', error);
  }
  
  return {
    user: null,
    token: null,
    isAuthenticated: false
  };
};

export const storeAuth = (auth: AuthState): void => {
  try {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(auth));
  } catch (error) {
    console.error('Error storing auth:', error);
  }
};

export const clearAuth = (): void => {
  try {
    localStorage.removeItem(AUTH_STORAGE_KEY);
  } catch (error) {
    console.error('Error clearing auth:', error);
  }
};

export const hasPermission = (user: User | null, requiredRoles: string[]): boolean => {
  if (!user) return false;
  return requiredRoles.includes(user.role);
};

export const formatUserName = (user: User | null): string => {
  if (!user) return '';
  return `${user.firstName} ${user.lastName}`;
};

export const getRoleDisplayName = (role: string): string => {
  switch (role) {
    case 'ADMIN':
      return 'Administrator';
    case 'STORE_MANAGER':
      return 'Store Manager';
    case 'PRODUCTION_OFFICER':
      return 'Production Officer';
    case 'SALES_OFFICER':
      return 'Sales Officer';
    default:
      return role;
  }
};
EOF

cat <<'EOF' > src/utils/format.ts
export const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR'
  }).format(amount);
};

export const formatDate = (dateString: string): string => {
  return new Date(dateString).toLocaleDateString('en-IN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });
};

export const formatDateTime = (dateString: string): string => {
  return new Date(dateString).toLocaleString('en-IN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
};

export const formatNumber = (num: number): string => {
  return new Intl.NumberFormat('en-IN').format(num);
};

export const getStatusColor = (status: string): string => {
  switch (status.toLowerCase()) {
    case 'pending':
    case 'created':
      return 'bg-yellow-100 text-yellow-800';
    case 'in_progress':
      return 'bg-blue-100 text-blue-800';
    case 'completed':
    case 'delivered':
      return 'bg-green-100 text-green-800';
    case 'cancelled':
      return 'bg-red-100 text-red-800';
    default:
      return 'bg-gray-100 text-gray-800';
  }
};

export const formatStatus = (status: string): string => {
  return status.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
};
EOF

cat <<'EOF' > src/hooks/useAuth.ts
import { useState, useEffect } from 'react';
import { AuthState, User } from '../types';
import { getStoredAuth, storeAuth, clearAuth } from '../utils/auth';

export const useAuth = () => {
  const [authState, setAuthState] = useState<AuthState>(getStoredAuth());

  useEffect(() => {
    storeAuth(authState);
  }, [authState]);

  const login = (user: User, token: string) => {
    const newAuthState = {
      user,
      token,
      isAuthenticated: true
    };
    setAuthState(newAuthState);
  };

  const logout = () => {
    const newAuthState = {
      user: null,
      token: null,
      isAuthenticated: false
    };
    setAuthState(newAuthState);
    clearAuth();
  };

  return {
    ...authState,
    login,
    logout
  };
};
EOF

cat <<'EOF' > src/components/common/Layout.tsx
import React from 'react';
import { User } from '../../types';
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
EOF

touch src/components/common/Header.tsx

cat <<'EOF' > src/components/common/Header.tsx
import React from 'react';
import { User, LogOut, Bell } from 'lucide-react';
import { User as UserType } from '../../types';
import { formatUserName, getRoleDisplayName } from '../../utils/auth';

interface HeaderProps {
  user: UserType | null;
  onLogout: () => void;
}

export const Header: React.FC<HeaderProps> = ({ user, onLogout }) => {
  return (
    <header className="bg-white shadow-sm border-b border-gray-200">
      <div className="flex items-center justify-between px-8 py-4">
        <div className="flex items-center space-x-4">
          <h1 className="text-2xl font-bold text-gray-900">
            Garment Inventory System
          </h1>
        </div>
        
        <div className="flex items-center space-x-4">
          <button className="p-2 text-gray-500 hover:text-gray-700 hover:bg-gray-100 rounded-lg transition-colors">
            <Bell className="w-5 h-5" />
          </button>
          
          <div className="flex items-center space-x-3">
            <div className="flex items-center space-x-3">
              <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center">
                <User className="w-4 h-4 text-white" />
              </div>
              <div className="hidden sm:block">
                <div className="text-sm font-medium text-gray-900">
                  {formatUserName(user)}
                </div>
                <div className="text-xs text-gray-500">
                  {user ? getRoleDisplayName(user.role) : ''}
                </div>
              </div>
            </div>
            
            <button
              onClick={onLogout}
              className="p-2 text-gray-500 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
              title="Logout"
            >
              <LogOut className="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>
    </header>
  );
};
EOF


# touch src/components/common/Footer.tsx
touch src/components/common/Sidebar.tsx
cat <<'EOF' > src/components/common/Sidebar.tsx
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
  Home
} from 'lucide-react';
import { User } from '../../types';
import { hasPermission } from '../../utils/auth';

interface SidebarProps {
  user: User | null;
}

interface NavItem {
  icon: React.ReactNode;
  label: string;
  href: string;
  roles: string[];
}

const navItems: NavItem[] = [
  {
    icon: <Home className="w-5 h-5" />,
    label: 'Dashboard',
    href: '#dashboard',
    roles: ['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER', 'SALES_OFFICER']
  },
  {
    icon: <Package className="w-5 h-5" />,
    label: 'Products',
    href: '#products',
    roles: ['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER']
  },
  {
    icon: <Warehouse className="w-5 h-5" />,
    label: 'Raw Materials',
    href: '#raw-materials',
    roles: ['ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER']
  },
  {
    icon: <Users className="w-5 h-5" />,
    label: 'Suppliers',
    href: '#suppliers',
    roles: ['ADMIN', 'STORE_MANAGER']
  },
  {
    icon: <Factory className="w-5 h-5" />,
    label: 'Production',
    href: '#production',
    roles: ['ADMIN', 'PRODUCTION_OFFICER']
  },
  {
    icon: <ShoppingCart className="w-5 h-5" />,
    label: 'Sales',
    href: '#sales',
    roles: ['ADMIN', 'SALES_OFFICER', 'STORE_MANAGER']
  },
  {
    icon: <TrendingUp className="w-5 h-5" />,
    label: 'Stock Management',
    href: '#stock',
    roles: ['ADMIN', 'STORE_MANAGER']
  },
  {
    icon: <BarChart3 className="w-5 h-5" />,
    label: 'Reports',
    href: '#reports',
    roles: ['ADMIN', 'STORE_MANAGER']
  },
  {
    icon: <Settings className="w-5 h-5" />,
    label: 'Settings',
    href: '#settings',
    roles: ['ADMIN']
  }
];

export const Sidebar: React.FC<SidebarProps> = ({ user }) => {
  const [activeItem, setActiveItem] = React.useState('#dashboard');

  const filteredNavItems = navItems.filter(item => 
    hasPermission(user, item.roles)
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
              key={item.href}
              onClick={() => setActiveItem(item.href)}
              className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                activeItem === item.href
                  ? 'bg-emerald-600 text-white'
                  : 'text-gray-300 hover:bg-slate-700 hover:text-white'
              }`}
            >
              {item.icon}
              <span className="font-medium">{item.label}</span>
            </button>
          ))}
        </div>
      </nav>
    </div>
  );
};
EOF



touch src/components/common/Card.tsx
cat <<'EOF' > src/components/common/Card.tsx
import React from 'react';

interface CardProps {
  children: React.ReactNode;
  className?: string;
  title?: string;
  subtitle?: string;
}

export const Card: React.FC<CardProps> = ({ children, className = '', title, subtitle }) => {
  return (
    <div className={`bg-white rounded-lg shadow-sm border border-gray-200 ${className}`}>
      {(title || subtitle) && (
        <div className="px-6 py-4 border-b border-gray-200">
          {title && <h3 className="text-lg font-semibold text-gray-900">{title}</h3>}
          {subtitle && <p className="text-sm text-gray-600 mt-1">{subtitle}</p>}
        </div>
      )}
      <div className="p-6">
        {children}
      </div>
    </div>
  );
};
EOF



touch src/components/common/Button.tsx
cat <<'EOF' > src/components/common/Button.tsx
import React from 'react';
import { Loader2 } from 'lucide-react';

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
  type?: 'button' | 'submit' | 'reset';
  variant?: 'primary' | 'secondary' | 'danger' | 'outline';
  size?: 'sm' | 'md' | 'lg';
  disabled?: boolean;
  loading?: boolean;
  className?: string;
}

export const Button: React.FC<ButtonProps> = ({
  children,
  onClick,
  type = 'button',
  variant = 'primary',
  size = 'md',
  disabled = false,
  loading = false,
  className = ''
}) => {
  const baseStyles = 'inline-flex items-center justify-center font-medium rounded-lg transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2';
  
  const variantStyles = {
    primary: 'bg-emerald-600 text-white hover:bg-emerald-700 focus:ring-emerald-500',
    secondary: 'bg-gray-200 text-gray-900 hover:bg-gray-300 focus:ring-gray-500',
    danger: 'bg-red-600 text-white hover:bg-red-700 focus:ring-red-500',
    outline: 'border border-gray-300 bg-white text-gray-700 hover:bg-gray-50 focus:ring-emerald-500'
  };
  
  const sizeStyles = {
    sm: 'px-3 py-1.5 text-sm',
    md: 'px-4 py-2 text-sm',
    lg: 'px-6 py-3 text-base'
  };

  const isDisabled = disabled || loading;

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={isDisabled}
      className={`${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${
        isDisabled ? 'opacity-50 cursor-not-allowed' : ''
      } ${className}`}
    >
      {loading && <Loader2 className="w-4 h-4 mr-2 animate-spin" />}
      {children}
    </button>
  );
};
EOF



touch src/components/common/StatusBadge.tsx
cat <<'EOF' > src/components/common/StatusBadge.tsx
import React from 'react';
import { getStatusColor, formatStatus } from '../../utils/format';

interface StatusBadgeProps {
  status: string;
  className?: string;
}

export const StatusBadge: React.FC<StatusBadgeProps> = ({ status, className = '' }) => {
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(status)} ${className}`}>
      {formatStatus(status)}
    </span>
  );
};
EOF

# mkdir src/components/auth
touch src/components/auth/LoginForm.tsx
cat <<'EOF' > src/components/auth/LoginForm.tsx
import React, { useState } from 'react';
import { Eye, EyeOff, Package } from 'lucide-react';
import { Button } from '../common/Button';
import { User } from '../../types';

interface LoginFormProps {
  onLogin: (user: User, token: string) => void;
}

export const LoginForm: React.FC<LoginFormProps> = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    // Mock authentication - replace with actual API call
    setTimeout(() => {
      const mockUser: User = {
        id: '1',
        email: email,
        firstName: 'John',
        lastName: 'Doe',
        role: 'ADMIN'
      };
      
      onLogin(mockUser, 'mock-jwt-token');
      setLoading(false);
    }, 1000);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-emerald-900">
      <div className="max-w-md w-full space-y-8 p-8">
        <div className="text-center">
          <div className="flex justify-center mb-4">
            <div className="w-16 h-16 bg-gradient-to-br from-emerald-400 to-cyan-500 rounded-2xl flex items-center justify-center">
              <Package className="w-8 h-8 text-white" />
            </div>
          </div>
          <h2 className="text-3xl font-bold text-white">Welcome back</h2>
          <p className="mt-2 text-sm text-gray-300">
            Sign in to your Garment Inventory System
          </p>
        </div>
        
        <form onSubmit={handleSubmit} className="mt-8 space-y-6">
          <div className="space-y-4">
            <div>
              <label htmlFor="email" className="sr-only">
                Email address
              </label>
              <input
                id="email"
                name="email"
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="relative block w-full px-3 py-3 border border-gray-600 bg-gray-700 text-white placeholder-gray-400 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 focus:z-10 sm:text-sm"
                placeholder="Email address"
              />
            </div>
            
            <div className="relative">
              <label htmlFor="password" className="sr-only">
                Password
              </label>
              <input
                id="password"
                name="password"
                type={showPassword ? 'text' : 'password'}
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="relative block w-full px-3 py-3 pr-10 border border-gray-600 bg-gray-700 text-white placeholder-gray-400 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 focus:z-10 sm:text-sm"
                placeholder="Password"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-300"
              >
                {showPassword ? (
                  <EyeOff className="w-5 h-5" />
                ) : (
                  <Eye className="w-5 h-5" />
                )}
              </button>
            </div>
          </div>

          <div>
            <Button
              type="submit"
              loading={loading}
              className="group relative w-full flex justify-center py-3 px-4 text-sm font-medium"
            >
              Sign in
            </Button>
          </div>
        </form>
        
        <div className="text-center">
          <p className="text-xs text-gray-400">
            Demo credentials: admin@example.com / password
          </p>
        </div>
      </div>
    </div>
  );
};
EOF

# mkdir src/components/dashboard
touch src/components/dashboard/DashboardStats.tsx
cat <<'EOF' > src/components/dashboard/DashboardStats.tsx
import React from 'react';
import { Package, ShoppingCart, Factory, TrendingUp, AlertTriangle } from 'lucide-react';
import { Card } from '../common/Card';
import { formatCurrency, formatNumber } from '../../utils/format';

interface StatItem {
  label: string;
  value: string;
  change: string;
  changeType: 'increase' | 'decrease' | 'neutral';
  icon: React.ReactNode;
  color: string;
}

export const DashboardStats: React.FC = () => {
  const stats: StatItem[] = [
    {
      label: 'Total Products',
      value: '1,234',
      change: '+12%',
      changeType: 'increase',
      icon: <Package className="w-6 h-6" />,
      color: 'from-blue-500 to-cyan-500'
    },
    {
      label: 'Sales This Month',
      value: formatCurrency(250000),
      change: '+8%',
      changeType: 'increase',
      icon: <ShoppingCart className="w-6 h-6" />,
      color: 'from-emerald-500 to-teal-500'
    },
    {
      label: 'Production Orders',
      value: '87',
      change: '+5%',
      changeType: 'increase',
      icon: <Factory className="w-6 h-6" />,
      color: 'from-purple-500 to-pink-500'
    },
    {
      label: 'Low Stock Items',
      value: '23',
      change: '-15%',
      changeType: 'decrease',
      icon: <AlertTriangle className="w-6 h-6" />,
      color: 'from-orange-500 to-red-500'
    }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      {stats.map((stat, index) => (
        <Card key={index} className="hover:shadow-md transition-shadow">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-gray-600">{stat.label}</p>
              <p className="text-2xl font-bold text-gray-900 mt-1">{stat.value}</p>
              <div className="flex items-center mt-2">
                <span className={`text-sm font-medium ${
                  stat.changeType === 'increase' ? 'text-emerald-600' : 
                  stat.changeType === 'decrease' ? 'text-red-600' : 'text-gray-600'
                }`}>
                  {stat.change}
                </span>
                <span className="text-sm text-gray-500 ml-1">from last month</span>
              </div>
            </div>
            <div className={`w-12 h-12 rounded-xl bg-gradient-to-br ${stat.color} flex items-center justify-center text-white`}>
              {stat.icon}
            </div>
          </div>
        </Card>
      ))}
    </div>
  );
};
EOF


touch src/components/dashboard/RecentActivity.tsx
cat <<'EOF' > src/components/dashboard/RecentActivity.tsx
import React from 'react';
import { Card } from '../common/Card';
import { StatusBadge } from '../common/StatusBadge';
import { formatDateTime } from '../../utils/format';

interface Activity {
  id: string;
  type: 'sale' | 'production' | 'purchase' | 'stock';
  description: string;
  timestamp: string;
  status: string;
}

export const RecentActivity: React.FC = () => {
  const activities: Activity[] = [
    {
      id: '1',
      type: 'sale',
      description: 'New sales order #SO-2024-001 for Cotton Shirt - Blue',
      timestamp: '2024-01-15T10:30:00Z',
      status: 'PENDING'
    },
    {
      id: '2',
      type: 'production',
      description: 'Production order #PO-2024-015 completed for Denim Jeans',
      timestamp: '2024-01-15T09:15:00Z',
      status: 'COMPLETED'
    },
    {
      id: '3',
      type: 'purchase',
      description: 'Purchase order #PR-2024-008 received from Textile Suppliers Ltd.',
      timestamp: '2024-01-15T08:45:00Z',
      status: 'DELIVERED'
    },
    {
      id: '4',
      type: 'stock',
      description: 'Stock adjustment for Silk Fabric - Red (Warehouse A)',
      timestamp: '2024-01-15T07:30:00Z',
      status: 'COMPLETED'
    }
  ];

  return (
    <Card title="Recent Activity" className="lg:col-span-2">
      <div className="space-y-4">
        {activities.map((activity) => (
          <div key={activity.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
            <div className="flex-1">
              <p className="text-sm font-medium text-gray-900">{activity.description}</p>
              <p className="text-xs text-gray-500 mt-1">{formatDateTime(activity.timestamp)}</p>
            </div>
            <StatusBadge status={activity.status} />
          </div>
        ))}
      </div>
    </Card>
  );
};
EOF

touch src/components/dashboard/Dashboard.tsx
cat <<'EOF' > src/components/dashboard/Dashboard.tsx
import React from 'react';
import { DashboardStats } from './DashboardStats';
import { RecentActivity } from './RecentActivity';
import { Card } from '../common/Card';

export const Dashboard: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      
      <DashboardStats />
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <RecentActivity />
        
        <Card title="Quick Actions">
          <div className="space-y-3">
            <button className="w-full text-left p-3 bg-emerald-50 hover:bg-emerald-100 rounded-lg transition-colors">
              <div className="font-medium text-emerald-700">Add New Product</div>
              <div className="text-sm text-emerald-600">Create a new product variant</div>
            </button>
            <button className="w-full text-left p-3 bg-blue-50 hover:bg-blue-100 rounded-lg transition-colors">
              <div className="font-medium text-blue-700">New Sales Order</div>
              <div className="text-sm text-blue-600">Process a customer order</div>
            </button>
            <button className="w-full text-left p-3 bg-purple-50 hover:bg-purple-100 rounded-lg transition-colors">
              <div className="font-medium text-purple-700">Stock Adjustment</div>
              <div className="text-sm text-purple-600">Update inventory levels</div>
            </button>
          </div>
        </Card>
      </div>
    </div>
  );
};
EOF

mkdir src/components/products
touch src/components/products/ProductList.tsx
cat <<'EOF' > src/components/dashboard/ProductList.tsx
import React, { useState } from 'react';
import { Plus, Search, Filter, Edit, Trash2 } from 'lucide-react';
import { Card } from '../common/Card';
import { Button } from '../common/Button';
import { StatusBadge } from '../common/StatusBadge';
import { Product, Variant } from '../../types';
import { formatCurrency, formatNumber } from '../../utils/format';

export const ProductList: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');

  // Mock data - replace with actual API call
  const products: Product[] = [
    {
      id: '1',
      name: 'Cotton T-Shirt',
      category: 'Apparel',
      description: 'Premium cotton t-shirt with comfortable fit',
      createdAt: '2024-01-01T00:00:00Z',
      variants: [
        { id: '1', productId: '1', size: 'M', color: 'Blue', fabric: 'Cotton', quantity: 150, sku: 'CT-BLU-M-001', unitPrice: 25.99 },
        { id: '2', productId: '1', size: 'L', color: 'Red', fabric: 'Cotton', quantity: 75, sku: 'CT-RED-L-002', unitPrice: 25.99 }
      ]
    },
    {
      id: '2',
      name: 'Denim Jeans',
      category: 'Apparel',
      description: 'Classic denim jeans with modern fit',
      createdAt: '2024-01-02T00:00:00Z',
      variants: [
        { id: '3', productId: '2', size: '32', color: 'Blue', fabric: 'Denim', quantity: 200, sku: 'DJ-BLU-32-001', unitPrice: 49.99 },
        { id: '4', productId: '2', size: '34', color: 'Black', fabric: 'Denim', quantity: 120, sku: 'DJ-BLK-34-002', unitPrice: 49.99 }
      ]
    }
  ];

  const categories = ['all', 'Apparel', 'Footwear', 'Accessories'];

  const getTotalQuantity = (variants: Variant[]) => {
    return variants.reduce((sum, variant) => sum + variant.quantity, 0);
  };

  const getAveragePrice = (variants: Variant[]) => {
    if (variants.length === 0) return 0;
    return variants.reduce((sum, variant) => sum + variant.unitPrice, 0) / variants.length;
  };

  const filteredProducts = products.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         product.category.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === 'all' || product.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Products</h1>
        <Button className="flex items-center space-x-2">
          <Plus className="w-4 h-4" />
          <span>Add Product</span>
        </Button>
      </div>

      <Card>
        <div className="flex flex-col sm:flex-row gap-4 mb-6">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
            <input
              type="text"
              placeholder="Search products..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
            />
          </div>
          
          <select
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
          >
            {categories.map(category => (
              <option key={category} value={category}>
                {category === 'all' ? 'All Categories' : category}
              </option>
            ))}
          </select>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 font-medium text-gray-900">Product</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Category</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Variants</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Total Stock</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Avg. Price</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredProducts.map((product) => (
                <tr key={product.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">
                    <div>
                      <div className="font-medium text-gray-900">{product.name}</div>
                      <div className="text-sm text-gray-500">{product.description}</div>
                    </div>
                  </td>
                  <td className="py-3 px-4">
                    <StatusBadge status={product.category} />
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {product.variants.length} variants
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {formatNumber(getTotalQuantity(product.variants))}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {formatCurrency(getAveragePrice(product.variants))}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex items-center space-x-2">
                      <button className="p-1 text-gray-400 hover:text-emerald-600 transition-colors">
                        <Edit className="w-4 h-4" />
                      </button>
                      <button className="p-1 text-gray-400 hover:text-red-600 transition-colors">
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};
EOF

cat <<'EOF' > src/App.tsx
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
EOF