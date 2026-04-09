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
    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";
    private static final String BOLD_WHITE = "\u001B[1;38;5;15m";
    private static final String RESET = "\u001B[0m";

    static Scanner scanner = new Scanner(System.in);
    static User[] users = new User[3];
    static User currentUser = null;

    public static void main(String[] args) {
        DisplayUtils.ASCIIArt(args);
        DisplayUtils.displayHeader();
        initializeUsers();
        showInitialBalance();

        boolean isRunning = true;
        while (isRunning) {
            int choice = displayMainMenu();

            switch (choice) {
                case 1:
                    loginUser();
                    
                    break;
                case 2:
                    if (currentUser != null) {
                        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
                        currentUser.showBalance();
                        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    
                    break;
                case 3:
                    if (currentUser != null) {
                        performTopUp();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    
                    break;
                case 4:
                    if (currentUser != null) {
                        performPayment();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    
                    break;
                case 5:
                    if (currentUser != null) {
                        currentUser.showTransactionHistory();
                    } else {
                        System.out.println("✗ Silakan login terlebih dahulu.");
                    }
                    
                    break;
                case 6:
                    if (currentUser != null) {
                        System.out.println("👋 " + currentUser.getName() + " logout. Sampai jumpa!");
                        currentUser = null;
                    } else {
                        System.out.println("ℹ️  Anda belum login.");
                    }
                    
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("👋 Terima kasih telah menggunakan 67 Cents. Sampai jumpa!");
                    break;
                default:
                    System.out.println("✗ Pilihan tidak valid. Silakan coba lagi.");
            }

            System.out.println();
        }

        scanner.close();
    }

    static void initializeUsers() {
        users[0] = new User("U001", "Budi", "081234567890", 100000);
        users[1] = new User("U002", "Siti", "089876543210", 200000);
        users[2] = new User("U003", "LilBah", "089876543210", 300000);
    }

    static void showInitialBalance() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * SALDO AWAL                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Saldo awal di masing masing akun yang terdaftar             ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        users[0].showBalance();
        users[1].showBalance();
        users[2].showBalance();
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    static int displayMainMenu() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * MENU UTAMA                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan pilih aktifitas yang ingin anda lakukan            ║" + RESET);
        System.out.println("╠══════════════════════════════╦════════════════════════════════╣");
        System.out.printf("║ %-28s ║ %-30s ║%n", "1. Login", "4. Bayar");
        System.out.printf("║ %-28s ║ %-30s ║%n", "2. Lihat Saldo", "5. Riwayat Transaksi");
        System.out.printf("║ %-28s ║ %-30s ║%n", "3. Top Up", "6. Logout");
        System.out.printf("║ %-28s ║ %-30s ║%n", "0. Exit", "");
        System.out.println("╚══════════════════════════════╩════════════════════════════════╝");
        return readIntInRange("↳ Pilih menu: ", 0, 6);
    }

    static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(BOLD_WHITE + prompt + RESET);
            try {
                int value = scanner.nextInt();
                if (value < min || value > max) {
                    System.out.println("✗ Input harus antara " + min + " - " + max + ".");
                    continue;
                }
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("✗ Input harus berupa angka.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Method untuk login sebagai user tertentu
     * 
     * Memberikan pilihan user yang tersedia untuk login
     * MILESTONE 2: Input validation pada pemilihan user
     */
    static void loginUser() {
        System.out.println("\n=== LOGIN ===");
        System.out.println("Pilih user:");
        for (int i = 0; i < users.length; i++) {
            System.out.println((i + 1) + ". " + users[i].getName() + " (" + users[i].getUserId() + ")");
        }

        int choice = readIntInRange("↳ Pilih nomor: ", 1, users.length);
        currentUser = users[choice - 1];
        System.out.println("✓ Login sebagai " + currentUser.getName() + " berhasil!");
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
        System.out.println("\n=== TOP UP ===");
        System.out.println("User: " + currentUser.getName());
        System.out.print("↳ Masukkan nominal top-up: Rp ");

        try {
            double amount = scanner.nextDouble();
            currentUser.topUp(amount);
            currentUser.showBalance();
        } catch (java.util.InputMismatchException e) {
            System.out.println("✗ Nominal harus berupa angka.");
            scanner.nextLine();
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
        System.out.println("\n=== BAYAR ===");
        System.out.println("User: " + currentUser.getName());
        System.out.print("↳ Masukkan nominal pembayaran: Rp ");

        try {
            double amount = scanner.nextDouble();
            currentUser.pay(amount);
            currentUser.showBalance();
        } catch (java.util.InputMismatchException e) {
            System.out.println("✗ Nominal harus berupa angka.");
            scanner.nextLine();
        }
    }
}

