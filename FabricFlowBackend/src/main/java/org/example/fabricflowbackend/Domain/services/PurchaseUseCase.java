package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.example.fabricflowbackend.Domain.entities.PurchaseItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseUseCase {
    Purchase createPurchase(Purchase purchase);
    Purchase updatePurchase(Purchase purchase);
    Optional<Purchase> getPurchaseById(UUID id);
    List<Purchase> getAllPurchases();
    List<Purchase> getPurchasesBySupplier(UUID supplierId);
    List<Purchase> getPurchasesByStatus(String status);
    List<Purchase> getPurchasesByDateRange(LocalDate startDate, LocalDate endDate);
    void deletePurchase(UUID id);

    // Purchase item operations
    PurchaseItem addPurchaseItem(UUID purchaseId, PurchaseItem item);
    void removePurchaseItem(UUID itemId);
    List<PurchaseItem> getPurchaseItems(UUID purchaseId);

    // Business operations
    void markPurchaseAsReceived(UUID purchaseId);
    void processPurchaseReceipt(UUID purchaseId);
}