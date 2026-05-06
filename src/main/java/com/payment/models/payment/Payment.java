package com.payment.models.payment;

import com.payment.models.user.MerchantUser;
import com.payment.models.user.User;

public class Payment {

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

    private void setStatus(String status) {
        this.status = status;
    }

    public void execute() {
        if (!validate()) {
            setStatus("FAILED");
            System.out.println("Gagal: Data transaksi tidak valid.");
            return;
        }

        if (amount > sender.getTransactionLimit()) {
            setStatus("FAILED");
            System.out.println("Gagal: Nominal melebihi limit transaksi akun " + sender.getAccountType() + ".");
            return;
        }

        if (!sender.hasSufficientBalance(amount)) {
            setStatus("FAILED");
            sender.pay(amount);
            return;
        }

        sender.pay(amount);

        if (receiver instanceof MerchantUser) {
            ((MerchantUser) receiver).receivePayment(amount);
        } else {
            receiver.receiveTransfer(amount);
        }

        setStatus("SUCCESS");
        printReceipt();
    }

    protected boolean validate() {
        return paymentId != null
            && !paymentId.isBlank()
            && amount > 0
            && sender != null
            && receiver != null
            && sender != receiver;
    }

    protected String getPaymentMethod() {
        return "Generic Payment";
    }

    private void printReceipt() {
        System.out.println("\n=== RECEIPT PEMBAYARAN ===");
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Method     : " + getPaymentMethod());
        System.out.println("Sender     : " + sender.getName());
        System.out.println("Receiver   : " + receiver.getName());
        System.out.println("Amount     : Rp" + String.format("%,.0f", amount));
        System.out.println("Status     : " + status);
    }
}
