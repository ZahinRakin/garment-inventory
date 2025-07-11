import React from 'react';
import { Card } from '../common/Card';

interface Purchase {
  id: string;
  supplierId: string;
  orderDate: string;
  status: string;
  totalAmount: string;
  createdAt: string;
}

// TODO: Replace this dummy data with a backend API call to /api/purchases
const dummyPurchases: Purchase[] = [
  {
    id: '1',
    supplierId: '1',
    orderDate: '2024-01-15',
    status: 'DELIVERED',
    totalAmount: '15000.00',
    createdAt: '2024-01-15T10:00:00Z',
  },
  {
    id: '2',
    supplierId: '2',
    orderDate: '2024-01-10',
    status: 'PENDING',
    totalAmount: '5000.00',
    createdAt: '2024-01-10T09:00:00Z',
  },
  {
    id: '3',
    supplierId: '3',
    orderDate: '2024-01-05',
    status: 'CANCELLED',
    totalAmount: '0.00',
    createdAt: '2024-01-05T08:00:00Z',
  },
];

export const PurchaseList: React.FC = () => {
  // TODO: Fetch purchases from backend here
  // useEffect(() => { fetch('/api/purchases') ... }, []);
  const purchases = dummyPurchases;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Purchases</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading purchases...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : purchases.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No purchases found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Order Date</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Status</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Total Amount</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Created At</th>
                </tr>
              </thead>
              <tbody>
                {purchases.map(purchase => (
                  <tr key={purchase.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{purchase.orderDate}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{purchase.status}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{purchase.totalAmount}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{new Date(purchase.createdAt).toLocaleString()}</td>
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