package com.payment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class Transaction - Kerangka cetakan struk/invoice untuk mencatat aktivitas keuangan aplikasi.
 * 
 * Deskripsi Detail:
 * Merepresentasikan histori data komplit transaksi (siapa bayar ke siapa, nominal, beserta jam detiknya).
 * 
 * Digunakan atau Berelasi di:
 * - TransactionService: Ter-instansiasi menjadi objek kedalam sebuah 'List collection', mengabadikan
 *   waktu (timestamp) secara otomatis pada sedetik pembuatan instance terjadi.
 */
public class Transaction {
    private String id;
    private String fromUserId;
    private String toUserId;
    private double amount;
    private String description;
    private LocalDateTime timestamp;

    // Constructor - Otomatis mencatat waktu live di baris `LocalDateTime.now()`
    // Di-instansiasi di: Secara khusus dibuat oleh TransactionService.transfer() sesudah uang divalidasi tersalurkan.
    public Transaction(String id, String fromUserId, String toUserId, double amount, String description) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Transaction{" +
                "id='" + id + '\'' +
                ", from='" + fromUserId + '\'' +
                ", to='" + toUserId + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp.format(formatter) +
                '}';
    }
}
