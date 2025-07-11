import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface SalesOrder {
  id: string;
  customerName: string;
  status: string;
  orderDate: string; // ISO string
  totalAmount: number;
  createdAt: string; // ISO string
}

export const SalesOrderList: React.FC = () => {
  const [salesOrders, setSalesOrders] = useState<SalesOrder[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSalesOrders = async () => {
      try {
        setLoading(true);
        const response = await axios.get("/api/sales/orders", {
          headers: { Authorization: `Bearer ${getStoredAuth().token}` }
        });
        
        const ordersData: SalesOrder[] = response.data;
        setSalesOrders(ordersData);
      } catch (error) {
        console.error('Error fetching sales orders:', error);
        setError('Failed to load sales orders');
      } finally {
        setLoading(false);
      }
    };

    fetchSalesOrders();
  }, []);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString();
  };

  const formatDateTime = (dateTimeString: string) => {
    return new Date(dateTimeString).toLocaleString();
  };

  const formatCurrency = (amount: number) => {
    return `৳ ${amount.toLocaleString()}`;
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING':
        return 'text-orange-600 bg-orange-100';
      case 'DELIVERED':
        return 'text-green-600 bg-green-100';
      case 'CANCELLED':
        return 'text-red-600 bg-red-100';
      default:
        return 'text-gray-600 bg-gray-100';
    }
  };

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
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 py-1 text-xs font-medium rounded-full ${getStatusColor(order.status)}`}>
                        {order.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{formatDate(order.orderDate)}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{formatCurrency(order.totalAmount)}</td>
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