package com.payment.models.payment;

import com.payment.models.user.User;

public class QRPayment extends Payment {

    private String qrCode;

    public QRPayment(String paymentId, double amount, User sender, User receiver, String qrCode) {
        super(paymentId, amount, sender, receiver);
        this.qrCode = qrCode;
    }

    @Override
    protected boolean validate() {
        return super.validate()
            && qrCode != null
            && qrCode.startsWith("QR-")
            && qrCode.length() >= 6;
    }

    @Override
    protected String getPaymentMethod() {
        return "QR Payment";
    }
}
