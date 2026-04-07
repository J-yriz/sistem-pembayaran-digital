package com.payment.models;

/**
 * Payment adalah parent class untuk seluruh metode pembayaran pada milestone 3.
 */
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

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Menjalankan pembayaran dengan validasi dasar.
     */
    public void execute() {
        if (!validate()) {
            setStatus("FAILED");
            System.out.println("✗ Data transaksi tidak valid.");
            return;
        }

        if (amount > sender.getTransactionLimit()) {
            setStatus("FAILED");
            System.out.println("✗ Nominal melebihi limit transaksi akun " + sender.getAccountType() + ".");
            return;
        }

        if (!sender.hasSufficientBalance(amount)) {
            setStatus("FAILED");
            // Reuse validasi existing pada User agar history FAILED tetap tercatat.
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

    /**
     * Validasi dasar parent Payment.
     */
    public boolean validate() {
        return paymentId != null
            && !paymentId.isBlank()
            && amount > 0
            && sender != null
            && receiver != null
            && sender != receiver;
    }

    /**
     * Nama metode pembayaran default.
     */
    public String getPaymentMethod() {
        return "Generic Payment";
    }

    /**
     * Mencetak ringkasan transaksi setelah berhasil.
     */
    public void printReceipt() {
        System.out.println("\n=== RECEIPT PEMBAYARAN ===");
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Method     : " + getPaymentMethod());
        System.out.println("Sender     : " + sender.getName());
        System.out.println("Receiver   : " + receiver.getName());
        System.out.println("Amount     : Rp" + String.format("%,.0f", amount));
        System.out.println("Status     : " + status);
    }
}
