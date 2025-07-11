import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/finished-goods-stock or /api/products might return
const dummyProducts = [
  { name: 'Denim Jeans - Blue, M', stock: 200, threshold: 30 },
  { name: 'Cotton Shirt - White, L', stock: 120, threshold: 20 },
  { name: 'Jacket - Black, XL', stock: 60, threshold: 10 },
];

export const ProductStock: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/finished-goods-stock or /api/products

  return (
    <Card title="Product Stock Levels">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-right">Current Stock</th>
              <th className="px-4 py-2 text-right">Threshold</th>
            </tr>
          </thead>
          <tbody>
            {dummyProducts.map((row, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{row.name}</td>
                <td className="px-4 py-2 text-right">{row.stock}</td>
                <td className="px-4 py-2 text-right">{row.threshold}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 