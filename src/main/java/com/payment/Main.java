package com.payment;

import com.payment.models.User;
import com.payment.utils.DisplayUtils;
import java.util.Scanner;

/**
 * Main Class - Entry Point Sistem Pembayaran Digital
 * 
 * Milestone 2 Features:
 * - Interactive menu dengan pilihan (Top Up, Bayar, Lihat History, Exit)
 * - Input validation untuk semua user input
 * - Demonstrasi Transaction History untuk setiap user
 * - Error handling untuk input yang tidak valid
 */
public class Main {
    // Italic + Orange foreground
    // Italic + Dark Gray foreground
    private static final String ITALIC_DARK_GRAY = "\u001B[3;38;5;243m";

    // Italic + Medium Gray foreground
    private static final String ITALIC_GRAY = "\u001B[3;38;5;247m";

    // Italic + Light Gray foreground
    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";

    // Bold + White foreground
    private static final String BOLD_WHITE = "\u001B[1;38;5;15m";

    // Reset
    private static final String RESET = "\u001B[0m";

    // Object Scanner untuk membaca input dari user melalui terminal
    static Scanner scanner = new Scanner(System.in);
    
    // Array untuk menyimpan daftar user dalam sistem
    static User[] users = new User[3];
    
    // Variable untuk menyimpan user yang sedang login
    static User currentUser = null;

    public static void main(String[] args) {
        // Tampilkan header ASCII art
        DisplayUtils.displayHeader();

        // INISIALISASI: Buat 2 object User untuk demo
        // User 1: Budi dengan ID U001, nomor 081234567890, saldo awal Rp 100.000
        users[0] = new User("U001", "Budi", "081234567890", 100000);
        
        // User 2: Siti dengan ID U002, nomor 089876543210, saldo awal Rp 200.000
        users[1] = new User("U002", "Siti", "089876543210", 200000);

        // User 3: LilBah dengan ID U003, nomor 089876543210, saldo awal Rp 300.000
        users[2] = new User("U003", "LilBah", "089876543210", 300000);

        // Tampilkan saldo awal semua user sebelum interaksi
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE +        "║ * SALDO AWAL                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Saldo awal di masing masing akun yang terdaftar             ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        users[0].showBalance();
        users[1].showBalance();
        users[2].showBalance();
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        // MAIN LOOP: Menu utama sistem yang terus berjalan sampai user exit
        // Loop ini menampilkan menu dan memproses pilihan user
        boolean isRunning = true;
        while (isRunning) {
            // Tampilkan menu utama dan dapatkan pilihan user
            int choice = displayMainMenu();
            
            // Proses pilihan user dengan switch-case
            switch (choice) {
                case 1:
                    // Pilihan 1: Login sebagai salah satu user
                    loginUser();
                    break;
                    
                case 2:
                    // Pilihan 2: Lihat Saldo
                    if (currentUser != null) {
                        currentUser.showBalance();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    break;
                    
                case 3:
                    // Pilihan 3: Top Up
                    if (currentUser != null) {
                        performTopUp();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    break;
                    
                case 4:
                    // Pilihan 4: Bayar
                    if (currentUser != null) {
                        performPayment();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    break;
                    
                case 5:
                    // Pilihan 5: Lihat Riwayat Transaksi (BARU MILESTONE 2)
                    if (currentUser != null) {
                        currentUser.showTransactionHistory();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    break;
                    
                case 6:
                    // Pilihan 6: Logout
                    if (currentUser != null) {
                        System.out.println("👋 " + currentUser.getName() + " logout. Sampai jumpa!");
                        currentUser = null;
                    } else {
                        System.out.println("ℹ️  Anda belum login.");
                    }
                    break;
                    
                case 0:
                    // Pilihan 0: Exit aplikasi
                    isRunning = false;
                    System.out.println("👋 Terima kasih telah menggunakan 67 Cents. Sampai jumpa!");
                    break;
                    
                default:
                    // Input tidak dikenali
                    System.out.println("✗ Pilihan tidak valid. Silakan coba lagi.");
            }
            
            // Beri jeda agar output lebih mudah dibaca
            System.out.println();
        }
        
        // Tutup scanner untuk menghindari memory leak
        scanner.close();
    }

    /**
     * Method untuk menampilkan menu utama
     * 
     * MILESTONE 2: Input validation - method ini akan terus meminta input
     * sampai user memberikan input yang valid (angka 0-6)
     * 
     * Keuntungan: Mencegah crash jika user input text bukan angka
     * 
     * @return int - pilihan menu dari user (0-6)
     */
    static int displayMainMenu() {
        // Tampilkan menu dengan formatted text
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE +        "║ * MENU UTAMA                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan pilih aktifitas yang ingin anda lakukan            ║" + RESET);
        System.out.println("╠══════════════════════════════╦════════════════════════════════╣");
        System.out.printf ("║ %-28s ║ %-30s ║%n", "1. Login", "4. Bayar");
        System.out.printf ("║ %-28s ║ %-30s ║%n", "2. Lihat Saldo", "5. Riwayat Transaksi (M2)");
        System.out.printf ("║ %-28s ║ %-30s ║%n", "3. Top Up", "6. Logout");
        System.out.printf ("║ %-28s ║ %-30s ║%n", "0. Exit", "");
        System.out.println("╚══════════════════════════════╩════════════════════════════════╝");
        System.out.print("Pilih menu: ");
        
        // VALIDASI MILESTONE 2: Cek apakah input adalah integer
        try {
            // Coba baca input sebagai integer
            int choice = scanner.nextInt();
            
            // Validasi range: hanya terima 0-6
            if (choice >= 0 && choice <= 6) {
                // Input valid - return pilihan
                return choice;
            } else {
                // Input di luar range yang diizinkan
                System.out.println("✗ Pilihan harus antara 0-6.");
                return displayMainMenu(); // Recursion: tampilkan menu lagi
            }
        } catch (java.util.InputMismatchException e) {
            // User input bukan integer (misal: "abc", "12a")
            System.out.println("✗ Input harus berupa angka. Silakan coba lagi.");
            scanner.nextLine(); // Bersihkan buffer input
            return displayMainMenu(); // Recursion: tampilkan menu lagi
        }
    }

    /**
     * Method untuk login sebagai user tertentu
     * 
     * Memberikan pilihan user yang tersedia untuk login
     * MILESTONE 2: Input validation pada pemilihan user
     */
    static void loginUser() {
        // Tampilkan list user yang tersedia
        System.out.println("\n=== LOGIN ===");
        System.out.println("Pilih user:");
        
        // Loop untuk menampilkan semua user yang tersedia
        for (int i = 0; i < users.length; i++) {
            // Tampilkan setiap user dengan nomor urut
            // Format: "1. Budi (U001)"
            System.out.println((i + 1) + ". " + users[i].getName() + " (" + users[i].getUserId() + ")");
        }
        
        System.out.print("Pilih nomor: ");
        
        // VALIDASI: Cek apakah input adalah integer
        try {
            // Baca input sebagai integer
            int choice = scanner.nextInt();
            
            // Validasi range: hanya terima 1 sampai jumlah user
            if (choice >= 1 && choice <= users.length) {
                // Input valid - set current user
                // choice-1 karena array index dimulai dari 0
                currentUser = users[choice - 1];
                System.out.println("✓ Login sebagai " + currentUser.getName() + " berhasil!");
            } else {
                // Pilihan di luar range
                System.out.println("✗ User tidak ditemukan.");
            }
        } catch (java.util.InputMismatchException e) {
            // User input bukan integer
            System.out.println("✗ Input harus berupa angka.");
            scanner.nextLine(); // Bersihkan buffer
        }
    }

    /**
     * Method untuk melakukan Top Up
     * 
     * Features:
     * - Input nominal yang akan di-top-up
     * - VALIDATION M2: Cek nominal harus angka desimal
     * - VALIDATION M2: Nominal harus positif (handled di User.topUp())
     * - Otomatis membuat Transaction record
     * - Menampilkan saldo setelah top-up
     */
    static void performTopUp() {
        // Tampilkan header untuk transaksi top-up
        System.out.println("\n=== TOP UP ===");
        System.out.println("User: " + currentUser.getName());
        System.out.print("Masukkan nominal top-up: Rp ");
        
        // VALIDASI M2: Cek apakah input adalah number
        try {
            // Baca input sebagai double (mendukung nominal dengan desimal)
            double amount = scanner.nextDouble();
            
            // Panggil method topUp dari User
            // Method ini sudah mempunyai validasi internal di User class
            currentUser.topUp(amount);
            
            // Tampilkan saldo terbaru setelah top-up
            currentUser.showBalance();
        } catch (java.util.InputMismatchException e) {
            // User input bukan angka
            System.out.println("✗ Nominal harus berupa angka.");
            scanner.nextLine(); // Bersihkan buffer input
        }
    }

    /**
     * Method untuk melakukan Pembayaran
     * 
     * Features:
     * - Input nominal yang akan dibayarkan
     * - VALIDATION M2: Cek nominal harus angka desimal
     * - VALIDATION M2: Nominal harus > 0 dan <= saldo (handled di User.pay())
     * - Otomatis membuat Transaction record (success atau failed)
     * - Menampilkan saldo setelah pembayaran
     */
    static void performPayment() {
        // Tampilkan header untuk transaksi pembayaran
        System.out.println("\n=== BAYAR ===");
        System.out.println("User: " + currentUser.getName());
        System.out.print("Masukkan nominal pembayaran: Rp ");
        
        // VALIDASI M2: Cek apakah input adalah number
        try {
            // Baca input sebagai double
            double amount = scanner.nextDouble();
            
            // Panggil method pay dari User
            // Method ini sudah mempunyai validasi internal
            currentUser.pay(amount);
            
            // Tampilkan saldo terbaru setelah pembayaran
            currentUser.showBalance();
        } catch (java.util.InputMismatchException e) {
            // User input bukan angka
            System.out.println("✗ Nominal harus berupa angka.");
            scanner.nextLine(); // Bersihkan buffer input
        }
    }
}

