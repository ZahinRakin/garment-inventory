import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

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

interface PurchaseDisplay {
  id: string;
  supplier: string;
  date: string;
  status: string;
  total: number;
}

export const RecentPurchases: React.FC = () => {
  const [purchases, setPurchases] = useState<PurchaseDisplay[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRecentPurchases = async () => {
      try {
        setLoading(true);
        
        // Fetch purchases and suppliers in parallel
        const [purchasesResponse, suppliersResponse] = await Promise.all([
          axios.get("/api/purchases", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/suppliers", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const purchaseData: Purchase[] = Array.isArray(purchasesResponse.data) ? purchasesResponse.data : [];
        const suppliers: Supplier[] = Array.isArray(suppliersResponse.data) ? suppliersResponse.data : [];

        // Create supplier map
        const supplierMap = new Map<string, string>();
        suppliers.forEach(supplier => {
          supplierMap.set(supplier.id, supplier.name);
        });

        // Transform purchase data for display
        const transformedPurchases: PurchaseDisplay[] = purchaseData
          .slice(0, 5) // Get recent 5 purchases
          .map(purchase => ({
            id: purchase.id.slice(0, 8), // Short ID for display
            supplier: supplierMap.get(purchase.supplierId) || 'Unknown Supplier',
            date: purchase.orderDate,
            status: purchase.status,
            total: purchase.totalAmount
          }))
          .sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime()); // Sort by date

        setPurchases(transformedPurchases);
      } catch (error) {
        console.error('Error fetching recent purchases:', error);
        setError('Failed to load recent purchases data');
        setPurchases([]);
      } finally {
        setLoading(false);
      }
    };

    fetchRecentPurchases();
  }, []);

  if (loading) {
    return (
      <Card title="Recent Purchases">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Recent Purchases">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Recent Purchases">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Purchase ID</th>
              <th className="px-4 py-2 text-left">Supplier</th>
              <th className="px-4 py-2 text-left">Date</th>
              <th className="px-4 py-2 text-left">Status</th>
              <th className="px-4 py-2 text-right">Total</th>
            </tr>
          </thead>
          <tbody>
            {purchases && purchases.length > 0 ? (
              purchases.map((purchase, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{purchase.id}</td>
                  <td className="px-4 py-2">{purchase.supplier}</td>
                  <td className="px-4 py-2">{purchase.date}</td>
                  <td className={`px-4 py-2 ${
                    purchase.status === 'PENDING' ? 'text-orange-600' : 
                    purchase.status === 'RECEIVED' || purchase.status === 'DELIVERED' ? 'text-emerald-700' : 
                    'text-gray-600'
                  }`}>
                    {purchase.status}
                  </td>
                  <td className="px-4 py-2 text-right">${purchase.total.toLocaleString()}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={5} className="px-4 py-2 text-center text-gray-500">
                  No recent purchases found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </Card>
  );
}; 