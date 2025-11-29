package accounts;

/**
 * PremiumAccount adds:
 *  - withdrawalLimit: maximum allowed per-withdrawal amount
 *  - interestBooster: a value that can be used by subclasses to boost interest (e.g. additional rate)
 *
 * PremiumAccount is abstract (cannot be instantiated).
 */
public abstract class PremiumAccount extends Account {
    protected double withdrawalLimit;    // max single withdrawal amount allowed by this account
    protected double interestBooster;    // additional interest factor (e.g. 0.02 for +2%)

    public PremiumAccount(String accountNumber, double initialBalance, double withdrawalLimit, double interestBooster) {
        super(accountNumber, initialBalance);
        this.withdrawalLimit = withdrawalLimit;
        this.interestBooster = interestBooster;
    }

    // Accessors / mutators
    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public double getInterestBooster() {
        return interestBooster;
    }

    public void setInterestBooster(double interestBooster) {
        this.interestBooster = interestBooster;
    }

    /**
     * Override withdraw to enforce withdrawalLimit, then delegate to parent behavior.
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount > withdrawalLimit) {
            System.out.printf("Withdrawal denied: amount $%.2f exceeds withdrawal limit $%.2f%n", amount, withdrawalLimit);
            return false;
        }
        // If within limit, use Account's withdraw. Subclasses may further override.
        return super.withdraw(amount);
    }

    @Override
    public void printAccountDetails() {
        super.printAccountDetails();
        System.out.printf("  (Premium) Withdrawal Limit: $%.2f | Interest Booster: %.4f%n", withdrawalLimit, interestBooster);
    }
}
