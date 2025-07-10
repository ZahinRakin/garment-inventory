package org.example.fabricflowbackend.application.dto.report;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SalesReportDTO {
    private List<Map<String, Object>> salesOrders;
    private int count;
    private BigDecimal totalRevenue;

    // Getters and Setters
    public List<Map<String, Object>> getSalesOrders() {
        return salesOrders;
    }

    public void setSalesOrders(List<Map<String, Object>> salesOrders) {
        this.salesOrders = salesOrders;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}