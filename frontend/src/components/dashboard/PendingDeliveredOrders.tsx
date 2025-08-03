import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

interface SalesOrder {
  id: string;
  customerName: string;
  status: string;
  orderDate: string;
  totalAmount: number;
  createdAt: string;
  items?: any[];
}

interface OrderCounts {
  pending: number;
  delivered: number;
}

export const PendingDeliveredOrders: React.FC = () => {
  const [orderCounts, setOrderCounts] = useState<OrderCounts>({
    pending: 0,
    delivered: 0
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        setLoading(true);
        const response = await api.get("/api/sales/orders");
        
        const orders: SalesOrder[] = response.data;
        
        // Count orders by status
        const pending = orders.filter(order => order.status === 'PENDING').length;
        const delivered = orders.filter(order => order.status === 'DELIVERED').length;
        
        setOrderCounts({ pending, delivered });
      } catch (error) {
        setError('Failed to load order data');
      } finally {
        setLoading(false);
      }
    };
    fetchOrders();
  }, []);

  const orders = [
    { status: 'PENDING', count: orderCounts.pending },
    { status: 'DELIVERED', count: orderCounts.delivered },
  ];

  return (
    <Card title="Pending vs Delivered Orders">
      {loading ? (
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      ) : error ? (
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      ) : (
        <div className="flex space-x-6 justify-center">
          {orders.map((order, idx) => (
            <div key={idx} className="flex flex-col items-center">
              <span className={`text-lg font-bold ${order.status === 'PENDING' ? 'text-orange-600' : 'text-emerald-700'}`}>
                {order.count}
              </span>
              <span className="text-xs text-gray-600">{order.status}</span>
            </div>
          ))}
        </div>
      )}
      {!loading && !error && (
        <div className="mt-2 text-xs text-gray-400 text-center">
          Total orders: {orderCounts.pending + orderCounts.delivered}
        </div>
      )}
    </Card>
  );
}; 