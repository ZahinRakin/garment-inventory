import React from 'react';
import { Card } from '../common/Card';

const ActiveProductionJobs = () => (
  <Card title="Active Production Jobs">
    <div className="text-gray-600">[Active production jobs widget here]</div>
  </Card>
);
const RawMaterialsConsumption = () => (
  <Card title="Raw Materials Consumption Summary">
    <div className="text-gray-600">[Raw materials consumption widget here]</div>
  </Card>
);
const FinishedGoods = () => (
  <Card title="Finished Goods Produced">
    <div className="text-gray-600">[Finished goods widget here]</div>
  </Card>
);
const MissingMaterialsAlerts = () => (
  <Card title="Alerts for Missing Materials">
    <div className="text-red-600">[Missing materials alerts widget here]</div>
  </Card>
);

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