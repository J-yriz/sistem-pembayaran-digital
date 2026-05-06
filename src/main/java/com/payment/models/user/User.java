package com.payment.models.user;

import com.payment.models.Transaction;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;

    private String name;

    private String phone;

    private double balance;

    private List<Transaction> transactionHistory;

    public User(String userId, String name, String phone, double balance) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;

        this.balance = balance >= 0 ? balance : 0;

        this.transactionHistory = new ArrayList<>();
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

    public void topUp(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction(amount, "TOP_UP", "SUCCESS");
            System.out.println("Berhasil: " + name + " top up Rp" + (long)amount + ".");
        } else {
            System.out.println("Gagal: Nominal top up harus lebih dari 0.");
            addTransaction(amount, "TOP_UP", "FAILED");
        }
    }

    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Gagal: Nominal pembayaran harus lebih dari 0.");
            addTransaction(amount, "PAY", "FAILED");
            return;
        }

        if (balance >= amount) {
            balance -= amount;
            addTransaction(amount, "PAY", "SUCCESS");
            System.out.println("Berhasil: " + name + " bayar Rp" + (long)amount + ".");
        } else {
            System.out.println("Gagal: Saldo " + name + " tidak cukup untuk bayar Rp" + (long)amount + ".");
            addTransaction(amount, "PAY", "FAILED");
        }
    }

    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            return;
        }

        balance += amount;
        addTransaction(amount, "RECEIVE", "SUCCESS");
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

    public void showBalance() {
        System.out.printf("║ User: %-20s Saldo: Rp %,24.0f ║%n", name, balance);
    }

    public void showTransactionHistory() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║         RIWAYAT TRANSAKSI " + name + "                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        if (transactionHistory.isEmpty()) {
            System.out.println("Info: Belum ada riwayat transaksi.");
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

    private void addTransaction(double amount, String type, String status) {
        transactionHistory.add(new Transaction(amount, type, status));
    }
}
