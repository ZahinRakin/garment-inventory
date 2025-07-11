import React from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';

// Dummy data matching LowStockItemDTO
const dummyLowStock = [
  { type: 'RAW_MATERIAL', id: 'RM-001', name: 'Cotton Fabric', current: 8, threshold: 15 },
  { type: 'VARIANT', id: 'V-002', name: 'Denim Jeans - Blue, M', current: 3, threshold: 10 },
];

export const LowStockAlerts: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/low-stock or /api/stock/alerts

  return (
    <Card title="Low Stock Alerts">
      <div className="space-y-2">
        {dummyLowStock.length === 0 ? (
          <div className="text-xs text-gray-500">No low stock items.</div>
        ) : (
          <ul className="text-xs text-gray-800 space-y-1">
            {dummyLowStock.map((item, idx) => (
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