import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

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

export const ProductStock: React.FC = () => {
  const [variants, setVariants] = useState<Variant[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchLowStockVariants = async () => {
      try {
        setLoading(true);
        const response = await axios.get("/api/products/variants/low-stock", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });
        console.log('Low stock variants response:', response.data);
        
        // Ensure response.data is an array
        if (Array.isArray(response.data)) {
          setVariants(response.data);
        } else {
          console.error('Expected array but got:', typeof response.data);
          setVariants([]);
          setError('Invalid data format received from server');
        }
      } catch (error) {
        console.error('Error fetching low stock variants:', error);
        setError('Failed to load product stock data');
        setVariants([]);
      } finally {
        setLoading(false);
      }
    };
    fetchLowStockVariants();
  }, []);

  if (loading) {
    return (
      <Card title="Product Stock Levels">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Product Stock Levels">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Product Stock Levels">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">SKU</th>
              <th className="px-4 py-2 text-left">Size</th>
              <th className="px-4 py-2 text-left">Color</th>
              <th className="px-4 py-2 text-right">Current Stock</th>
            </tr>
          </thead>
          <tbody>
            {variants && variants.length > 0 ? (
              variants.map((variant, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{variant.sku}</td>
                  <td className="px-4 py-2">{variant.size}</td>
                  <td className="px-4 py-2">{variant.color}</td>
                  <td className={`px-4 py-2 text-right font-semibold ${
                    variant.quantity <= 10 ? 'text-red-600' : 'text-gray-900'
                  }`}>
                    {variant.quantity}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={4} className="px-4 py-2 text-center text-gray-500">
                  No low stock products found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </Card>
  );
}; 