package com.payment.utils;

import java.util.Scanner;

/**
 * Class InputHandler - Alat bantu pelindung Terminal / Papan Input Keyboard.
 * 
 * Deskripsi Detail:
 * Murni mengamankan class utama dari error format / infinite Buffer Loop.
 * Meng-enkapsulisasi object `Scanner` menggunakan while loop `try/catch` bawaannya, menolak huruf jika meminta angka.
 * 
 * Digunakan / Didapatkan Dari:
 * - Secara merata diandalkan seluruh logic Input di `Main.java` dalam menarik pilihan menu
 *   dan memasukkan argumen-argumen (ID String maupun Nominal Uang Saldo Double).
 */
public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    // Membuka interaksi pertanyaan ke terminal CLI berupa penerimaan huruf fleksibel (bebas format spasi ganda dlsb).
    // Digunakan di: Method form pendaftaran ID, pengisian Name, ID pengirim/penerima dan Desc ketika transfer.
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Baca input angka double
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = Double.parseDouble(scanner.nextLine().trim());
                if (input < 0) {
                    System.out.println("Masukkan angka yang valid (tidak negatif)");
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
            }
        }
    }

    // Baca input integer
    public static int readInteger(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka bulat.");
            }
        }
    }

    // Baca pilihan menu
    public static int readMenuChoice(int maxOption) {
        while (true) {
            int choice = readInteger("\nPilihan Anda: ");
            if (choice >= 1 && choice <= maxOption) {
                return choice;
            }
            System.out.println("Pilihan tidak valid. Masukkan angka 1-" + maxOption);
        }
    }

    // Tutup scanner
    public static void closeScanner() {
        scanner.close();
    }
}
