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
