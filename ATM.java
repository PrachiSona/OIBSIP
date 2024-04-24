import java.io.*;
import java.util.*;

class Account {
    private String accountNumber;
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String userId, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public boolean validateUserId(String inputUserId) {
        return this.userId.equals(inputUserId);
    }

    public void deposit(double amount) {
        this.balance += amount;
        this.transactionHistory.add("Deposit: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            this.transactionHistory.add("Withdraw: $" + amount);
            return true;
        }
        return false;
    }

    public void transfer(Account toAccount, double amount) {
        if (this.withdraw(amount)) {
            toAccount.deposit(amount);
            this.transactionHistory.add("Transfer: $" + amount + " to account " + toAccount.getAccountNumber());
            toAccount.transactionHistory.add("Transfer: $" + amount + " from account " + this.accountNumber);
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction history:");
        for (String transaction : this.transactionHistory) {
            System.out.println(transaction);
        }
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }
}

public class ATM {
    private static final String FILE_NAME = "transaction_history.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;

    public static void main(String[] args) {
        Account[] accounts = {
            new Account("123456789", "user1", "1234", 1000),
            new Account("987654321", "user2", "4321", 2000)
        };

        System.out.println("Enter your user ID:");
        String userId = scanner.nextLine();

        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();

        for (Account account : accounts) {
            if (account.validateUserId(userId) && account.validatePin(pin)) {
                currentAccount = account;
                break;
            }
        }

        if (currentAccount == null) {
            System.out.println("Invalid user ID or PIN.");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    currentAccount.printTransactionHistory();
                    break;
                case 2:
                    System.out.println("Enter the amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentAccount.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.println("Enter the amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.println("Enter the account number to transfer to:");
                    String toAccountNumber = scanner.nextLine();
                    System.out.println("Enter the amount to transfer:");
                    double transferAmount = scanner.nextDouble();
                    for (Account account : accounts) {
                        if (account.getAccountNumber().equals(toAccountNumber)) {
                            currentAccount.transfer(account, transferAmount);
                            break;
                        }
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        saveTransactionHistory(currentAccount);
        System.out.println("Thank you for using the ATM.");
    }

    private static void saveTransactionHistory(Account account) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            for (String transaction : account.getTransactionHistory()) {
                fileWriter.write(transaction + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction history: " + e.getMessage());
        }
    }
}