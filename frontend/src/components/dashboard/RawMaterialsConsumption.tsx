import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/raw-material-stock might return
const dummyConsumption = [
  { name: 'Cotton Fabric', consumed: 1200, remaining: 800 },
  { name: 'Denim', consumed: 900, remaining: 400 },
  { name: 'Buttons', consumed: 500, remaining: 200 },
];

export const RawMaterialsConsumption: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/raw-material-stock

  return (
    <Card title="Raw Materials Consumption Summary">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Material</th>
              <th className="px-4 py-2 text-right">Consumed (this month)</th>
              <th className="px-4 py-2 text-right">Remaining</th>
            </tr>
          </thead>
          <tbody>
            {dummyConsumption.map((row, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{row.name}</td>
                <td className="px-4 py-2 text-right">{row.consumed}</td>
                <td className="px-4 py-2 text-right">{row.remaining}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 