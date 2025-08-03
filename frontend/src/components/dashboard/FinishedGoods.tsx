//--------------------------------------------------------------------------------//
// endpoint /api/reports/finished-goods-stock-------------------------------------//
// get----------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//


import { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

interface Variant {
  id: string;
  sku: string;
  quantity: number;
  size: string;
  color: string;
  fabric: string;
  productId: string;
  createdAt: string;
}

interface FinishedGoodsResponse {
  variants: Variant[];
  lowStockVariants: Variant[];
}

export const FinishedGoods: React.FC = () => {
  const [finishedGoods, setFinishedGoods] = useState<FinishedGoodsResponse>({
    variants: [],
    lowStockVariants: []
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchFinishedGoods = async () => {
      try {
        setLoading(true);
        const response = await api.get("/api/reports/finished-goods-stock");
        console.log('Finished goods response:', response.data);
        setFinishedGoods(response.data);
      } catch (error) {
        console.error('Error fetching finished goods:', error);
        setError('Failed to load finished goods data');
      } finally {
        setLoading(false);
      }
    };
    fetchFinishedGoods();
  }, []);

  if (loading) {
    return (
      <Card title="Finished Goods Produced">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Finished Goods Produced">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Finished Goods Stock">
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
            {finishedGoods.variants && finishedGoods.variants.length > 0 ? (
              finishedGoods.variants.map((variant, idx) => (
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
                  No finished goods found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      {finishedGoods.lowStockVariants && finishedGoods.lowStockVariants.length > 0 && (
        <div className="mt-4 p-3 bg-red-50 border border-red-200 rounded-lg">
          <div className="text-sm font-medium text-red-800 mb-2">
            Low Stock Items ({finishedGoods.lowStockVariants.length})
          </div>
          <div className="text-xs text-red-600">
            {finishedGoods.lowStockVariants.map((variant, idx) => (
              <div key={idx}>
                {variant.sku} - {variant.quantity} in stock
              </div>
            ))}
          </div>
        </div>
      )}
    </Card>
  );
}; 