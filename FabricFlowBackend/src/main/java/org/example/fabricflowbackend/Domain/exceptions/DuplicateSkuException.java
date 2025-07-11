package org.example.fabricflowbackend.Domain.exceptions;

public class DuplicateSkuException extends RuntimeException {
  private final String sku;

  public DuplicateSkuException(String sku) {
    super(String.format("SKU %s already exists", sku));
    this.sku = sku;
  }

  public String getSku() { return sku; }
}
