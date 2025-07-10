package org.example.fabricflowbackend.Domain.exceptions;

public class InvalidOperationException extends RuntimeException {
  public InvalidOperationException(String message) {
    super(message);
  }

  public InvalidOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}