package accounts;

/**
 * CheckingAccount adds overdraftLimit and allows balance to go negative up to that limit.
 */
public class CheckingAccount extends Account {
    private double overdraftLimit; // positive number representing how far below zero balance can go

    public CheckingAccount(String accountNumber, double initialBalance, double overdraftLimit) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    // Accessors / mutators
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Withdraw allows going below zero balance up to negative overdraftLimit.
     * If overdraftLimit is 500, balance is allowed down to -500.
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdraw amount must be positive.");
            return false;
        }
        double projected = balance - amount;
        if (projected >= -overdraftLimit) {
            balance = projected;
            return true;
        } else {
            System.out.printf("Overdraft denied: would exceed overdraft limit. Requested=%.2f, allowed to=%.2f%n",
                    amount, balance + overdraftLimit);
            return false;
        }
    }

    @Override
    public void printAccountDetails() {
        super.printAccountDetails();
        System.out.printf("  (Checking) Overdraft Limit: $%.2f%n", overdraftLimit);
    }
}
