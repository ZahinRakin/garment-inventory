import React from 'react';
import { Card } from '../common/Card';
import { StatusBadge } from '../common/StatusBadge';
import { formatDateTime } from '../../utils/format';

interface Activity {
  id: string;
  type: 'sale' | 'production' | 'purchase' | 'stock';
  description: string;
  timestamp: string;
  status: string;
}

export const RecentActivity: React.FC = () => {
  const activities: Activity[] = [
    {
      id: '1',
      type: 'sale',
      description: 'New sales order #SO-2024-001 for Cotton Shirt - Blue',
      timestamp: '2024-01-15T10:30:00Z',
      status: 'PENDING'
    },
    {
      id: '2',
      type: 'production',
      description: 'Production order #PO-2024-015 completed for Denim Jeans',
      timestamp: '2024-01-15T09:15:00Z',
      status: 'COMPLETED'
    },
    {
      id: '3',
      type: 'purchase',
      description: 'Purchase order #PR-2024-008 received from Textile Suppliers Ltd.',
      timestamp: '2024-01-15T08:45:00Z',
      status: 'DELIVERED'
    },
    {
      id: '4',
      type: 'stock',
      description: 'Stock adjustment for Silk Fabric - Red (Warehouse A)',
      timestamp: '2024-01-15T07:30:00Z',
      status: 'COMPLETED'
    }
  ];

  return (
    <Card title="Recent Activity" className="lg:col-span-2">
      <div className="space-y-4">
        {activities.map((activity) => (
          <div key={activity.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
            <div className="flex-1">
              <p className="text-sm font-medium text-gray-900">{activity.description}</p>
              <p className="text-xs text-gray-500 mt-1">{formatDateTime(activity.timestamp)}</p>
            </div>
            <StatusBadge status={activity.status} />
          </div>
        ))}
      </div>
    </Card>
  );
};
