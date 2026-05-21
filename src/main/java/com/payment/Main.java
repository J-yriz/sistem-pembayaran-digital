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
    private static final String ITALIC_DARK_GRAY = "\u001B[3;38;5;59m";
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
                    if (currentUser == null) {
                        loginUser();
                    } else {
                        System.out.println("Info: Anda sudah login sebagai " + currentUser.getName() + ".");
                    }
                    break;
                case 2:
                    if (currentUser != null) {
                        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
                        currentUser.showBalance();
                        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                    }
                    break;
                case 3:
                    if (currentUser != null) {
                        performTopUp();
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    }
                    break;
                case 4:
                    if (currentUser != null) {
                        performPayment();
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    }
                    break;
                case 5:
                    if (currentUser != null) {
                        currentUser.showTransactionHistory();
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    } else {
                        System.out.println("Gagal: Silakan login terlebih dahulu.");
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    }
                    break;
                case 6:
                    if (currentUser != null) {
                        System.out.println("Logout: " + currentUser.getName() + " telah keluar. Sampai jumpa.");
                        currentUser = null;
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    } else {
                        System.out.println("Info: Anda belum login.");
                        System.out.println("─────────────────────────────────────────────────────────────────");
                    }
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Terima kasih telah menggunakan 67 Cents. Sampai jumpa!");
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
        System.out.print("\n \n");
        return readIntInRange("\u21A3 Pilih menu: ", 0, 6);
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(BOLD_WHITE + prompt + RESET);

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
        System.out.println("Input executed, please wait for awhile...");
        System.out.print("───────────────────────────────────────────────────────────────── \n \n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * PILIH AKUN                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan pilih akun untuk melanjutkan aktifitas             ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                               ║");

        for (int i = 0; i < users.length; i++) {
            String name = users[i].getName();
            String id = users[i].getUserId();
            String type = users[i].getAccountType();
            double capacity = users[i].getTransactionLimit();
            double cbRate = users[i].getCashbackRate() * 100;

            String leftText1 = (i + 1) + ". " + name;
            String rightText1 = "(" + id + ") - " + type;

            int spaces1 = 61 - leftText1.length() - rightText1.length();
            if (spaces1 < 1) spaces1 = 1;

            String rightText2 = String.format("Kapasitas: Rp %,.0f | CB: %.0f%%", capacity, cbRate);

            int spaces2 = 61 - rightText2.length();
            if (spaces2 < 1) spaces2 = 1;

            System.out.println(
                "║ "
                + (i + 1) + ". " + BOLD_WHITE + name + RESET
                + " ".repeat(spaces1)
                + ITALIC_DARK_GRAY + rightText1 + RESET
                + " ║"
            );

            System.out.println(
                "║ "
                + " ".repeat(spaces2)
                + rightText2
                + " ║"
            );

            if (i != users.length - 1) {
                System.out.println("║───────────────────────────────────────────────────────────────║");
            }

            if (i < users.length - 1) {
                System.out.println("║" + " ".repeat(63) + "║");
            }
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        int choice = readIntInRange("\n\u21A3 Pilih nomor: ", 1, users.length);
        currentUser = users[choice - 1];

        System.out.println("Berhasil: Login sebagai " + BOLD_WHITE + currentUser.getName() + RESET + ".");
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static void performTopUp() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * TOP UP SALDO                                                ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan ketik nominal yang anda inginkan                   ║" + RESET);
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        double amount = readPositiveAmount("Masukkan nominal top-up: Rp ");
        currentUser.topUp(amount);
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        currentUser.showBalance();
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    private static void performPayment() {
        String senderInfo = "Pengirim: " + currentUser.getName() + " (" + currentUser.getAccountType() + ")";
        String name = "║ " + String.format("%-61s", senderInfo) + " ║";
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * BAYAR / TRANSFER                                            ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Silahkan lengkapi form dibawah ini                          ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println(name);
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        User receiver = chooseReceiverUser();
        if (receiver == null) {
            return;
        }

        double amount = readPositiveAmount(BOLD_WHITE + "\u21B3 Masukkan nominal pembayaran: " + RESET + "Rp");
        Payment payment = createPayment(amount, receiver);

        if (payment == null) {
            return;
        }

        processPayment(payment);
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        currentUser.showBalance();
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    private static void processPayment(Payment payment) {
        if (payment == null) {
            System.out.println("Gagal: Pembayaran tidak dapat diproses.");
            return;
        }

        payment.execute();
    }

    private static User chooseReceiverUser() {
        System.out.println(BOLD_WHITE + "\u21B3 Pilih penerima:" + RESET);

        int optionNumber = 1;
        User[] receiverOptions = new User[users.length - 1];

        for (User user : users) {
            if (user == currentUser) {
                continue;
            }

            receiverOptions[optionNumber - 1] = user;
            String visibleText = optionNumber + ". " + user.getName() + " (" + user.getAccountType() + ")";
            int spaces = 61 - visibleText.length();
            if (spaces < 1) spaces = 1;
            String receiverInfo = "  " + optionNumber + ". " + BOLD_WHITE + user.getName() + RESET + " (" + user.getAccountType() + ")";
            System.out.println(receiverInfo + " ".repeat(spaces));
            optionNumber++;
        }

        int selectedIndex = readIntInRange(BOLD_WHITE + "\u21B3 Pilih nomor penerima: " + RESET, 1, receiverOptions.length);
        return receiverOptions[selectedIndex - 1];
    }

    private static Payment createPayment(double amount, User receiver) {
        System.out.println(BOLD_WHITE + "\u21B3 Pilih metode pembayaran:" + RESET);
        System.out.println("  1. Bank Transfer");
        System.out.println("  2. QR Payment");
        System.out.println("  3. Wallet Transfer");

        int paymentChoice = readIntInRange(BOLD_WHITE + "\u21B3 Pilih metode: " + RESET, 1, 3);
        String paymentId = "P" + System.currentTimeMillis();

        scanner.nextLine();

        switch (paymentChoice) {
            case 1:
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan nama bank: " + RESET);
                String bankName = scanner.nextLine();
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan nomor rekening tujuan: " + RESET);
                String destinationAccount = scanner.nextLine();
                return new BankTransfer(paymentId, amount, currentUser, receiver, bankName, destinationAccount);

            case 2:
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan kode QR (contoh: QR-STORE01): " + RESET);
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
