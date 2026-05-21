package com.payment.models.payment;

import com.payment.models.user.User;

public class WalletTransfer extends Payment {

    private final String destinationPhone;

    public WalletTransfer(String paymentId, double amount, User sender, User receiver, String destinationPhone) {
        super(paymentId, amount, sender, receiver);
        this.destinationPhone = destinationPhone;
    }

    public String getDestinationPhone() {
        return destinationPhone;
    }

    @Override
    public boolean validate() {
        return validateCommonFields()
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
