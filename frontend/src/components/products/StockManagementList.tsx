import React from 'react';
import { Card } from '../common/Card';

// --- Types based on backend DTOs ---
interface StockAdjustment {
  id: string;
  variantId: string;
  locationId: string;
  adjustmentType: 'INCREASE' | 'DECREASE';
  quantity: number;
  reason: string;
  adjustedBy: string;
  adjustedAt: string; // ISO string
}

interface StockAlert {
  id: string;
  variantId: string;
  message: string;
  alertType: 'LOW_STOCK' | 'OUT_OF_STOCK';
  createdAt: string; // ISO string
}

// --- Dummy Data ---
// TODO: Replace with backend API calls to /api/stock-adjustments and /api/stock/alerts
const dummyStockAdjustments: StockAdjustment[] = [
  {
    id: '1',
    variantId: '101',
    locationId: 'W1',
    adjustmentType: 'INCREASE',
    quantity: 100,
    reason: 'Restock',
    adjustedBy: 'Admin',
    adjustedAt: '2024-01-15T10:00:00Z',
  },
  {
    id: '2',
    variantId: '102',
    locationId: 'S1',
    adjustmentType: 'DECREASE',
    quantity: 50,
    reason: 'Damaged',
    adjustedBy: 'Store Manager',
    adjustedAt: '2024-01-12T09:00:00Z',
  },
  {
    id: '3',
    variantId: '103',
    locationId: 'W2',
    adjustmentType: 'INCREASE',
    quantity: 200,
    reason: 'Transfer',
    adjustedBy: 'Production Officer',
    adjustedAt: '2024-01-10T08:00:00Z',
  },
];

const dummyStockAlerts: StockAlert[] = [
  {
    id: 'a1',
    variantId: '101',
    message: 'Low stock for Cotton Shirt (variant 101)',
    alertType: 'LOW_STOCK',
    createdAt: '2024-01-15T11:00:00Z',
  },
  {
    id: 'a2',
    variantId: '102',
    message: 'Out of stock for Denim Jeans (variant 102)',
    alertType: 'OUT_OF_STOCK',
    createdAt: '2024-01-14T15:30:00Z',
  },
];

export const StockManagementList: React.FC = () => {
  // TODO: Fetch stock adjustments and alerts from backend here
  // useEffect(() => { fetch('/api/stock-adjustments') ... }, []);
  const stockAdjustments = dummyStockAdjustments;
  const stockAlerts = dummyStockAlerts;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Stock Management</h1>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card title="Stock Adjustments">
          {loading ? (
            <div className="text-center text-emerald-600 py-8">Loading stock adjustments...</div>
          ) : error ? (
            <div className="text-center text-red-500 py-8">{error}</div>
          ) : stockAdjustments.length === 0 ? (
            <div className="text-center text-gray-500 py-8">No stock adjustments found.</div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full bg-white rounded-lg">
                <thead className="bg-slate-800 text-white">
                  <tr>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Variant ID</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Location ID</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Type</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Quantity</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Reason</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Adjusted By</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Adjusted At</th>
                  </tr>
                </thead>
                <tbody>
                  {stockAdjustments.map(adj => (
                    <tr key={adj.id} className={
                      `border-b transition-colors ${
                        adj.adjustmentType === 'INCREASE' ? 'hover:bg-emerald-50' : 'hover:bg-red-50'
                      }`
                    }>
                      <td className="px-4 py-2 text-gray-900">{adj.variantId}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.locationId}</td>
                      <td className={
                        `px-4 py-2 font-bold ${adj.adjustmentType === 'INCREASE' ? 'text-emerald-700' : 'text-red-600'}`
                      }>{adj.adjustmentType}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.quantity}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.reason}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.adjustedBy}</td>
                      <td className="px-4 py-2 text-gray-500">{new Date(adj.adjustedAt).toLocaleString()}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </Card>
        <Card title="Stock Alerts">
          {stockAlerts.length === 0 ? (
            <div className="text-center text-gray-500 py-8">No stock alerts.</div>
          ) : (
            <ul className="space-y-3">
              {stockAlerts.map(alert => (
                <li key={alert.id} className={`p-4 rounded-lg flex items-center gap-3 shadow-sm ${
                  alert.alertType === 'LOW_STOCK' ? 'bg-orange-50 border-l-4 border-orange-400' : 'bg-red-50 border-l-4 border-red-500'
                }`}>
                  <span className={
                    `font-bold ${alert.alertType === 'LOW_STOCK' ? 'text-orange-700' : 'text-red-700'}`
                  }>
                    {alert.alertType === 'LOW_STOCK' ? 'Low Stock' : 'Out of Stock'}
                  </span>
                  <span className="text-gray-800">{alert.message}</span>
                  <span className="ml-auto text-xs text-gray-500">{new Date(alert.createdAt).toLocaleString()}</span>
                </li>
              ))}
            </ul>
          )}
        </Card>
      </div>
    </div>
  );
}; 