//----------------------------------------------------------------------------------//
// api endpoint   /api/reports/low-stock------------------------------------------//
// get
// expected low stock items at response.data------------------------//
//----------------------------------------------------------------------------------//
import React, { useEffect, useState } from 'react';
import { Card } from '../common/Card';
import { AlertTriangle } from 'lucide-react';
import axios from 'axios';
import { getStoredAuth } from '../../utils/auth';

// const dummyOverduePOs = [
//   { id: 'PO-2024-003', supplier: 'Textile Suppliers Ltd.', dueDate: '2024-01-10' },
// ];

export const Alerts: React.FC = () => {
  const [lowStockItems, setLowStockItems] = useState<any[]>([
    {
      id: "RM-001",
      name: "Cotton Fabric",
      type: "Raw Material",
      current: 15,
      threshold: 30
    },
    {
      id: "FG-015",
      name: "Linen Dress",
      type: "Finished Good",
      current: 5,
      threshold: 10
    },
    {
      id: "RM-022",
      name: "Zippers",
      type: "Raw Material",
      current: 20,
      threshold: 50
    }
  ]);
  

  useEffect(() => {
    const fetchAlerts = async () => {
      try {
        const response = await axios.get("/api/reports/low-stock", {
          headers: {
            Authorization: `Bearer ${getStoredAuth().token}`
          }
        });
        console.log(response.data); // debugging log
        setLowStockItems(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchAlerts();
  }, []);

  return (
    <Card title="Alerts">
      <div className="space-y-4">
        <div>
          <div className="flex items-center mb-2 text-orange-600 font-semibold">
            <AlertTriangle className="w-5 h-5 mr-2" /> Low Stock Items
          </div>
          {lowStockItems.length === 0 ? (
            <div className="text-xs text-gray-500">No low stock items.</div>
          ) : (
            <ul className="text-xs text-gray-800 space-y-1">
              {lowStockItems.map((item, idx) => (
                <li key={idx} className="flex justify-between">
                  <span>{item.name} ({item.type})</span>
                  <span className="text-red-600 font-bold">{item.current} / {item.threshold}</span>
                </li>
              ))}
            </ul>
          )}
        </div>
        <div>
          <div className="flex items-center mb-2 text-red-700 font-semibold">
            <AlertTriangle className="w-5 h-5 mr-2" /> Overdue Purchase Orders
          </div>
          {/* {dummyOverduePOs.length === 0 ? (
            <div className="text-xs text-gray-500">No overdue purchase orders.</div>
          ) : (
            <ul className="text-xs text-gray-800 space-y-1">
              {dummyOverduePOs.map((po, idx) => (
                <li key={idx} className="flex justify-between">
                  <span>Order {po.id} ({po.supplier})</span>
                  <span className="text-red-600 font-bold">Due: {po.dueDate}</span>
                </li>
              ))}
            </ul>
          )} */}
        </div>
      </div>
    </Card>
  );
};
