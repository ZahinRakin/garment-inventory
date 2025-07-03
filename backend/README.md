the folder structure:
garments-inventory/
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── garments/
        │           └── inventory/
        │               ├── GarmentsInventoryApp.java
        │               ├── config/
        │               │   ├── SecurityConfig.java
        │               │   └── BeanConfig.java
        │               ├── domain/
        │               │   ├── model/
        │               │   │   ├── Product.java
        │               │   │   ├── Variant.java
        │               │   │   ├── RawMaterial.java
        │               │   │   ├── Supplier.java
        │               │   │   ├── Purchase.java
        │               │   │   ├── PurchaseItem.java
        │               │   │   ├── ProductionOrder.java
        │               │   │   ├── SalesOrder.java
        │               │   │   └── SalesItem.java
        │               │   └── port/
        │               │       ├── in/
        │               │       │   ├── ManageProductUseCase.java
        │               │       │   ├── ManageInventoryUseCase.java
        │               │       │   └── ManageSalesUseCase.java
        │               │       └── out/
        │               │           ├── ProductRepositoryPort.java
        │               │           ├── RawMaterialPersistencePort.java
        │               │           └── SalesOrderPersistencePort.java
        │               ├── application/
        │               │   ├── ProductService.java
        │               │   ├── VariantService.java
        │               │   ├── RawMaterialService.java
        │               │   ├── SupplierService.java
        │               │   ├── PurchaseService.java
        │               │   ├── ProductionService.java
        │               │   ├── SalesService.java
        │               │   └── dto/
        │               │       ├── ProductDTO.java
        │               │       ├── VariantDTO.java
        │               │       ├── SalesOrderDTO.java
        │               │       └── RawMaterialDTO.java
        │               ├── infrastructure/
        │               │   ├── controller/
        │               │   │   ├── AuthController.java
        │               │   │   ├── ProductController.java
        │               │   │   ├── RawMaterialController.java
        │               │   │   ├── SupplierController.java
        │               │   │   ├── PurchaseController.java
        │               │   │   ├── ProductionController.java
        │               │   │   ├── SalesController.java
        │               │   │   └── ReportController.java
        │               │   ├── entity/
        │               │   │   ├── ProductEntity.java
        │               │   │   ├── VariantEntity.java
        │               │   │   ├── RawMaterialEntity.java
        │               │   │   ├── SupplierEntity.java
        │               │   │   ├── PurchaseEntity.java
        │               │   │   ├── PurchaseItemEntity.java
        │               │   │   ├── ProductionOrderEntity.java
        │               │   │   ├── SalesOrderEntity.java
        │               │   │   └── SalesItemEntity.java
        │               │   └── repository/
        │               │       ├── ProductRepository.java
        │               │       ├── VariantRepository.java
        │               │       ├── RawMaterialRepository.java
        │               │       ├── SupplierRepository.java
        │               │       ├── PurchaseRepository.java
        │               │       ├── PurchaseItemRepository.java
        │               │       ├── ProductionOrderRepository.java
        │               │       ├── SalesOrderRepository.java
        │               │       └── SalesItemRepository.java
        └── resources/
            ├── application.yml
            └── schema.sql
