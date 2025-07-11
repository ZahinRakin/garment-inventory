import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

// --- Types based on backend DTOs ---
interface SalesReport {
  totalSales: number;
  totalOrders: number;
  totalRevenue: number;
  period: string;
}

interface TopItem {
  id: string;
  count: number;
}

interface RevenueData {
  totalRevenue: number;
  orderCount: number;
}

export const SalesReportPage: React.FC = () => {
  const [salesReport, setSalesReport] = useState<SalesReport>({
    totalSales: 0,
    totalOrders: 0,
    totalRevenue: 0,
    period: ''
  });
  const [topItems, setTopItems] = useState<TopItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSalesData = async () => {
      try {
        setLoading(true);
        
        // Calculate current month date range
        const now = new Date();
        const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
        const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);
        const startDate = startOfMonth.toISOString().split('T')[0];
        const endDate = endOfMonth.toISOString().split('T')[0];
        const period = `${now.toLocaleString('default', { month: 'long' })} ${now.getFullYear()}`;

        // Fetch sales data from multiple endpoints in parallel
        const [revenueResponse, topProductsResponse] = await Promise.all([
          axios.get(`/api/reports/sales/revenue?startDate=${startDate}&endDate=${endDate}`, {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/reports/sales/top-products?limit=5", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const revenueData: RevenueData = revenueResponse.data;
        const topProductsData: TopItem[] = topProductsResponse.data;

        // Process sales report data
        setSalesReport({
          totalSales: revenueData.totalRevenue,
          totalOrders: revenueData.orderCount,
          totalRevenue: revenueData.totalRevenue,
          period
        });

        // Process top selling items
        setTopItems(topProductsData);

      } catch (error) {
        console.error('Error fetching sales data:', error);
        setError('Failed to load sales data');
      } finally {
        setLoading(false);
      }
    };

    fetchSalesData();
  }, []);

  const formatCurrency = (amount: number) => {
    return `৳ ${amount.toLocaleString()}`;
  };

  if (loading) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Sales Report</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-gray-500">Loading sales data...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Sales Report</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-red-500">{error}</div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Sales Report</h1>
        <span className="text-sm text-gray-500">Period: {salesReport.period}</span>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card title="Sales Summary">
          <div className="flex flex-col gap-2">
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Sales</span>
              <span className="font-bold text-emerald-700">{formatCurrency(salesReport.totalSales)}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Orders</span>
              <span className="font-bold text-emerald-700">{salesReport.totalOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Revenue</span>
              <span className="font-bold text-emerald-700">{formatCurrency(salesReport.totalRevenue)}</span>
            </div>
          </div>
        </Card>
        <Card title="Top Selling Items">
          <table className="min-w-full bg-white rounded-lg">
            <thead className="bg-emerald-100">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-emerald-700">Variant ID</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-emerald-700">Sold Quantity</th>
              </tr>
            </thead>
            <tbody>
              {topItems && topItems.length > 0 ? (
                topItems.map(item => (
                  <tr key={item.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-4 py-2 text-gray-900">{item.id}</td>
                    <td className="px-4 py-2 text-gray-700">{item.count}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={2} className="px-4 py-2 text-center text-gray-500">
                    No sales data available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </Card>
      </div>
    </div>
  );
}; 