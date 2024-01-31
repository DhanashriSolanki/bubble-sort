package java_assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String accountHolder;
    private double balance;
    private Map<String, Double> transactionHistory;

    public Account(String accountHolder) {
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.put("DEPOSIT", amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.put("WITHDRAWAL", amount);
        }
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public Map<String, Double> getTransactionHistory() {
        return transactionHistory;
    }
}

public class SimpleBankingSystemGUI {
    private Map<String, Account> accounts;

    private JFrame frame;
    private JTextField accountHolderTextField;
    private JTextField amountTextField;
    private JTextArea outputTextArea;

    public SimpleBankingSystemGUI() {
        accounts = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Simple Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(new JScrollPane(outputTextArea), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        JLabel accountHolderLabel = new JLabel("Account Holder:");
        accountHolderTextField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();

        inputPanel.add(accountHolderLabel);
        inputPanel.add(accountHolderTextField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton viewTransactionHistoryButton = new JButton("View Transaction History");
        JButton exitButton = new JButton("Exit");

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransaction("DEPOSIT");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransaction("WITHDRAWAL");
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        viewTransactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTransactionHistory();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(createAccountButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(viewTransactionHistoryButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private void createAccount() {
        String accountHolder = accountHolderTextField.getText().trim();
        if (!accountHolder.isEmpty()) {
            Account newAccount = new Account(accountHolder);
            accounts.put(accountHolder, newAccount);
            outputTextArea.append("Account created for " + accountHolder + "\n");
            outputTextArea.append("Current Balance: $0.0\n");
        } else {
            outputTextArea.append("Please enter a valid account holder name.\n");
        }
    }

    private void performTransaction(String transactionType) {
        String accountHolder = accountHolderTextField.getText().trim();
        if (accounts.containsKey(accountHolder)) {
            Account account = accounts.get(accountHolder);

            try {
                double amount = Double.parseDouble(amountTextField.getText().trim());
                if (amount > 0) {
                    if ("DEPOSIT".equals(transactionType)) {
                        account.deposit(amount);
                        outputTextArea.append("Deposited: $" + amount + "\n");
                    } else if ("WITHDRAWAL".equals(transactionType)) {
                        account.withdraw(amount);
                        outputTextArea.append("Withdrawn: $" + amount + "\n");
                    }
                    displayBalance(account);
                } else {
                    outputTextArea.append("Please enter a valid positive amount.\n");
                }
            } catch (NumberFormatException e) {
                outputTextArea.append("Please enter a valid numeric amount.\n");
            }
        } else {
            outputTextArea.append("Account not found. Please create an account first.\n");
        }
    }

    private void checkBalance() {
        String accountHolder = accountHolderTextField.getText().trim();
        if (accounts.containsKey(accountHolder)) {
            Account account = accounts.get(accountHolder);
            displayBalance(account);
        } else {
            outputTextArea.append("Account not found. Please create an account first.\n");
        }
    }

    private void displayBalance(Account account) {
        outputTextArea.append("Current Balance for " + account.getAccountHolder() + ": $" + account.getBalance() + "\n");
    }

    private void viewTransactionHistory() {
        String accountHolder = accountHolderTextField.getText().trim();
        if (accounts.containsKey(accountHolder)) {
            Account account = accounts.get(accountHolder);
            Map<String, Double> transactionHistory = account.getTransactionHistory();
            if (!transactionHistory.isEmpty()) {
                outputTextArea.append("Transaction History for " + account.getAccountHolder() + ":\n");
                for (Map.Entry<String, Double> entry : transactionHistory.entrySet()) {
                    outputTextArea.append(entry.getKey() + " of $" + entry.getValue() + "\n");
                }
            } else {
                outputTextArea.append("No transactions found for " + account.getAccountHolder() + "\n");
            }
        } else {
            outputTextArea.append("Account not found. Please create an account first.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleBankingSystemGUI();
            }

