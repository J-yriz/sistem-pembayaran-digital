package com.payment.models.user;

public class MerchantUser extends User {

    private static final double BALANCE_LIMIT = 50_000_000;
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
        return BALANCE_LIMIT;
    }

    @Override
    public double getCashbackRate() {
        return CASHBACK_RATE;
    }

    public void receivePayment(double amount) {
        receiveTransfer(amount);
    }
}
