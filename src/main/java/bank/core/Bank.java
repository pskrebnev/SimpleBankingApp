package bank.core;

import bank.exception.BankingSystemException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {

  private static final String SOURCE_ACCOUNT_NUMBER = "SOURCE-001";
  private static final BigDecimal INFINITE_BALANCE = new BigDecimal("999999999.00");

  private final Map<String, Person> persons;
  private final Map<String, Account> accounts;
  private final Account sourceAccount;
  private final AtomicInteger accountCounter;
  private final AtomicInteger personCounter;

  public Bank() {
    this.persons = new HashMap<>();
    this.accounts = new HashMap<>();
    this.accountCounter = new AtomicInteger(1);
    this.personCounter = new AtomicInteger(1);

    // create the special SOURCE account with unlimited funds
    this.sourceAccount = new Account(SOURCE_ACCOUNT_NUMBER, null, true);
    try {
      this.sourceAccount.deposit(INFINITE_BALANCE);
    } catch (BankingSystemException e) {
      // hope never be happen
      throw new RuntimeException("Failed to initialize source account", e);
    }

    this.accounts.put(SOURCE_ACCOUNT_NUMBER, sourceAccount);
  }

  // create a new person
  public Person createPerson(String name) throws BankingSystemException {
    if (name == null || name.trim().isEmpty()) {
      throw new BankingSystemException("Person name cannot be empty");
    }

    String personId = "P" + String.format("%04d", personCounter.getAndIncrement());
    Person person = new Person(personId, name);
    persons.put(personId, person);

    System.out.println("+ Created person: " + person);
    return person;
  }

  // create a new account for a person
  public Account createAccount(Person person) throws BankingSystemException {
    if (person == null) {
      throw new BankingSystemException("Person cannot be null");
    }

    if (!persons.containsKey(person.getId())) {
      throw new BankingSystemException("Person not found in bank system");
    }

    String accountNumber = "ACC" + String.format("%06d", accountCounter.getAndIncrement());
    Account account = new Account(accountNumber, person);

    accounts.put(accountNumber, account);
    person.addAccount(account);

    System.out.println("+ Created account: " + account);
    return account;
  }

  // get account by account number
  public Account getAccount(String accountNumber) throws BankingSystemException {
    Account account = accounts.get(accountNumber);
    if (account == null) {
      throw new BankingSystemException("Account not found: " + accountNumber);
    }
    return account;
  }

  // get person by ID
  public Person getPerson(String personId) throws BankingSystemException {
    Person person = persons.get(personId);
    if (person == null) {
      throw new BankingSystemException("Person not found: " + personId);
    }
    return person;
  }

  // check balance of an account
  public BigDecimal checkBalance(String accountNumber) throws BankingSystemException {
    Account account = getAccount(accountNumber);
    return account.getBalance();
  }

  // add money to account from SOURCE account
  public void addMoneyFromSource(String accountNumber, double amount)
      throws BankingSystemException {
    addMoneyFromSource(accountNumber, BigDecimal.valueOf(amount)
        .setScale(2, RoundingMode.HALF_UP));
  }

  // add money to account from SOURCE account
  public void addMoneyFromSource(String accountNumber, BigDecimal amount)
      throws BankingSystemException {
    Account targetAccount = getAccount(accountNumber);
    sourceAccount.transferTo(targetAccount, amount);
    System.out.printf("+ Added $%.2f to account %s from SOURCE\n", amount, accountNumber);
  }

  // withdraw money from account
  public void withdrawMoney(String accountNumber, double amount) throws BankingSystemException {
    withdrawMoney(accountNumber, BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP));
  }

  // withdraw money from account
  public void withdrawMoney(String accountNumber, BigDecimal amount) throws BankingSystemException {
    Account account = getAccount(accountNumber);
    account.withdraw(amount);
    System.out.printf("+ Withdrew $%.2f from account %s\\n", amount, accountNumber);
  }

  // deposit money to account
  public void depositMoney(String accountNumber, BigDecimal amount) throws BankingSystemException {
    Account account = getAccount(accountNumber);
    account.deposit(amount);
    System.out.printf("+ Deposited $%.2f to account %s\n", amount, accountNumber);
  }

  // transfer money between accounts
  public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount)
      throws BankingSystemException {
    transferMoney(fromAccountNumber, toAccountNumber,
        BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP));
  }

  // transfer money between accounts
  public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount)
      throws BankingSystemException {
    Account fromAccount = getAccount(fromAccountNumber);
    Account toAccount = getAccount(toAccountNumber);

    fromAccount.transferTo(toAccount, amount);
    System.out.printf("+ Transferred $%.2f from %s to %s\n", amount, fromAccountNumber,
        toAccountNumber);
  }

  


}
