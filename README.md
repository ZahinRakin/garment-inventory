# FabricFlow - Garment Inventory Management System

A comprehensive inventory management system for garment manufacturing, built with Spring Boot backend and React frontend.

## 🚀 Features

- **Product Management**: Create and manage products with variants (size, color, fabric)
- **Raw Material Management**: Track raw materials inventory and reorder levels
- **Supplier Management**: Manage supplier information and relationships
- **Purchase Management**: Handle purchase orders and receipt processing
- **Sales Management**: Process sales orders and track deliveries
- **Production Management**: Manage production orders and workflows
- **Stock Management**: Real-time stock tracking with alerts and adjustments
- **Reporting**: Comprehensive reports for inventory, sales, and production
- **Authentication**: JWT-based user authentication and authorization

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.5, Java 17
- **Database**: PostgreSQL
- **Frontend**: React.js with Vite
- **Authentication**: JWT
- **Build Tool**: Maven

## 🔧 Setup Instructions

### Backend Setup

1. Navigate to the FabricFlowBackend directory:
   ```bash
   cd FabricFlowBackend
   ```

2. Update `application.properties` with your database configuration

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on port 8000 (configurable in application.properties).

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

## 📚 API Documentation

Base URL: `http://localhost:8000`

### 🔐 Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/api/login` | User login | `{ "email": "string", "password": "string" }` |
| POST | `/api/register` | User registration | `{ "firstName": "string", "lastName": "string", "email": "string", "password": "string", "role": "string" }` |

### 📦 Product Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/products` | Get all products | - |
| GET | `/api/products/{id}` | Get product by ID | - |
| GET | `/api/products/category/{category}` | Get products by category | - |
| POST | `/api/products` | Create new product | `{ "name": "string", "category": "string", "description": "string" }` |
| PUT | `/api/products/{id}` | Update product | `{ "name": "string", "category": "string", "description": "string" }` |
| DELETE | `/api/products/{id}` | Delete product | - |

### 🎨 Product Variants

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/products/{productId}/variants` | Get variants by product | - |
| GET | `/api/products/variants/{id}` | Get variant by ID | - |
| GET | `/api/products/variants/low-stock` | Get low stock variants | Query: `?threshold=10` |
| POST | `/api/products/{productId}/variants` | Create variant | `{ "size": "string", "color": "string", "fabric": "string", "quantity": 0, "sku": "string" }` |
| PUT | `/api/products/variants/{id}` | Update variant | `{ "productId": "uuid", "size": "string", "color": "string", "fabric": "string", "quantity": 0, "sku": "string" }` |
| DELETE | `/api/products/variants/{id}` | Delete variant | - |

### 🧵 Raw Materials Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/raw-materials` | Get all raw materials | - |
| GET | `/api/raw-materials/{id}` | Get raw material by ID | - |
| GET | `/api/raw-materials/category/{category}` | Get materials by category | - |
| GET | `/api/raw-materials/needing-reorder` | Get materials needing reorder | - |
| GET | `/api/raw-materials/low-stock` | Get low stock materials | Query: `?threshold=0` |
| POST | `/api/raw-materials` | Create raw material | `{ "name": "string", "unit": "string", "currentStock": 0, "reorderLevel": 0, "category": "string" }` |
| PUT | `/api/raw-materials/{id}` | Update raw material | `{ "name": "string", "unit": "string", "currentStock": 0, "reorderLevel": 0, "category": "string" }` |
| DELETE | `/api/raw-materials/{id}` | Delete raw material | - |
| POST | `/api/raw-materials/{id}/consume` | Consume stock | `{ "quantity": 0 }` |
| POST | `/api/raw-materials/{id}/add-stock` | Add stock | `{ "quantity": 0 }` |

### 🏢 Supplier Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/suppliers` | Get all suppliers | - |
| GET | `/api/suppliers/{id}` | Get supplier by ID | - |
| GET | `/api/suppliers/search` | Search suppliers by name | Query: `?name=string` |
| POST | `/api/suppliers` | Create supplier | `{ "name": "string", "email": "string", "phone": "string", "address": "string" }` |
| PUT | `/api/suppliers/{id}` | Update supplier | `{ "name": "string", "email": "string", "phone": "string", "address": "string" }` |
| DELETE | `/api/suppliers/{id}` | Delete supplier | - |

### 🛒 Purchase Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/purchases` | Get all purchases | - |
| GET | `/api/purchases/{id}` | Get purchase by ID | - |
| GET | `/api/purchases/supplier/{supplierId}` | Get purchases by supplier | - |
| GET | `/api/purchases/status/{status}` | Get purchases by status | - |
| GET | `/api/purchases/date-range` | Get purchases by date range | Query: `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| POST | `/api/purchases` | Create purchase order | `{ "supplierId": "uuid", "orderDate": "YYYY-MM-DD", "items": [...] }` |
| PUT | `/api/purchases/{id}` | Update purchase order | `{ "supplierId": "uuid", "orderDate": "YYYY-MM-DD", "items": [...] }` |
| DELETE | `/api/purchases/{id}` | Delete purchase order | - |
| POST | `/api/purchases/{purchaseId}/items` | Add purchase item | `{ "materialId": "uuid", "quantity": 0, "unitPrice": 0.0 }` |
| DELETE | `/api/purchases/items/{itemId}` | Remove purchase item | - |
| GET | `/api/purchases/{purchaseId}/items` | Get purchase items | - |
| POST | `/api/purchases/{purchaseId}/receive` | Mark as received | - |
| POST | `/api/purchases/{purchaseId}/process-receipt` | Process receipt | - |
| POST | `/api/purchases/{purchaseId}/status` | Update status | `{ "status": "string" }` |

### 💰 Sales Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/sales/orders` | Get all sales orders | - |
| GET | `/api/sales/orders/{id}` | Get sales order by ID | - |
| GET | `/api/sales/orders/customer/{customerName}` | Get orders by customer | - |
| GET | `/api/sales/orders/status/{status}` | Get orders by status | - |
| GET | `/api/sales/orders/date-range` | Get orders by date range | Query: `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| POST | `/api/sales/orders` | Create sales order | `{ "customerName": "string", "orderDate": "YYYY-MM-DD", "items": [...] }` |
| PUT | `/api/sales/orders/{id}` | Update sales order | `{ "customerName": "string", "orderDate": "YYYY-MM-DD", "items": [...] }` |
| DELETE | `/api/sales/orders/{id}` | Delete sales order | - |
| POST | `/api/sales/orders/{orderId}/items` | Add sales item | `{ "variantId": "uuid", "quantity": 0, "unitPrice": 0.0 }` |
| DELETE | `/api/sales/items/{itemId}` | Remove sales item | - |
| GET | `/api/sales/orders/{orderId}/items` | Get sales items | - |
| POST | `/api/sales/orders/{orderId}/process` | Process sales order | - |
| POST | `/api/sales/orders/{orderId}/deliver` | Mark as delivered | - |
| POST | `/api/sales/orders/{orderId}/status` | Update order status | `{ "status": "string" }` |
| GET | `/api/sales/stock/check` | Check stock availability | Query: `?variantId=uuid&quantity=0` |

### 🏭 Production Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/production-orders` | Get all production orders | - |
| GET | `/api/production-orders/{id}` | Get production order by ID | - |
| GET | `/api/production-orders/variant/{variantId}` | Get orders by variant | - |
| GET | `/api/production-orders/status/{status}` | Get orders by status | - |
| GET | `/api/production-orders/pending` | Get pending orders | - |
| GET | `/api/production-orders/in-progress` | Get in-progress orders | - |
| POST | `/api/production-orders` | Create production order | `{ "variantId": "uuid", "quantity": 0 }` |
| PUT | `/api/production-orders/{id}` | Update production order | `{ "variantId": "uuid", "quantity": 0 }` |
| DELETE | `/api/production-orders/{id}` | Delete production order | - |
| POST | `/api/production-orders/{id}/start` | Start production | - |
| POST | `/api/production-orders/{id}/complete` | Complete production | - |

### 📊 Stock Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/api/stock/adjust` | Adjust stock | `{ "itemId": "uuid", "quantity": 0, "reason": "string", "isRawMaterial": true }` |
| POST | `/api/stock/transfer` | Transfer stock | `{ "fromId": "uuid", "toId": "uuid", "quantity": 0, "isRawMaterial": true }` |
| GET | `/api/stock/raw-materials/low-stock` | Get low stock raw materials | - |
| GET | `/api/stock/variants/low-stock` | Get low stock variants | - |
| POST | `/api/stock/validate` | Validate stock | `{ "itemId": "uuid", "requiredQuantity": 0, "isRawMaterial": true }` |
| GET | `/api/stock/alerts` | Get stock alerts | - |
| GET | `/api/stock/adjustments` | Get stock adjustments | - |
| GET | `/api/stock/report` | Generate stock report | - |

### 📈 Reports & Analytics

| Method | Endpoint | Description | Query Parameters |
|--------|----------|-------------|------------------|
| GET | `/api/reports/stock-summary` | Get stock summary | - |
| GET | `/api/reports/raw-material-stock` | Get raw material stock report | - |
| GET | `/api/reports/finished-goods-stock` | Get finished goods stock report | - |
| GET | `/api/reports/low-stock` | Get low stock report | - |
| GET | `/api/reports/purchases` | Get purchase report | `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| GET | `/api/reports/purchases/supplier` | Get supplier purchase report | `?supplierId=uuid&startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| GET | `/api/reports/top-suppliers` | Get top suppliers | `?limit=5` |
| GET | `/api/reports/production` | Get production report | `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| GET | `/api/reports/production/status` | Get production orders by status | - |
| GET | `/api/reports/production/efficiency` | Get production efficiency | - |
| GET | `/api/reports/sales` | Get sales report | `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| GET | `/api/reports/sales/top-products` | Get top selling products | `?limit=5` |
| GET | `/api/reports/sales/top-customers` | Get top customers | `?limit=5` |
| GET | `/api/reports/sales/revenue` | Get sales revenue report | `?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` |
| GET | `/api/reports/inventory/value` | Get total inventory value | - |
| GET | `/api/reports/alerts` | Get all alerts | - |
| GET | `/api/reports/alerts/stock` | Get stock alerts | - |
| GET | `/api/reports/alerts/production` | Get production alerts | - |
| GET | `/api/reports/alerts/purchase` | Get purchase alerts | - |

## 📝 Response Formats

### Standard Response Structure

**Success Response:**
```json
{
  "id": "uuid",
  "data": { ... },
  "timestamp": "2025-08-03T10:00:00Z"
}
```

**Error Response:**
```json
{
  "error": "Error message",
  "timestamp": "2025-08-03T10:00:00Z",
  "status": 400
}
```

### Authentication Response

```json
{
  "accessToken": "jwt_token_here",
  "user": {
    "id": "uuid",
    "firstName": "string",
    "lastName": "string", 
    "email": "string",
    "role": "string"
  }
}
```

## 🚀 Getting Started

1. Clone the repository
2. Set up the database connection in `application.properties`
3. Run the backend: `mvn spring-boot:run`
4. Install frontend dependencies: `npm install`
5. Start the frontend: `npm run dev`
6. Access the application at `http://localhost:3000`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.