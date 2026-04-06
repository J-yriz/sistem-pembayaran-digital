package com.payment.models;

public class User {
    private String userId;
    private String name;
    private String phone;
    private double balance;

    public User(String userId, String name, String phone, double balance) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
    }

    public void topUp(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(name + " top up Rp" + amount + " berhasil.");
        } else {
            System.out.println("Nominal top up harus lebih dari 0.");
        }
    }

    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Nominal pembayaran harus lebih dari 0.");
            return;
        }

        if (balance >= amount) {
            balance -= amount;
            System.out.println(name + " bayar Rp" + amount + " berhasil.");
        } else {
            System.out.println("Saldo " + name + " tidak cukup untuk bayar Rp" + amount + ".");
        }
    }

    public void showBalance() {
        System.out.println("Saldo " + name + " (" + userId + "): Rp" + balance);
    }
}
