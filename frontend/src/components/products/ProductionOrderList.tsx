import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: string;
  startDate: string; // ISO string
  endDate: string | null; // ISO string or null
  createdAt: string; // ISO string
}

export const ProductionOrderList: React.FC = () => {
  const [productionOrders, setProductionOrders] = useState<ProductionOrder[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProductionOrders = async () => {
      try {
        setLoading(true);
        const response = await api.get("/api/production-orders");
        
        const orders: ProductionOrder[] = response.data;
        setProductionOrders(orders);
      } catch (error) {
        console.error('Error fetching production orders:', error);
        setError('Failed to load production orders');
      } finally {
        setLoading(false);
      }
    };

    fetchProductionOrders();
  }, []);

  const formatDate = (dateString: string | null) => {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString();
  };

  const formatDateTime = (dateTimeString: string) => {
    return new Date(dateTimeString).toLocaleString();
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'CREATED':
        return 'text-blue-600 bg-blue-100';
      case 'IN_PROGRESS':
        return 'text-orange-600 bg-orange-100';
      case 'COMPLETED':
        return 'text-green-600 bg-green-100';
      default:
        return 'text-gray-600 bg-gray-100';
    }
  };

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
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 py-1 text-xs font-medium rounded-full ${getStatusColor(order.status)}`}>
                        {order.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{formatDate(order.startDate)}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{formatDate(order.endDate)}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{formatDateTime(order.createdAt)}</td>
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