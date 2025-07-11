import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface TopItem {
  id: string;
  count: number;
}

interface Product {
  id: string;
  name: string;
  category: string;
  description: string;
}

interface Variant {
  id: string;
  productId: string;
  size: string;
  color: string;
  fabric: string;
  quantity: number;
  sku: string;
  createdAt: string;
}

interface TopProductDisplay {
  name: string;
  sold: number;
}

export const TopSellingProducts: React.FC = () => {
  const [topProducts, setTopProducts] = useState<TopProductDisplay[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTopSellingProducts = async () => {
      try {
        setLoading(true);
        
        // Fetch top selling products, all products, and all variants in parallel
        const [topProductsResponse, productsResponse, variantsResponse] = await Promise.all([
          axios.get("/api/reports/sales/top-products?limit=5", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/products", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get("/api/products", {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }).then(async (productsRes) => {
            // For each product, get its variants
            const products: Product[] = productsRes.data;
            const allVariants: Variant[] = [];
            
            for (const product of products) {
              try {
                const variantsRes = await axios.get(`/api/products/${product.id}/variants`, {
                  headers: { Authorization: `Bearer ${getStoredAuth().token}` }
                });
                allVariants.push(...variantsRes.data);
              } catch (error) {
                console.warn(`Failed to fetch variants for product ${product.id}:`, error);
              }
            }
            return { data: allVariants };
          })
        ]);

        const topItems: TopItem[] = topProductsResponse.data;
        const products: Product[] = productsResponse.data;
        const variants: Variant[] = variantsResponse.data;

        // Create maps for quick lookup
        const productMap = new Map<string, Product>();
        products.forEach(product => {
          productMap.set(product.id, product);
        });

        const variantMap = new Map<string, Variant>();
        variants.forEach(variant => {
          variantMap.set(variant.id, variant);
        });

        // Transform top items to display format
        const transformedProducts: TopProductDisplay[] = topItems
          .map(item => {
            const variant = variantMap.get(item.id);
            if (!variant) {
              return null;
            }
            
            const product = productMap.get(variant.productId);
            if (!product) {
              return null;
            }

            // Create a descriptive name: "Product Name - Color, Size"
            const name = `${product.name} - ${variant.color}, ${variant.size}`;
            
            return {
              name,
              sold: item.count
            };
          })
          .filter((item): item is TopProductDisplay => item !== null);

        setTopProducts(transformedProducts);
      } catch (error) {
        console.error('Error fetching top selling products:', error);
        setError('Failed to load top selling products data');
      } finally {
        setLoading(false);
      }
    };

    fetchTopSellingProducts();
  }, []);

  if (loading) {
    return (
      <Card title="Top-Selling Products">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Top-Selling Products">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Top-Selling Products">
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Product</th>
              <th className="px-4 py-2 text-right">Quantity Sold</th>
            </tr>
          </thead>
          <tbody>
            {topProducts && topProducts.length > 0 ? (
              topProducts.map((product, idx) => (
                <tr key={idx} className="border-b last:border-0">
                  <td className="px-4 py-2 font-medium text-gray-900">{product.name}</td>
                  <td className="px-4 py-2 text-right">{product.sold}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={2} className="px-4 py-2 text-center text-gray-500">
                  No top selling products found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </Card>
  );
}; 