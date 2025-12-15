package bank.core;

import java.math.BigDecimal;
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
      this.sourceAccount.d

    }


  }

}
