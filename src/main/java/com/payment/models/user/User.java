package com.payment.models.user;

import com.payment.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class User — tidak dapat di-instantiate langsung karena setiap akun
 * memiliki tipe, batas transaksi, dan cashback yang berbeda. Subclass wajib
 * mengimplementasikan {@link #getTransactionLimit()}, {@link #getCashbackRate()},
 * dan {@link #getAccountType()}.
 */
public abstract class User {
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
            System.out.println("Gagal: User ID tidak boleh kosong.");
            return;
        }
        this.userId = userId;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("Gagal: Nama tidak boleh kosong.");
            return;
        }
        this.name = name;
    }

    private void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            System.out.println("Gagal: Nomor telepon tidak boleh kosong.");
            return;
        }
        this.phone = phone;
    }

    private void setBalance(double balance) {
        if (balance < 0) {
            System.out.println("Gagal: Saldo tidak boleh negatif.");
            return;
        }
        if (balance > getTransactionLimit()) {
            System.out.println("Gagal: Saldo melebihi batas kapasitas dompet.");
            this.balance = getTransactionLimit();
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

    public boolean canHoldAdditional(double amount) {
        return balance + amount <= getTransactionLimit();
    }

    public void topUp(double amount) {
        if (amount > 0) {
            if (!canHoldAdditional(amount)) {
                System.out.println(
                    "Gagal: Top up melebihi batas kapasitas dompet (maks. Rp" + (long) getTransactionLimit() + ")."
                );
                addTransaction(amount, "TOP_UP", "FAILED");
                return;
            }
            creditBalance(amount);
            addTransaction(amount, "TOP_UP", "SUCCESS");
            System.out.println("Berhasil: " + name + " top up Rp" + (long) amount + ".");
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
            debitBalance(amount);
            addTransaction(amount, "PAY", "SUCCESS");
            System.out.println("Berhasil: " + name + " bayar Rp" + (long) amount + ".");
        } else {
            System.out.println("Gagal: Saldo " + name + " tidak cukup untuk bayar Rp" + (long) amount + ".");
            addTransaction(amount, "PAY", "FAILED");
        }
    }

    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            return;
        }

        if (!canHoldAdditional(amount)) {
            System.out.println("Gagal: Saldo " + name + " melebihi batas kapasitas dompet.");
            addTransaction(amount, "RECEIVE", "FAILED");
            return;
        }

        creditBalance(amount);
        addTransaction(amount, "RECEIVE", "SUCCESS");
    }

    public void receiveCashback(double amount) {
        if (amount <= 0) {
            return;
        }

        if (!canHoldAdditional(amount)) {
            System.out.println("Gagal: Cashback melebihi batas kapasitas dompet " + name + ".");
            addTransaction(amount, "CASHBACK", "FAILED");
            return;
        }

        creditBalance(amount);
        addTransaction(amount, "CASHBACK", "SUCCESS");
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    public abstract String getAccountType();

    public abstract double getTransactionLimit();

    public abstract double getCashbackRate();

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
            System.out.println("║  Belum ada riwayat transaksi.                                  ║");
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
