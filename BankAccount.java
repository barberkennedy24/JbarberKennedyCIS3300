package bank;

public class BankAccount {
    private static int nextAccountNumber = 1000;
    private static String bankName = "Default Bank";
    private static int numAccounts = 0;

    private int accountNumber;
    private String owner;
    private double balance;

    public BankAccount() {
        this("Unknown", 0.0);
    }

    public BankAccount(String owner, double initialBalance) {
        if (initialBalance < 0) {
            initialBalance = 0.0;
        }
        this.owner = owner;
        this.balance = initialBalance;
        this.accountNumber = nextAccountNumber++;
        numAccounts++;
    }

    public static String getBankName() {
        return bankName;
    }

    public static void setBankName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            bankName = name;
        }
    }

    public static int getNumAccounts() {
        return numAccounts;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit failed: amount must be positive.");
            return;
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdraw failed: amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Withdraw failed: insufficient funds.");
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean transferTo(BankAccount other, double amount) {
        if (other == null) {
            System.out.println("Transfer failed: destination account is null.");
            return false;
        }
        if (this.withdraw(amount)) {
            other.deposit(amount);
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("%s Account #%d | Owner: %s | Balance: $%.2f",
                bankName, accountNumber, owner, balance);
    }

    public static double totalAssets(BankAccount[] accounts) {
        double total = 0.0;
        if (accounts == null) return 0.0;
        for (BankAccount a : accounts) {
            if (a != null) total += a.getBalance();
        }
        return total;
    }
}
