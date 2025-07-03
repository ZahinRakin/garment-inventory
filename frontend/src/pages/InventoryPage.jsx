import React, { useState } from 'react';
import {
    Search, Plus, Filter, Download, Upload,
    Package, AlertTriangle, TrendingUp, Eye, Edit, Trash2
} from 'lucide-react';
import InventoryManagement from '../components/InventoryManagement';
import AddItemModal from '../components/AddItemModal';
import StatsCard from '../components/StatsCard';
import InventoryTable from '../components/InventoryTable';

const InventoryPage = () => {
    const [showAddModal, setShowAddModal] = useState(false);

    return (
        <InventoryManagement>
            {(inventoryHook) => {
                const {
                    filteredInventory,
                    searchTerm,
                    setSearchTerm,
                    selectedCategory,
                    setSelectedCategory,
                    categories,
                    getStockStatus,
                    getStats,
                    addItem,
                    deleteItem,
                    exportInventory
                } = inventoryHook;

                const stats = getStats();

                const handleAddItem = (itemData) => {
                    addItem(itemData);
                    setShowAddModal(false);
                };

                const handleView = (item) => console.log('View item:', item);
                const handleEdit = (item) => console.log('Edit item:', item);

                const handleDelete = (id) => {
                    if (window.confirm('Are you sure you want to delete this item?')) {
                        deleteItem(id);
                    }
                };

                const handleExport = (format = 'csv') => exportInventory(format);

                return (
                    <div className="min-h-screen bg-gray-50">
                        <div className="bg-white shadow-sm border-b">
                            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                                <div className="flex justify-between items-center py-4">
                                    <div>
                                        <h1 className="text-2xl font-bold text-gray-900">Garment Inventory</h1>
                                        <p className="text-gray-600">Manage your clothing inventory efficiently</p>
                                    </div>
                                    <div className="flex gap-3">
                                        <button
                                            onClick={() => handleExport('csv')}
                                            className="flex items-center gap-2 px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
                                        >
                                            <Download className="w-4 h-4" /> Export CSV
                                        </button>
                                        <button
                                            onClick={() => handleExport('json')}
                                            className="flex items-center gap-2 px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
                                        >
                                            <Download className="w-4 h-4" /> Export JSON
                                        </button>
                                        <button className="flex items-center gap-2 px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200">
                                            <Upload className="w-4 h-4" /> Import
                                        </button>
                                        <button
                                            onClick={() => setShowAddModal(true)}
                                            className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                                        >
                                            <Plus className="w-4 h-4" /> Add Item
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                            <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
                                <StatsCard
                                    title="Total Items"
                                    value={stats.totalItems}
                                    icon={Package}
                                    color="blue"
                                />
                                <StatsCard
                                    title="Total Value"
                                    value={`$${stats.totalValue.toFixed(2)}`}
                                    icon={TrendingUp}
                                    color="green"
                                />
                                <StatsCard
                                    title="Low Stock"
                                    value={stats.lowStock}
                                    icon={AlertTriangle}
                                    color="yellow"
                                />
                                <StatsCard
                                    title="Out of Stock"
                                    value={stats.outOfStock}
                                    icon={AlertTriangle}
                                    color="red"
                                />
                            </div>

                            <div className="bg-white p-6 rounded-lg shadow-sm mb-6">
                                <div className="flex flex-col md:flex-row gap-4">
                                    <div className="flex-1 relative">
                                        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
                                        <input
                                            type="text"
                                            placeholder="Search by name or SKU..."
                                            value={searchTerm}
                                            onChange={(e) => setSearchTerm(e.target.value)}
                                            className="w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                        />
                                    </div>
                                    <div className="flex gap-3">
                                        <select
                                            value={selectedCategory}
                                            onChange={(e) => setSelectedCategory(e.target.value)}
                                            className="px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                        >
                                            {categories.map(category => (
                                                <option key={category} value={category}>
                                                    {category === 'all' ? 'All Categories' : category}
                                                </option>
                                            ))}
                                        </select>
                                        <button className="flex items-center gap-2 px-4 py-2 border rounded-lg hover:bg-gray-50">
                                            <Filter className="w-4 h-4" /> More Filters
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <InventoryTable
                                items={filteredInventory}
                                onView={handleView}
                                onEdit={handleEdit}
                                onDelete={handleDelete}
                                getStockStatus={getStockStatus}
                            />
                        </div>

                        <AddItemModal
                            isOpen={showAddModal}
                            onClose={() => setShowAddModal(false)}
                            onAdd={handleAddItem}
                            categories={categories.filter(cat => cat !== 'all')}
                        />
                    </div>
                );
            }}
        </InventoryManagement>
    );
};

export default InventoryPage;