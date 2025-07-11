
import { PendingDeliveredOrders } from './PendingDeliveredOrders';
import { TopSellingProducts } from './TopSellingProducts';
import { RevenueSummary } from './RevenueSummary';
import { CustomerOrders } from './CustomerOrders';

export const DashboardSalesOfficer: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Sales Officer Dashboard</h1>
        <div className="text-sm text-gray-500">
          Last updated: {new Date().toLocaleString()}
        </div>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <PendingDeliveredOrders />
        <TopSellingProducts />
        <RevenueSummary />
        <CustomerOrders />
      </div>
    </div>
  );
}; 