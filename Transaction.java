package bank;

public class Transaction {
    private String type;
    private String targetAccount;
    private String sourceAccount;
    private double amount;

    public Transaction(String type, double amount, String targetAccount, String sourceAccount) {
        this.type = type;
        this.amount = amount;
        this.targetAccount = targetAccount;
        if (sourceAccount == null || sourceAccount.equalsIgnoreCase("null") || sourceAccount.trim().isEmpty()) {
            this.sourceAccount = null;
        } else {
            this.sourceAccount = sourceAccount;
        }
    }

    public Transaction(String type, double amount, String targetAccount) {
        this(type, amount, targetAccount, null);
    }

    public String getType() { return type; }
    public String getTargetAccount() { return targetAccount; }
    public String getSourceAccount() { return sourceAccount; }
    public double getAmount() { return amount; }
}
