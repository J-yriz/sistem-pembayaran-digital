package com.payment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final LocalDateTime timestamp;
    private final double amount;
    private final String type;
    private final String status;

    public Transaction(double amount, String type, String status) {
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
        this.type = type;
        this.status = status;
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = timestamp.format(formatter);

        String icon;
        if ("TOP_UP".equals(type)) {
            icon = "TOPUP";
        } else if ("RECEIVE".equals(type)) {
            icon = "TERIMA";
        } else if ("CASHBACK".equals(type)) {
            icon = "CASHBACK";
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
