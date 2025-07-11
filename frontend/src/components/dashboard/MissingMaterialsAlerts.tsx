import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface LowStockItem {
  type: string; // "RAW_MATERIAL" or "VARIANT"
  id: string;
  name: string;
  current: number;
  threshold: number;
}

export const MissingMaterialsAlerts: React.FC = () => {
  const [missingMaterials, setMissingMaterials] = useState<LowStockItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchMissingMaterials = async () => {
      try {
        setLoading(true);
        const response = await axios.get("/api/reports/low-stock", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });
        setMissingMaterials(response.data);
      } catch (error) {
        setError('Failed to load missing materials alerts');
      } finally {
        setLoading(false);
      }
    };
    fetchMissingMaterials();
  }, []);

  return (
    <Card title="Alerts for Missing Materials">
      <div className="space-y-2">
        {loading ? (
          <div className="text-xs text-gray-500">Loading...</div>
        ) : error ? (
          <div className="text-xs text-red-500">{error}</div>
        ) : missingMaterials.length === 0 ? (
          <div className="text-xs text-gray-500">No missing or low stock materials.</div>
        ) : (
          <ul className="text-xs text-gray-800 space-y-1">
            {missingMaterials.map((item, idx) => (
              <li key={idx} className="flex justify-between items-center">
                <span className="flex items-center">
                  <AlertTriangle className="w-4 h-4 text-red-600 mr-1" />
                  {item.name} ({item.type})
                </span>
                <span className="text-red-600 font-bold">{item.current} / {item.threshold}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
      {missingMaterials.length > 0 && (
        <div className="mt-2 text-xs text-gray-400">{missingMaterials.length} missing/low stock item(s) found.</div>
      )}
    </Card>
  );
}; 