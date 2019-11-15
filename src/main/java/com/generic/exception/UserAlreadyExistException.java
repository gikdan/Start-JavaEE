package com.generic.exception;

public final class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;
    private String message = "Invalid user details!";

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
        this.message = message;
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
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
