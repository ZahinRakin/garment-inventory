import React, { useState, useEffect } from 'react';
import { Card } from '../common/Card';
import { StatusBadge } from '../common/StatusBadge';

// --- Types based on backend DTOs ---
interface StockAdjustment {
  id: string;
  itemId: string;
  itemType: 'VARIANT' | 'RAW_MATERIAL';
  quantityBefore: number;
  quantityAfter: number;
  adjustmentAmount: number;
  reason: string;
  adjustedBy: string;
  adjustmentDate: string; // ISO string
}

interface StockAlert {
  message: string;
  itemType: 'RAW_MATERIAL' | 'VARIANT';
  itemIdentifier?: string;
  currentStock?: number;
  threshold?: number;
}

export const StockManagementList: React.FC = () => {
  const [stockAdjustments, setStockAdjustments] = useState<StockAdjustment[]>([]);
  const [stockAlerts, setStockAlerts] = useState<StockAlert[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch stock adjustments
        const adjustmentsResponse = await fetch('/api/stock/adjustments', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
          },
        });

        if (!adjustmentsResponse.ok) {
          throw new Error('Failed to fetch stock adjustments');
        }

        const adjustmentsData = await adjustmentsResponse.json();

        // Fetch stock alerts
        const alertsResponse = await fetch('/api/stock/alerts', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
          },
        });

        if (!alertsResponse.ok) {
          throw new Error('Failed to fetch stock alerts');
        }

        const alertsData = await alertsResponse.json();

        setStockAdjustments(adjustmentsData);
        setStockAlerts(alertsData);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const getAdjustmentType = (adjustment: StockAdjustment) => {
    return adjustment.adjustmentAmount > 0 ? 'INCREASE' : 'DECREASE';
  };

  const getAdjustmentTypeColor = (adjustment: StockAdjustment) => {
    return adjustment.adjustmentAmount > 0 ? 'success' : 'danger';
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString();
  };

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
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Item ID</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Type</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Adjustment</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Before</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">After</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Reason</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Adjusted By</th>
                    <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider">Date</th>
                  </tr>
                </thead>
                <tbody>
                  {stockAdjustments.map(adj => (
                    <tr key={adj.id} className={
                      `border-b transition-colors ${
                        adj.adjustmentAmount > 0 ? 'hover:bg-emerald-50' : 'hover:bg-red-50'
                      }`
                    }>
                      <td className="px-4 py-2 text-gray-900">{adj.itemId}</td>
                      <td className="px-4 py-2 text-gray-700">
                        <StatusBadge 
                          status={adj.itemType} 
                        />
                      </td>
                      <td className="px-4 py-2">
                        <StatusBadge 
                          status={getAdjustmentType(adj)} 
                        />
                      </td>
                      <td className="px-4 py-2 text-gray-700">{adj.quantityBefore}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.quantityAfter}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.reason}</td>
                      <td className="px-4 py-2 text-gray-700">{adj.adjustedBy}</td>
                      <td className="px-4 py-2 text-gray-500">{formatDate(adj.adjustmentDate)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </Card>
        <Card title="Stock Alerts">
          {loading ? (
            <div className="text-center text-emerald-600 py-8">Loading stock alerts...</div>
          ) : error ? (
            <div className="text-center text-red-500 py-8">{error}</div>
          ) : stockAlerts.length === 0 ? (
            <div className="text-center text-gray-500 py-8">No stock alerts.</div>
          ) : (
            <ul className="space-y-3">
              {stockAlerts.map((alert, index) => (
                <li key={index} className={`p-4 rounded-lg flex items-center gap-3 shadow-sm ${
                  alert.itemType === 'RAW_MATERIAL' ? 'bg-orange-50 border-l-4 border-orange-400' : 'bg-red-50 border-l-4 border-red-500'
                }`}>
                  <span className={
                    `font-bold ${alert.itemType === 'RAW_MATERIAL' ? 'text-orange-700' : 'text-red-700'}`
                  }>
                    {alert.itemType === 'RAW_MATERIAL' ? 'Raw Material' : 'Variant'}
                  </span>
                  <span className="text-gray-800">{alert.message}</span>
                  {alert.currentStock !== undefined && (
                    <span className="text-sm text-gray-600">
                      (Current: {alert.currentStock}
                      {alert.threshold !== undefined && `, Threshold: ${alert.threshold}`})
                    </span>
                  )}
                </li>
              ))}
            </ul>
          )}
        </Card>
      </div>
    </div>
  );
}; 