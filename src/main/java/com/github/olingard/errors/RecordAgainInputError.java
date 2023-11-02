package com.github.olingard.errors;

public class RecordAgainInputError extends RuntimeException {
  public RecordAgainInputError(String message) {
    super(message);
  }

  public RecordAgainInputError(String message, Throwable cause) {
    super(message, cause);
  }
}
