//--------------------------------------------------------------------------------//
// api endpoint /api/products------------------------------------------------------//
// api endpoint /api/reports/sales/revenue?startDate=2024-01-01&endDate=2024-01-31-//
// api endpoint /api/production-orders---------------------------------------------//
// api endpoint /api/reports/low-stock---------------------------------------------//
//--------------------------------------------------------------------------------//
import React, { useState, useEffect } from 'react';
import { Package, ShoppingCart, Factory, AlertTriangle } from 'lucide-react';
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

interface DashboardData {
  totalProducts: number;
  salesThisMonth: number;
  productionOrders: number;
  lowStockItems: number;
  prevTotalProducts: number;
  prevSales: number;
  prevProductionOrders: number;
  prevLowStockItems: number;
}

function getPercentageChange(current: number, prev: number): { change: string; changeType: 'increase' | 'decrease' | 'neutral' } {
  if (prev === 0 && current === 0) return { change: '0%', changeType: 'neutral' };
  if (prev === 0) return { change: '+100%', changeType: 'increase' };
  const diff = current - prev;
  const percent = Math.round((diff / prev) * 100);
  if (percent > 0) return { change: `+${percent}%`, changeType: 'increase' };
  if (percent < 0) return { change: `${percent}%`, changeType: 'decrease' };
  return { change: '0%', changeType: 'neutral' };
}

export const DashboardStats: React.FC = () => {
  const [data, setData] = useState<DashboardData>({
    totalProducts: 0,
    salesThisMonth: 0,
    productionOrders: 0,
    lowStockItems: 0,
    prevTotalProducts: 0,
    prevSales: 0,
    prevProductionOrders: 0,
    prevLowStockItems: 0
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        setLoading(true);
        const now = new Date();
        const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
        const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);
        const prevMonth = new Date(now.getFullYear(), now.getMonth() - 1, 1);
        const startOfPrevMonth = new Date(prevMonth.getFullYear(), prevMonth.getMonth(), 1);
        const endOfPrevMonth = new Date(prevMonth.getFullYear(), prevMonth.getMonth() + 1, 0);

        const startDate = startOfMonth.toISOString().split('T')[0];
        const endDate = endOfMonth.toISOString().split('T')[0];
        const prevStartDate = startOfPrevMonth.toISOString().split('T')[0];
        const prevEndDate = endOfPrevMonth.toISOString().split('T')[0];

        const [productsRes, salesRes, productionRes, lowStockRes, prevSalesRes] = await Promise.all([
          fetch('/api/products'),
          fetch(`/api/reports/sales/revenue?startDate=${startDate}&endDate=${endDate}`),
          fetch('/api/production-orders'),
          fetch('/api/reports/low-stock'),
          fetch(`/api/reports/sales/revenue?startDate=${prevStartDate}&endDate=${prevEndDate}`)
        ]);

        if (!productsRes.ok || !salesRes.ok || !productionRes.ok || !lowStockRes.ok || !prevSalesRes.ok) {
          throw new Error('Failed to fetch dashboard data');
        }

        const [products, salesData, productionOrders, lowStockItems, prevSalesData] = await Promise.all([
          productsRes.json(),
          salesRes.json(),
          productionRes.json(),
          lowStockRes.json(),
          prevSalesRes.json()
        ]);

        setData({
          totalProducts: products.length,
          salesThisMonth: salesData.totalRevenue || 0,
          productionOrders: productionOrders.length,
          lowStockItems: lowStockItems.length,
          prevTotalProducts: 0, // Only include if backend supports filtering products by date
          prevSales: prevSalesData.totalRevenue || 0,
          prevProductionOrders: 0, // Only include if backend supports filtering production orders by date
          prevLowStockItems: 0 // Same as above
        });
      } catch (err) {
        console.error('Error fetching dashboard data:', err);
        setError('Failed to load dashboard data');
      } finally {
        setLoading(false);
      }
    };
    fetchDashboardData();
  }, []);

  const productChange = getPercentageChange(data.totalProducts, data.prevTotalProducts);
  const salesChange = getPercentageChange(data.salesThisMonth, data.prevSales);
  const productionChange = getPercentageChange(data.productionOrders, data.prevProductionOrders);
  const lowStockChange = getPercentageChange(data.lowStockItems, data.prevLowStockItems);

  const stats: StatItem[] = [
    {
      label: 'Total Products',
      value: loading ? '...' : formatNumber(data.totalProducts),
      change: loading ? '...' : productChange.change,
      changeType: productChange.changeType,
      icon: <Package className="w-6 h-6" />,
      color: 'from-blue-500 to-cyan-500'
    },
    {
      label: 'Sales This Month',
      value: loading ? '...' : formatCurrency(data.salesThisMonth),
      change: loading ? '...' : salesChange.change,
      changeType: salesChange.changeType,
      icon: <ShoppingCart className="w-6 h-6" />,
      color: 'from-emerald-500 to-teal-500'
    },
    {
      label: 'Production Orders',
      value: loading ? '...' : formatNumber(data.productionOrders),
      change: loading ? '...' : productionChange.change,
      changeType: productionChange.changeType,
      icon: <Factory className="w-6 h-6" />,
      color: 'from-purple-500 to-pink-500'
    },
    {
      label: 'Low Stock Items',
      value: loading ? '...' : formatNumber(data.lowStockItems),
      change: loading ? '...' : lowStockChange.change,
      changeType: lowStockChange.changeType,
      icon: <AlertTriangle className="w-6 h-6" />,
      color: 'from-orange-500 to-red-500'
    }
  ];

  if (error) {
    return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {stats.map((stat, index) => (
          <Card key={index} className="hover:shadow-md transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">{stat.label}</p>
                <p className="text-2xl font-bold text-red-600 mt-1">Error</p>
                <div className="flex items-center mt-2">
                  <span className="text-sm text-gray-500">Failed to load data</span>
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
  }

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
                  stat.changeType === 'decrease' ? 'text-red-600' :
                  'text-gray-600'
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
