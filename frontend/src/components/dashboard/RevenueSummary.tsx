import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { formatCurrency } from '../../utils/format';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

interface RevenueData {
  totalRevenue: number;
  orderCount: number;
}

interface RevenueSummary {
  total: number;
  trend: string;
}

export const RevenueSummary: React.FC = () => {
  const [revenueData, setRevenueData] = useState<RevenueSummary>({
    total: 0,
    trend: '0%'
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRevenueData = async () => {
      try {
        setLoading(true);
        
        // Calculate date ranges for current month and previous month
        const now = new Date();
        const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
        const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);
        
        const prevMonth = new Date(now.getFullYear(), now.getMonth() - 1, 1);
        const startOfPrevMonth = new Date(prevMonth.getFullYear(), prevMonth.getMonth(), 1);
        const endOfPrevMonth = new Date(prevMonth.getFullYear(), prevMonth.getMonth() + 1, 0);

        const startDate = startOfMonth.toISOString().split('T')[0];
        const endDate = endOfMonth.toISOString().split('T')[0];
        const prevStartDate = startOfPrevMonth.toISOString().split('T')[0];
        const prevEndDate = endOfPrevMonth.toISOString().split('T')[0];

        // Fetch revenue data for current month and previous month in parallel
        const [currentMonthResponse, previousMonthResponse] = await Promise.all([
          axios.get(`/api/reports/sales/revenue?startDate=${startDate}&endDate=${endDate}`, {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          }),
          axios.get(`/api/reports/sales/revenue?startDate=${prevStartDate}&endDate=${prevEndDate}`, {
            headers: { Authorization: `Bearer ${getStoredAuth().token}` }
          })
        ]);

        const currentMonthData: RevenueData = currentMonthResponse.data;
        const previousMonthData: RevenueData = previousMonthResponse.data;

        // Calculate trend percentage
        let trendPercentage = 0;
        if (previousMonthData.totalRevenue > 0) {
          trendPercentage = ((currentMonthData.totalRevenue - previousMonthData.totalRevenue) / previousMonthData.totalRevenue) * 100;
        }

        const trend = trendPercentage >= 0 ? `+${trendPercentage.toFixed(1)}%` : `${trendPercentage.toFixed(1)}%`;

        setRevenueData({
          total: currentMonthData.totalRevenue,
          trend: trend
        });
      } catch (error) {
        console.error('Error fetching revenue data:', error);
        setError('Failed to load revenue data');
      } finally {
        setLoading(false);
      }
    };

    fetchRevenueData();
  }, []);

  if (loading) {
    return (
      <Card title="Revenue Summary">
        <div className="text-center py-4">
          <div className="text-gray-500">Loading...</div>
        </div>
      </Card>
    );
  }

  if (error) {
    return (
      <Card title="Revenue Summary">
        <div className="text-center py-4">
          <div className="text-red-500">{error}</div>
        </div>
      </Card>
    );
  }

  return (
    <Card title="Revenue Summary">
      <div className="flex flex-col items-center justify-center py-4">
        <span className="text-2xl font-bold text-emerald-700">
          {formatCurrency(revenueData.total)}
        </span>
        <span className="text-sm text-gray-500 mt-1">This month</span>
        <span className={`text-xs mt-2 font-medium ${
          revenueData.trend.startsWith('+') ? 'text-emerald-600' : 'text-red-600'
        }`}>
          {revenueData.trend} from last month
        </span>
      </div>
    </Card>
  );
}; 