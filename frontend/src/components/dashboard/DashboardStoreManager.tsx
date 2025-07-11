import React from 'react';
import { RawMaterialsStock } from './RawMaterialsStock';
import { ProductStock } from './ProductStock';
import { LowStockAlerts } from './LowStockAlerts';
import { RecentPurchases } from './RecentPurchases';

export const DashboardStoreManager: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Store Manager Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <RawMaterialsStock />
        <ProductStock />
        <LowStockAlerts />
        <RecentPurchases />
      </div>
    </div>
  );
}; 