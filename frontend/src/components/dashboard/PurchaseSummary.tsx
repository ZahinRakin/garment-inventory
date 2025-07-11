import React from 'react';
import { Card } from '../common/Card';

// Dummy data structure matching what the backend will send
// (see SupplierReportRequestDTO, TopItemsDTO, and purchase endpoints)
const dummyPurchaseSummary = [
  {
    supplierName: 'Textile Suppliers Ltd.',
    totalPurchases: 120000,
    outstandingOrders: 3,
  },
  {
    supplierName: 'Global Fabrics Co.',
    totalPurchases: 95000,
    outstandingOrders: 1,
  },
  {
    supplierName: 'Cotton World',
    totalPurchases: 78000,
    outstandingOrders: 0,
  },
];

export const PurchaseSummary: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/purchases/supplier or similar endpoint
  // Example: fetch(`/api/reports/purchases/supplier?...`)

  return (
    <Card title="Purchase Summary by Supplier">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Supplier</th>
              <th className="px-4 py-2 text-right">Total Purchases</th>
              <th className="px-4 py-2 text-right">Outstanding Orders</th>
            </tr>
          </thead>
          <tbody>
            {dummyPurchaseSummary.map((row, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{row.supplierName}</td>
                <td className="px-4 py-2 text-right text-blue-700 font-semibold">${row.totalPurchases.toLocaleString()}</td>
                <td className="px-4 py-2 text-right">{row.outstandingOrders}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 