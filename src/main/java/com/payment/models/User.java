package com.payment.models;

import java.util.ArrayList;
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
    
    // Menyimpan ID unik user (contoh: U001, U002)
    private String userId;
    
    // Menyimpan nama user lengkap
    private String name;
    
    // Menyimpan nomor telepon user
    private String phone;
    
    // Menyimpan saldo uang dalam rupiah (tidak boleh negatif)
    private double balance;
    
    // Menyimpan riwayat semua transaksi user.
    private List<Transaction> transactionHistory;

    /**
     * Constructor untuk membuat object User baru
     */
    public User(String userId, String name, String phone, double balance) {
        setUserId(userId);
        setName(name);
        setPhone(phone);
        setBalance(balance);
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Mendapatkan ID user
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Mendapatkan nama user
     */
    public String getName() {
        return name;
    }
    
    /**
     * Mendapatkan nomor telepon user
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Mendapatkan saldo user saat ini
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Mendapatkan list seluruh transaction history user.
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Mengubah ID user dengan validasi sederhana.
     */
    public void setUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            System.out.println("✗ User ID tidak boleh kosong.");
            return;
        }
        this.userId = userId;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("✗ Nama tidak boleh kosong.");
            return;
        }
        this.name = name;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            System.out.println("✗ Nomor telepon tidak boleh kosong.");
            return;
        }
        this.phone = phone;
    }

    /**
     * Mengubah saldo dengan validasi agar tidak negatif.
     */
    public void setBalance(double balance) {
        if (balance < 0) {
            System.out.println("✗ Saldo tidak boleh negatif.");
            return;
        }
        this.balance = balance;
    }

    /**
     * Mengganti riwayat transaksi user (misalnya saat load data).
     */
    public void setTransactionHistory(List<Transaction> transactionHistory) {
        if (transactionHistory == null) {
            this.transactionHistory = new ArrayList<>();
            return;
        }
        this.transactionHistory = new ArrayList<>(transactionHistory);
    }

    /**
     * Menambah saldo user (top up).
     */
    public void topUp(double amount) {
        if (amount > 0) {
            balance += amount;
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
            balance -= amount;
            addTransaction(amount, "PAY", "SUCCESS");
            System.out.println("✓ " + name + " bayar Rp" + (long)amount + " berhasil.");
        } else {
            System.out.println("✗ Saldo " + name + " tidak cukup untuk bayar Rp" + (long)amount + ".");
            addTransaction(amount, "PAY", "FAILED");
        }
    }

    /**
     * Menambah saldo dari transaksi masuk (contoh transfer dari user lain).
     */
    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            return;
        }

        balance += amount;
        addTransaction(amount, "RECEIVE", "SUCCESS");
    }

    /**
     * Cek apakah saldo user cukup untuk nominal tertentu.
     */
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    /**
     * Informasi umum tipe akun. Akan dioverride oleh subclass di Milestone 3.
     */
    public String getAccountType() {
        return "User";
    }

    /**
     * Limit transaksi default untuk parent class.
     */
    public double getTransactionLimit() {
        return Double.MAX_VALUE;
    }

    /**
     * Cashback default untuk parent class.
     */
    public double getCashbackRate() {
        return 0.0;
    }

    /**
     * Method untuk menampilkan saldo user saat ini
     * Format: Saldo Ahmad (U001): Rp 500000
     */
    public void showBalance() {
        System.out.printf("║ User: %-20s Saldo: Rp %,24.0f ║%n", name, balance);
    }

    /**
     * Menampilkan seluruh riwayat transaksi user.
     */
    public void showTransactionHistory() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║         RIWAYAT TRANSAKSI " + name + "                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        if (transactionHistory.isEmpty()) {
            System.out.println("ℹ️  Belum ada riwayat transaksi.");
            return;
        }

        for (int i = 0; i < transactionHistory.size(); i++) {
            Transaction tx = transactionHistory.get(i);
            System.out.println((i + 1) + ". " + tx.toString());
        }

        System.out.println("─────────────────────────────────────────────────────");
        System.out.println("Total Transaksi: " + transactionHistory.size());
        System.out.println();
    }

    /**
     * Helper internal untuk mencatat transaksi ke history.
     */
    protected void addTransaction(double amount, String type, String status) {
        transactionHistory.add(new Transaction(amount, type, status));
    }
}
