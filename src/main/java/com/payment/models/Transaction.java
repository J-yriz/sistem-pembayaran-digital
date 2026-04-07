package com.payment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction Class - Menyimpan Riwayat Setiap Transaksi User
 * 
 * Deskripsi:
 * Kelas ini merepresentasikan satu transaksi (top-up atau pembayaran) yang dilakukan oleh user.
 * Setiap transaksi mencatat waktu kapan terjadi, besaran nominal, tipe transaksi, dan status
 * yang membantu user melacak aktivitas finansial mereka.
 * 
 * Milestone 2: Encapsulation - Semua atribut private dengan akses via method
 */
public class Transaction {
    
    // ======== ATRIBUT PRIVATE ========
    // Menyimpan timestamp kapan transaksi terjadi (format: 2024-04-07 14:30:45)
    private LocalDateTime timestamp;
    
    // Menyimpan nominal uang yang ditransaksikan (contoh: 50000)
    private double amount;
    
    // Tipe transaksi - bisa "TOP_UP" atau "PAY" (withdrawal/pembayaran)
    private String type;
    
    // Status transaksi - bisa "SUCCESS" atau "FAILED" (untuk validasi nantinya)
    private String status;
    
    // ======== CONSTRUCTOR ========
    /**
     * Constructor untuk membuat object Transaction baru
     * 
     * @param amount  = nominal uang yang ditransaksikan
     * @param type    = jenis transaksi ("TOP_UP" atau "PAY")
     * @param status  = status ("SUCCESS" atau "FAILED")
     */
    public Transaction(double amount, String type, String status) {
        // Mengambil waktu saat ini sebagai timestamp transaksi
        this.timestamp = LocalDateTime.now();
        
        // Menyimpan nominal transaksi
        this.amount = amount;
        
        // Menyimpan jenis transaksi (TOP_UP untuk pemasukan, PAY untuk pengeluaran)
        this.type = type;
        
        // Menyimpan status (SUCCESS jika berhasil, FAILED jika gagal)
        this.status = status;
    }
    
    // ======== GETTER METHODS ========
    /**
     * Mendapatkan timestamp (waktu) transaksi
     * @return LocalDateTime - waktu kapan transaksi terjadi
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Mendapatkan nominal/jumlah uang transaksi
     * @return double - jumlah uang dalam rupiah
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * Mendapatkan tipe transaksi
     * @return String - tipe transaksi ("TOP_UP" atau "PAY")
     */
    public String getType() {
        return type;
    }
    
    /**
     * Mendapatkan status transaksi
     * @return String - status ("SUCCESS" atau "FAILED")
     */
    public String getStatus() {
        return status;
    }
    
    // ======== SETTER METHODS ========
    /**
     * Mengubah status transaksi jika diperlukan
     * @param status = status baru untuk transaksi
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    // ======== UTILITY METHODS ========
    /**
     * Menampilkan informasi transaksi dalam format yang mudah dibaca
     * Format: [2024-04-07 14:30:45] TOP_UP Rp50000 - SUCCESS
     * 
     * @return String - representasi transaksi yang sudah diformat
     */
    @Override
    public String toString() {
        // Format tanggal-waktu menjadi string readable (dd-MM-yyyy HH:mm:ss)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
        
        // Menentukan simbol/ikon berdasarkan tipe transaksi
        String icon = type.equals("TOP_UP") ? "↑ TOPUP" : "↓ BAYAR";
        
        // Menentukan warna status (untuk terminal output yang lebih bagus)
        String statusDisplay = status.equals("SUCCESS") ? "✓ SUCCESS" : "✗ FAILED";
        
        // Menggabungkan semua info dalam satu string yang rapi
        return String.format("[%s] %s Rp%.0f - %s", 
                             formattedTime, 
                             icon, 
                             amount, 
                             statusDisplay);
    }
}
