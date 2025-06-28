import React, { useState } from 'react';

const useInventoryManagement = () => {
    const [inventory, setInventory] = useState([
        {
            id: 1,
            name: "Classic Cotton T-Shirt",
            sku: "CT001",
            category: "T-Shirts",
            size: "M",
            color: "White",
            quantity: 45,
            minStock: 10,
            price: 29.99,
            supplier: "TextileCorp",
            lastUpdated: "2025-06-27",
        },
        {
            id: 2,
            name: "Denim Jacket",
            sku: "DJ002",
            category: "Jackets",
            size: "L",
            color: "Blue",
            quantity: 8,
            minStock: 15,
            price: 89.99,
            supplier: "DenimWorks",
            lastUpdated: "2025-06-26",
        },
        {
            id: 3,
            name: "Summer Dress",
            sku: "SD003",
            category: "Dresses",
            size: "S",
            color: "Floral",
            quantity: 23,
            minStock: 8,
            price: 65.99,
            supplier: "FashionPlus",
            lastUpdated: "2025-06-25",
        },
        {
            id: 4,
            name: "Cargo Pants",
            sku: "CP004",
            category: "Pants",
            size: "32",
            color: "Khaki",
            quantity: 0,
            minStock: 12,
            price: 55.99,
            supplier: "UrbanWear",
            lastUpdated: "2025-06-24",
        }
    ]);

    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('all');

    const categories = ['all', 'T-Shirts', 'Jackets', 'Dresses', 'Pants'];

    const getStockStatus = (quantity, minStock) => {
        if (quantity === 0) return { status: 'Out of Stock', color: 'text-red-600 bg-red-100' };
        if (quantity <= minStock) return { status: 'Low Stock', color: 'text-yellow-600 bg-yellow-100' };
        return { status: 'In Stock', color: 'text-green-600 bg-green-100' };
    };

    const generateNextId = () => Math.max(...inventory.map(item => item.id), 0) + 1;

    const addItem = (itemData) => {
        const newItem = {
            ...itemData,
            id: generateNextId(),
            quantity: parseInt(itemData.quantity, 10),
            minStock: parseInt(itemData.minStock, 10),
            price: parseFloat(itemData.price),
            lastUpdated: new Date().toISOString().split('T')[0],
            status: getStockStatus(parseInt(itemData.quantity, 10), parseInt(itemData.minStock, 10)).status
        };
        setInventory(prev => [...prev, newItem]);
        return newItem;
    };

    const updateItem = (id, updates) => {
        setInventory(prev => prev.map(item => {
            if (item.id === id) {
                const updatedItem = { ...item, ...updates };
                if (updates.quantity !== undefined || updates.minStock !== undefined) {
                    updatedItem.status = getStockStatus(
                        updates.quantity !== undefined ? updates.quantity : item.quantity,
                        updates.minStock !== undefined ? updates.minStock : item.minStock
                    ).status;
                }
                updatedItem.lastUpdated = new Date().toISOString().split('T')[0];
                return updatedItem;
            }
            return item;
        }));
    };

    const deleteItem = (id) => {
        setInventory(prev => prev.filter(item => item.id !== id));
    };

    const filteredInventory = inventory.filter(item => {
        const matchesSearch = item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            item.sku.toLowerCase().includes(searchTerm.toLowerCase());
        const matchesCategory = selectedCategory === 'all' || item.category === selectedCategory;
        return matchesSearch && matchesCategory;
    });

    const getStats = () => ({
        totalItems: inventory.length,
        totalValue: inventory.reduce((sum, item) => sum + (item.quantity * item.price), 0),
        lowStock: inventory.filter(item => item.quantity > 0 && item.quantity <= item.minStock).length,
        outOfStock: inventory.filter(item => item.quantity === 0).length,
        inStock: inventory.filter(item => item.quantity > item.minStock).length
    });

    const exportInventory = (format = 'json') => {
        const date = new Date().toISOString().split('T')[0];

        if (format === 'json') {
            const dataStr = JSON.stringify(inventory, null, 2);
            const dataBlob = new Blob([dataStr], { type: 'application/json' });
            const url = URL.createObjectURL(dataBlob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `inventory_${date}.json`;
            link.click();
            URL.revokeObjectURL(url);
        } else if (format === 'csv') {
            const headers = ['ID', 'Name', 'SKU', 'Category', 'Size', 'Color', 'Quantity', 'Min Stock', 'Price', 'Supplier', 'Status', 'Last Updated'];
            const csvContent = [
                headers.join(','),
                ...inventory.map(item => [
                    item.id,
                    `"${item.name.replace(/"/g, '""')}"`,
                    item.sku,
                    item.category,
                    item.size,
                    item.color,
                    item.quantity,
                    item.minStock,
                    item.price,
                    `"${item.supplier.replace(/"/g, '""')}"`,
                    getStockStatus(item.quantity, item.minStock).status,
                    item.lastUpdated
                ].join(','))
            ].join('\n');

            const dataBlob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
            const url = URL.createObjectURL(dataBlob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `inventory_${date}.csv`;
            link.click();
            URL.revokeObjectURL(url);
        }
    };

    return {
        inventory,
        filteredInventory,
        searchTerm,
        setSearchTerm,
        selectedCategory,
        setSelectedCategory,
        categories,
        getStockStatus,
        getStats,
        addItem,
        updateItem,
        deleteItem,
        exportInventory
    };
};

export const InventoryManagement = ({ children }) => {
    const inventoryManagement = useInventoryManagement();
    return children(inventoryManagement);
};

export default InventoryManagement;




