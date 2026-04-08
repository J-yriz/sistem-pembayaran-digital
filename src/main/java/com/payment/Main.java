package com.payment;

import com.payment.models.User;
import com.payment.utils.DisplayUtils;

public class Main {
    public static void main(String[] args) {
        DisplayUtils.ASCIIArt(args);
        DisplayUtils.displayHeader();

        User user1 = new User("U001", "Budi", "081234567890", 100000);
        User user2 = new User("U002", "Siti", "089876543210", 200000);

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║ * SALDO AWAL                                                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        user1.showBalance();
        user2.showBalance();
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║ * TRANSAKSI USER 1                                            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        user1.topUp(50000);
        user1.pay(120000);
        user1.showBalance();
        System.out.println("Command executed, please wait for awhile...");
        System.out.println("─────────────────────────────────────────────────────────────────");

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║ * TRANSAKSI USER 2                                            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        user2.topUp(100000);
        user2.pay(350000);
        user2.showBalance();
        System.out.println("Command executed, please wait for awhile...");
        System.out.println("─────────────────────────────────────────────────────────────────");
    }
}
