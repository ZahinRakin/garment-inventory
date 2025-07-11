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

interface StockSummaryReport {
  totalRawMaterials: number;
  lowStockRawMaterials: number;
  totalVariants: number;
  lowStockVariants: number;
}

interface LowStockItem {
  type: string;
  id: string;
  name: string;
  current: number;
  threshold: number;
}

interface TopItem {
  id: string;
  count: number;
}

interface RevenueData {
  totalRevenue: number;
  orderCount: number;
}

export const ReportPage: React.FC = () => {
  const [salesReport, setSalesReport] = useState<SalesReport>({
    totalSales: 0,
    totalOrders: 0,
    totalRevenue: 0,
    period: ''
  });
  const [stockSummary, setStockSummary] = useState<StockSummaryReport>({
    totalRawMaterials: 0,
    lowStockRawMaterials: 0,
    totalVariants: 0,
    lowStockVariants: 0
  });
  const [lowStockItems, setLowStockItems] = useState<LowStockItem[]>([]);
  const [topItems, setTopItems] = useState<TopItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchReportData = async () => {
      try {
        setLoading(true);
        
        // Calculate current month date range
        const now = new Date();
        const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
        const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);
        const startDate = startOfMonth.toISOString().split('T')[0];
        const endDate = endOfMonth.toISOString().split('T')[0];
        const period = `${now.toLocaleString('default', { month: 'long' })} ${now.getFullYear()}`;

        // Fetch all report data in parallel
        const [revenueResponse, stockSummaryResponse, lowStockResponse, topProductsResponse] = await Promise.all([
          axios.get(`/api/reports/sales/revenue?startDate=${startDate}&endDate=${endDate}`, {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/reports/stock-summary", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/reports/low-stock", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/reports/sales/top-products?limit=5", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const revenueData: RevenueData = revenueResponse.data;
        const stockSummaryData: StockSummaryReport = stockSummaryResponse.data;
        const lowStockData: LowStockItem[] = lowStockResponse.data;
        const topProductsData: TopItem[] = topProductsResponse.data;

        // Process sales report data
        setSalesReport({
          totalSales: revenueData.totalRevenue,
          totalOrders: revenueData.orderCount,
          totalRevenue: revenueData.totalRevenue,
          period
        });

        // Process stock summary data
        setStockSummary(stockSummaryData);

        // Process low stock items
        setLowStockItems(lowStockData);

        // Process top selling items
        setTopItems(topProductsData);

      } catch (error) {
        console.error('Error fetching report data:', error);
        setError('Failed to load report data');
      } finally {
        setLoading(false);
      }
    };

    fetchReportData();
  }, []);

  // Calculate derived values for stock summary
  const totalProducts = stockSummary.totalVariants;
  const totalRawMaterials = stockSummary.totalRawMaterials;
  const lowStockItemsCount = stockSummary.lowStockRawMaterials + stockSummary.lowStockVariants;
  const outOfStockItems = lowStockItems.filter(item => item.current === 0).length;

  const formatCurrency = (amount: number) => {
    return `৳ ${amount.toLocaleString()}`;
  };

  if (loading) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Reports</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-gray-500">Loading report data...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Reports</h1>
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
        <h1 className="text-3xl font-bold text-gray-900">Reports</h1>
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
        <Card title="Stock Summary">
          <div className="flex flex-col gap-2">
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Products</span>
              <span className="font-bold text-blue-700">{totalProducts}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Raw Materials</span>
              <span className="font-bold text-blue-700">{totalRawMaterials}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Low Stock Items</span>
              <span className="font-bold text-orange-600">{lowStockItemsCount}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Out of Stock Items</span>
              <span className="font-bold text-red-600">{outOfStockItems}</span>
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
              {lowStockItems && lowStockItems.length > 0 ? (
                lowStockItems.map(item => (
                  <tr key={item.id} className="border-b hover:bg-orange-50 transition-colors">
                    <td className="px-4 py-2 text-gray-900">{item.name}</td>
                    <td className={`px-4 py-2 text-gray-700 ${
                      item.current === 0 ? 'text-red-600 font-semibold' : ''
                    }`}>
                      {item.current}
                    </td>
                    <td className="px-4 py-2 text-gray-700">{item.threshold}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={3} className="px-4 py-2 text-center text-gray-500">
                    No low stock items found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
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