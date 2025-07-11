import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { StatusBadge } from '../common/StatusBadge';
import { formatDateTime } from '../../utils/format';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface SalesOrder {
  id: string;
  customerName: string;
  status: string;
  orderDate: string;
  totalAmount: number;
  createdAt: string;
}

interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: string;
  startDate: string;
  endDate: string;
  createdAt: string;
}

interface Purchase {
  id: string;
  supplierId: string;
  orderDate: string;
  status: string;
  totalAmount: number;
  createdAt: string;
}

interface Supplier {
  id: string;
  name: string;
  email: string;
  phone: string;
  address: string;
}

interface StockAlert {
  message: string;
  itemType: string;
  itemIdentifier: string;
  currentStock: number;
  threshold: number;
}

interface Activity {
  id: string;
  type: 'sale' | 'production' | 'purchase' | 'stock';
  description: string;
  timestamp: string;
  status: string;
}

export const RecentActivity: React.FC = () => {
  const [activities, setActivities] = useState<Activity[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRecentActivity = async () => {
      try {
        setLoading(true);
        
        // Fetch all data in parallel
        const [salesResponse, productionResponse, purchasesResponse, stockAlertsResponse, suppliersResponse] = await Promise.all([
          axios.get("/api/sales/orders", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/production-orders", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/purchases", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/stock/alerts", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/suppliers", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const salesOrders: SalesOrder[] = salesResponse.data;
        const productionOrders: ProductionOrder[] = productionResponse.data;
        const purchases: Purchase[] = purchasesResponse.data;
        const stockAlerts: StockAlert[] = stockAlertsResponse.data;
        const suppliers: Supplier[] = suppliersResponse.data;

        // Create supplier map for purchase orders
        const supplierMap = new Map<string, string>();
        suppliers.forEach(supplier => {
          supplierMap.set(supplier.id, supplier.name);
        });

        // Transform data into activities
        const allActivities: Activity[] = [];

        // Sales activities
        salesOrders.slice(0, 5).forEach(order => {
          allActivities.push({
            id: `sale-${order.id}`,
            type: 'sale',
            description: `New sales order #${order.id.slice(0, 8)} for ${order.customerName}`,
            timestamp: order.createdAt,
            status: order.status
          });
        });

        // Production activities
        productionOrders.slice(0, 5).forEach(order => {
          const statusText = order.status === 'COMPLETED' ? 'completed' : 
                           order.status === 'IN_PROGRESS' ? 'in progress' : 
                           order.status === 'CREATED' ? 'created' : order.status.toLowerCase();
          allActivities.push({
            id: `production-${order.id}`,
            type: 'production',
            description: `Production order #${order.id.slice(0, 8)} ${statusText} for ${order.quantity} units`,
            timestamp: order.createdAt,
            status: order.status
          });
        });

        // Purchase activities
        purchases.slice(0, 5).forEach(purchase => {
          const supplierName = supplierMap.get(purchase.supplierId) || 'Unknown Supplier';
          const statusText = purchase.status === 'DELIVERED' ? 'received from' : 
                           purchase.status === 'PENDING' ? 'ordered from' : 
                           purchase.status.toLowerCase();
          allActivities.push({
            id: `purchase-${purchase.id}`,
            type: 'purchase',
            description: `Purchase order #${purchase.id.slice(0, 8)} ${statusText} ${supplierName}`,
            timestamp: purchase.createdAt,
            status: purchase.status
          });
        });

        // Stock alert activities
        stockAlerts.slice(0, 3).forEach(alert => {
          allActivities.push({
            id: `stock-${alert.itemIdentifier}`,
            type: 'stock',
            description: alert.message,
            timestamp: new Date().toISOString(), // Stock alerts don't have timestamps, use current time
            status: 'ALERT'
          });
        });

        // Sort by timestamp (most recent first) and take top 10
        const sortedActivities = allActivities
          .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
          .slice(0, 10);

        setActivities(sortedActivities);
      } catch (error) {
        console.error('Error fetching recent activity:', error);
        setError('Failed to load recent activity data');
      } finally {
        setLoading(false);
      }
    };

    fetchRecentActivity();
  }, []);

  if (loading) {
    return (
      <Card title="Recent Activity" className="lg:col-span-2">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Recent Activity" className="lg:col-span-2">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Recent Activity" className="lg:col-span-2">
      <div className="space-y-4">
        {activities && activities.length > 0 ? (
          activities.map((activity) => (
            <div key={activity.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
              <div className="flex-1">
                <p className="text-sm font-medium text-gray-900">{activity.description}</p>
                <p className="text-xs text-gray-500 mt-1">{formatDateTime(activity.timestamp)}</p>
              </div>
              <StatusBadge status={activity.status} />
            </div>
          ))
        ) : (
          <div className="text-center py-4">
            <div className="text-gray-500">No recent activity found</div>
          </div>
        )}
      </div>
    </Card>
  );
};
