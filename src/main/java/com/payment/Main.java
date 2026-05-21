package com.payment;

import com.payment.models.payment.BankTransfer;
import com.payment.models.payment.Payment;
import com.payment.models.payment.QRPayment;
import com.payment.models.payment.WalletTransfer;
import com.payment.models.promo.CashbackPromo;
import com.payment.models.promo.DiscountPromo;
import com.payment.models.promo.Promo;
import com.payment.models.user.MerchantUser;
import com.payment.models.user.PremiumUser;
import com.payment.models.user.RegularUser;
import com.payment.models.user.User;
import com.payment.services.UserDataStorage;
import com.payment.utils.DisplayUtils;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";
    private static final String ITALIC_DARK_GRAY = "\u001B[3;38;5;59m";
    private static final String BOLD_WHITE = "\u001B[1;38;5;15m";
    private static final String RESET = "\u001B[0m";

    private static final Scanner scanner = new Scanner(System.in);
    private static User[] users = new User[0];
    private static User currentUser = null;

    public static void main(String[] args) {
        DisplayUtils.displayHeader();
        initializeUsers();
        showInitialBalance();

        boolean isRunning = true;
        while (isRunning) {
            int choice = displayMainMenu();

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> requireLogin(() -> showBalance());
                case 3 -> requireLogin(() -> performTopUp());
                case 4 -> requireLogin(() -> performPayment());
                case 5 -> requireLogin(() -> {
                    currentUser.showTransactionHistory();
                    System.out.println("─────────────────────────────────────────────────────────────────");
                });
                case 6 -> requireLogin(() -> performSplitBill());
                case 7 -> requireLogin(() -> {
                    currentUser.showMonthlyFinancialReport();
                    System.out.println("─────────────────────────────────────────────────────────────────");
                });
                case 8 -> saveUserData();
                case 9 -> loadUserData();
                case 10 -> handleLogout();
                case 0 -> {
                    isRunning = false;
                    System.out.println("Terima kasih telah menggunakan 67 Cents. Sampai jumpa!");
                }
                default -> System.out.println("Gagal: Pilihan tidak valid. Silakan coba lagi.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void initializeUsers() {
        try {
            User[] loaded = UserDataStorage.load();
            if (loaded != null && loaded.length > 0) {
                users = loaded;
                System.out.println("Info: Data user dimuat dari " + UserDataStorage.getDataFilePath() + ".");
                return;
            }
        } catch (IOException e) {
            System.out.println("Info: Gagal memuat data, menggunakan data default.");
        }

        users = new User[] {
            new RegularUser("U001", "Budi", "081234567890", 100000),
            new PremiumUser("U002", "Siti", "089876543210", 200000),
            new MerchantUser("U003", "Toko Makmur", "081122334455", 300000)
        };
    }

    private static void showInitialBalance() {
        if (users.length == 0) {
            return;
        }

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * SALDO AWAL                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Saldo awal di masing masing akun yang terdaftar             ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        for (User user : users) {
            user.showBalance();
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    private static int displayMainMenu() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * MENU UTAMA (M6)                                             ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   PIN default: 1234 | Promo, Split Bill, Save/Load            ║" + RESET);
        System.out.println("╠══════════════════════════════╦════════════════════════════════╣");
        System.out.printf("║ %-28s ║ %-30s ║%n", "1. Login", "6. Split Bill");
        System.out.printf("║ %-28s ║ %-30s ║%n", "2. Lihat Saldo", "7. Laporan Bulanan");
        System.out.printf("║ %-28s ║ %-30s ║%n", "3. Top Up", "8. Simpan Data");
        System.out.printf("║ %-28s ║ %-30s ║%n", "4. Bayar / Transfer", "9. Muat Data");
        System.out.printf("║ %-28s ║ %-30s ║%n", "5. Riwayat Transaksi", "10. Logout");
        System.out.printf("║ %-28s ║ %-30s ║%n", "0. Exit", "");
        System.out.println("╚══════════════════════════════╩════════════════════════════════╝");
        System.out.print("\n \n");
        return readIntInRange("\u21A3 Pilih menu: ", 0, 10);
    }

    private static void handleLogin() {
        if (currentUser != null) {
            System.out.println("Info: Anda sudah login sebagai " + currentUser.getName() + ".");
            return;
        }
        loginUser();
    }

    private static void handleLogout() {
        if (currentUser != null) {
            System.out.println("Logout: " + currentUser.getName() + " telah keluar. Sampai jumpa.");
            currentUser = null;
            System.out.println("─────────────────────────────────────────────────────────────────");
        } else {
            System.out.println("Info: Anda belum login.");
            System.out.println("─────────────────────────────────────────────────────────────────");
        }
    }

    private static void requireLogin(Runnable action) {
        if (currentUser == null) {
            System.out.println("Gagal: Silakan login terlebih dahulu.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }
        action.run();
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
        User selectedUser = users[choice - 1];

        if (!verifyPinForUser(selectedUser)) {
            System.out.println("Gagal: Login dibatalkan karena PIN salah.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

        currentUser = selectedUser;
        System.out.println("Berhasil: Login sebagai " + BOLD_WHITE + currentUser.getName() + RESET + ".");
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static boolean verifyPinForUser(User user) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print(BOLD_WHITE + "\u21A3 Masukkan PIN (percobaan " + attempt + "/3): " + RESET);
            String pin = scanner.next();
            if (user.verifyPin(pin)) {
                return true;
            }
            System.out.println("Gagal: PIN tidak sesuai.");
        }
        return false;
    }

    private static boolean verifyCurrentUserPin() {
        return verifyPinForUser(currentUser);
    }

    private static void showBalance() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        currentUser.showBalance();
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static void performTopUp() {
        if (!verifyCurrentUserPin()) {
            System.out.println("Gagal: Top up dibatalkan.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

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
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static void performPayment() {
        if (!verifyCurrentUserPin()) {
            System.out.println("Gagal: Pembayaran dibatalkan.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

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

        Promo promo = choosePromo(amount);
        if (promo != null) {
            payment.setPromo(promo);
        }

        processPayment(payment);
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        currentUser.showBalance();
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static Promo choosePromo(double amount) {
        System.out.println(BOLD_WHITE + "\u21B3 Gunakan promo?" + RESET);
        System.out.println("  0. Tidak");
        System.out.println("  1. DISKON10 (diskon 10%, min Rp25.000)");
        System.out.println("  2. CBEXTRA (bonus cashback 2%, min Rp50.000)");

        int promoChoice = readIntInRange(BOLD_WHITE + "\u21B3 Pilih promo: " + RESET, 0, 2);

        return switch (promoChoice) {
            case 1 -> new DiscountPromo("DISKON10", 0.10);
            case 2 -> new CashbackPromo("CBEXTRA", 0.02);
            default -> null;
        };
    }

    private static void performSplitBill() {
        if (!verifyCurrentUserPin()) {
            System.out.println("Gagal: Split bill dibatalkan.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * SPLIT BILL                                                  ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║   Bagi tagihan dengan peserta lain                            ║" + RESET);
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        double totalBill = readPositiveAmount(BOLD_WHITE + "\u21B3 Total tagihan: Rp " + RESET);
        int participants = readIntInRange(BOLD_WHITE + "\u21B3 Jumlah peserta (termasuk Anda): " + RESET, 2, users.length);

        double sharePerPerson = totalBill / participants;
        System.out.printf("Info: Bagian per orang = Rp %,.0f%n", sharePerPerson);

        if (!currentUser.hasSufficientBalance(sharePerPerson)) {
            System.out.println("Gagal: Saldo Anda tidak cukup untuk bagian Anda.");
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

        currentUser.pay(sharePerPerson);
        System.out.println("Berhasil: Anda membayar bagian Rp" + (long) sharePerPerson + ".");

        int othersToCollect = participants - 1;
        if (othersToCollect <= 0) {
            System.out.println("─────────────────────────────────────────────────────────────────");
            return;
        }

        System.out.println(BOLD_WHITE + "\u21B3 Pilih " + othersToCollect + " peserta untuk menagih bagian mereka:" + RESET);

        User[] selectedOthers = new User[othersToCollect];
        for (int i = 0; i < othersToCollect; i++) {
            User payer = chooseSplitBillPayer(selectedOthers, i + 1);
            if (payer == null) {
                System.out.println("Gagal: Pemilihan peserta dibatalkan.");
                System.out.println("─────────────────────────────────────────────────────────────────");
                return;
            }
            selectedOthers[i] = payer;

            if (!payer.hasSufficientBalance(sharePerPerson)) {
                System.out.println("Gagal: Saldo " + payer.getName() + " tidak cukup.");
                payer.pay(sharePerPerson);
                continue;
            }

            payer.pay(sharePerPerson);
            currentUser.receiveTransfer(sharePerPerson);
            System.out.println("Berhasil: " + payer.getName() + " membayar bagian Rp" + (long) sharePerPerson + ".");
        }

        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static User chooseSplitBillPayer(User[] alreadySelected, int round) {
        System.out.println(BOLD_WHITE + "\u21B3 Peserta ke-" + round + ":" + RESET);

        int optionNumber = 1;
        User[] options = new User[users.length - 1];
        int optionIndex = 0;

        for (User user : users) {
            if (user == currentUser || isAlreadySelected(user, alreadySelected)) {
                continue;
            }
            options[optionIndex++] = user;
            System.out.println("  " + optionNumber + ". " + user.getName() + " (" + user.getAccountType() + ")");
            optionNumber++;
        }

        if (optionIndex == 0) {
            return null;
        }

        int selected = readIntInRange(BOLD_WHITE + "\u21B3 Pilih nomor: " + RESET, 1, optionIndex);
        return options[selected - 1];
    }

    private static boolean isAlreadySelected(User user, User[] selected) {
        if (selected == null) {
            return false;
        }
        for (User candidate : selected) {
            if (candidate == user) {
                return true;
            }
        }
        return false;
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
            String receiverInfo = "  " + optionNumber + ". " + BOLD_WHITE + user.getName() + RESET + " (" + user.getAccountType() + ")";
            System.out.println(receiverInfo);
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

        return switch (paymentChoice) {
            case 1 -> {
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan nama bank: " + RESET);
                String bankName = scanner.nextLine();
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan nomor rekening tujuan: " + RESET);
                String destinationAccount = scanner.nextLine();
                yield new BankTransfer(paymentId, amount, currentUser, receiver, bankName, destinationAccount);
            }
            case 2 -> {
                System.out.print(BOLD_WHITE + "\u21B3 Masukkan kode QR (contoh: QR-STORE01): " + RESET);
                String qrCode = scanner.nextLine();
                yield new QRPayment(paymentId, amount, currentUser, receiver, qrCode);
            }
            case 3 -> new WalletTransfer(paymentId, amount, currentUser, receiver, receiver.getPhone());
            default -> {
                System.out.println("Gagal: Metode pembayaran tidak valid.");
                yield null;
            }
        };
    }

    private static void saveUserData() {
        try {
            UserDataStorage.save(users);
            System.out.println("Berhasil: Data disimpan ke " + UserDataStorage.getDataFilePath() + ".");
        } catch (IOException e) {
            System.out.println("Gagal: Tidak dapat menyimpan data (" + e.getMessage() + ").");
        }
        System.out.println("─────────────────────────────────────────────────────────────────");
    }

    private static void loadUserData() {
        try {
            User[] loaded = UserDataStorage.load();
            if (loaded == null || loaded.length == 0) {
                System.out.println("Gagal: File data kosong atau belum ada.");
            } else {
                users = loaded;
                currentUser = null;
                System.out.println("Berhasil: " + users.length + " akun dimuat dari file.");
                showInitialBalance();
            }
        } catch (IOException e) {
            System.out.println("Gagal: Tidak dapat memuat data (" + e.getMessage() + ").");
        }
        System.out.println("─────────────────────────────────────────────────────────────────");
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
