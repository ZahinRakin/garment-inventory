import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/finished-goods-stock might return
const dummyFinishedGoods = [
  { name: 'Denim Jeans - Blue, M', produced: 500, stock: 200 },
  { name: 'Cotton Shirt - White, L', produced: 300, stock: 120 },
];

export const FinishedGoods: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/finished-goods-stock

  return (
    <Card title="Finished Goods Produced">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-right">Produced (this month)</th>
              <th className="px-4 py-2 text-right">Current Stock</th>
            </tr>
          </thead>
          <tbody>
            {dummyFinishedGoods.map((row, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{row.name}</td>
                <td className="px-4 py-2 text-right">{row.produced}</td>
                <td className="px-4 py-2 text-right">{row.stock}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 