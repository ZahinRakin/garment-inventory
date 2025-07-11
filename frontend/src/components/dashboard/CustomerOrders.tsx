import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/sales or /api/sales/orders might return
const dummyOrders = [
  { id: 'SO-2024-001', customer: 'Alice Smith', date: '2024-01-15', status: 'PENDING', total: 1200 },
  { id: 'SO-2024-002', customer: 'Bob Lee', date: '2024-01-14', status: 'DELIVERED', total: 950 },
  { id: 'SO-2024-003', customer: 'Carol Kim', date: '2024-01-13', status: 'DELIVERED', total: 780 },
];

export const CustomerOrders: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/sales or /api/sales/orders

  return (
    <Card title="Customer Orders">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Order ID</th>
              <th className="px-4 py-2 text-left">Customer</th>
              <th className="px-4 py-2 text-left">Date</th>
              <th className="px-4 py-2 text-left">Status</th>
              <th className="px-4 py-2 text-right">Total</th>
            </tr>
          </thead>
          <tbody>
            {dummyOrders.map((order, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{order.id}</td>
                <td className="px-4 py-2">{order.customer}</td>
                <td className="px-4 py-2">{order.date}</td>
                <td className={`px-4 py-2 ${order.status === 'PENDING' ? 'text-orange-600' : 'text-emerald-700'}`}>{order.status}</td>
                <td className="px-4 py-2 text-right">${order.total.toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 