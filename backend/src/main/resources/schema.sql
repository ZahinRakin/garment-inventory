-- User Table
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP
);

-- Product Table
CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP
);

-- Variant Table
CREATE TABLE IF NOT EXISTS variants (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    size VARCHAR(50),
    color VARCHAR(50),
    fabric VARCHAR(100),
    quantity INT,
    sku VARCHAR(100),
    created_at TIMESTAMP
);

-- Raw Material Table
CREATE TABLE IF NOT EXISTS raw_materials (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(50),
    current_stock INT,
    reorder_level INT,
    category VARCHAR(255),
    created_at TIMESTAMP
);

-- Supplier Table
CREATE TABLE IF NOT EXISTS suppliers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    address TEXT,
    created_at TIMESTAMP
);

-- Purchase Table
CREATE TABLE IF NOT EXISTS purchases (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id) ON DELETE SET NULL,
    order_date DATE,
    status VARCHAR(50),
    total_amount DECIMAL(18,2),
    created_at TIMESTAMP
);

-- Purchase Item Table
CREATE TABLE IF NOT EXISTS purchase_items (
    id UUID PRIMARY KEY,
    purchase_id UUID REFERENCES purchases(id) ON DELETE CASCADE,
    material_id UUID REFERENCES raw_materials(id) ON DELETE SET NULL,
    quantity INT,
    unit_price DECIMAL(18,2)
);

-- Sales Order Table
CREATE TABLE IF NOT EXISTS sales_orders (
    id UUID PRIMARY KEY,
    customer_name VARCHAR(255),
    status VARCHAR(50),
    order_date DATE,
    total_amount DECIMAL(18,2)
);

-- Sales Item Table
CREATE TABLE IF NOT EXISTS sales_items (
    id UUID PRIMARY KEY,
    sales_order_id UUID REFERENCES sales_orders(id) ON DELETE CASCADE,
    variant_id UUID REFERENCES variants(id) ON DELETE SET NULL,
    quantity INT,
    unit_price DECIMAL(18,2)
);

-- Production Order Table
CREATE TABLE IF NOT EXISTS production_orders (
    id UUID PRIMARY KEY,
    variant_id UUID REFERENCES variants(id) ON DELETE SET NULL,
    quantity INT,
    status VARCHAR(50),
    start_date DATE,
    end_date DATE
);
