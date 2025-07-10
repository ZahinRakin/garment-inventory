package org.example.fabricflowbackend.application.dto.report;


import java.time.LocalDate;

public class DateRangeRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}