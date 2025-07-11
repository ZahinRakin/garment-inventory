import React from 'react';
import { Card } from '../common/Card';

// Dummy data matching what /api/reports/production/status might return
const dummyJobs = [
  { id: 'PO-2024-015', product: 'Denim Jeans', variant: 'Blue, M', quantity: 500, status: 'IN_PROGRESS' },
  { id: 'PO-2024-016', product: 'Cotton Shirt', variant: 'White, L', quantity: 300, status: 'PENDING' },
];

export const ActiveProductionJobs: React.FC = () => {
  // TODO: Fetch from backend: /api/reports/production/status

  return (
    <Card title="Active Production Jobs">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Job ID</th>
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-left">Variant</th>
              <th className="px-4 py-2 text-right">Quantity</th>
              <th className="px-4 py-2 text-left">Status</th>
            </tr>
          </thead>
          <tbody>
            {dummyJobs.map((job, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{job.id}</td>
                <td className="px-4 py-2">{job.product}</td>
                <td className="px-4 py-2">{job.variant}</td>
                <td className="px-4 py-2 text-right">{job.quantity}</td>
                <td className="px-4 py-2">{job.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Replace with backend data)</div>
    </Card>
  );
}; 