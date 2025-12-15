package bank.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    if (owner==null && !isSourceAccount) {
      throw new IllegalArgumentException("Account must have an owner");
    }

    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    this.isSourceAccount=isSourceAccount;
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
  public void deposit(BigDecimal amount) throws BankingSystemException





}
