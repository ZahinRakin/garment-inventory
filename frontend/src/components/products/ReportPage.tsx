import React from 'react';
import { Card } from '../common/Card';

// --- Types based on backend DTOs ---
interface SalesReport {
  totalSales: number;
  totalOrders: number;
  totalRevenue: number;
  period: string;
}

interface StockSummaryReport {
  totalProducts: number;
  totalRawMaterials: number;
  lowStockItems: number;
  outOfStockItems: number;
}

interface LowStockItem {
  id: string;
  name: string;
  currentStock: number;
  reorderLevel: number;
}

interface TopItem {
  id: string;
  name: string;
  soldQuantity: number;
  revenue: number;
}

// --- Dummy Data ---
// TODO: Replace with backend API calls to /api/reports/summary, /api/reports/sales, etc.
const dummySalesReport: SalesReport = {
  totalSales: 120,
  totalOrders: 95,
  totalRevenue: 250000,
  period: 'Jan 2024',
};

const dummyStockSummary: StockSummaryReport = {
  totalProducts: 35,
  totalRawMaterials: 18,
  lowStockItems: 4,
  outOfStockItems: 1,
};

const dummyLowStockItems: LowStockItem[] = [
  { id: '1', name: 'Cotton Fabric', currentStock: 120, reorderLevel: 200 },
  { id: '2', name: 'Polyester Thread', currentStock: 30, reorderLevel: 50 },
  { id: '3', name: 'Metal Button', currentStock: 900, reorderLevel: 1000 },
  { id: '4', name: 'Elastic Band', currentStock: 40, reorderLevel: 100 },
];

const dummyTopItems: TopItem[] = [
  { id: '1', name: 'Cotton Shirt', soldQuantity: 320, revenue: 48000 },
  { id: '2', name: 'Denim Jeans', soldQuantity: 210, revenue: 63000 },
  { id: '3', name: 'Silk Scarf', soldQuantity: 150, revenue: 22500 },
];

export const ReportPage: React.FC = () => {
  // TODO: Fetch report data from backend here
  // useEffect(() => { fetch('/api/reports/summary') ... }, []);
  const salesReport = dummySalesReport;
  const stockSummary = dummyStockSummary;
  const lowStockItems = dummyLowStockItems;
  const topItems = dummyTopItems;

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Reports</h1>
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
        <Card title="Stock Summary">
          <div className="flex flex-col gap-2">
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Products</span>
              <span className="font-bold text-blue-700">{stockSummary.totalProducts}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Raw Materials</span>
              <span className="font-bold text-blue-700">{stockSummary.totalRawMaterials}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Low Stock Items</span>
              <span className="font-bold text-orange-600">{stockSummary.lowStockItems}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Out of Stock Items</span>
              <span className="font-bold text-red-600">{stockSummary.outOfStockItems}</span>
            </div>
          </div>
        </Card>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card title="Low Stock Items">
          <table className="min-w-full bg-white rounded-lg">
            <thead className="bg-orange-100">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-orange-700">Name</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-orange-700">Current Stock</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-orange-700">Reorder Level</th>
              </tr>
            </thead>
            <tbody>
              {lowStockItems.map(item => (
                <tr key={item.id} className="border-b hover:bg-orange-50 transition-colors">
                  <td className="px-4 py-2 text-gray-900">{item.name}</td>
                  <td className="px-4 py-2 text-gray-700">{item.currentStock}</td>
                  <td className="px-4 py-2 text-gray-700">{item.reorderLevel}</td>
                </tr>
              ))}
            </tbody>
          </table>
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