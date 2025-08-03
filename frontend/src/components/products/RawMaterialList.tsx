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
  createdAt: string; // ISO string for now
}

export const RawMaterialList: React.FC = () => {
  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRawMaterials = async () => {
      try {
        setLoading(true);
        const response = await api.get("/api/raw-materials");
        
        const materialsData: RawMaterial[] = response.data;
        setRawMaterials(materialsData);
      } catch (error) {
        console.error('Error fetching raw materials:', error);
        setError('Failed to load raw materials');
      } finally {
        setLoading(false);
      }
    };

    fetchRawMaterials();
  }, []);

  const formatDateTime = (dateTimeString: string) => {
    return new Date(dateTimeString).toLocaleString();
  };

  const getStockStatusColor = (currentStock: number, reorderLevel: number) => {
    if (currentStock === 0) {
      return 'text-red-600 font-semibold';
    } else if (currentStock <= reorderLevel) {
      return 'text-orange-600 font-semibold';
    }
    return 'text-gray-700';
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Raw Materials</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading raw materials...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : rawMaterials.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No raw materials found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Name</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Category</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Unit</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Current Stock</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Reorder Level</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Created At</th>
                </tr>
              </thead>
              <tbody>
                {rawMaterials.map(material => (
                  <tr key={material.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{material.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{material.category}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{material.unit}</td>
                    <td className={`px-6 py-4 whitespace-nowrap ${getStockStatusColor(material.currentStock, material.reorderLevel)}`}>
                      {material.currentStock}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{material.reorderLevel}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{formatDateTime(material.createdAt)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
}; 