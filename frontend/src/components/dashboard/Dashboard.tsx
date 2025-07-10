import React from 'react';
import { DashboardStats } from './DashboardStats';
import { RecentActivity } from './RecentActivity';
import { Card } from '../common/Card';

export const Dashboard: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      
      <DashboardStats />
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <RecentActivity />
        
        <Card title="Quick Actions">
          <div className="space-y-3">
            <button className="w-full text-left p-3 bg-emerald-50 hover:bg-emerald-100 rounded-lg transition-colors">
              <div className="font-medium text-emerald-700">Add New Product</div>
              <div className="text-sm text-emerald-600">Create a new product variant</div>
            </button>
            <button className="w-full text-left p-3 bg-blue-50 hover:bg-blue-100 rounded-lg transition-colors">
              <div className="font-medium text-blue-700">New Sales Order</div>
              <div className="text-sm text-blue-600">Process a customer order</div>
            </button>
            <button className="w-full text-left p-3 bg-purple-50 hover:bg-purple-100 rounded-lg transition-colors">
              <div className="font-medium text-purple-700">Stock Adjustment</div>
              <div className="text-sm text-purple-600">Update inventory levels</div>
            </button>
          </div>
        </Card>
      </div>
    </div>
  );
};
