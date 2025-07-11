import React from 'react';
import { Card } from '../common/Card';

// --- Types based on backend DTOs ---
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

// --- Dummy Data ---
// TODO: Replace with backend API calls to /api/reports/inventory
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

export const InventoryReportPage: React.FC = () => {
  // TODO: Fetch inventory report data from backend here
  // useEffect(() => { fetch('/api/reports/inventory') ... }, []);
  const stockSummary = dummyStockSummary;
  const lowStockItems = dummyLowStockItems;

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
      </div>
    </div>
  );
}; 