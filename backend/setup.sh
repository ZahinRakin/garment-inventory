#!/bin/bash

# Base package path
BASE=src/main/java/com/garments/inventory

# Create directories
mkdir -p $BASE/{config,application/dto,infrastructure/{controller,entity,repository},domain/{model,port/{in,out}}}
mkdir -p src/main/resources

# Main class
touch $BASE/GarmentsInventoryApp.java

# Config classes
touch $BASE/config/{SecurityConfig.java,BeanConfig.java}

# Domain models
touch $BASE/domain/model/{Product.java,Variant.java,RawMaterial.java,Supplier.java,Purchase.java,PurchaseItem.java,ProductionOrder.java,SalesOrder.java,SalesItem.java}

# Port interfaces (domain/port/in and out)
touch $BASE/domain/port/in/{ManageProductUseCase.java,ManageInventoryUseCase.java,ManageSalesUseCase.java}
touch $BASE/domain/port/out/{ProductRepositoryPort.java,RawMaterialPersistencePort.java,SalesOrderPersistencePort.java}

# Application services
touch $BASE/application/{ProductService.java,VariantService.java,RawMaterialService.java,SupplierService.java,PurchaseService.java,ProductionService.java,SalesService.java}

# DTOs
touch $BASE/application/dto/{ProductDTO.java,VariantDTO.java,SalesOrderDTO.java,RawMaterialDTO.java}

# Controllers
touch $BASE/infrastructure/controller/{AuthController.java,ProductController.java,RawMaterialController.java,SupplierController.java,PurchaseController.java,ProductionController.java,SalesController.java,ReportController.java}

# Entity classes
touch $BASE/infrastructure/entity/{ProductEntity.java,VariantEntity.java,RawMaterialEntity.java,SupplierEntity.java,PurchaseEntity.java,PurchaseItemEntity.java,ProductionOrderEntity.java,SalesOrderEntity.java,SalesItemEntity.java}

# Repository interfaces
touch $BASE/infrastructure/repository/{ProductRepository.java,VariantRepository.java,RawMaterialRepository.java,SupplierRepository.java,PurchaseRepository.java,PurchaseItemRepository.java,ProductionOrderRepository.java,SalesOrderRepository.java,SalesItemRepository.java}

# Resource files
touch src/main/resources/{application.yml,schema.sql}
