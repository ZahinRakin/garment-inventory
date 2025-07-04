package com.garments.inventory.application;

import com.garments.inventory.application.dto.SalesOrderDTO;
import com.garments.inventory.application.dto.SalesItemDTO;
import com.garments.inventory.domain.entities.SalesOrder;
import com.garments.inventory.domain.entities.SalesItem;
import com.garments.inventory.domain.entities.Variant;
import com.garments.inventory.domain.repositories.SalesOrderRepository;
import com.garments.inventory.domain.repositories.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private VariantRepository variantRepository;

    public SalesOrderDTO createSalesOrder(SalesOrderDTO salesOrderDTO) {
        // Calculate total amount
        BigDecimal totalAmount = salesOrderDTO.getSalesItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SalesOrder salesOrder = new SalesOrder(salesOrderDTO.getCustomerName(), "PENDING",
                LocalDate.now(), totalAmount);
        SalesOrder savedSalesOrder = salesOrderRepository.save(salesOrder);

        // Create sales items and update stock
        List<SalesItem> salesItems = salesOrderDTO.getSalesItems().stream()
                .map(itemDTO -> {
                    SalesItem item = new SalesItem(savedSalesOrder.getId(), itemDTO.getVariantId(),
                            itemDTO.getQuantity(), itemDTO.getUnitPrice());
                    // Update variant stock
                    updateVariantStock(itemDTO.getVariantId(), itemDTO.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        savedSalesOrder.setSalesItems(salesItems);
        return mapToDTO(savedSalesOrder);
    }

    public Optional<SalesOrderDTO> getSalesOrderById(UUID id) {
        return salesOrderRepository.findById(id).map(this::mapToDTO);
    }

    public List<SalesOrderDTO> getAllSalesOrders() {
        return salesOrderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SalesOrderDTO updateOrderStatus(UUID id, String status) {
        Optional<SalesOrder> existingOrder = salesOrderRepository.findById(id);
        if (existingOrder.isPresent()) {
            SalesOrder order = existingOrder.get();
            order.setStatus(status);
            SalesOrder savedOrder = salesOrderRepository.save(order);
            return mapToDTO(savedOrder);
        }
        throw new RuntimeException("Sales order not found with id: " + id);
    }

    private void updateVariantStock(UUID variantId, int quantity) {
        Optional<Variant> variant = variantRepository.findById(variantId);
        if (variant.isPresent()) {
            Variant v = variant.get();
            if (v.getQuantity() >= quantity) {
                v.setQuantity(v.getQuantity() - quantity);
                variantRepository.save(v);
            } else {
                throw new RuntimeException("Insufficient stock for variant: " + variantId);
            }
        }
    }

    private SalesOrderDTO mapToDTO(SalesOrder salesOrder) {
        SalesOrderDTO dto = new SalesOrderDTO(salesOrder.getId(), salesOrder.getCustomerName(),
                salesOrder.getStatus(), salesOrder.getOrderDate(),
                salesOrder.getTotalAmount());
        if (salesOrder.getSalesItems() != null) {
            dto.setSalesItems(salesOrder.getSalesItems().stream()
                    .map(this::mapSalesItemToDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private SalesItemDTO mapSalesItemToDTO(SalesItem salesItem) {
        return new SalesItemDTO(salesItem.getId(), salesItem.getSalesOrderId(),
                salesItem.getVariantId(), salesItem.getQuantity(),
                salesItem.getUnitPrice());
    }
}
