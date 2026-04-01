package com.payment.utils;

/**
 * Class DisplayUtils - Bertanggung Jawab untuk Menampilkan Tampilan CLI yang Konsisten dan Estetis.
 * 
 * Deskripsi Detail:
 * Kelas ini menyimpan semua metode statis yang digunakan untuk menampilkan berbagai bagian
 * antarmuka pengguna di terminal. Dengan menggunakan ANSI color codes, kelas ini memberikan
 * tampilan yang lebih menarik dan mudah dibaca, serta menjaga konsistensi visual di seluruh aplikasi.
 */
public class DisplayUtils {

    // ANSI Color Codes
    private static final String ORANGE = "\u001B[38;5;214m";
    private static final String GREY = "\u001B[38;5;242m";
    private static final String GREEN = "\u001B[38;5;46m";
    private static final String RED = "\u001B[38;5;196m";
    private static final String RESET = "\u001B[0m";

    public static void displayHeader() {
        System.out.println();
        System.out.println(ORANGE + "██████╗ ███████╗    ██████╗ ███████╗███╗   ██╗████████╗███████╗" + RESET);
        System.out.println(ORANGE + "██╔════╝ ╚════██║    ██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝██╔════╝" + RESET);
        System.out.println(ORANGE + "███████╗     ██╔╝    ██║      █████╗  ██╔██╗ ██║   ██║   ███████╗" + RESET);
        System.out.println(ORANGE + "██╔═══██╗   ██╔╝     ██║      ██╔══╝  ██║╚██╗██║   ██║   ╚════██║" + RESET);
        System.out.println(ORANGE + "╚██████╔╝   ██║      ╚██████╗ ███████╗██║ ╚████║   ██║   ███████║" + RESET);
        System.out.println(ORANGE + " ╚═════╝    ╚═╝       ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚══════╝" + RESET);
        System.out.println();
        System.out.println(ORANGE + "┌─────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(ORANGE + "│ Wellcome to 67 Cents, the most broke app to move your money │" + RESET);
        System.out.println(ORANGE + "└─────────────────────────────────────────────────────────────┘" + RESET);
        System.out.println();
    }

    public static void displayMainMenu() {
        System.out.println(GREY + "Select an activity with your small pennies:" + RESET);
        System.out.println(RESET + "  1. Kelola User" + RESET);
        System.out.println(RESET + "  2. Lakukan Transfer");
        System.out.println(RESET + "  3. Lihat Riwayat Transaksi");
        System.out.println(RESET + "  4. Lihat Info User");
        System.out.println(RESET + "  5. Keluar");
        System.out.println();
    }

    public static void displayUserMenu() {
        System.out.println("\n" + ORANGE + "=== KELOLA USER ===" + RESET);
        System.out.println(RESET + "  1. Tambah User Baru" + RESET);
        System.out.println(RESET + "  2. Lihat Semua User");
        System.out.println(RESET + "  3. Cari User");
        System.out.println(RESET + "  4. Kembali ke Menu Utama");
        System.out.println();
    }

    public static void displayAccountInput() {
        System.out.println(GREY + "Masukkan ID Akun:" + RESET);
        System.out.print(ORANGE + "> " + RESET);
    }

    public static void displayContinuePrompt() {
        System.out.println();
        System.out.println(GREY + "Apakah Anda ingin melanjutkan?" + RESET);
        System.out.println("  " + GREEN + "1. Yes   " + RED + "2. No" + RESET);
        System.out.print(ORANGE + "> " + RESET);
    }


    public static void displayDivider() {
        System.out.println(GREY + "════════════════════════════════════════" + RESET);
    }

    public static void displaySuccess(String message) {
        System.out.println("\n" + GREEN + "✓ " + message + RESET);
    }

    public static void displayError(String message) {
        System.out.println("\n" + RED + "✗ " + message + RESET);
    }

    public static void displayInfo(String message) {
        System.out.println("\n" + GREY + "ℹ " + message + RESET);
    }
}