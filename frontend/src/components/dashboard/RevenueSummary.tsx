import React from 'react';
import { Card } from '../common/Card';
import { formatCurrency } from '../../utils/format';

// Dummy data matching what /api/reports/sales/revenue might return
const dummyRevenue = {
  total: 250000,
  trend: '+8%',
};

export const RevenueSummary: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/sales/revenue

  return (
    <Card title="Revenue Summary">
      <div className="flex flex-col items-center justify-center py-4">
        <span className="text-2xl font-bold text-emerald-700">{formatCurrency(dummyRevenue.total)}</span>
        <span className="text-sm text-gray-500 mt-1">This month</span>
        <span className="text-xs mt-2 font-medium text-emerald-600">{dummyRevenue.trend} from last month</span>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 