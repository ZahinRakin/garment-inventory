import React from 'react';
import { Card } from '../common/Card';

const PendingDeliveredOrders = () => (
  <Card title="Pending vs Delivered Orders">
    <div className="text-gray-600">[Pending vs delivered orders widget here]</div>
  </Card>
);
const TopSellingProducts = () => (
  <Card title="Top-Selling Products">
    <div className="text-gray-600">[Top-selling products widget here]</div>
  </Card>
);
const RevenueSummary = () => (
  <Card title="Revenue Summary">
    <div className="text-gray-600">[Revenue summary widget here]</div>
  </Card>
);
const CustomerOrders = () => (
  <Card title="Customer Orders">
    <div className="text-gray-600">[Customer orders widget here]</div>
  </Card>
);

export const DashboardSalesOfficer: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Sales Officer Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <PendingDeliveredOrders />
        <TopSellingProducts />
        <RevenueSummary />
        <CustomerOrders />
      </div>
    </div>
  );
}; 