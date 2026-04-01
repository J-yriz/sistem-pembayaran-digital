package com.payment;

import com.payment.models.User;
import com.payment.services.UserService;
import com.payment.services.TransactionService;
import com.payment.utils.InputHandler;
import com.payment.utils.DisplayUtils;

/**
 * Class Main - Titik Awal Program / Gerbang Jalur Kontrol Layar CLI & Komando Sistem.
 * 
 * Deskripsi Detail:
 * Sangat sentral karna menyita alur Loop-tak-terbatas (Infinite Engine CLI),
 * yang mewayangkan `Display Utils`, menunggu jawaban via `InputHandler`, kemudian
 * menengahi jalannya penarikan/pemanggilan aksi terhadap `UserService` (memuat daftar user)
 * maupun interaksi `TransactionService` (mentransfer uang user tsb).
 */
public class Main {
    private static UserService userService;
    private static TransactionService transactionService;

    public static void main(String[] args) {
        // Melakukan Inject (Inisialisasi Pertama) service-service logic engine memori.
        userService = new UserService();
        transactionService = new TransactionService(userService);

        // Memompa sampel bawaan awal uji coba kedatabase ArrayList.
        initializeSampleData();

        // Mengunci Program menyalakan perulangan Papan Menu Menunggu interaksi Terminal.
        runApplication();
    }

    /**
     * Memasukkan list nasabah raksasa percobaan awal, biar aplikasi di startup sudah seru.
     * Mengkreasikan (Get Object) -> Dan Menambahkan ke memori aktif (via Method `userService.addUser()`).
     */
    private static void initializeSampleData() {
        userService.addUser(new User("user001", "Budi Santoso", "budi@email.com", 500000));
        userService.addUser(new User("user002", "Siti Nurhaliza", "siti@email.com", 750000));
        userService.addUser(new User("user003", "Ahmad Wijaya", "ahmad@email.com", 1000000));
    }

    /**
     * Jalankan loop aplikasi utama
     */
    private static void runApplication() {
        boolean running = true;

        while (running) {
            DisplayUtils.displayHeader();
            DisplayUtils.displayMainMenu();

            int choice = InputHandler.readMenuChoice(5);

            switch (choice) {
                case 1:
                    handleUserMenu();
                    break;
                case 2:
                    handleTransfer();
                    break;
                case 3:
                    handleTransactionHistory();
                    break;
                case 4:
                    handleUserInfo();
                    break;
                case 5:
                    running = false;
                    DisplayUtils.displaySuccess("Terima kasih! Program selesai.");
                    break;
                default:
                    DisplayUtils.displayError("Pilihan tidak valid");
            }
        }

        InputHandler.closeScanner();
    }

    /**
     * Handle menu kelola user
     */
    private static void handleUserMenu() {
        boolean inUserMenu = true;

        while (inUserMenu) {
            DisplayUtils.displayUserMenu();
            int choice = InputHandler.readMenuChoice(4);

            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    userService.displayAllUsers();
                    break;
                case 3:
                    searchUser();
                    break;
                case 4:
                    inUserMenu = false;
                    break;
            }
        }
    }

    /**
     * Tambah user baru
     */
    private static void addNewUser() {
        System.out.println("\n=== TAMBAH USER BARU ===");
        String id = InputHandler.readString("Masukkan ID User: ");
        String name = InputHandler.readString("Masukkan Nama: ");
        String email = InputHandler.readString("Masukkan Email: ");
        double balance = InputHandler.readDouble("Masukkan Saldo Awal (Rp): ");

        User newUser = new User(id, name, email, balance);
        userService.addUser(newUser);
    }

    /**
     * Cari user berdasarkan nama
     */
    private static void searchUser() {
        System.out.println("\n=== CARI USER ===");
        String name = InputHandler.readString("Masukkan Nama User: ");
        User user = userService.getUserByName(name);

        if (user != null) {
            System.out.println("\n" + user);
        } else {
            DisplayUtils.displayError("User tidak ditemukan");
        }
    }

    /**
     * Fungsi Pengendali Interaksi UI Transfer uang: Mengatur layar form secara kronologis melalui prompts CLI.
     * Dapat data dari ketikan User melalui permohonan `InputHandler.readXXX()`.
     * Menginstruksikannya (Data passing parameter) ke dalam Core logic Mutasinya (yaitu `transactionService.transfer()`).
     */
    private static void handleTransfer() {
        System.out.println("\n=== TRANSFER UANG ===");

        if (userService.getUserCount() < 2) {
            DisplayUtils.displayError("Minimal harus ada 2 user untuk melakukan transfer");
            return;
        }

        String fromUserId = InputHandler.readString("Masukkan ID Pengirim: ");
        String toUserId = InputHandler.readString("Masukkan ID Penerima: ");
        double amount = InputHandler.readDouble("Masukkan Jumlah Transfer (Rp): ");
        String description = InputHandler.readString("Masukkan Deskripsi (opsional): ");

        transactionService.transfer(fromUserId, toUserId, amount, description);
    }

    /**
     * Handle riwayat transaksi
     */
    private static void handleTransactionHistory() {
        System.out.println("\n=== RIWAYAT TRANSAKSI ===");
        System.out.println("1. Lihat Semua Transaksi");
        System.out.println("2. Lihat Transaksi User Spesifik");

        int choice = InputHandler.readMenuChoice(2);

        if (choice == 1) {
            transactionService.displayTransactionHistory();
        } else {
            String userId = InputHandler.readString("Masukkan ID User: ");
            transactionService.displayUserTransactions(userId);
        }
    }

    /**
     * Handle lihat info user
     */
    private static void handleUserInfo() {
        System.out.println("\n=== INFO USER ===");
        String userId = InputHandler.readString("Masukkan ID User: ");

        User user = userService.getUserById(userId);
        if (user != null) {
            System.out.println("\n" + user);
        } else {
            DisplayUtils.displayError("User tidak ditemukan");
        }
    }
}
