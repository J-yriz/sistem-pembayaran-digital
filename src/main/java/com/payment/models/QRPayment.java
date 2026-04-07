package com.payment.models;

/**
 * QRPayment adalah turunan Payment untuk simulasi pembayaran menggunakan QR.
 */
public class QRPayment extends Payment {

    private String qrCode;

    public QRPayment(String paymentId, double amount, User sender, User receiver, String qrCode) {
        super(paymentId, amount, sender, receiver);
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    @Override
    public boolean validate() {
        return super.validate()
            && qrCode != null
            && qrCode.startsWith("QR-")
            && qrCode.length() >= 6;
    }

    @Override
    public double calculateFee() {
        if (getAmount() < 100_000) {
            return 0.0;
        }
        return getAmount() * 0.007;
    }

    @Override
    public String getPaymentMethod() {
        return "QR Payment";
    }
}
