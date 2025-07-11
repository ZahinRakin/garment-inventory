import React from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';

// Dummy data matching LowStockItemDTO and possible alert strings
const dummyLowStock = [
  { type: 'RAW_MATERIAL', id: 'RM-001', name: 'Cotton Fabric', current: 8, threshold: 15 },
  { type: 'VARIANT', id: 'V-002', name: 'Denim Jeans - Blue, M', current: 3, threshold: 10 },
];
const dummyOverduePOs = [
  { id: 'PO-2024-003', supplier: 'Textile Suppliers Ltd.', dueDate: '2024-01-10' },
];

export const Alerts: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/low-stock, /api/reports/alerts, or similar endpoints
  // Example: fetch('/api/reports/low-stock')

  return (
    <Card title="Alerts">
      <div className="space-y-4">
        <div>
          <div className="flex items-center mb-2 text-orange-600 font-semibold">
            <AlertTriangle className="w-5 h-5 mr-2" /> Low Stock Items
          </div>
          {dummyLowStock.length === 0 ? (
            <div className="text-xs text-gray-500">No low stock items.</div>
          ) : (
            <ul className="text-xs text-gray-800 space-y-1">
              {dummyLowStock.map((item, idx) => (
                <li key={idx} className="flex justify-between">
                  <span>{item.name} ({item.type})</span>
                  <span className="text-red-600 font-bold">{item.current} / {item.threshold}</span>
                </li>
              ))}
            </ul>
          )}
        </div>
        <div>
          <div className="flex items-center mb-2 text-red-700 font-semibold">
            <AlertTriangle className="w-5 h-5 mr-2" /> Overdue Purchase Orders
          </div>
          {dummyOverduePOs.length === 0 ? (
            <div className="text-xs text-gray-500">No overdue purchase orders.</div>
          ) : (
            <ul className="text-xs text-gray-800 space-y-1">
              {dummyOverduePOs.map((po, idx) => (
                <li key={idx} className="flex justify-between">
                  <span>Order {po.id} ({po.supplier})</span>
                  <span className="text-red-600 font-bold">Due: {po.dueDate}</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 