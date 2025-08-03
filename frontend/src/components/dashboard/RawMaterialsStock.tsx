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

export const RawMaterialsStock: React.FC = () => {
  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRawMaterials = async () => {
      try {
        setLoading(true);
        const auth = getStoredAuth();
        console.log('Auth state:', auth);
        
        if (!auth.isAuthenticated || !auth.token) {
          setError('Please log in to access this data');
          return;
        }
        
        const response = await api.get("/api/raw-materials");
        console.log('Raw materials stock response:', response.data);
        
        // Ensure response.data is an array
        if (Array.isArray(response.data)) {
          setRawMaterials(response.data);
        } else {
          console.error('Expected array but got:', typeof response.data);
          setRawMaterials([]);
          setError('Invalid data format received from server');
        }
      } catch (error: any) {
        console.error('Error fetching raw materials stock:', error);
        if (error.response?.status === 403) {
          setError('Access denied. Please check your permissions.');
        } else if (error.response?.status === 401) {
          setError('Authentication required. Please log in again.');
        } else {
          setError('Failed to load raw materials stock data');
        }
        setRawMaterials([]);
      } finally {
        setLoading(false);
      }
    };
    fetchRawMaterials();
  }, []);

  if (loading) {
    return (
      <Card title="Raw Materials Stock Levels">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Raw Materials Stock Levels">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Raw Materials Stock Levels">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Material</th>
              <th className="px-4 py-2 text-right">Current Stock</th>
              <th className="px-4 py-2 text-right">Threshold</th>
            </tr>
          </thead>
          <tbody>
            {rawMaterials && rawMaterials.length > 0 ? (
              rawMaterials.map((material, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{material.name}</td>
                  <td className={`px-4 py-2 text-right font-semibold ${
                    material.currentStock <= material.reorderLevel ? 'text-red-600' : 'text-gray-900'
                  }`}>
                    {material.currentStock} {material.unit}
                  </td>
                  <td className="px-4 py-2 text-right">{material.reorderLevel} {material.unit}</td>
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
    </Card>
  );
}; 