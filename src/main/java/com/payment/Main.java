package com.payment;

import com.payment.models.payment.BankTransfer;
import com.payment.models.payment.Payment;
import com.payment.models.payment.QRPayment;
import com.payment.models.payment.WalletTransfer;
import com.payment.models.user.MerchantUser;
import com.payment.models.user.PremiumUser;
import com.payment.models.user.RegularUser;
import com.payment.models.user.User;
import com.payment.utils.DisplayUtils;
import java.util.Scanner;

public class Main {
    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";

    private static final String BOLD_WHITE = "\u001B[1;38;5;15m";

    private static final String RESET = "\u001B[0m";

    private static final Scanner scanner = new Scanner(System.in);
    private static final User[] users = new User[3];
    private static User currentUser = null;

    public static void main(String[] args) {
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
                        currentUser.showBalance();
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                    }
                    break;
                case 3:
                    if (currentUser != null) {
                        performTopUp();
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                    }
                    break;
                case 4:
                    if (currentUser != null) {
                        performPayment();
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                    }
                    break;
                case 5:
                    if (currentUser != null) {
                        currentUser.showTransactionHistory();
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                    }
                    break;
                case 6:
                    if (currentUser != null) {
                        System.out.println("Logout: " + currentUser.getName() + " telah keluar. Sampai jumpa.");
                        currentUser = null;
                    } else {
                        System.out.println("Info: Anda belum login.");
                    }
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Terima kasih telah menggunakan 67 Cents. Sampai jumpa.");
                    break;
                default:
                    System.out.println("Gagal: Pilihan tidak valid. Silakan coba lagi.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void initializeUsers() {
        users[0] = new RegularUser("U001", "Budi", "081234567890", 100000);
        users[1] = new PremiumUser("U002", "Siti", "089876543210", 200000);
        users[2] = new MerchantUser("U003", "Toko Makmur", "081122334455", 300000);
    }

    private static void showInitialBalance() {
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

    private static int displayMainMenu() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * MENU UTAMA                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan pilih aktifitas yang ingin anda lakukan            ║" + RESET);
        System.out.println("╠══════════════════════════════╦════════════════════════════════╣");
        System.out.printf("║ %-28s ║ %-30s ║%n", "1. Login", "4. Bayar / Transfer");
        System.out.printf("║ %-28s ║ %-30s ║%n", "2. Lihat Saldo", "5. Riwayat Transaksi");
        System.out.printf("║ %-28s ║ %-30s ║%n", "3. Top Up", "6. Logout");
        System.out.printf("║ %-28s ║ %-30s ║%n", "0. Exit", "");
        System.out.println("╚══════════════════════════════╩════════════════════════════════╝");
        return readIntInRange("Pilih menu: ", 0, 6);
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);

            try {
                int value = scanner.nextInt();
                if (value < min || value > max) {
                    System.out.println("Gagal: Input harus antara " + min + " - " + max + ".");
                    continue;
                }
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Gagal: Input harus berupa angka.");
                scanner.nextLine();
            }
        }
    }

    private static void loginUser() {
        System.out.println("\n=== LOGIN ===");
        System.out.println("Pilih user:");

        for (int i = 0; i < users.length; i++) {
            System.out.println(
                (i + 1)
                    + ". "
                    + users[i].getName()
                    + " ("
                    + users[i].getUserId()
                    + ") - "
                    + users[i].getAccountType()
                    + " | Limit Rp"
                    + String.format("%,.0f", users[i].getTransactionLimit())
                    + " | Cashback "
                    + String.format("%.0f%%", users[i].getCashbackRate() * 100)
            );
        }

        int choice = readIntInRange("Pilih nomor: ", 1, users.length);
        currentUser = users[choice - 1];
        System.out.println("Login berhasil sebagai " + currentUser.getName() + ".");
    }

    private static void performTopUp() {
        System.out.println("\n=== TOP UP ===");
        System.out.println("User: " + currentUser.getName());

        double amount = readPositiveAmount("Masukkan nominal top-up: Rp ");
        currentUser.topUp(amount);
        currentUser.showBalance();
    }

    private static void performPayment() {
        System.out.println("\n=== BAYAR / TRANSFER ===");
        System.out.println("Pengirim: " + currentUser.getName() + " (" + currentUser.getAccountType() + ")");

        User receiver = chooseReceiverUser();
        if (receiver == null) {
            return;
        }

        double amount = readPositiveAmount("Masukkan nominal pembayaran: Rp ");
        Payment payment = createPayment(amount, receiver);

        if (payment == null) {
            return;
        }

        payment.execute();
        currentUser.showBalance();
    }

    private static User chooseReceiverUser() {
        System.out.println("Pilih penerima:");
        int optionNumber = 1;
        User[] receiverOptions = new User[users.length - 1];

        for (User user : users) {
            if (user == currentUser) {
                continue;
            }

            receiverOptions[optionNumber - 1] = user;
            System.out.println(optionNumber + ". " + user.getName() + " (" + user.getAccountType() + ")");
            optionNumber++;
        }

        int selectedIndex = readIntInRange("Pilih nomor penerima: ", 1, receiverOptions.length);
        return receiverOptions[selectedIndex - 1];
    }

    private static Payment createPayment(double amount, User receiver) {
        System.out.println("Pilih metode pembayaran:");
        System.out.println("1. Bank Transfer");
        System.out.println("2. QR Payment");
        System.out.println("3. Wallet Transfer");

        int paymentChoice = readIntInRange("Pilih metode: ", 1, 3);
        String paymentId = "P" + System.currentTimeMillis();

        scanner.nextLine();

        switch (paymentChoice) {
            case 1:
                System.out.print("Masukkan nama bank: ");
                String bankName = scanner.nextLine();
                System.out.print("Masukkan nomor rekening tujuan: ");
                String destinationAccount = scanner.nextLine();
                return new BankTransfer(paymentId, amount, currentUser, receiver, bankName, destinationAccount);

            case 2:
                System.out.print("Masukkan kode QR (contoh: QR-STORE01): ");
                String qrCode = scanner.nextLine();
                return new QRPayment(paymentId, amount, currentUser, receiver, qrCode);

            case 3:
                return new WalletTransfer(paymentId, amount, currentUser, receiver, receiver.getPhone());

            default:
                System.out.println("Gagal: Metode pembayaran tidak valid.");
                return null;
        }
    }

    private static double readPositiveAmount(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                double amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Gagal: Nominal harus lebih dari 0.");
                    continue;
                }
                return amount;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Gagal: Nominal harus berupa angka.");
                scanner.nextLine();
            }
        }
    }
}
