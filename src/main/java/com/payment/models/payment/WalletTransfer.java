package com.payment.models.payment;

import com.payment.models.user.User;

public class WalletTransfer extends Payment {

    private String destinationPhone;

    public WalletTransfer(String paymentId, double amount, User sender, User receiver, String destinationPhone) {
        super(paymentId, amount, sender, receiver);
        this.destinationPhone = destinationPhone;
    }

    @Override
    protected boolean validate() {
        return super.validate()
            && destinationPhone != null
            && destinationPhone.startsWith("08")
            && destinationPhone.length() >= 10;
    }

    @Override
    protected String getPaymentMethod() {
        return "Wallet Transfer";
    }
}
