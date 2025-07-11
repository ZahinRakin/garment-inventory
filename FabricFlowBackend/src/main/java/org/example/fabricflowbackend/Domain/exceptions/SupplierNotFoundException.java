package org.example.fabricflowbackend.Domain.exceptions;

import java.util.UUID;

public class SupplierNotFoundException extends RuntimeException {
  private final UUID supplierId;

  public SupplierNotFoundException(UUID supplierId) {
    super(String.format("Supplier with ID %s not found", supplierId));
    this.supplierId = supplierId;
  }

  public UUID getSupplierId() { return supplierId; }
}
