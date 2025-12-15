package bank.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {

  private final String id;
  private final String name;
  private final List<Account> accounts;

  public Person(String id, String name) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("Person ID cannot be null or empty");
    }

    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Person name cannot be null or empty");
    }

    this.id = id;
    this.name = name;
    this.accounts = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  // to prevent external modification it returns a copy
  public List<Account> getAccounts() {
    return new ArrayList<>(accounts);
  }

  // add account to this person
  public void addAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null");
    }

    if (!account.getOwner().equals(this)) {
      throw new IllegalArgumentException("Account must belong to this person");
    }
    accounts.add(account);
  }

  // find account by account number
  public Account findAccount(String accountNumber) {
    return accounts.stream()
        .filter(acc -> acc.getAccountNumber()
            .equals(accountNumber))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Person person = (Person) o;
    return Objects.equals(id, person.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Person{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", accounts=" + accounts.size() +
        '}';
  }
}
