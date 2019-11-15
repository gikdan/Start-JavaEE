package com.generic.exception;

/**
 * The com.generic.exception IncompleteRestUserCreationData.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Oct 10, 2017 6:56:36 AM
 */
public final class IncompleteRestUserCreationData extends Exception {

  private static final long serialVersionUID = 1L;

  private String message = "Incomplete user information for creating user";

  public IncompleteRestUserCreationData() {
    super();
  }

  public IncompleteRestUserCreationData(String message) {
    super(message);
    this.message = message;
  }

  public IncompleteRestUserCreationData(Throwable cause) {
    super(cause);
  }

  public IncompleteRestUserCreationData(String message, Throwable cause) {
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
