import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/purchases or /api/reports/purchases might return
const dummyPurchases = [
  { id: 'PR-2024-001', supplier: 'Textile Suppliers Ltd.', date: '2024-01-15', status: 'RECEIVED', total: 12000 },
  { id: 'PR-2024-002', supplier: 'Global Fabrics Co.', date: '2024-01-14', status: 'PENDING', total: 9500 },
  { id: 'PR-2024-003', supplier: 'Cotton World', date: '2024-01-13', status: 'RECEIVED', total: 7800 },
];

export const RecentPurchases: React.FC = () => {
  // TODO: Fetch from backend: /api/purchases or /api/reports/purchases

  return (
    <Card title="Recent Purchases">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Purchase ID</th>
              <th className="px-4 py-2 text-left">Supplier</th>
              <th className="px-4 py-2 text-left">Date</th>
              <th className="px-4 py-2 text-left">Status</th>
              <th className="px-4 py-2 text-right">Total</th>
            </tr>
          </thead>
          <tbody>
            {dummyPurchases.map((purchase, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{purchase.id}</td>
                <td className="px-4 py-2">{purchase.supplier}</td>
                <td className="px-4 py-2">{purchase.date}</td>
                <td className={`px-4 py-2 ${purchase.status === 'PENDING' ? 'text-orange-600' : 'text-emerald-700'}`}>{purchase.status}</td>
                <td className="px-4 py-2 text-right">${purchase.total.toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 