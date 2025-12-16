package bank.core;

import bank.exception.BankingSystemException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a bank account
 */
public class Account {

  private final String accountNumber;
  private BigDecimal balance;
  private final Person owner;
  private final boolean isSourceAccount;

  // for regular accounts
  public Account(String accountNumber, Person owner) {
    this(accountNumber, owner, false);
  }

  // this constructor allows creating source account
  public Account(String accountNumber, Person owner, boolean isSourceAccount) {
    if (accountNumber == null || accountNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Account number cannot be null or empty");
    }

    if (owner == null && !isSourceAccount) {
      throw new IllegalArgumentException("Account must have an owner");
    }

    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    this.isSourceAccount = isSourceAccount;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public Person getOwner() {
    return owner;
  }

  public boolean isSourceAccount() {
    return isSourceAccount;
  }

  // check account balance
  public String checkBalance() {
    return String.format("Account %s balance: $%.2f", accountNumber, balance);
  }

  // deposit money to account
  public void deposit(BigDecimal amount) throws BankingSystemException {
    validateAmount(amount);
    balance = balance.add(amount);
  }

  // withdraw money from account
  public void withdraw(BigDecimal amount) throws BankingSystemException {
    validateAmount(amount);

    if (balance.compareTo(amount) < 0) {
      throw new BankingSystemException(
          String.format("Insufficient funds. Balance: $%.2f, Requested: $%.2f", balance, amount));
    }

    balance = balance.subtract(amount);
  }

  /**
   * Transfer money to another account
   * @param targetAccount, amount
   * @throws BankingSystemException
   */
  public void transferTo(Account targetAccount, BigDecimal amount) throws BankingSystemException {
    if (targetAccount == null) {
      throw new BankingSystemException("Targer account cannot be null");
    }

    if (targetAccount.equals(this)) {
      throw new BankingSystemException("Cannot transfer to the same account");
    }

    validateAmount(amount);

    // withdraw from this account
    this.withdraw(amount);

    try {
      // deposit to target account
      targetAccount.deposit(amount);
    } catch (BankingSystemException e) {
      // rollback if deposit fails
      this.deposit(amount);
      throw new BankingSystemException("Transfer failed: " + e.getMessage());
    }
  }

  // validate amount format and value
  private void validateAmount(BigDecimal amount) throws BankingSystemException {
    if (amount == null) {
      throw new BankingSystemException("Amount cannot be null");
    }

    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new BankingSystemException("Amount cannot be negative: $" + amount);
    }

    if (amount.scale() > 2) {
      throw new BankingSystemException("Amount must have at most 2 decimal places: $" + amount);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Account account = (Account) o;
    return Objects.equals(accountNumber, account.accountNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber);
  }

  @Override
  public String toString() {
    String ownerInfo = isSourceAccount ? "SOURCE" : owner.getName();
    return String.format("Account{number='%s', balance=$%.2f, owner=%s}",
        accountNumber, balance, ownerInfo);
  }
}
