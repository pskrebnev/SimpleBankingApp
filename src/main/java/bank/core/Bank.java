package bank.core;

import java.math.BigDecimal;

public class Bank {
  private static final String SOURCE_ACCOUNT_NUMBER = "SOURCE-001";
  private static final BigDecimal INFINITE_BALANCE = new BigDecimal("999999999.00");

  private final Map<String, Person> persons;
  private final Map<String, Account> accounts;
  private final Account sourceAccount;
  private final AtomicInteger accountCounter;
  private final AtomicInteger personCounter;

}
