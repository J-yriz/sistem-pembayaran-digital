package com.payment.models.user;

public class PremiumUser extends User {

    private static final double BALANCE_LIMIT = 10_000_000;
    private static final double CASHBACK_RATE = 0.05;

    public PremiumUser(String userId, String name, String phone, double balance) {
        super(userId, name, phone, balance);
    }

    @Override
    public String getAccountType() {
        return "Premium User";
    }

    @Override
    public double getTransactionLimit() {
        return BALANCE_LIMIT;
    }

    @Override
    public double getCashbackRate() {
        return CASHBACK_RATE;
    }

    @Override
    public double calculateCashback(double transactionAmount) {
        return Math.min(super.calculateCashback(transactionAmount), 50_000);
    }
}
