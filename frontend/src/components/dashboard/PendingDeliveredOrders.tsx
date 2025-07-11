import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/sales/orders/status or /api/reports/sales might return
const dummyOrders = [
  { status: 'PENDING', count: 8 },
  { status: 'DELIVERED', count: 22 },
];

export const PendingDeliveredOrders: React.FC = () => {
  // TODO: Fetch from backend: /api/sales/orders/status or /api/reports/sales

  return (
    <Card title="Pending vs Delivered Orders">
      <div className="flex space-x-6 justify-center">
        {dummyOrders.map((order, idx) => (
          <div key={idx} className="flex flex-col items-center">
            <span className={`text-lg font-bold ${order.status === 'PENDING' ? 'text-orange-600' : 'text-emerald-700'}`}>{order.count}</span>
            <span className="text-xs text-gray-600">{order.status}</span>
          </div>
        ))}
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 