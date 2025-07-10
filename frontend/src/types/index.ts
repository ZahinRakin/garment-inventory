// Auth Types
export interface User {
  id: string;
  email: string;
  role: 'ADMIN' | 'STORE_MANAGER' | 'PRODUCTION_OFFICER' | 'SALES_OFFICER';
  firstName: string;
  lastName: string;
}

export interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
}

// Product Types
export interface Product {
  id: string;
  name: string;
  category: string;
  description?: string;
  createdAt: string;
  variants: Variant[];
}

export interface Variant {
  id: string;
  productId: string;
  size: string;
  color: string;
  fabric: string;
  quantity: number;
  sku: string;
  unitPrice: number;
  product?: Product;
}

// Raw Material Types
export interface RawMaterial {
  id: string;
  name: string;
  unit: string;
  currentStock: number;
  reorderLevel: number;
  category: string;
  unitPrice: number;
  supplierId?: string;
  supplier?: Supplier;
}

// Supplier Types
export interface Supplier {
  id: string;
  name: string;
  contactPerson: string;
  email: string;
  phone: string;
  address: string;
  purchases: Purchase[];
}

export interface Purchase {
  id: string;
  supplierId: string;
  status: 'PENDING' | 'DELIVERED' | 'CANCELLED';
  totalAmount: number;
  orderDate: string;
  deliveryDate?: string;
  supplier?: Supplier;
  purchaseItems: PurchaseItem[];
}

export interface PurchaseItem {
  id: string;
  purchaseId: string;
  materialId: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  material?: RawMaterial;
}

// Production Types
export interface ProductionOrder {
  id: string;
  variantId: string;
  quantity: number;
  status: 'CREATED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
  startDate: string;
  completionDate?: string;
  variant?: Variant;
  materialConsumption: MaterialConsumption[];
}

export interface MaterialConsumption {
  id: string;
  productionOrderId: string;
  materialId: string;
  quantityUsed: number;
  material?: RawMaterial;
}

// Sales Types
export interface SalesOrder {
  id: string;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  status: 'PENDING' | 'DELIVERED' | 'CANCELLED';
  totalAmount: number;
  orderDate: string;
  deliveryDate?: string;
  salesItems: SalesItem[];
}

export interface SalesItem {
  id: string;
  salesOrderId: string;
  variantId: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  variant?: Variant;
}

// Stock Types
export interface StockLocation {
  id: string;
  name: string;
  address: string;
  type: 'WAREHOUSE' | 'STORE' | 'PRODUCTION';
}

export interface StockAdjustment {
  id: string;
  variantId: string;
  locationId: string;
  adjustmentType: 'INCREASE' | 'DECREASE';
  quantity: number;
  reason: string;
  adjustedBy: string;
  adjustedAt: string;
}

// Common Types
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}