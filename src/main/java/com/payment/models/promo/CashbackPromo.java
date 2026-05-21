package com.payment.models.promo;

public class CashbackPromo extends Promo {

    private final double bonusCashbackRate;

    public CashbackPromo(String promoCode, double bonusCashbackRate) {
        super(promoCode, "Bonus cashback +" + (bonusCashbackRate * 100) + "% dari nominal");
        this.bonusCashbackRate = bonusCashbackRate;
    }

    @Override
    public String getPromoType() {
        return "Cashback Promo";
    }

    @Override
    public boolean isApplicable(double amount) {
        return amount >= 50_000;
    }

    @Override
    public double adjustAmount(double amount) {
        return amount;
    }

    @Override
    public double adjustFee(double fee) {
        return fee;
    }

    public double calculateBonusCashback(double amount) {
        if (!isApplicable(amount)) {
            return 0.0;
        }
        return amount * bonusCashbackRate;
    }
}
