package banking;

import bank.Account;
import bank.Bank;
import bank.Transaction;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class BankingApp {

    public static void loadAccounts(Bank bank, String filename) {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextLine;
            reader.readNext(); // Skip header
            while ((nextLine = reader.readNext()) != null) {
                String acctNum = nextLine[0].trim();
                String owner = nextLine[1].trim();
                double balance = Double.parseDouble(nextLine[2].trim());
                Account acc = new Account(acctNum, owner, balance);
                bank.addAccount(acc);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error loading accounts from " + filename);
            e.printStackTrace();
        }
    }

    public static void processTransactions(Bank bank, String filename) {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextLine;
            reader.readNext(); // Skip header
            while ((nextLine = reader.readNext()) != null) {
                String type = nextLine[0].trim();
                double amount = Double.parseDouble(nextLine[1].trim());
                String target = nextLine[2].trim();
                String source = nextLine.length > 3 ? nextLine[3].trim() : null;

                Transaction txn;
                if (source == null || source.equalsIgnoreCase("null") || source.isEmpty()) {
                    txn = new Transaction(type, amount, target);
                } else {
                    txn = new Transaction(type, amount, target, source);
                }
                bank.processTransaction(txn);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error processing transactions from " + filename);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        String accountsFile    = "files/tiny_accounts.csv";
        String transactionsFile = "files/tiny_transactions.csv";

        loadAccounts(bank, accountsFile);
        bank.printAccounts();

        processTransactions(bank, transactionsFile);
        System.out.println();
        bank.printAccounts();
    }
}
