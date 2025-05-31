
-- Users table for Authentication
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    role TEXT CHECK (role IN ('ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER', 'SALES_OFFICER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Variants table
CREATE TABLE variants (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    size TEXT,
    color TEXT,
    fabric TEXT,
    quantity INTEGER DEFAULT 0,
    sku TEXT UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Raw Materials table
CREATE TABLE raw_materials (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    unit TEXT NOT NULL,
    current_stock INTEGER DEFAULT 0,
    reorder_level INTEGER,
    category TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Suppliers table
CREATE TABLE suppliers (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT,
    phone TEXT,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Purchases table
CREATE TABLE purchases (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    order_date DATE,
    status TEXT CHECK (status IN ('CREATED', 'RECEIVED', 'CANCELLED')),
    total_amount DECIMAL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Purchase Items table
CREATE TABLE purchase_items (
    id UUID PRIMARY KEY,
    purchase_id UUID REFERENCES purchases(id) ON DELETE CASCADE,
    material_id UUID REFERENCES raw_materials(id),
    quantity INTEGER,
    unit_price DECIMAL
);

-- Production Orders table
CREATE TABLE production_orders (
    id UUID PRIMARY KEY,
    variant_id UUID REFERENCES variants(id),
    quantity INTEGER,
    status TEXT CHECK (status IN ('CREATED', 'IN_PROGRESS', 'COMPLETED')),
    start_date DATE,
    end_date DATE
);

-- Sales Orders table
CREATE TABLE sales_orders (
    id UUID PRIMARY KEY,
    customer_name TEXT NOT NULL,
    status TEXT CHECK (status IN ('PENDING', 'DELIVERED', 'CANCELLED')),
    order_date DATE,
    total_amount DECIMAL
);

-- Sales Items table
CREATE TABLE sales_items (
    id UUID PRIMARY KEY,
    sales_order_id UUID REFERENCES sales_orders(id) ON DELETE CASCADE,
    variant_id UUID REFERENCES variants(id),
    quantity INTEGER,
    unit_price DECIMAL
);


-- Optional: Material Usage tracking per Production Order
CREATE TABLE material_usage (
    id UUID PRIMARY KEY,
    production_order_id UUID REFERENCES production_orders(id) ON DELETE CASCADE,
    material_id UUID REFERENCES raw_materials(id),
    quantity_used INTEGER NOT NULL
);

-- Optional: Audit Logs (basic)
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    action TEXT NOT NULL,
    table_name TEXT NOT NULL,
    record_id UUID,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Product Images
CREATE TABLE product_images (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


