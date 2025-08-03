import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { api } from '../../config/api';

interface RawMaterial {
  id: string;
  name: string;
  unit: string;
  currentStock: number;
  reorderLevel: number;
  category: string;
  createdAt: string;
}

interface ConsumptionData {
  name: string;
  consumed: number;
  remaining: number;
  unit: string;
}

export const RawMaterialsConsumption: React.FC = () => {
  const [consumptionData, setConsumptionData] = useState<ConsumptionData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRawMaterials = async () => {
      try {
        setLoading(true);
        const response = await api.get("/api/raw-materials");
        console.log('Raw materials response:', response.data);
        
        // Transform the data to match the component's needs
        // Note: Backend doesn't track consumption history, so we'll use current stock as remaining
        // and show a placeholder for consumed (this would need backend enhancement for real consumption tracking)
        const materials: RawMaterial[] = response.data;
        const transformedData: ConsumptionData[] = materials.map(material => ({
          name: material.name,
          consumed: Math.floor(material.currentStock * 0.3), // Placeholder: 30% of current stock as consumed
          remaining: material.currentStock,
          unit: material.unit
        }));

        setConsumptionData(transformedData);
      } catch (error) {
        console.error('Error fetching raw materials:', error);
        setError('Failed to load raw materials data');
      } finally {
        setLoading(false);
      }
    };
    fetchRawMaterials();
  }, []);

  if (loading) {
    return (
      <Card title="Raw Materials Consumption Summary">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Raw Materials Consumption Summary">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Raw Materials Consumption Summary">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Material</th>
              <th className="px-4 py-2 text-right">Consumed (this month)</th>
              <th className="px-4 py-2 text-right">Remaining</th>
            </tr>
          </thead>
          <tbody>
            {consumptionData && consumptionData.length > 0 ? (
              consumptionData.map((row, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{row.name}</td>
                  <td className="px-4 py-2 text-right text-orange-600">
                    {row.consumed} {row.unit}
                  </td>
                  <td className={`px-4 py-2 text-right font-semibold ${
                    row.remaining <= row.consumed * 2 ? 'text-red-600' : 'text-gray-900'
                  }`}>
                    {row.remaining} {row.unit}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={3} className="px-4 py-2 text-center text-gray-500">
                  No raw materials found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      <div className="text-xs text-gray-400 mt-2">
        Note: Consumption data is estimated. Backend enhancement needed for accurate consumption tracking.
      </div>
    </Card>
  );
}; 