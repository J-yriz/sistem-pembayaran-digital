package com.payment.services;

import com.payment.models.Transaction;
import com.payment.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Class TransactionService - Engine penanggungjawab utama untuk validasi lalu lintas mutasi saldo Uang.
 * 
 * Deskripsi Detail:
 * Sangat berkuasa dalam memvalidasi saldo kurang/memadai, serta mentenagai mutasi properti.
 * Ia membutuhkan disuntikannya properti 'UserService' agar dia dapat mencari dan mengganti informasi Nasabahnya.
 * 
 * Digunakan / Berelasi di:
 * - Selalu di akses di Main.java (sebagai otak operasional dari form layar Transfer & Transaction History Menu).
 */
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private UserService userService;
    private int transactionCounter = 1;

    public TransactionService(UserService userService) {
        this.userService = userService;
    }

    // Mesin Inti Pertukaran Uang: Mengkoordinasi banyak proses (tarik db -> filter saldo -> potong&tambah -> bikin objek Struk).
    // Mendapatkan Data Dari: String argumen yang dikirim dari tangkapan interaksi CLI `Main.handleTransfer()`.
    public boolean transfer(String fromUserId, String toUserId, double amount, String description) {
        User fromUser = userService.getUserById(fromUserId);
        User toUser = userService.getUserById(toUserId);

        // Validasi
        if (fromUser == null) {
            System.out.println("✗ Pengirim tidak ditemukan");
            return false;
        }
        if (toUser == null) {
            System.out.println("✗ Penerima tidak ditemukan");
            return false;
        }
        if (amount <= 0) {
            System.out.println("✗ Jumlah transfer harus lebih dari 0");
            return false;
        }
        if (fromUser.getBalance() < amount) {
            System.out.println("✗ Saldo tidak cukup. Saldo Anda: Rp" + fromUser.getBalance());
            return false;
        }

        // Lakukan transfer
        fromUser.deductBalance(amount);
        toUser.addBalance(amount);

        // Catat transaksi
        String transactionId = "TRX" + String.format("%06d", transactionCounter++);
        Transaction transaction = new Transaction(transactionId, fromUserId, toUserId, amount, description);
        transactions.add(transaction);

        System.out.println("✓ Transfer berhasil!");
        System.out.println("  ID Transaksi: " + transactionId);
        System.out.println("  Dari: " + fromUser.getName() + " (Saldo: Rp" + fromUser.getBalance() + ")");
        System.out.println("  Ke: " + toUser.getName() + " (Saldo: Rp" + toUser.getBalance() + ")");
        System.out.println("  Jumlah: Rp" + amount);

        return true;
    }

    // Tampilkan riwayat transaksi
    public void displayTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        System.out.println("\n=== RIWAYAT TRANSAKSI ===");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    // Tampilkan transaksi user
    public void displayUserTransactions(String userId) {
        List<Transaction> userTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getFromUserId().equals(userId) || transaction.getToUserId().equals(userId)) {
                userTransactions.add(transaction);
            }
        }

        if (userTransactions.isEmpty()) {
            System.out.println("User ini belum memiliki transaksi.");
            return;
        }

        System.out.println("\n=== TRANSAKSI USER: " + userId + " ===");
        for (Transaction transaction : userTransactions) {
            System.out.println(transaction);
        }
    }

    // Dapatkan jumlah transaksi
    public int getTransactionCount() {
        return transactions.size();
    }
}
