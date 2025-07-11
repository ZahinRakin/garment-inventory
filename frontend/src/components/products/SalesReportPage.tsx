import React from 'react';
import { Card } from '../common/Card';

// --- Types based on backend DTOs ---
interface SalesReport {
  totalSales: number;
  totalOrders: number;
  totalRevenue: number;
  period: string;
}

interface TopItem {
  id: string;
  name: string;
  soldQuantity: number;
  revenue: number;
}

// --- Dummy Data ---
// TODO: Replace with backend API calls to /api/reports/sales
const dummySalesReport: SalesReport = {
  totalSales: 120,
  totalOrders: 95,
  totalRevenue: 250000,
  period: 'Jan 2024',
};

const dummyTopItems: TopItem[] = [
  { id: '1', name: 'Cotton Shirt', soldQuantity: 320, revenue: 48000 },
  { id: '2', name: 'Denim Jeans', soldQuantity: 210, revenue: 63000 },
  { id: '3', name: 'Silk Scarf', soldQuantity: 150, revenue: 22500 },
];

export const SalesReportPage: React.FC = () => {
  // TODO: Fetch sales report data from backend here
  // useEffect(() => { fetch('/api/reports/sales') ... }, []);
  const salesReport = dummySalesReport;
  const topItems = dummyTopItems;

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
              <span className="font-bold text-emerald-700">{salesReport.totalSales}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Orders</span>
              <span className="font-bold text-emerald-700">{salesReport.totalOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Revenue</span>
              <span className="font-bold text-emerald-700">৳ {salesReport.totalRevenue.toLocaleString()}</span>
            </div>
          </div>
        </Card>
        <Card title="Top Selling Items">
          <table className="min-w-full bg-white rounded-lg">
            <thead className="bg-emerald-100">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-emerald-700">Name</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-emerald-700">Sold Quantity</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-emerald-700">Revenue</th>
              </tr>
            </thead>
            <tbody>
              {topItems.map(item => (
                <tr key={item.id} className="border-b hover:bg-emerald-50 transition-colors">
                  <td className="px-4 py-2 text-gray-900">{item.name}</td>
                  <td className="px-4 py-2 text-gray-700">{item.soldQuantity}</td>
                  <td className="px-4 py-2 text-gray-700">৳ {item.revenue.toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      </div>
    </div>
  );
}; 