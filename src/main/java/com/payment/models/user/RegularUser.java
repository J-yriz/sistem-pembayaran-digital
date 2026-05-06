package com.payment.models.user;

public class RegularUser extends User {

    private static final double TRANSACTION_LIMIT = 2_000_000;
    private static final double CASHBACK_RATE = 0.01;

    public RegularUser(String userId, String name, String phone, double balance) {
        super(userId, name, phone, balance);
    }

    @Override
    public String getAccountType() {
        return "Regular User";
    }

    @Override
    public double getTransactionLimit() {
        return TRANSACTION_LIMIT;
    }

    @Override
    public double getCashbackRate() {
        return CASHBACK_RATE;
    }
}
