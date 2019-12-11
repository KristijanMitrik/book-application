package com.example.books_application.exception;

public class ValidationExceptions extends RuntimeException {

  public enum Kind {
    EMAIL_ERROR,
    PASSWORD_ERROR,
    EMAIL_PASSWORD_ERROR
  }

  private final Kind kind;

  private ValidationExceptions(Kind kind) {
    this.kind = kind;
  }

  private ValidationExceptions(Kind kind, String message) {
    super(message);
    this.kind = kind;
  }

  private ValidationExceptions(Kind kind, Throwable cause) {
    super(cause);
    this.kind = kind;
  }

  private ValidationExceptions(Kind kind, String message, Throwable cause) {
    super(message, cause);
    this.kind = kind;
  }

  public Kind getKind() {
    return kind;
  }

  public static class Builder {

    private ValidationExceptions.Kind kind ;
    private String message;
    private Throwable cause;

    public Builder setKind(ValidationExceptions.Kind kind) {
      this.kind = kind;
      return this;
    }

    public Builder setMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder setCause(Throwable cause) {
      this.cause = cause;
      return this;
    }

    public ValidationExceptions build() {
      if (message == null && cause == null) {
        return new ValidationExceptions(kind);
      } else if (message == null) {
        return new ValidationExceptions(kind, cause);
      } else if (cause == null) {
        return new ValidationExceptions(kind, message);
      } else{
        return new ValidationExceptions(kind, message, cause);
      }
    }
  }

}
