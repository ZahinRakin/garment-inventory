import React from 'react';
import { Card } from '../common/Card';

interface SalesOrder {
  id: string;
  customerName: string;
  status: string;
  orderDate: string; // ISO string
  totalAmount: string;
  createdAt: string; // ISO string
}

// TODO: Replace this dummy data with a backend API call to /api/sales-orders
const dummySalesOrders: SalesOrder[] = [
  {
    id: '1',
    customerName: 'John Doe',
    status: 'PENDING',
    orderDate: '2024-01-15',
    totalAmount: '12000.00',
    createdAt: '2024-01-15T10:00:00Z',
  },
  {
    id: '2',
    customerName: 'Jane Smith',
    status: 'DELIVERED',
    orderDate: '2024-01-10',
    totalAmount: '8000.00',
    createdAt: '2024-01-10T09:00:00Z',
  },
  {
    id: '3',
    customerName: 'Acme Corp.',
    status: 'CANCELLED',
    orderDate: '2024-01-05',
    totalAmount: '0.00',
    createdAt: '2024-01-05T08:00:00Z',
  },
];

export const SalesOrderList: React.FC = () => {
  // TODO: Fetch sales orders from backend here
  // useEffect(() => { fetch('/api/sales-orders') ... }, []);
  const salesOrders = dummySalesOrders;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Sales Orders</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading sales orders...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : salesOrders.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No sales orders found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Customer Name</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Status</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Order Date</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Total Amount</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Created At</th>
                </tr>
              </thead>
              <tbody>
                {salesOrders.map(order => (
                  <tr key={order.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{order.customerName}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.status}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.orderDate}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{order.totalAmount}</td>
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