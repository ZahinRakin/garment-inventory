package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.*;
import org.example.fabricflowbackend.Domain.repositories.*;
import org.example.fabricflowbackend.Domain.services.ReportUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService implements ReportUseCase {

    private final RawMaterialRepository rawMaterialRepository;
    private final VariantRepository variantRepository;
    private final PurchaseRepository purchaseRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final AlertRepository alertRepository;
    private final SalesItemRepository salesItemRepository;
    public ReportService(RawMaterialRepository rawMaterialRepository,
                         VariantRepository variantRepository,
                         PurchaseRepository purchaseRepository,
                         SalesOrderRepository salesOrderRepository,
                         ProductionOrderRepository productionOrderRepository,
                         AlertRepository alertRepository, SalesItemRepository salesItemRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.variantRepository = variantRepository;
        this.purchaseRepository = purchaseRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.productionOrderRepository = productionOrderRepository;
        this.alertRepository = alertRepository;
        this.salesItemRepository = salesItemRepository;
    }

    @Override
    public Map<String, Object> getStockSummaryReport() {
        Map<String, Object> report = new HashMap<>();

        // Raw materials
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        report.put("totalRawMaterials", rawMaterials.size());
        report.put("lowStockRawMaterials", rawMaterialRepository.findMaterialsNeedingReorder().size());

        // Finished goods
        List<Variant> variants = variantRepository.findAll();
        report.put("totalVariants", variants.size());
        report.put("lowStockVariants", variantRepository.findByQuantityLessThanEqual(10).size());

        return report;
    }

    @Override
    public Map<String, Object> getRawMaterialStockReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("materials", rawMaterialRepository.findAll());
        report.put("lowStockMaterials", rawMaterialRepository.findMaterialsNeedingReorder());
        return report;
    }

    @Override
    public Map<String, Object> getFinishedGoodsStockReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("variants", variantRepository.findAll());
        report.put("lowStockVariants", variantRepository.findByQuantityLessThanEqual(10));
        return report;
    }

    @Override
    public List<Map<String, Object>> getLowStockReport() {
        List<Map<String, Object>> report = new ArrayList<>();

        rawMaterialRepository.findMaterialsNeedingReorder().forEach(m -> {
            Map<String, Object> item = new HashMap<>();
            item.put("type", "RAW_MATERIAL");
            item.put("id", m.getId());
            item.put("name", m.getName());
            item.put("current", m.getCurrentStock());
            item.put("threshold", m.getReorderLevel());
            report.add(item);
        });

        variantRepository.findByQuantityLessThanEqual(10).forEach(v -> {
            Map<String, Object> item = new HashMap<>();
            item.put("type", "VARIANT");
            item.put("id", v.getId());
            item.put("sku", v.getSku());
            item.put("current", v.getQuantity());
            item.put("threshold", 10);
            report.add(item);
        });

        return report;
    }

    @Override
    public Map<String, Object> getPurchaseReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        List<Purchase> purchases = purchaseRepository.findByOrderDateBetween(startDate, endDate);
        report.put("purchases", purchases);
        report.put("count", purchases.size());
        return report;
    }

    @Override
    public Map<String, Object> getPurchaseReportBySupplier(UUID supplierId, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        List<Purchase> purchases = purchaseRepository.findBySupplierId(supplierId).stream()
                .filter(p -> !p.getOrderDate().isBefore(startDate) && !p.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());
        report.put("purchases", purchases);
        report.put("count", purchases.size());
        return report;
    }

    @Override
    public List<Map<String, Object>> getTopSuppliers(int limit) {
        return purchaseRepository.findAll().stream()
                .collect(Collectors.groupingBy(Purchase::getSupplierId, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("supplierId", e.getKey());
                    map.put("purchaseCount", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getProductionReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        List<ProductionOrder> orders = productionOrderRepository.findAll().stream()
                .filter(o -> o.getStartDate() != null &&
                        !o.getStartDate().isBefore(startDate) &&
                        !o.getStartDate().isAfter(endDate))
                .collect(Collectors.toList());
        report.put("productionOrders", orders);
        report.put("count", orders.size());
        return report;
    }

    @Override
    public List<Map<String, Object>> getProductionOrdersByStatus() {
        return productionOrderRepository.findAll().stream()
                .collect(Collectors.groupingBy(ProductionOrder::getStatus, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getProductionEfficiencyReport() {
        Map<String, Object> report = new HashMap<>();
        List<ProductionOrder> completedOrders = productionOrderRepository.findAll().stream()
                .filter(o -> "COMPLETED".equals(o.getStatus()))
                .collect(Collectors.toList());
        report.put("completedOrders", completedOrders.size());
        return report;
    }

    @Override
    public Map<String, Object> getSalesReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        List<SalesOrder> orders = salesOrderRepository.findByOrderDateBetween(startDate, endDate);
        report.put("salesOrders", orders);
        report.put("count", orders.size());
        return report;
    }

    @Override
    public List<Map<String, Object>> getTopSellingProducts(int limit) {
        return salesItemRepository.findAll().stream()
                .collect(Collectors.groupingBy(SalesItem::getVariantId, Collectors.summingInt(SalesItem::getQuantity)))
                .entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("variantId", e.getKey());
                    map.put("quantitySold", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTopCustomers(int limit) {
        return salesOrderRepository.findAll().stream()
                .collect(Collectors.groupingBy(SalesOrder::getCustomerName, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerName", e.getKey());
                    map.put("orderCount", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getSalesRevenueReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        List<SalesOrder> orders = salesOrderRepository.findByOrderDateBetween(startDate, endDate);
        report.put("totalRevenue", orders.stream()
                .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount().doubleValue() : 0)
                .sum());
        report.put("orderCount", orders.size());
        return report;
    }

    @Override
    public BigDecimal getTotalInventoryValue() {
        return BigDecimal.ZERO; // Not implemented in domain
    }

    @Override
    public BigDecimal getTotalRawMaterialValue() {
        return BigDecimal.ZERO; // Not implemented in domain
    }

    @Override
    public BigDecimal getTotalFinishedGoodsValue() {
        return BigDecimal.ZERO; // Not implemented in domain
    }

    @Override
    public Map<String, Object> getProfitLossReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        report.put("message", "Profit/Loss calculation not implemented");
        return report;
    }

    @Override
    public List<String> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(Alert::getMessage)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStockAlerts() {
        return alertRepository.findByType("LOW_STOCK").stream()
                .map(Alert::getMessage)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getProductionAlerts() {
        return alertRepository.findByType("PRODUCTION_ALERT").stream()
                .map(Alert::getMessage)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPurchaseAlerts() {
        return alertRepository.findByType("PURCHASE_ALERT").stream()
                .map(Alert::getMessage)
                .collect(Collectors.toList());
    }
}