package com.payment.utils;

/**
 * Class DisplayUtils - Lemari penyimpanan desain teks tata letak antar-muka untuk (ASCII UI/Print View).
 * 
 * Deskripsi Detail:
 * Membersihkan tanggung jawab mencetak baris pembatas '=== MENU ===' dan info visual success terminal dari Class Utama,
 * agar penatapan codingan di tempat class utama bersih berfokus ke perakitan logika flow menu utamanya.
 * 
 * Digunakan / Tampil Di:
 * - Rutin dimunculkan di metode interaktif `Main.java` (seperti saat runApplication, tampilkan menu).
 */
public class DisplayUtils {

    // Menggambar banner logo / Header identitas sambutan di awalan clear layar System Terminal.
    // Dipanggil di: Titik mula Loop While Menu `Main.runApplication()`
    public static void displayHeader() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   SISTEM PEMBAYARAN DIGITAL - v1.0     ║");
        System.out.println("║   Aplikasi CLI Sederhana untuk Pemula  ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    // Tampilkan menu utama
    public static void displayMainMenu() {
        System.out.println("\n=== MENU UTAMA ===");
        System.out.println("1. Kelola User");
        System.out.println("2. Lakukan Transfer");
        System.out.println("3. Lihat Riwayat Transaksi");
        System.out.println("4. Lihat Info User");
        System.out.println("5. Keluar");
    }

    // Tampilkan menu user
    public static void displayUserMenu() {
        System.out.println("\n=== KELOLA USER ===");
        System.out.println("1. Tambah User Baru");
        System.out.println("2. Lihat Semua User");
        System.out.println("3. Cari User");
        System.out.println("4. Kembali ke Menu Utama");
    }

    // Garis pemisah
    public static void displayDivider() {
        System.out.println("════════════════════════════════════════");
    }

    // Pesan kesuksesan
    public static void displaySuccess(String message) {
        System.out.println("\n✓ " + message);
    }

    // Pesan error
    public static void displayError(String message) {
        System.out.println("\n✗ " + message);
    }

    // Pesan info
    public static void displayInfo(String message) {
        System.out.println("\nℹ " + message);
    }
}
