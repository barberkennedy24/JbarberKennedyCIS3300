package accounts;

/**
 * BusinessAccount is a PremiumAccount and includes a business name.
 * All business accounts are premium accounts per assignment.
 */
public class BusinessAccount extends PremiumAccount {
    private String businessName;

    public BusinessAccount(String accountNumber, double initialBalance,
                           double withdrawalLimit, double interestBooster,
                           String businessName) {
        super(accountNumber, initialBalance, withdrawalLimit, interestBooster);
        this.businessName = businessName;
    }

    // Accessors / mutators
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public void printAccountDetails() {
        super.printAccountDetails();
        System.out.printf("  Business Name: %s%n", businessName);
    }
}
