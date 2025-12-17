package bank;

import bank.core.Account;
import bank.core.Bank;
import bank.core.Person;
import bank.exception.BankingSystemException;
import java.math.BigDecimal;

/**
 * Main running class for Demo
 */
public class MainRun {

  public static void main(String[] args) {
    System.out.println("╔════════════════════════════════════════════════════════════╗");
    System.out.println("║          PRIMITIVE BANKING SYSTEM DEMO                     ║");
    System.out.println("╚════════════════════════════════════════════════════════════╝\n");

    Bank bank = new Bank();

    try {
      // ═══════════════════════════════════════════════════════════
      // 1. CREATE PERSONS
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 1: Creating Persons");
      System.out.println("-".repeat(60));

      Person p1 = bank.createPerson("John Smith");
      Person p2 = bank.createPerson("Alice Johnson");
      Person p3 = bank.createPerson("Bob Williams");

      // ═══════════════════════════════════════════════════════════
      // 2. CREATE ACCOUNTS
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 2: Creating Accounts");
      System.out.println("─".repeat(60));

      Account p1acc1 = bank.createAccount(p1);
      Account p1acc2 = bank.createAccount(p1); // additional account
      Account p2acc1 = bank.createAccount(p2);
      Account p3acc1 = bank.createAccount(p3);

      // ═══════════════════════════════════════════════════════════
      // 3. ADD MONEY FROM SOURCE
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 3: Adding Money from SOURCE Account");
      System.out.println("─".repeat(60));

      bank.addMoneyFromSource(p1acc1.getAccountNumber(), 1000.00);
      bank.addMoneyFromSource(p1acc2.getAccountNumber(), 500.40);
      bank.addMoneyFromSource(p2acc1.getAccountNumber(), 1500.75);
      bank.addMoneyFromSource(p3acc1.getAccountNumber(), 750.25);

      // ═══════════════════════════════════════════════════════════
      // 4. CHECK BALANCES
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 4: Checking Balances");
      System.out.println("─".repeat(60));

      System.out.println(p1acc1.checkBalance());
      System.out.println(p1acc2.checkBalance());
      System.out.println(p2acc1.checkBalance());
      System.out.println(p3acc1.checkBalance());

      // ═══════════════════════════════════════════════════════════
      // 5. TRANSFER MONEY BETWEEN ACCOUNTS
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 5: Transferring Money");
      System.out.println("─".repeat(60));

      bank.transferMoney(
          p1acc1.getAccountNumber()
          , p2acc1.getAccountNumber()
          , 200.00
      );

      bank.transferMoney(
          p1acc2.getAccountNumber(),
          p2acc1.getAccountNumber(),
          150.50
      );

      // within the same person
      bank.transferMoney(
          p1acc1.getAccountNumber(),
          p1acc2.getAccountNumber(),
          100.00
      );

      // ═══════════════════════════════════════════════════════════
      // 6. WITHDRAW MONEY
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 6: Withdrawing Money");
      System.out.println("─".repeat(60));

      bank.withdrawMoney(p1acc1.getAccountNumber(), 300.00);
      bank.withdrawMoney(p3acc1.getAccountNumber(), 50.00);

      // ═══════════════════════════════════════════════════════════
      // 7. DEPOSIT MONEY
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 7: Depositing Money");
      System.out.println("─".repeat(60));

      bank.depositMoney(p3acc1.getAccountNumber(), BigDecimal.valueOf(250.00));

      // ═══════════════════════════════════════════════════════════
      // 8. FINAL BALANCES
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 8: Final Account Balances");
      System.out.println("─".repeat(60));

      bank.printPersonAccounts(p1.getId());
      bank.printPersonAccounts(p2.getId());
      bank.printPersonAccounts(p3.getId());

      // ═══════════════════════════════════════════════════════════
      // 9. PRINT ALL PERSONS
      // ═══════════════════════════════════════════════════════════
      bank.printAllPersons();

      // ═══════════════════════════════════════════════════════════
      // 10. ERROR HANDLING DEMONSTRATIONS
      // ═══════════════════════════════════════════════════════════
      System.out.println("\nSTEP 9: Error Handling Demonstrations");
      System.out.println("─".repeat(60));

      demonstrateErrorHandling(bank, p1acc2);

      // ═══════════════════════════════════════════════════════════
      // FINAL SUMMARY
      // ═══════════════════════════════════════════════════════════
      bank.printSummary();
    } catch (BankingSystemException e) {
      System.err.println("Banking System Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Demonstrate various error scenarios
   */
  private static void demonstrateErrorHandling(Bank bank, Account account) {
    System.out.println("\nTesting error scenarios...\n");

    // Test 1: Negative amount
    try {
      System.out.println("Test 1: Attempting to deposit negative amount...");
      bank.depositMoney(account.getAccountNumber(), BigDecimal.valueOf(-100.00));
    } catch (BankingSystemException e) {
      System.out.println("  + Correctly rejected: " + e.getMessage());
    }

    // Test 2: Insufficient funds
    try {
      System.out.println("\nTest 2: Attempting to withdraw more than balance...");
      bank.withdrawMoney(account.getAccountNumber(), 999999.00);
    } catch (BankingSystemException e) {
      System.out.println("  + Correctly rejected: " + e.getMessage());
    }

    // Test 3: Invalid account
    try {
      System.out.println("\nTest 3: Attempting to access non-existent account...");
      bank.checkBalance("INVALID-ACCOUNT");
    } catch (BankingSystemException e) {
      System.out.println("  + Correctly rejected: " + e.getMessage());
    }

    // Test 4: Amount with too many decimal places
    try {
      System.out.println("\nTest 4: Attempting amount with 3 decimal places...");
      account.deposit(new BigDecimal("100.123"));
    } catch (BankingSystemException e) {
      System.out.println("  + Correctly rejected: " + e.getMessage());
    }

    // Test 5: Valid transaction for comparison
    try {
      System.out.println("\nTest 5: Valid transaction (for comparison)...");
      bank.depositMoney(account.getAccountNumber(), BigDecimal.valueOf(50.99));
      System.out.println("  + Transaction successful!");
    } catch (BankingSystemException e) {
      System.out.println("  - Unexpected error: " + e.getMessage());
    }
  }
}
