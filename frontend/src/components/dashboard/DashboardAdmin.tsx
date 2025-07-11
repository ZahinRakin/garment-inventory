import React from 'react';
import { Card } from '../common/Card';
import { DashboardStats } from './DashboardStats';
import { RecentActivity } from './RecentActivity';

// Placeholder widgets for missing KPIs
const PurchaseSummary = () => (
  <Card title="Purchase Summary by Supplier">
    <div className="text-gray-600">[Purchase summary widget here]</div>
  </Card>
);
const Alerts = () => (
  <Card title="Alerts">
    <div className="text-red-600">[Low stock, overdue POs, etc.]</div>
  </Card>
);

export const DashboardAdmin: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      {/* KPIs */}
      <DashboardStats />
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <RecentActivity />
        <div className="space-y-6">
          <PurchaseSummary />
          <Alerts />
        </div>
      </div>
    </div>
  );
}; 