package org.example.fabricflowbackend.application.dto.report;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class StockSummaryReportDTO {
    private int totalRawMaterials;
    private int lowStockRawMaterials;
    private int totalVariants;
    private int lowStockVariants;

    // Getters and Setters
    public int getTotalRawMaterials() {
        return totalRawMaterials;
    }

    public void setTotalRawMaterials(int totalRawMaterials) {
        this.totalRawMaterials = totalRawMaterials;
    }

    public int getLowStockRawMaterials() {
        return lowStockRawMaterials;
    }

    public void setLowStockRawMaterials(int lowStockRawMaterials) {
        this.lowStockRawMaterials = lowStockRawMaterials;
    }

    public int getTotalVariants() {
        return totalVariants;
    }

    public void setTotalVariants(int totalVariants) {
        this.totalVariants = totalVariants;
    }

    public int getLowStockVariants() {
        return lowStockVariants;
    }

    public void setLowStockVariants(int lowStockVariants) {
        this.lowStockVariants = lowStockVariants;
    }
}