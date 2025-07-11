package org.example.fabricflowbackend.infrastructure.controllers;

import org.example.fabricflowbackend.application.ReportService;
import org.example.fabricflowbackend.application.dto.report.LowStockItemDTO;
import org.example.fabricflowbackend.application.dto.report.SalesReportDTO;
import org.example.fabricflowbackend.application.dto.report.StockSummaryReportDTO;
import org.example.fabricflowbackend.application.dto.report.TopItemsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stock-summary")
    public ResponseEntity<StockSummaryReportDTO> getStockSummaryReport() {
        Map<String, Object> report = reportService.getStockSummaryReport();
        StockSummaryReportDTO dto = new StockSummaryReportDTO();
        dto.setTotalRawMaterials((Integer) report.get("totalRawMaterials"));
        dto.setLowStockRawMaterials((Integer) report.get("lowStockRawMaterials"));
        dto.setTotalVariants((Integer) report.get("totalVariants"));
        dto.setLowStockVariants((Integer) report.get("lowStockVariants"));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/raw-material-stock")
    public ResponseEntity<Map<String, Object>> getRawMaterialStockReport() {
        return ResponseEntity.ok(reportService.getRawMaterialStockReport());
    }

    @GetMapping("/finished-goods-stock")
    public ResponseEntity<Map<String, Object>> getFinishedGoodsStockReport() {
        return ResponseEntity.ok(reportService.getFinishedGoodsStockReport());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockItemDTO>> getLowStockReport() {
        List<Map<String, Object>> report = reportService.getLowStockReport();
        List<LowStockItemDTO> dtos = report.stream()
                .map(item -> {
                    LowStockItemDTO dto = new LowStockItemDTO();
                    dto.setType((String) item.get("type"));
                    dto.setId(item.get("id").toString());
                    dto.setName(item.containsKey("name") ? (String) item.get("name") : (String) item.get("sku"));
                    dto.setCurrent((Integer) item.get("current"));
                    dto.setThreshold((Integer) item.get("threshold"));
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/purchases")
    public ResponseEntity<Map<String, Object>> getPurchaseReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getPurchaseReport(startDate, endDate));
    }

    @GetMapping("/purchases/supplier")
    public ResponseEntity<Map<String, Object>> getPurchaseReportBySupplier(
            @RequestParam UUID supplierId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getPurchaseReportBySupplier(supplierId, startDate, endDate));
    }

    @GetMapping("/top-suppliers")
    public ResponseEntity<List<Map<String, Object>>> getTopSuppliers(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(reportService.getTopSuppliers(limit));
    }

    @GetMapping("/production")
    public ResponseEntity<Map<String, Object>> getProductionReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getProductionReport(startDate, endDate));
    }

    @GetMapping("/production/status")
    public ResponseEntity<List<Map<String, Object>>> getProductionOrdersByStatus() {
        return ResponseEntity.ok(reportService.getProductionOrdersByStatus());
    }

    @GetMapping("/production/efficiency")
    public ResponseEntity<Map<String, Object>> getProductionEfficiencyReport() {
        return ResponseEntity.ok(reportService.getProductionEfficiencyReport());
    }

    @GetMapping("/sales")
    public ResponseEntity<SalesReportDTO> getSalesReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        Map<String, Object> report = reportService.getSalesReport(startDate, endDate);
        SalesReportDTO dto = new SalesReportDTO();
        dto.setSalesOrders((List<Map<String, Object>>) report.get("salesOrders"));
        dto.setCount((Integer) report.get("count"));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/sales/top-products")
    public ResponseEntity<List<TopItemsDTO>> getTopSellingProducts(@RequestParam(defaultValue = "5") int limit) {
        List<Map<String, Object>> report = reportService.getTopSellingProducts(limit);
        List<TopItemsDTO> dtos = report.stream()
                .map(item -> {
                    TopItemsDTO dto = new TopItemsDTO();
                    dto.setId(item.get("variantId").toString());
                    dto.setCount((Integer) item.get("quantitySold"));
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/sales/top-customers")
    public ResponseEntity<List<TopItemsDTO>> getTopCustomers(@RequestParam(defaultValue = "5") int limit) {
        List<Map<String, Object>> report = reportService.getTopCustomers(limit);
        List<TopItemsDTO> dtos = report.stream()
                .map(item -> {
                    TopItemsDTO dto = new TopItemsDTO();
                    dto.setName((String) item.get("customerName"));
                    dto.setCount(((Long) item.get("orderCount")).intValue());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/sales/revenue")
    public ResponseEntity<Map<String, Object>> getSalesRevenueReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getSalesRevenueReport(startDate, endDate));
    }

    @GetMapping("/inventory/value")
    public ResponseEntity<BigDecimal> getTotalInventoryValue() {
        return ResponseEntity.ok(reportService.getTotalInventoryValue());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<String>> getAllAlerts() {
        return ResponseEntity.ok(reportService.getAllAlerts());
    }

    @GetMapping("/alerts/stock")
    public ResponseEntity<List<String>> getStockAlerts() {
        return ResponseEntity.ok(reportService.getStockAlerts());
    }

    @GetMapping("/alerts/production")
    public ResponseEntity<List<String>> getProductionAlerts() {
        return ResponseEntity.ok(reportService.getProductionAlerts());
    }

    @GetMapping("/alerts/purchase")
    public ResponseEntity<List<String>> getPurchaseAlerts() {
        return ResponseEntity.ok(reportService.getPurchaseAlerts());
    }
}