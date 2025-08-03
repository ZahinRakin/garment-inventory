import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

// --- Types based on backend DTOs ---
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

export const InventoryReportPage: React.FC = () => {
  const [stockSummary, setStockSummary] = useState<StockSummaryReport>({
    totalRawMaterials: 0,
    lowStockRawMaterials: 0,
    totalVariants: 0,
    lowStockVariants: 0
  });
  const [lowStockItems, setLowStockItems] = useState<LowStockItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchInventoryData = async () => {
      try {
        setLoading(true);
        
        // Fetch stock summary and low stock items in parallel
        const [stockSummaryResponse, lowStockResponse] = await Promise.all([
          api.get("/api/reports/stock-summary"),
          api.get("/api/reports/low-stock")
        ]);

        const summaryData: StockSummaryReport = stockSummaryResponse.data;
        const lowStockData: LowStockItem[] = lowStockResponse.data;

        setStockSummary(summaryData);
        setLowStockItems(lowStockData);
      } catch (error) {
        console.error('Error fetching inventory data:', error);
        setError('Failed to load inventory data');
      } finally {
        setLoading(false);
      }
    };

    fetchInventoryData();
  }, []);

  // Calculate derived values
  const totalProducts = stockSummary.totalVariants;
  const totalRawMaterials = stockSummary.totalRawMaterials;
  const lowStockItemsCount = stockSummary.lowStockRawMaterials + stockSummary.lowStockVariants;
  const outOfStockItems = lowStockItems.filter(item => item.current === 0).length;

  if (loading) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Inventory Report</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-gray-500">Loading inventory data...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Inventory Report</h1>
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
        <h1 className="text-3xl font-bold text-gray-900">Inventory Report</h1>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
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
      </div>
    </div>
  );
}; 