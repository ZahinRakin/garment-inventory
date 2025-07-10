package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.example.fabricflowbackend.Domain.entities.PurchaseItem;
import org.example.fabricflowbackend.Domain.exceptions.InvalidOperationException;
import org.example.fabricflowbackend.Domain.exceptions.RawMaterialNotFoundException;
import org.example.fabricflowbackend.Domain.exceptions.SupplierNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.PurchaseItemRepository;
import org.example.fabricflowbackend.Domain.repositories.PurchaseRepository;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.repositories.SupplierRepository;
import org.example.fabricflowbackend.Domain.services.PurchaseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PurchaseService implements PurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final SupplierRepository supplierRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final RawMaterialService rawMaterialService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,
                               PurchaseItemRepository purchaseItemRepository,
                               SupplierRepository supplierRepository,
                               RawMaterialRepository rawMaterialRepository,
                               RawMaterialService rawMaterialService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.supplierRepository = supplierRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.rawMaterialService = rawMaterialService;
    }

    @Override
    public Purchase createPurchase(Purchase purchase) {
        if (!supplierRepository.existsById(purchase.getSupplierId())) {
            throw new SupplierNotFoundException(purchase.getSupplierId());
        }

        if (purchase.getOrderDate() == null) {
            purchase.setOrderDate(LocalDate.now());
        }

        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase updatePurchase(Purchase purchase) {
        if (!purchaseRepository.existsById(purchase.getId())) {
            throw new InvalidOperationException("Purchase not found with id: " + purchase.getId());
        }

        purchase.calculateTotalAmount();
        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseById(UUID id) {
        return purchaseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesBySupplier(UUID supplierId) {
        return purchaseRepository.findBySupplierId(supplierId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByStatus(String status) {
        return purchaseRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByDateRange(LocalDate startDate, LocalDate endDate) {
        return purchaseRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public void deletePurchase(UUID id) {
        if (!purchaseRepository.existsById(id)) {
            throw new InvalidOperationException("Purchase not found with id: " + id);
        }

        Purchase purchase = purchaseRepository.findById(id).get();
        if (purchase.isCompleted()) {
            throw new InvalidOperationException("Cannot delete completed purchase");
        }

        purchaseRepository.deleteById(id);
    }

    @Override
    public PurchaseItem addPurchaseItem(UUID purchaseId, PurchaseItem item) {
        if (!purchaseRepository.existsById(purchaseId)) {
            throw new InvalidOperationException("Purchase not found with id: " + purchaseId);
        }

        if (!rawMaterialRepository.existsById(item.getMaterialId())) {
            throw new RawMaterialNotFoundException(item.getMaterialId());
        }

        item.setPurchaseId(purchaseId);
        PurchaseItem savedItem = purchaseItemRepository.save(item);

        // Update purchase total
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        purchase.calculateTotalAmount();
        purchaseRepository.save(purchase);

        return savedItem;
    }

    @Override
    public void removePurchaseItem(UUID itemId) {
        Optional<PurchaseItem> item = purchaseItemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new InvalidOperationException("Purchase item not found with id: " + itemId);
        }

        UUID purchaseId = item.get().getPurchaseId();
        purchaseItemRepository.deleteById(itemId);

        // Update purchase total
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        purchase.calculateTotalAmount();
        purchaseRepository.save(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseItem> getPurchaseItems(UUID purchaseId) {
        return purchaseItemRepository.findByPurchaseId(purchaseId);
    }

    @Override
    public void markPurchaseAsReceived(UUID purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new InvalidOperationException("Purchase not found with id: " + purchaseId));

        if (purchase.isCompleted()) {
            throw new InvalidOperationException("Purchase is already received");
        }

        purchase.markAsReceived();
        purchaseRepository.save(purchase);
    }

    @Override
    public void processPurchaseReceipt(UUID purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new InvalidOperationException("Purchase not found with id: " + purchaseId));

        if (purchase.isCompleted()) {
            throw new InvalidOperationException("Purchase is already processed");
        }

        List<PurchaseItem> items = purchaseItemRepository.findByPurchaseId(purchaseId);

        // Update raw material stock
        for (PurchaseItem item : items) {
            rawMaterialService.addStock(item.getMaterialId(), item.getQuantity());
        }

        purchase.markAsReceived();
        purchaseRepository.save(purchase);
    }
}