import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface StockAlert {
  message: string;
  itemType: string; // "RAW_MATERIAL" or "VARIANT"
  itemIdentifier: string; // name or SKU
  currentStock: number;
  threshold: number;
}

export const LowStockAlerts: React.FC = () => {
  const [lowStock, setLowStock] = useState<StockAlert[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchLowStock = async () => {
      try {
        setLoading(true);
        const response = await axios.get("/api/stock/alerts", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });
        setLowStock(response.data);
      } catch (error) {
        setError('Failed to load low stock alerts');
      } finally {
        setLoading(false);
      }
    };
    fetchLowStock();
  }, []);

  return (
    <Card title="Low Stock Alerts">
      <div className="space-y-2">
        {loading ? (
          <div className="text-xs text-gray-500">Loading...</div>
        ) : error ? (
          <div className="text-xs text-red-500">{error}</div>
        ) : lowStock.length === 0 ? (
          <div className="text-xs text-gray-500">No low stock items.</div>
        ) : (
          <ul className="text-xs text-gray-800 space-y-1">
            {lowStock.map((item, idx) => (
              <li key={idx} className="flex justify-between items-center">
                <span className="flex items-center">
                  <AlertTriangle className="w-4 h-4 text-red-600 mr-1" />
                  {item.itemIdentifier} ({item.itemType})
                </span>
                <span className="text-red-600 font-bold">{item.currentStock} / {item.threshold}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
      {lowStock.length > 0 && (
        <div className="mt-2 text-xs text-gray-400">{lowStock.length} low stock alert(s) found.</div>
      )}
    </Card>
  );
}; 