import React from 'react';
import { Card } from '../common/Card';

interface Product {
  id: string;
  name: string;
  category: string;
  description: string;
}

const dummyProducts: Product[] = [
  {
    id: '1',
    name: 'Cotton Shirt',
    category: 'Apparel',
    description: 'A comfortable cotton shirt.'
  },
  {
    id: '2',
    name: 'Denim Jeans',
    category: 'Apparel',
    description: 'Classic blue denim jeans.'
  },
  {
    id: '3',
    name: 'Silk Scarf',
    category: 'Accessories',
    description: 'Elegant silk scarf in various colors.'
  }
];

export const ProductList: React.FC = () => {
  const products = dummyProducts;
  const loading = false;
  const error = null;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold text-gray-900">Products & Variants</h1>
      </div>
      <Card>
        {loading ? (
          <div className="text-center text-emerald-600 py-8">Loading products...</div>
        ) : error ? (
          <div className="text-center text-red-500 py-8">{error}</div>
        ) : products.length === 0 ? (
          <div className="text-center text-gray-500 py-8">No products found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-white rounded-lg">
              <thead className="bg-slate-800 text-white">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Name</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Category</th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Description</th>
                </tr>
              </thead>
              <tbody>
                {products.map(product => (
                  <tr key={product.id} className="border-b hover:bg-emerald-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-gray-900">{product.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-700">{product.category}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-gray-500">{product.description}</td>
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