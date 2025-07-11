import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface PurchaseItem {
  id: string;
  purchaseId: string;
  materialId: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}

interface Purchase {
  id: string;
  supplierId: string;
  orderDate: string;
  status: string;
  totalAmount: number;
  items: PurchaseItem[];
  createdAt: string;
}

interface Supplier {
  id: string;
  name: string;
  email: string;
  phone: string;
  address: string;
}

interface PurchaseSummaryData {
  supplierName: string;
  totalPurchases: number;
  outstandingOrders: number;
}

export const PurchaseSummary: React.FC = () => {
  const [purchaseSummary, setPurchaseSummary] = useState<PurchaseSummaryData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPurchaseSummary = async () => {
      try {
        setLoading(true);
        
        // Fetch all purchases
        const purchasesResponse = await axios.get("/api/purchases", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });
        
        // Fetch all suppliers to get supplier names
        const suppliersResponse = await axios.get("/api/suppliers", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });

        const purchases: Purchase[] = purchasesResponse.data;
        const suppliers: Supplier[] = suppliersResponse.data;

        // Create a map of supplier IDs to supplier names
        const supplierMap = new Map<string, string>();
        suppliers.forEach(supplier => {
          supplierMap.set(supplier.id, supplier.name);
        });

        // Group purchases by supplier and calculate summary
        const summaryMap = new Map<string, { totalPurchases: number; outstandingOrders: number }>();
        
        purchases.forEach(purchase => {
          const supplierName = supplierMap.get(purchase.supplierId) || 'Unknown Supplier';
          const current = summaryMap.get(supplierName) || { totalPurchases: 0, outstandingOrders: 0 };
          
          current.totalPurchases += purchase.totalAmount;
          if (purchase.status === 'PENDING') {
            current.outstandingOrders += 1;
          }
          
          summaryMap.set(supplierName, current);
        });

        // Convert to array and sort by total purchases
        const summaryArray: PurchaseSummaryData[] = Array.from(summaryMap.entries())
          .map(([supplierName, data]) => ({
            supplierName,
            totalPurchases: data.totalPurchases,
            outstandingOrders: data.outstandingOrders
          }))
          .sort((a, b) => b.totalPurchases - a.totalPurchases)
          .slice(0, 5); // Top 5 suppliers

        setPurchaseSummary(summaryArray);
      } catch (error) {
        console.error('Error fetching purchase summary:', error);
        setError('Failed to load purchase summary data');
      } finally {
        setLoading(false);
      }
    };
    fetchPurchaseSummary();
  }, []);

  if (loading) {
    return (
      <Card title="Purchase Summary by Supplier">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Purchase Summary by Supplier">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Purchase Summary by Supplier">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Supplier</th>
              <th className="px-4 py-2 text-right">Total Purchases</th>
              <th className="px-4 py-2 text-right">Outstanding Orders</th>
            </tr>
          </thead>
          <tbody>
            {purchaseSummary && purchaseSummary.length > 0 ? (
              purchaseSummary.map((row, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{row.supplierName}</td>
                  <td className="px-4 py-2 text-right text-blue-700 font-semibold">
                    ${row.totalPurchases.toLocaleString()}
                  </td>
                  <td className="px-4 py-2 text-right">{row.outstandingOrders}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={3} className="px-4 py-2 text-center text-gray-500">
                  No purchase data found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </Card>
  );
}; 