import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/raw-material-stock or /api/raw-materials might return
const dummyRawMaterials = [
  { name: 'Cotton Fabric', stock: 800, threshold: 15 },
  { name: 'Denim', stock: 400, threshold: 20 },
  { name: 'Buttons', stock: 200, threshold: 50 },
];

export const RawMaterialsStock: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/raw-material-stock or /api/raw-materials

  return (
    <Card title="Raw Materials Stock Levels">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Material</th>
              <th className="px-4 py-2 text-right">Current Stock</th>
              <th className="px-4 py-2 text-right">Threshold</th>
            </tr>
          </thead>
          <tbody>
            {dummyRawMaterials.map((row, idx) => (
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