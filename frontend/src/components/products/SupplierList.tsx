import React from 'react';
import { Card } from '../common/Card';

interface Supplier {
  id: string;
  name: string;
  email: string;
  phone: string;
  address: string;
}

// TODO: Replace this dummy data with a backend API call to /api/suppliers
const dummySuppliers: Supplier[] = [
  {
    id: '1',
    name: 'Textile Suppliers Ltd.',
    email: 'contact@textilesuppliers.com',
    phone: '+880123456789',
    address: '123 Main Street, Dhaka',
  },
  {
    id: '2',
    name: 'Button World',
    email: 'info@buttonworld.com',
    phone: '+880987654321',
    address: '456 Button Avenue, Chittagong',
  },
  {
    id: '3',
    name: 'Thread & Yarn Co.',
    email: 'sales@threadyarn.com',
    phone: '+880112233445',
    address: '789 Yarn Road, Khulna',
  },
];

export const SupplierList: React.FC = () => {
  // TODO: Fetch suppliers from backend here
  // useEffect(() => { fetch('/api/suppliers') ... }, []);
  const suppliers = dummySuppliers;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Suppliers</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading suppliers...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : suppliers.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No suppliers found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Name</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Email</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Phone</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Address</th>
                </tr>
              </thead>
              <tbody>
                {suppliers.map(supplier => (
                  <tr key={supplier.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{supplier.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{supplier.email}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{supplier.phone}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{supplier.address}</td>
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