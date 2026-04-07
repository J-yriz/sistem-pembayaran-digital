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
    
    // ======== ATRIBUT PRIVATE ========
    // Milestone 2: Semua atribut dijadikan private untuk Encapsulation
    
    // Menyimpan ID unik user (contoh: U001, U002)
    private String userId;
    
    // Menyimpan nama user lengkap
    private String name;
    
    // Menyimpan nomor telepon user
    private String phone;
    
    // Menyimpan saldo uang dalam rupiah (tidak boleh negatif)
    private double balance;
    
    // BARU M2: List untuk menyimpan semua transaksi user (top-up dan pembayaran)
    // Setiap transaksi otomatis ditambahkan ke list ini
    private List<Transaction> transactionHistory;

    // ======== CONSTRUCTOR ========
    /**
     * Constructor untuk membuat object User baru
     * Milestone 2: Constructor juga menginisialisasi transactionHistory sebagai ArrayList kosong
     * 
     * @param userId  = ID unik user
     * @param name    = nama user
     * @param phone   = nomor telepon user
     * @param balance = saldo awal user
     */
    public User(String userId, String name, String phone, double balance) {
        // Menyimpan ID user
        this.userId = userId;
        
        // Menyimpan nama user
        this.name = name;
        
        // Menyimpan nomor telepon user
        this.phone = phone;
        
        // VALIDASI MILESTONE 2: Cek balance tidak boleh negatif
        // Jika balance negatif, set ke 0 sebagai default
        this.balance = balance >= 0 ? balance : 0;
        
        // BARU M2: Inisialisasi ArrayList kosong untuk history transaksi
        // Setiap object User punya list transaction masing-masing
        this.transactionHistory = new ArrayList<>();
    }

    // ======== GETTER METHODS ========
    /**
     * Mendapatkan ID user
     * @return String - ID user unik
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Mendapatkan nama user
     * @return String - nama user lengkap
     */
    public String getName() {
        return name;
    }
    
    /**
     * Mendapatkan nomor telepon user
     * @return String - nomor telepon
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Mendapatkan saldo user saat ini
     * @return double - saldo dalam rupiah
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * BARU M2: Mendapatkan list seluruh transaction history user
     * @return List<Transaction> - daftar semua transaksi user
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    // ======== SETTER METHODS ========
    /**
     * Mengubah nama user
     * @param name = nama user baru
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Mengubah nomor telepon user
     * @param phone = nomor telepon baru
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // ======== CORE TRANSACTION METHODS ========
    /**
     * Method untuk menambah saldo user (Top Up)
     * 
     * VALIDASI MILESTONE 2:
     * - Amount harus lebih dari 0 (positif)
     * - Jika valid, tambah saldo dan buat record Transaction dengan status SUCCESS
     * - Jika tidak valid, tampilkan error dan buat record Transaction dengan status FAILED
     * 
     * @param amount = jumlah uang yang di-top-up (dalam rupiah)
     */
    public void topUp(double amount) {
        // VALIDASI: Cek apakah amount lebih dari 0 (positif)
        if (amount > 0) {
            // Amount valid - lakukan top-up
            // Tambahkan amount ke balance
            balance += amount;
            
            // BARU M2: Buat object Transaction baru dengan status SUCCESS
            // Tipe "TOP_UP" menunjukkan ini adalah transaksi pemasukan
            Transaction transaction = new Transaction(amount, "TOP_UP", "SUCCESS");
            
            // BARU M2: Tambahkan transaction ke history list
            transactionHistory.add(transaction);
            
            // Feedback ke user
            System.out.println("✓ " + name + " top up Rp" + (long)amount + " berhasil.");
        } else {
            // Amount tidak valid (0 atau negatif)
            System.out.println("✗ Nominal top up harus lebih dari 0.");
            
            // BARU M2: Buat record Transaction dengan status FAILED
            // Ini untuk audit trail - mencatat semua attempt transaksi
            Transaction failedTransaction = new Transaction(amount, "TOP_UP", "FAILED");
            
            // BARU M2: Tambahkan failed transaction ke history
            transactionHistory.add(failedTransaction);
        }
    }

    /**
     * Method untuk melakukan pembayaran (debit/withdrawal)
     * 
     * VALIDASI MILESTONE 2:
     * - Amount harus lebih dari 0
     * - Saldo harus cukup (balance >= amount)
     * - Jika valid, kurangi saldo dan buat record Transaction dengan status SUCCESS
     * - Jika tidak valid, tampilkan error dan buat record Transaction dengan status FAILED
     * 
     * @param amount = jumlah uang yang dibayarkan (dalam rupiah)
     */
    public void pay(double amount) {
        // VALIDASI 1: Cek apakah amount lebih dari 0
        if (amount <= 0) {
            System.out.println("✗ Nominal pembayaran harus lebih dari 0.");
            
            // BARU M2: Buat record Transaction gagal karena amount invalid
            Transaction failedTransaction = new Transaction(amount, "PAY", "FAILED");
            transactionHistory.add(failedTransaction);
            
            return; // Keluar dari method karena validasi gagal
        }

        // VALIDASI 2: Cek apakah saldo cukup untuk pembayaran
        if (balance >= amount) {
            // Semua validasi pass - lakukan pembayaran
            // Kurangi saldo dengan jumlah pembayaran
            balance -= amount;
            
            // BARU M2: Buat object Transaction baru dengan status SUCCESS
            // Tipe "PAY" menunjukkan ini adalah transaksi pengeluaran
            Transaction transaction = new Transaction(amount, "PAY", "SUCCESS");
            
            // BARU M2: Tambahkan transaction ke history list
            transactionHistory.add(transaction);
            
            // Feedback ke user
            System.out.println("✓ " + name + " bayar Rp" + (long)amount + " berhasil.");
        } else {
            // Saldo tidak cukup
            System.out.println("✗ Saldo " + name + " tidak cukup untuk bayar Rp" + (long)amount + ".");
            
            // BARU M2: Buat record Transaction yang gagal karena saldo insufficient
            Transaction failedTransaction = new Transaction(amount, "PAY", "FAILED");
            transactionHistory.add(failedTransaction);
        }
    }

    /**
     * BARU M2: Method untuk cek apakah saldo cukup untuk pembayaran tertentu
     * Method helper yang memudahkan validasi sebelum proses pembayaran
     * 
     * @param amount = jumlah yang akan dicek
     * @return boolean - true jika saldo cukup, false jika tidak
     */
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    // ======== DISPLAY METHODS ========
    /**
     * Method untuk menampilkan saldo user saat ini
     * Format: Saldo Ahmad (U001): Rp 500000
     */
    public void showBalance() {
        // System.out.println("📊 Saldo " + name + " (" + userId + "): Rp " + String.format("%.0f", balance));
        
        System.out.printf("║ User: %-20s Saldo: Rp %,24.0f ║%n", name, balance);
        

        // If you need a middle divider, use this one:
        // System.out.println("╠══════════════════════════════════════════════════════╣");
    }

    /**
     * BARU M2: Method untuk menampilkan seluruh riwayat transaksi user
     * 
     * Fitur:
     * - Menampilkan semua transaksi (TOP_UP maupun PAY)
     * - Menampilkan waktu, nominal, tipe, dan status setiap transaksi
     * - Jika belum ada transaksi, tampilkan pesan "Tidak ada riwayat transaksi"
     */
    public void showTransactionHistory() {
        // Tampilkan header
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║         RIWAYAT TRANSAKSI " + name + "                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        // VALIDASI: Cek apakah history kosong
        if (transactionHistory.isEmpty()) {
            // Jika tidak ada transaksi
            System.out.println("ℹ️  Belum ada riwayat transaksi.");
            return; // Keluar dari method
        }
        
        // Jika ada transaksi, tampilkan satu per satu
        // Loop untuk setiap transaction dalam list
        for (int i = 0; i < transactionHistory.size(); i++) {
            // Ambil transaction pada index i
            Transaction tx = transactionHistory.get(i);
            
            // Tampilkan nomor urut dan info transaksi
            // trans.toString() sudah diformat rapi di class Transaction
            System.out.println((i + 1) + ". " + tx.toString());
        }
        
        // Tampilkan footer dengan total
        System.out.println("─────────────────────────────────────────────────────");
        System.out.println("Total Transaksi: " + transactionHistory.size());
        System.out.println();
    }
}
