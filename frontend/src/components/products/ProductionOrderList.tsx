import React from 'react';
import { Card } from '../common/Card';

interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: string;
  startDate: string; // ISO string
  endDate: string | null; // ISO string or null
  createdAt: string; // ISO string
}

// TODO: Replace this dummy data with a backend API call to /api/production-orders
const dummyProductionOrders: ProductionOrder[] = [
  {
    id: '1',
    variantId: '101',
    quantity: 500,
    status: 'CREATED',
    startDate: '2024-01-10',
    endDate: null,
    createdAt: '2024-01-09T10:00:00Z',
  },
  {
    id: '2',
    variantId: '102',
    quantity: 300,
    status: 'IN_PROGRESS',
    startDate: '2024-01-08',
    endDate: null,
    createdAt: '2024-01-07T09:00:00Z',
  },
  {
    id: '3',
    variantId: '103',
    quantity: 200,
    status: 'COMPLETED',
    startDate: '2024-01-01',
    endDate: '2024-01-05',
    createdAt: '2023-12-31T08:00:00Z',
  },
];

export const ProductionOrderList: React.FC = () => {
  // TODO: Fetch production orders from backend here
  // useEffect(() => { fetch('/api/production-orders') ... }, []);
  const productionOrders = dummyProductionOrders;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Production Orders</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading production orders...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : productionOrders.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No production orders found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Variant ID</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Quantity</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Status</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Start Date</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">End Date</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Created At</th>
                </tr>
              </thead>
              <tbody>
                {productionOrders.map(order => (
                  <tr key={order.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{order.variantId}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.quantity}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.status}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.startDate}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.endDate ? order.endDate : '-'}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{new Date(order.createdAt).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
}; 