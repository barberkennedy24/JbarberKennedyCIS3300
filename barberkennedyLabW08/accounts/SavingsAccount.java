package accounts;

/**
 * SavingsAccount adds interestRate and applies interest on deposit.
 * It extends Account directly (not every savings must be premium).
 */
public class SavingsAccount extends Account {
    private double interestRate; // expressed as decimal, e.g. 0.02 for 2%

    public SavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    // Accessors / mutators
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Deposit: applies interest to the deposit amount immediately.
     * Example: deposit 100 with 2% interest -> actual added = 100 + 2 = 102.
     */
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        double interest = amount * interestRate;
        double total = amount + interest;
        balance += total;
        System.out.printf("Deposited $%.2f (interest $%.2f applied) into savings.%n", amount, interest);
    }

    @Override
    public void printAccountDetails() {
        super.printAccountDetails();
        System.out.printf("  (Savings) Interest Rate: %.4f%n", interestRate);
    }
}
