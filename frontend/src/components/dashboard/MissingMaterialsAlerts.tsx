import React from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';

// Dummy data matching LowStockItemDTO
const dummyMissingMaterials = [
  { type: 'RAW_MATERIAL', id: 'RM-002', name: 'Polyester Thread', current: 0, threshold: 10 },
  { type: 'RAW_MATERIAL', id: 'RM-003', name: 'Zipper', current: 2, threshold: 15 },
];

export const MissingMaterialsAlerts: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/low-stock or /api/stock/alerts

  return (
    <Card title="Alerts for Missing Materials">
      <div className="space-y-2">
        {dummyMissingMaterials.length === 0 ? (
          <div className="text-xs text-gray-500">No missing or low stock materials.</div>
        ) : (
          <ul className="text-xs text-gray-800 space-y-1">
            {dummyMissingMaterials.map((item, idx) => (
              <li key={idx} className="flex justify-between items-center">
                <span className="flex items-center">
                  <AlertTriangle className="w-4 h-4 text-red-600 mr-1" />
                  {item.name} ({item.type})
                </span>
                <span className="text-red-600 font-bold">{item.current} / {item.threshold}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 