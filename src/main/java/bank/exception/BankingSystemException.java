package bank.exception;

/**
 * Custom exception for banking system errors
 */
public class BankingSystemException extends Exception {

  public BankingSystemException(String message) {
    super(message);
  }

  public BankingSystemException(String message, Throwable cause) {
    super(message, cause);
  }
}
