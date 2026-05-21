package com.payment.services;

import com.payment.models.Transaction;
import com.payment.models.user.MerchantUser;
import com.payment.models.user.PremiumUser;
import com.payment.models.user.RegularUser;
import com.payment.models.user.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataStorage {

    private static final Path DATA_FILE = Path.of("data", "users_data.txt");

    private UserDataStorage() {
    }

    public static void save(User[] users) throws IOException {
        Files.createDirectories(DATA_FILE.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE)) {
            for (User user : users) {
                if (user == null) {
                    continue;
                }
                writer.write(formatUserLine(user));
                writer.newLine();

                for (Transaction tx : user.getTransactionHistory()) {
                    writer.write(formatTransactionLine(user.getUserId(), tx));
                    writer.newLine();
                }
            }
        }
    }

    public static User[] load() throws IOException {
        if (!Files.exists(DATA_FILE)) {
            return null;
        }

        Map<String, User> usersById = new HashMap<>();
        Map<String, List<Transaction>> transactionsByUser = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(DATA_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(";", -1);
                if (parts.length < 2) {
                    continue;
                }

                if ("USER".equals(parts[0])) {
                    User user = parseUser(parts);
                    if (user != null) {
                        usersById.put(user.getUserId(), user);
                        transactionsByUser.putIfAbsent(user.getUserId(), new ArrayList<>());
                    }
                } else if ("TX".equals(parts[0]) && parts.length >= 6) {
                    String userId = parts[1];
                    Transaction tx = Transaction.fromFileString(parts[2], parts[3], parts[4], parts[5]);
                    transactionsByUser.computeIfAbsent(userId, key -> new ArrayList<>()).add(tx);
                }
            }
        }

        if (usersById.isEmpty()) {
            return null;
        }

        User[] loadedUsers = new User[usersById.size()];
        int index = 0;
        for (User user : usersById.values()) {
            user.restoreState(
                transactionsByUser.getOrDefault(user.getUserId(), List.of())
            );
            loadedUsers[index++] = user;
        }

        return loadedUsers;
    }

    public static Path getDataFilePath() {
        return DATA_FILE;
    }

    private static String formatUserLine(User user) {
        return String.join(";",
            "USER",
            user.getUserTypeKey(),
            user.getUserId(),
            user.getName(),
            user.getPhone(),
            String.valueOf((long) user.getBalance()),
            user.getPinForStorage()
        );
    }

    private static String formatTransactionLine(String userId, Transaction tx) {
        return String.join(";",
            "TX",
            userId,
            tx.getTimestampForStorage(),
            String.valueOf((long) tx.getAmount()),
            tx.getType(),
            tx.getStatus()
        );
    }

    private static User parseUser(String[] parts) {
        if (parts.length < 7) {
            return null;
        }

        String type = parts[1];
        String userId = parts[2];
        String name = parts[3];
        String phone = parts[4];
        double balance = Double.parseDouble(parts[5]);
        String pin = parts[6];

        return switch (type) {
            case "REGULAR" -> new RegularUser(userId, name, phone, balance, pin);
            case "PREMIUM" -> new PremiumUser(userId, name, phone, balance, pin);
            case "MERCHANT" -> new MerchantUser(userId, name, phone, balance, pin);
            default -> null;
        };
    }
}
