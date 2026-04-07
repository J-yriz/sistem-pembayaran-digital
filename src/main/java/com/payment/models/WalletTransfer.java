package com.payment.models;

/**
 * WalletTransfer adalah turunan Payment untuk transfer antar user di aplikasi.
 */
public class WalletTransfer extends Payment {

    private String destinationPhone;

    public WalletTransfer(String paymentId, double amount, User sender, User receiver, String destinationPhone) {
        super(paymentId, amount, sender, receiver);
        this.destinationPhone = destinationPhone;
    }

    public String getDestinationPhone() {
        return destinationPhone;
    }

    @Override
    public boolean validate() {
        return super.validate()
            && destinationPhone != null
            && destinationPhone.startsWith("08")
            && destinationPhone.length() >= 10;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getPaymentMethod() {
        return "Wallet Transfer";
    }
}
