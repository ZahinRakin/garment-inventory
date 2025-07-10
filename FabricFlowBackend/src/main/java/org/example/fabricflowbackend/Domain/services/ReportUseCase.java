package org.example.fabricflowbackend.Domain.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReportUseCase {
    // Stock reports
    Map<String, Object> getStockSummaryReport();
    Map<String, Object> getRawMaterialStockReport();
    Map<String, Object> getFinishedGoodsStockReport();
    List<Map<String, Object>> getLowStockReport();

    // Purchase reports
    Map<String, Object> getPurchaseReport(LocalDate startDate, LocalDate endDate);
    Map<String, Object> getPurchaseReportBySupplier(UUID supplierId, LocalDate startDate, LocalDate endDate);
    List<Map<String, Object>> getTopSuppliers(int limit);

    // Production reports
    Map<String, Object> getProductionReport(LocalDate startDate, LocalDate endDate);
    List<Map<String, Object>> getProductionOrdersByStatus();
    Map<String, Object> getProductionEfficiencyReport();

    // Sales reports
    Map<String, Object> getSalesReport(LocalDate startDate, LocalDate endDate);
    List<Map<String, Object>> getTopSellingProducts(int limit);
    List<Map<String, Object>> getTopCustomers(int limit);
    Map<String, Object> getSalesRevenueReport(LocalDate startDate, LocalDate endDate);

    // Financial reports
    BigDecimal getTotalInventoryValue();
    BigDecimal getTotalRawMaterialValue();
    BigDecimal getTotalFinishedGoodsValue();
    Map<String, Object> getProfitLossReport(LocalDate startDate, LocalDate endDate);

    // Alert reports
    List<String> getAllAlerts();
    List<String> getStockAlerts();
    List<String> getProductionAlerts();
    List<String> getPurchaseAlerts();
}
