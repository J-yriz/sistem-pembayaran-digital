package com.payment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDateTime timestamp;
    private double amount;
    private String type;
    private String status;

    public Transaction(double amount, String type, String status) {
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
        this.type = type;
        this.status = status;
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
