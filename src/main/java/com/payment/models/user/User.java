package com.payment.models.user;

import com.payment.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User Class - Merepresentasikan User dalam Sistem E-Wallet
 * 
 * Deskripsi Milestone 2:
 * Kelas ini mengimplementasikan ENCAPSULATION dengan menjadikan semua atribut PRIVATE.
 * Setiap perubahan saldo melalui validation ketat dan otomatis membuat record Transaction.
 * Semua akses ke atribut dilakukan via getter/setter dan method khusus.
 */
public class User {
    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";
    private static final String BOLD_WHITE        = "\u001B[1;38;5;15m";
    private static final String RESET             = "\u001B[0m";
    
    private String userId;
    
    private String name;
    
    private String phone;
    
    private double balance;
    
    private final List<Transaction> transactionHistory;

    
    public User(String userId, String name, String phone, double balance) {
        this.transactionHistory = new ArrayList<>();
        setUserId(userId);
        setName(name);
        setPhone(phone);
        setBalance(balance);
    }

    
    public String getUserId() {
        return userId;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public String getPhone() {
        return phone;
    }
    
    
    public double getBalance() {
        return balance;
    }
    
    
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    
    private void setUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            System.out.println("✗ User ID tidak boleh kosong.");
            return;
        }
        this.userId = userId;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("✗ Nama tidak boleh kosong.");
            return;
        }
        this.name = name;
    }

    private void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            System.out.println("✗ Nomor telepon tidak boleh kosong.");
            return;
        }
        this.phone = phone;
    }

    
    private void setBalance(double balance) {
        if (balance < 0) {
            System.out.println("✗ Saldo tidak boleh negatif.");
            return;
        }
        this.balance = balance;
    }

    
    void replaceTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory.clear();
        if (transactionHistory != null) {
            this.transactionHistory.addAll(transactionHistory);
        }
    }

    
    public void topUp(double amount) {
        if (amount > 0) {
            creditBalance(amount);
            addTransaction(amount, "TOP_UP", "SUCCESS");
            System.out.println("✓ " + name + " top up Rp" + (long)amount + " berhasil.");
        } else {
            System.out.println("✗ Nominal top up harus lebih dari 0.");
            addTransaction(amount, "TOP_UP", "FAILED");
        }
    }

    /**
     * Mengurangi saldo saat user melakukan pembayaran.
     */
    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("✗ Nominal pembayaran harus lebih dari 0.");
            addTransaction(amount, "PAY", "FAILED");
            return;
        }

        if (balance >= amount) {
            debitBalance(amount);
            addTransaction(amount, "PAY", "SUCCESS");
            System.out.println("✓ " + name + " bayar Rp" + (long)amount + " berhasil.");
        } else {
            System.out.println("✗ Saldo " + name + " tidak cukup untuk bayar Rp" + (long)amount + ".");
            addTransaction(amount, "PAY", "FAILED");
        }
    }

    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            return;
        }

        creditBalance(amount);
        addTransaction(amount, "RECEIVE", "SUCCESS");
    }

    public void receiveCashback(double amount) {
        if (amount <= 0) {
            return;
        }

        creditBalance(amount);
        addTransaction(amount, "CASHBACK", "SUCCESS");
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    public String getAccountType() {
        return "User";
    }

    public double getTransactionLimit() {
        return Double.MAX_VALUE;
    }

    public double getCashbackRate() {
        return 0.0;
    }

    public double calculateCashback(double transactionAmount) {
        if (transactionAmount <= 0) {
            return 0.0;
        }
        return transactionAmount * getCashbackRate();
    }

    public void showBalance() {
        
        System.out.printf("║ User: %-20s Saldo: Rp %,24.0f ║%n", name, balance);
    }

    public void showTransactionHistory() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");         
        System.out.println(BOLD_WHITE + "║ * RIWAYAT TRANSAKSI                                           ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Menampilkan riwayat transaksi dari saudara " + String.format("%-16s", name) + " ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");

        if (transactionHistory.isEmpty()) {
            System.out.println("║                                                               ║");         
            System.out.println("║  \u2715 Belum ada riwayat transaksi.                               ║");
            System.out.println("║                                                               ║");         
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();
            return;
        }

        for (int i = 0; i < transactionHistory.size(); i++) {
            Transaction tx = transactionHistory.get(i);
            System.out.println("║ " + String.format("%-62s", (i + 1) + ". " + tx.toString()) + "║");
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════╝");


        System.out.println();
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println("Total Transaksi: " + transactionHistory.size());
        System.out.println();
    }

    protected void addTransaction(double amount, String type, String status) {
        transactionHistory.add(new Transaction(amount, type, status));
    }

    private void creditBalance(double amount) {
        balance += amount;
    }

    private void debitBalance(double amount) {
        balance -= amount;
    }
}
