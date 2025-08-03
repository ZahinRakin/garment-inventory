//-----------------------------------------------------------------------------------------//
// api endpoint /api/sales/orders----------------------------------------------------------//
// method: get ----------------------------------------------------------------------------//
// expected orders at response.data--------------------------------------------------------//
// send accessToken at bearer token--------------------------------------------------------//
// ----------------------------------------------------------------------------------------//

import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

export const CustomerOrders: React.FC = () => {
  const [orders, setOrders] = useState<any[]>([
    {
      id: '00000000-0000-0000-0000-000000000001',
      customerName: 'Alice Smith',
      status: 'PENDING',
      orderDate: '2024-01-15',
      totalAmount: 1200.00,
    },
    {
      id: '00000000-0000-0000-0000-000000000002',
      customerName: 'Bob Lee',
      status: 'DELIVERED',
      orderDate: '2024-01-14',
      totalAmount: 950.00,
    },
    {
      id: '00000000-0000-0000-0000-000000000003',
      customerName: 'Carol Kim',
      status: 'DELIVERED',
      orderDate: '2024-01-13',
      totalAmount: 780.00,
    }
  ]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await api.get("/api/sales/orders");
        console.log(response.data); //debugging log
        setOrders(response.data);
      } catch (error) {
        console.error(error); //debugging log
      }
    };
    fetchOrders();
  }, []);

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
            {orders.map((order, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{order.id}</td>
                <td className="px-4 py-2">{order.customerName}</td>
                <td className="px-4 py-2">{order.orderDate}</td>
                <td className={`px-4 py-2 ${order.status === 'PENDING' ? 'text-orange-600' : 'text-emerald-700'}`}>
                  {order.status}
                </td>
                <td className="px-4 py-2 text-right">${Number(order.totalAmount).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Card>
  );
};
