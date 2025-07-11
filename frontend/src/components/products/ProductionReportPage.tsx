import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

// --- Types based on production report needs ---
interface ProductionSummary {
  totalOrders: number;
  completedOrders: number;
  inProgressOrders: number;
  cancelledOrders: number;
  totalProduced: number;
  period: string;
}

interface TopProducedItem {
  id: string;
  name: string;
  producedQuantity: number;
}

interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: string;
  startDate: string;
  endDate: string | null;
  createdAt: string;
}

interface Product {
  id: string;
  name: string;
  category: string;
  description: string;
}

interface Variant {
  id: string;
  productId: string;
  size: string;
  color: string;
  fabric: string;
  quantity: number;
  sku: string;
  createdAt: string;
}

interface StatusCount {
  status: string;
  count: number;
}

export const ProductionReportPage: React.FC = () => {
  const [productionSummary, setProductionSummary] = useState<ProductionSummary>({
    totalOrders: 0,
    completedOrders: 0,
    inProgressOrders: 0,
    cancelledOrders: 0,
    totalProduced: 0,
    period: ''
  });
  const [topProducedItems, setTopProducedItems] = useState<TopProducedItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProductionData = async () => {
      try {
        setLoading(true);
        
        // Fetch production data from multiple endpoints
        const [statusResponse, ordersResponse, productsResponse] = await Promise.all([
          axios.get("/api/reports/production/status", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/production-orders", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/products", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const statusData: StatusCount[] = statusResponse.data;
        const ordersData: ProductionOrder[] = ordersResponse.data;
        const productsData: Product[] = productsResponse.data;

        // Calculate production summary from status data
        const totalOrders = ordersData.length;
        const completedOrders = statusData.find(s => s.status === 'COMPLETED')?.count || 0;
        const inProgressOrders = statusData.find(s => s.status === 'IN_PROGRESS')?.count || 0;
        const cancelledOrders = statusData.find(s => s.status === 'CANCELLED')?.count || 0;
        
        // Calculate total produced from completed orders
        const totalProduced = ordersData
          .filter(order => order.status === 'COMPLETED')
          .reduce((sum, order) => sum + order.quantity, 0);

        // Calculate current month period
        const now = new Date();
        const period = `${now.toLocaleString('default', { month: 'long' })} ${now.getFullYear()}`;

        setProductionSummary({
          totalOrders,
          completedOrders,
          inProgressOrders,
          cancelledOrders,
          totalProduced,
          period
        });

        // Calculate top produced items
        const completedOrdersByVariant = ordersData
          .filter(order => order.status === 'COMPLETED')
          .reduce((acc, order) => {
            acc[order.variantId] = (acc[order.variantId] || 0) + order.quantity;
            return acc;
          }, {} as Record<string, number>);

        // Get top 5 produced variants
        const topVariants = Object.entries(completedOrdersByVariant)
          .sort(([,a], [,b]) => b - a)
          .slice(0, 5);

        // For now, we'll use variant IDs as names since we don't have variant details
        // In a real implementation, you'd fetch variant details to get product names
        const topItems: TopProducedItem[] = topVariants.map(([variantId, quantity]) => ({
          id: variantId,
          name: `Variant ${variantId}`, // Placeholder - would need variant details
          producedQuantity: quantity
        }));

        setTopProducedItems(topItems);

      } catch (error) {
        console.error('Error fetching production data:', error);
        setError('Failed to load production data');
      } finally {
        setLoading(false);
      }
    };

    fetchProductionData();
  }, []);

  if (loading) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Production Report</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-gray-500">Loading production data...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="space-y-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold text-gray-900">Production Report</h1>
        </div>
        <div className="text-center py-8">
          <div className="text-red-500">{error}</div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Production Report</h1>
        <span className="text-sm text-gray-500">Period: {productionSummary.period}</span>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card title="Production Summary">
          <div className="flex flex-col gap-2">
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Orders</span>
              <span className="font-bold text-blue-700">{productionSummary.totalOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Completed Orders</span>
              <span className="font-bold text-emerald-700">{productionSummary.completedOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">In Progress Orders</span>
              <span className="font-bold text-orange-600">{productionSummary.inProgressOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Cancelled Orders</span>
              <span className="font-bold text-red-600">{productionSummary.cancelledOrders}</span>
            </div>
            <div className="flex justify-between text-lg">
              <span className="text-gray-700">Total Produced</span>
              <span className="font-bold text-purple-700">{productionSummary.totalProduced}</span>
            </div>
          </div>
        </Card>
        <Card title="Top Produced Items">
          <table className="min-w-full bg-white rounded-lg">
            <thead className="bg-purple-100">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-purple-700">Name</th>
                <th className="px-4 py-2 text-left text-xs font-medium uppercase tracking-wider text-purple-700">Produced Quantity</th>
              </tr>
            </thead>
            <tbody>
              {topProducedItems && topProducedItems.length > 0 ? (
                topProducedItems.map(item => (
                  <tr key={item.id} className="border-b hover:bg-purple-50 transition-colors">
                    <td className="px-4 py-2 text-gray-900">{item.name}</td>
                    <td className="px-4 py-2 text-gray-700">{item.producedQuantity}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={2} className="px-4 py-2 text-center text-gray-500">
                    No production data available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </Card>
      </div>
    </div>
  );
}; 