#!/bin/bash

mkdir -p src/main/java/com/garments/inventory/{config,domain,application/dto,infrastructure/{controller,entity,repository}}
mkdir -p src/main/resources

# Create main application file
touch src/main/java/com/garments/inventory/GarmentsInventoryApp.java

# Domain classes
touch src/main/java/com/garments/inventory/domain/{Product.java,Variant.java,RawMaterial.java,Supplier.java,Purchase.java,PurchaseItem.java,ProductionOrder.java,SalesOrder.java,SalesItem.java}

# Application services and DTOs
touch src/main/java/com/garments/inventory/application/{ProductService.java,VariantService.java,RawMaterialService.java,SupplierService.java,PurchaseService.java,ProductionService.java,SalesService.java}
touch src/main/java/com/garments/inventory/application/dto/{ProductDTO.java,VariantDTO.java,SalesOrderDTO.java,RawMaterialDTO.java}

# Controllers
touch src/main/java/com/garments/inventory/infrastructure/controller/{AuthController.java,ProductController.java,RawMaterialController.java,SupplierController.java,PurchaseController.java,ProductionController.java,SalesController.java,ReportController.java}

# Entity classes
touch src/main/java/com/garments/inventory/infrastructure/entity/{ProductEntity.java,VariantEntity.java,RawMaterialEntity.java,SupplierEntity.java,PurchaseEntity.java,PurchaseItemEntity.java,ProductionOrderEntity.java,SalesOrderEntity.java,SalesItemEntity.java}

# Repositories
touch src/main/java/com/garments/inventory/infrastructure/repository/{ProductRepository.java,VariantRepository.java,RawMaterialRepository.java,SupplierRepository.java,PurchaseRepository.java,PurchaseItemRepository.java,ProductionOrderRepository.java,SalesOrderRepository.java,SalesItemRepository.java}

# Config classes
touch src/main/java/com/garments/inventory/config/{SecurityConfig.java,BeanConfig.java}

# Resource files
touch src/main/resources/{application.yml,schema.sql}
