package core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bank.core.Account;
import bank.core.Bank;
import bank.core.Person;
import java.math.BigDecimal;

public class BankingSystemTest {

  private static int testsPassed = 0;
  private static int testsFailed = 0;

  public static void main(String[] args) {
    System.out.println("╔════════════════════════════════════════════════════════════╗");
    System.out.println("║          BANKING SYSTEM UNIT TESTS                         ║");
    System.out.println("╚════════════════════════════════════════════════════════════╝\n");



  }

  private static void testPersonCreation() {
    System.out.println("Test 1: Person Creation");
    System.out.println("─".repeat(60));
    try {
      Bank bank = new Bank();
      Person person = bank.createPerson("Test User");

      assertTrue(person != null, "Person should be created");
      assertTrue(person.getId() != null, "Person ID should be assigned");
      assertTrue(person.getName().equals("Test User"), "Person name should match");

      System.out.println("+ PASSED\n");
    } catch (Exception e) {
      System.out.println("- FAILED: " + e.getMessage() + "\n");
      testsFailed++;
    }
  }

  private static void testAccountCreation() {
    System.out.println("Test 2: Account Creation");
    System.out.println("─".repeat(60));
    try {
      Bank bank = new Bank();
      Person person = bank.createPerson("Test User");
      Account account = bank.createAccount(person);

      assertTrue(account != null, "Account should be created");
      assertTrue(account.getAccountNumber() != null, "Account number should be assigned");
      assertTrue(account.getBalance().equals(BigDecimal.ZERO.setScale(2)),
          "Initial balance should be 0.00");
      assertTrue(account.getOwner().equals(person), "Account owner should match");

      System.out.println("+ PASSED\n");
    } catch (Exception e) {
      System.out.println("- FAILED: " + e.getMessage() + "\n");
      testsFailed++;
    }
  }

  private static void testDeposit() {
    System.out.println("Test 3: Deposit Money");
    System.out.println("─".repeat(60));
    try {
      Bank bank = new Bank();
      Person person = bank.createPerson("Test User");
      Account account = bank.createAccount(person);

      bank.addMoneyFromSource(account.getAccountNumber(), 100.00);
      BigDecimal balance = bank.checkBalance(account.getAccountNumber());

      assertTrue(balance.compareTo(new BigDecimal("100.00")) == 0,
          "Balance should be 100.00");

      System.out.println("+ PASSED\n");
    } catch (Exception e) {
      System.out.println("- FAILED: " + e.getMessage() + "\n");
      testsFailed++;
    }
  }





}
