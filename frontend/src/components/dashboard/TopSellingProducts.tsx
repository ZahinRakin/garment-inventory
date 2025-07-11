import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/sales/top-products might return
const dummyTopProducts = [
  { name: 'Denim Jeans - Blue, M', sold: 120 },
  { name: 'Cotton Shirt - White, L', sold: 95 },
  { name: 'Jacket - Black, XL', sold: 60 },
];

export const TopSellingProducts: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/sales/top-products

  return (
    <Card title="Top-Selling Products">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-right">Quantity Sold</th>
            </tr>
          </thead>
          <tbody>
            {dummyTopProducts.map((row, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{row.name}</td>
                <td className="px-4 py-2 text-right">{row.sold}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 