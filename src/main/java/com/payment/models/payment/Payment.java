package com.payment.models.payment;

import com.payment.models.user.MerchantUser;
import com.payment.models.user.User;

public class Payment {

    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";
    private static final String BOLD_WHITE        = "\u001B[1;38;5;15m";
    private static final String RESET             = "\u001B[0m";

    private String paymentId;
    private double amount;
    private User sender;
    private User receiver;
    private String status;

    public Payment(String paymentId, double amount, User sender, User receiver) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.status = "PENDING";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public final void execute() {
        if (!validate()) {
            setStatus("FAILED");
            System.out.println("✗ Data transaksi tidak valid.");
            return;
        }

        double fee = calculateFee();
        double totalDebit = amount + fee;

        if (amount > sender.getTransactionLimit()) {
            setStatus("FAILED");
            System.out.println("✗ Nominal melebihi limit transaksi akun " + sender.getAccountType() + ".");
            return;
        }

        if (!sender.hasSufficientBalance(totalDebit)) {
            setStatus("FAILED");
            sender.pay(totalDebit);
            return;
        }

        sender.pay(totalDebit);

        if (receiver instanceof MerchantUser) {
            ((MerchantUser) receiver).receivePayment(amount);
        } else {
            receiver.receiveTransfer(amount);
        }

        double cashback = sender.calculateCashback(amount);
        if (cashback > 0) {
            sender.receiveCashback(cashback);
        }

        setStatus("SUCCESS");
        printReceipt();
    }

    public boolean validate() {
        return paymentId != null
            && !paymentId.isBlank()
            && amount > 0
            && sender != null
            && receiver != null
            && sender != receiver;
    }

    public double calculateFee() {
        return 0.0;
    }

    public String getPaymentMethod() {
        return "Generic Payment";
    }

    public void printReceipt() {
        double fee = calculateFee();
        double totalDebit = amount + fee;

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * RECEIPT PEMBAYARAN                                          ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║    Terima kasih telah menggunakan layanan kami                ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║    Payment ID : " + BOLD_WHITE + String.format("%-46s", paymentId) +  RESET + "║");
        System.out.println("║    Method     : " + BOLD_WHITE + String.format("%-46s", getPaymentMethod()) +  RESET + "║");
        System.out.println("║    Sender     : " + BOLD_WHITE + String.format("%-46s", sender.getName()) +  RESET + "║");
        System.out.println("║    Receiver   : " + BOLD_WHITE + String.format("%-46s", receiver.getName()) +  RESET + "║");
        System.out.println("║    Amount     : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", amount)) +  RESET + "║");
        System.out.println("║    Fee        : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", fee)) +  RESET + "║");
        System.out.println("║    Total Debit: Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", totalDebit)) +  RESET + "║");
        System.out.println("║    Cashback   : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", sender.calculateCashback(amount))) +  RESET + "║");
        System.out.println("║    Status     : " + BOLD_WHITE + String.format("%-46s", status) +  RESET + "║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
