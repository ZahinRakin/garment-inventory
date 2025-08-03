//----------------------------------------------------------------------------------//
// api endpoint   /api/production-orders/status/IN_PROGRESS-------------------------//
// get
// expected in progress jobs at response.data---------------------------------------//
//----------------------------------------------------------------------------------//
import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

export const ActiveProductionJobs: React.FC = () => {
  const [jobs, setJobs] = useState<any[]>([
    {
      id: 'PO-2024-001',
      variantId: 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
      quantity: 100,
      status: 'IN_PROGRESS'
    },
    {
      id: 'PO-2024-002',
      variantId: 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
      quantity: 250,
      status: 'IN_PROGRESS'
    }
  ]);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const response = await api.get("/api/production-orders/status/IN_PROGRESS");
        console.log(response.data); // debugging log
        setJobs(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchJobs();
  }, []);

  return (
    <Card title="Active Production Jobs">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Job ID</th>
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-left">Variant ID</th>
              <th className="px-4 py-2 text-right">Quantity</th>
              <th className="px-4 py-2 text-left">Status</th>
            </tr>
          </thead>
          <tbody>
            {jobs.map((job, idx) => (
              <tr key={idx} className="border-b last:border-0">
                <td className="px-4 py-2 font-medium text-gray-900">{job.id}</td>
                <td className="px-4 py-2">Unknown Product</td>
                <td className="px-4 py-2">{job.variantId?.slice(0, 8)}...</td>
                <td className="px-4 py-2 text-right">{job.quantity}</td>
                <td className="px-4 py-2">{job.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">(Showing live backend data)</div>
    </Card>
  );
};
