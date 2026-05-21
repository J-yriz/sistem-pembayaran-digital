package com.payment.models.user;

public class RegularUser extends User {

    private static final double BALANCE_LIMIT = 2_000_000;
    private static final double CASHBACK_RATE = 0.01;

    public RegularUser(String userId, String name, String phone, double balance) {
        super(userId, name, phone, balance);
    }

    public RegularUser(String userId, String name, String phone, double balance, String pin) {
        super(userId, name, phone, balance, pin);
    }

    @Override
    public String getUserTypeKey() {
        return "REGULAR";
    }

    @Override
    public String getAccountType() {
        return "Regular User";
    }

    @Override
    public double getTransactionLimit() {
        return BALANCE_LIMIT;
    }

    @Override
    public double getCashbackRate() {
        return CASHBACK_RATE;
    }
}
