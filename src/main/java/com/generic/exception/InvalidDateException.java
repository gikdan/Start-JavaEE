package com.generic.exception;

public final class InvalidDateException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message = "Invalid date!!";

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidDateException(Throwable cause) {
        super(cause);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
