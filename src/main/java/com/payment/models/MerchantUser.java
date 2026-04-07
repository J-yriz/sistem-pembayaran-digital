package com.payment.models;

/**
 * MerchantUser dipakai sebagai akun merchant yang menerima pembayaran dari user lain.
 */
public class MerchantUser extends User {

    private static final double TRANSACTION_LIMIT = 50_000_000;
    private static final double CASHBACK_RATE = 0.0;

    public MerchantUser(String userId, String name, String phone, double balance) {
        super(userId, name, phone, balance);
    }

    @Override
    public String getAccountType() {
        return "Merchant User";
    }

    @Override
    public double getTransactionLimit() {
        return TRANSACTION_LIMIT;
    }

    @Override
    public double getCashbackRate() {
        return CASHBACK_RATE;
    }

    /**
     * Method khusus merchant untuk menerima pembayaran.
     */
    public void receivePayment(double amount) {
        receiveTransfer(amount);
    }
}
