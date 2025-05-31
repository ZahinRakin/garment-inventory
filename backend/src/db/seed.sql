-- Insert sample users
INSERT INTO users (id, username, email, password_hash, role) VALUES
  (gen_random_uuid(), 'admin', 'admin@example.com', 'hashed_password1', 'ADMIN'),
  (gen_random_uuid(), 'manager', 'manager@example.com', 'hashed_password2', 'STORE_MANAGER');

-- Insert sample products
INSERT INTO products (id, name, category, description) VALUES
  (gen_random_uuid(), 'T-Shirt', 'Topwear', 'Cotton T-shirt'),
  (gen_random_uuid(), 'Jeans', 'Bottomwear', 'Denim jeans');

-- Insert sample variants
INSERT INTO variants (id, product_id, size, color, fabric, quantity, sku) VALUES
  (gen_random_uuid(), (SELECT id FROM products LIMIT 1), 'M', 'Red', 'Cotton', 100, 'TSHIRT-M-RED'),
  (gen_random_uuid(), (SELECT id FROM products OFFSET 1 LIMIT 1), '32', 'Blue', 'Denim', 50, 'JEANS-32-BLUE');

-- Insert sample raw materials
INSERT INTO raw_materials (id, name, unit, current_stock, reorder_level, category) VALUES
  (gen_random_uuid(), 'Cotton Fabric', 'meter', 500, 100, 'Fabric'),
  (gen_random_uuid(), 'Denim Fabric', 'meter', 300, 50, 'Fabric');

-- Insert sample suppliers
INSERT INTO suppliers (id, name, email, phone, address) VALUES
  (gen_random_uuid(), 'ABC Textiles', 'abc@textiles.com', '1234567890', 'Dhaka, Bangladesh'),
  (gen_random_uuid(), 'XYZ Fabrics', 'xyz@fabrics.com', '0987654321', 'Chittagong, Bangladesh');

-- Insert sample purchases
INSERT INTO purchases (id, supplier_id, order_date, status, total_amount) VALUES
  (gen_random_uuid(), (SELECT id FROM suppliers LIMIT 1), '2024-05-01', 'RECEIVED', 10000),
  (gen_random_uuid(), (SELECT id FROM suppliers OFFSET 1 LIMIT 1), '2024-05-10', 'CREATED', 5000);

-- Insert sample purchase_items
INSERT INTO purchase_items (id, purchase_id, material_id, quantity, unit_price) VALUES
  (gen_random_uuid(), (SELECT id FROM purchases LIMIT 1), (SELECT id FROM raw_materials LIMIT 1), 100, 50),
  (gen_random_uuid(), (SELECT id FROM purchases OFFSET 1 LIMIT 1), (SELECT id FROM raw_materials OFFSET 1 LIMIT 1), 50, 100);

-- Insert sample production_orders
INSERT INTO production_orders (id, variant_id, quantity, status, start_date, end_date) VALUES
  (gen_random_uuid(), (SELECT id FROM variants LIMIT 1), 100, 'IN_PROGRESS', '2024-05-15', NULL),
  (gen_random_uuid(), (SELECT id FROM variants OFFSET 1 LIMIT 1), 50, 'CREATED', '2024-05-20', NULL);

-- Insert sample sales_orders
INSERT INTO sales_orders (id, customer_name, status, order_date, total_amount) VALUES
  (gen_random_uuid(), 'John Doe', 'PENDING', '2024-05-25', 2000),
  (gen_random_uuid(), 'Jane Smith', 'DELIVERED', '2024-05-28', 3500);

-- Insert sample sales_items
INSERT INTO sales_items (id, sales_order_id, variant_id, quantity, unit_price) VALUES
  (gen_random_uuid(), (SELECT id FROM sales_orders LIMIT 1), (SELECT id FROM variants LIMIT 1), 2, 500),
  (gen_random_uuid(), (SELECT id FROM sales_orders OFFSET 1 LIMIT 1), (SELECT id FROM variants OFFSET 1 LIMIT 1), 1, 700);

-- Insert sample material_usage
INSERT INTO material_usage (id, production_order_id, material_id, quantity_used) VALUES
  (gen_random_uuid(), (SELECT id FROM production_orders LIMIT 1), (SELECT id FROM raw_materials LIMIT 1), 50),
  (gen_random_uuid(), (SELECT id FROM production_orders OFFSET 1 LIMIT 1), (SELECT id FROM raw_materials OFFSET 1 LIMIT 1), 30);

-- Insert sample audit_logs
INSERT INTO audit_logs (id, user_id, action, table_name, record_id) VALUES
  (gen_random_uuid(), (SELECT id FROM users LIMIT 1), 'INSERT', 'products', (SELECT id FROM products LIMIT 1)),
  (gen_random_uuid(), (SELECT id FROM users OFFSET 1 LIMIT 1), 'UPDATE', 'variants', (SELECT id FROM variants LIMIT 1));

-- Insert sample product_images
INSERT INTO product_images (id, product_id, image_url) VALUES
  (gen_random_uuid(), (SELECT id FROM products LIMIT 1), 'https://example.com/images/tshirt.jpg'),
  (gen_random_uuid(), (SELECT id FROM products OFFSET 1 LIMIT 1), 'https://example.com/images/jeans.jpg');