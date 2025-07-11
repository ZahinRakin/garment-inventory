import React from 'react';
import { ActiveProductionJobs } from './ActiveProductionJobs';
import { RawMaterialsConsumption } from './RawMaterialsConsumption';
import { FinishedGoods } from './FinishedGoods';
import { MissingMaterialsAlerts } from './MissingMaterialsAlerts';

export const DashboardProductionOfficer: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Production Officer Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ActiveProductionJobs />
        <RawMaterialsConsumption />
        <FinishedGoods />
        <MissingMaterialsAlerts />
      </div>
    </div>
  );
}; 