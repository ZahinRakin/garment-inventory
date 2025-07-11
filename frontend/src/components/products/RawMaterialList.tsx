import React from 'react';
import { Card } from '../common/Card';

interface RawMaterial {
  id: string;
  name: string;
  unit: string;
  currentStock: number;
  reorderLevel: number;
  category: string;
  createdAt: string; // ISO string for now
}

// TODO: Replace this dummy data with a backend API call to /api/raw-materials
const dummyRawMaterials: RawMaterial[] = [
  {
    id: '1',
    name: 'Cotton Fabric',
    unit: 'meter',
    currentStock: 1200,
    reorderLevel: 200,
    category: 'Fabric',
    createdAt: '2024-01-01T10:00:00Z',
  },
  {
    id: '2',
    name: 'Polyester Thread',
    unit: 'spool',
    currentStock: 300,
    reorderLevel: 50,
    category: 'Thread',
    createdAt: '2024-01-05T14:30:00Z',
  },
  {
    id: '3',
    name: 'Metal Button',
    unit: 'piece',
    currentStock: 5000,
    reorderLevel: 1000,
    category: 'Accessory',
    createdAt: '2024-01-10T09:15:00Z',
  },
];

export const RawMaterialList: React.FC = () => {
  // TODO: Fetch raw materials from backend here
  // useEffect(() => { fetch('/api/raw-materials') ... }, []);
  const rawMaterials = dummyRawMaterials;
  const loading = false;
  const error = null;

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
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{material.currentStock}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{material.reorderLevel}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{new Date(material.createdAt).toLocaleString()}</td>
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