package com.payment.models;

/**
 * Class User - Mewakili entitas pengguna dalam sistem pembayaran.
 * 
 * Deskripsi Detail:
 * Blueprint ini menyimpan data profil beserta saldo pengguna (balance).
 * 
 * Digunakan atau Berelasi di:
 * - UserService: Disimpan ke dalam Collection (Map HashMap) sebagai database in-memory.
 * - TransactionService: Diakses untuk dicek saldonya, ditambah, atau dikurangi.
 * - Main: Di-instansiasi pertama kali secara dummy saat awal eksekusi program.
 */
public class User {
    private String id;
    private String name;
    private String email;
    private double balance;

    // Constructor - Method build object otomatis saat inisialisasi awal.
    // Dipanggil di: Main.initializeSampleData() & Main.addNewUser() memakai keyword 'new'
    public User(String id, String name, String email, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method untuk menambah saldo: Mengubah properti (mutasi) nilai balance.
    // Dipanggil / Digunakan spesifik oleh: TransactionService.transfer() guna mem-push uang ke penerima transfer.
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    // Method untuk mengurangi saldo: Melakukan filter validasi minimum saldo terlebih dahulu.
    // Dipanggil / Digunakan di: TransactionService.transfer() untuk men-deduct uang pihak pengirim.
    // Nilai kembalian 'true/false' menjadi kunci untuk memblokir proses transfer jika gagal akibat saldo minus.
    public boolean deductBalance(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    // Method untuk menampilkan informasi user
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
