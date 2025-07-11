import React from 'react';
import { Card } from '../common/Card';

// --- Types based on production report needs ---
interface ProductionSummary {
  totalOrders: number;
  completedOrders: number;
  inProgressOrders: number;
  cancelledOrders: number;
  totalProduced: number;
  period: string;
}

interface TopProducedItem {
  id: string;
  name: string;
  producedQuantity: number;
}

// --- Dummy Data ---
// TODO: Replace with backend API calls to /api/reports/production
const dummyProductionSummary: ProductionSummary = {
  totalOrders: 40,
  completedOrders: 28,
  inProgressOrders: 8,
  cancelledOrders: 4,
  totalProduced: 12000,
  period: 'Jan 2024',
};

const dummyTopProducedItems: TopProducedItem[] = [
  { id: '1', name: 'Cotton Shirt', producedQuantity: 3200 },
  { id: '2', name: 'Denim Jeans', producedQuantity: 2100 },
  { id: '3', name: 'Silk Scarf', producedQuantity: 1500 },
];

export const ProductionReportPage: React.FC = () => {
  // TODO: Fetch production report data from backend here
  // useEffect(() => { fetch('/api/reports/production') ... }, []);
  const productionSummary = dummyProductionSummary;
  const topProducedItems = dummyTopProducedItems;

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Production Report</h1>
        <span className="text-sm text-gray-500">Period: {productionSummary.period}</span>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card title="Production Summary">
          <div className="flex flex-col gap-2">
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Orders</span>
              <span className="font-bold text-blue-700">{productionSummary.totalOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Completed Orders</span>
              <span className="font-bold text-emerald-700">{productionSummary.completedOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">In Progress Orders</span>
              <span className="font-bold text-orange-600">{productionSummary.inProgressOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Cancelled Orders</span>
              <span className="font-bold text-red-600">{productionSummary.cancelledOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Produced</span>
              <span className="font-bold text-purple-700">{productionSummary.totalProduced}</span>
            </div>
          </div>
        </Card>
        <Card title="Top Produced Items">
          <table className="min-w-full bg-white rounded-lg">
            <thead className="bg-purple-100">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-purple-700">Name</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-purple-700">Produced Quantity</th>
              </tr>
            </thead>
            <tbody>
              {topProducedItems.map(item => (
                <tr key={item.id} className="border-b hover:bg-purple-50 transition-colors">
                  <td className="px-4 py-2 text-gray-900">{item.name}</td>
                  <td className="px-4 py-2 text-gray-700">{item.producedQuantity}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      </div>
    </div>
  );
}; 