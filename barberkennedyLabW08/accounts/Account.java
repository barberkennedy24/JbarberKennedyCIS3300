package accounts;

/**
 * Abstract base Account class.
 * Contains accountNumber and balance, basic deposit/withdraw and printing.
 * Cannot be instantiated by itself (abstract).
 */
public abstract class Account {
    protected String accountNumber;
    protected double balance;

    // Parameterized constructor (initializes all class members)
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Accessors / mutators
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // Core behaviors
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
    }

    /**
     * Basic withdraw: only allows withdraw if there is enough balance.
     * Subclasses override to change behavior (e.g. overdraft / limits).
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdraw amount must be positive.");
            return false;
        }
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds for withdraw: requested=" + amount + ", balance=" + balance);
            return false;
        }
    }

    /**
     * Print account details. Subclasses should override and can call super.printAccountDetails().
     */
    public void printAccountDetails() {
        System.out.printf("Account Number: %s | Balance: $%.2f%n", accountNumber, balance);
    }

    @Override
    public String toString() {
        return String.format("Account[%s]: $%.2f", accountNumber, balance);
    }
}
