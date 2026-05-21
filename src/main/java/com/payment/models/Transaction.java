package com.payment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static final DateTimeFormatter STORAGE_FORMATTER =
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final LocalDateTime timestamp;
    private final double amount;
    private final String type;
    private final String status;

    public Transaction(double amount, String type, String status) {
        this(LocalDateTime.now(), amount, type, status);
    }

    public Transaction(LocalDateTime timestamp, double amount, String type, String status) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public static Transaction fromFileString(String timestamp, String amount, String type, String status) {
        LocalDateTime parsedTime = LocalDateTime.parse(timestamp, STORAGE_FORMATTER);
        return new Transaction(parsedTime, Double.parseDouble(amount), type, status);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestampForStorage() {
        return timestamp.format(STORAGE_FORMATTER);
    }

    public boolean isSuccessfulExpense() {
        return "SUCCESS".equals(status) && "PAY".equals(type);
    }

    public boolean isSuccessfulIncome() {
        return "SUCCESS".equals(status)
            && ("TOP_UP".equals(type) || "RECEIVE".equals(type) || "CASHBACK".equals(type));
    }

    @Override
    public String toString() {
        String formattedTime = timestamp.format(STORAGE_FORMATTER);

        String icon;
        if ("TOP_UP".equals(type)) {
            icon = "TOPUP";
        } else if ("RECEIVE".equals(type)) {
            icon = "TERIMA";
        } else if ("CASHBACK".equals(type)) {
            icon = "CASHBACK";
        } else if ("SPLIT_BILL".equals(type)) {
            icon = "SPLIT";
        } else {
            icon = "BAYAR";
        }

        String statusDisplay = status.equals("SUCCESS") ? "SUCCESS" : "FAILED";

        return String.format("[%s] %s Rp%.0f - %s",
                             formattedTime,
                             icon,
                             amount,
                             statusDisplay);
    }
}
