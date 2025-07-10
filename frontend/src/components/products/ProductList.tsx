import React, { useState } from 'react';
import { Plus, Search, Filter, Edit, Trash2 } from 'lucide-react';
import { Card } from '../common/Card';
import { Button } from '../common/Button';
import { StatusBadge } from '../common/StatusBadge';
import type { Product, Variant } from '../../types';
import { formatCurrency, formatNumber } from '../../utils/format';

export const ProductList: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');

  // Mock data - replace with actual API call
  const products: Product[] = [
    {
      id: '1',
      name: 'Cotton T-Shirt',
      category: 'Apparel',
      description: 'Premium cotton t-shirt with comfortable fit',
      createdAt: '2024-01-01T00:00:00Z',
      variants: [
        { id: '1', productId: '1', size: 'M', color: 'Blue', fabric: 'Cotton', quantity: 150, sku: 'CT-BLU-M-001', unitPrice: 25.99 },
        { id: '2', productId: '1', size: 'L', color: 'Red', fabric: 'Cotton', quantity: 75, sku: 'CT-RED-L-002', unitPrice: 25.99 }
      ]
    },
    {
      id: '2',
      name: 'Denim Jeans',
      category: 'Apparel',
      description: 'Classic denim jeans with modern fit',
      createdAt: '2024-01-02T00:00:00Z',
      variants: [
        { id: '3', productId: '2', size: '32', color: 'Blue', fabric: 'Denim', quantity: 200, sku: 'DJ-BLU-32-001', unitPrice: 49.99 },
        { id: '4', productId: '2', size: '34', color: 'Black', fabric: 'Denim', quantity: 120, sku: 'DJ-BLK-34-002', unitPrice: 49.99 }
      ]
    }
  ];

  const categories = ['all', 'Apparel', 'Footwear', 'Accessories'];

  const getTotalQuantity = (variants: Variant[]) => {
    return variants.reduce((sum, variant) => sum + variant.quantity, 0);
  };

  const getAveragePrice = (variants: Variant[]) => {
    if (variants.length === 0) return 0;
    return variants.reduce((sum, variant) => sum + variant.unitPrice, 0) / variants.length;
  };

  const filteredProducts = products.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         product.category.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === 'all' || product.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Products</h1>
        <Button className="flex items-center space-x-2">
          <Plus className="w-4 h-4" />
          <span>Add Product</span>
        </Button>
      </div>

      <Card>
        <div className="flex flex-col sm:flex-row gap-4 mb-6">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
            <input
              type="text"
              placeholder="Search products..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
            />
          </div>
          
          <select
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
          >
            {categories.map(category => (
              <option key={category} value={category}>
                {category === 'all' ? 'All Categories' : category}
              </option>
            ))}
          </select>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 font-medium text-gray-900">Product</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Category</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Variants</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Total Stock</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Avg. Price</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredProducts.map((product) => (
                <tr key={product.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">
                    <div>
                      <div className="font-medium text-gray-900">{product.name}</div>
                      <div className="text-sm text-gray-500">{product.description}</div>
                    </div>
                  </td>
                  <td className="py-3 px-4">
                    <StatusBadge status={product.category} />
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {product.variants.length} variants
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {formatNumber(getTotalQuantity(product.variants))}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm font-medium text-gray-900">
                      {formatCurrency(getAveragePrice(product.variants))}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex items-center space-x-2">
                      <button className="p-1 text-gray-400 hover:text-emerald-600 transition-colors">
                        <Edit className="w-4 h-4" />
                      </button>
                      <button className="p-1 text-gray-400 hover:text-red-600 transition-colors">
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};