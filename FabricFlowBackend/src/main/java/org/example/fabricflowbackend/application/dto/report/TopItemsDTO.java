package org.example.fabricflowbackend.application.dto.report;


import java.util.List;
import java.util.Map;

public class TopItemsDTO {
    private String id;
    private String name;
    private int count;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}