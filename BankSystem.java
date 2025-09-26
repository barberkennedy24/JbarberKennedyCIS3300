package banking;

import bank.BankAccount;

public class BankSystem {
    public static void main(String[] args) {
        BankAccount.setBankName("Kenji's Lounge Bank");

        BankAccount acct1 = new BankAccount("Alice", 500.00); 
        BankAccount acct2 = new BankAccount("Bob", 200.00);
        BankAccount acct3 = new BankAccount();

        System.out.println("Initial accounts:");
        System.out.println(acct1);
        System.out.println(acct2);
        System.out.println(acct3);

        System.out.println("\n-- Transactions --");
        acct1.deposit(150.00);
        System.out.printf("Deposited $150 to %s (Acct #%d). New balance: $%.2f%n",
                acct1.getOwner(), acct1.getAccountNumber(), acct1.getBalance());

        acct2.withdraw(50.00);
        System.out.printf("Withdrew $50 from %s (Acct #%d). New balance: $%.2f%n",
                acct2.getOwner(), acct2.getAccountNumber(), acct2.getBalance());

        boolean ok = acct1.transferTo(acct2, 200.00);
        System.out.printf("Transfer $200 from %s to %s: %s%n",
                acct1.getOwner(), acct2.getOwner(), ok ? "SUCCESS" : "FAILED");

        BankAccount[] accounts = new BankAccount[] {acct1, acct2, acct3};
        System.out.println("\nFinal accounts:");
        for (BankAccount a : accounts) {
            System.out.println(a);
        }

        System.out.printf("%nTotal accounts created (static count): %d%n", BankAccount.getNumAccounts());
        System.out.printf("Total assets (computed via static helper): $%.2f%n", BankAccount.totalAssets(accounts));
    }
}
