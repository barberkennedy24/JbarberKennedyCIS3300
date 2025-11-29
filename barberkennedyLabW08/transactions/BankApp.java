package transactions;

import accounts.*;

/**
 * BankApp main method to create accounts and test behaviors.
 * This class demonstrates the required behaviors and prints results.
 */
public class BankApp {
    public static void main(String[] args) {
        System.out.println("=== BankApp Test Start ===");

        // 1) Simple Savings account (not premium)
        SavingsAccount sav1 = new SavingsAccount("SAV1001", 500.00, 0.02); // 2% interest
        sav1.printAccountDetails();
        sav1.deposit(100.00); // deposit should apply interest
        System.out.printf("After deposit, balance: $%.2f%n%n", sav1.getBalance());

        // 2) Checking account with overdraft allowed
        CheckingAccount chk1 = new CheckingAccount("CHK2001", 200.00, 300.00); // overdraft up to $300
        chk1.printAccountDetails();
        System.out.println("Withdraw $350 from checking (should allow, go negative):");
        boolean result = chk1.withdraw(350.00);
        System.out.println("Withdraw success: " + result);
        System.out.printf("Checking balance: $%.2f%n%n", chk1.getBalance());

        // 3) Premium saving-like account (business uses premium below)
        // We don't have a concrete PremiumSaving class; premium behavior is in PremiumAccount
        // Create BusinessAccount (which is PremiumAccount)
        BusinessAccount bus1 = new BusinessAccount("BUS3001", 1000.00, 500.00, 0.01, "Acme, Inc.");
        bus1.printAccountDetails();
        System.out.println("Attempt to withdraw $600 from business (should be denied by withdrawalLimit):");
        boolean w1 = bus1.withdraw(600.00); // exceeds withdrawalLimit 500.00 -> denied
        System.out.println("Withdraw success: " + w1);
        System.out.println("Attempt to withdraw $400 from business (should succeed if enough balance):");
        boolean w2 = bus1.withdraw(400.00); // within limit and within balance
        System.out.println("Withdraw success: " + w2);
        System.out.printf("Business balance: $%.2f%n%n", bus1.getBalance());

        // 4) Demonstrate Account withdraw insufficient funds (regular Account behavior)
        SavingsAccount sav2 = new SavingsAccount("SAV1002", 50.00, 0.05);
        sav2.printAccountDetails();
        System.out.println("Attempt to withdraw $200 from savings (should fail):");
        boolean w3 = sav2.withdraw(200.00);
        System.out.println("Withdraw success: " + w3);
        System.out.printf("Savings balance: $%.2f%n%n", sav2.getBalance());

        // 5) Show deposit into savings applies interest
        System.out.println("Deposit $500 into SAV1002 (5% interest):");
        sav2.deposit(500.00); // should add 500 + 25 interest
        System.out.printf("Savings balance: $%.2f%n%n", sav2.getBalance());

        System.out.println("=== BankApp Test End ===");
    }
}
