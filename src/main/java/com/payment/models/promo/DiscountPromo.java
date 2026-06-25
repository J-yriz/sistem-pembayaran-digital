package com.payment.models.promo;

public class DiscountPromo extends Promo {

    private final double discountRate;

    public DiscountPromo(String promoCode, double discountRate) {
        super(promoCode, "Diskon " + (discountRate * 100) + "% dari nominal pembayaran");
        this.discountRate = discountRate;
    }

    @Override
    public String getPromoType() {
        return "Discount Promo";
    }

    @Override
    public boolean isApplicable(double amount) {
          return amount >= 25_000;
    }  


    @Override
    public double adjustAmount(double amount) {
        return amount * (1 - discountRate);
    }

    @Override
    public double adjustFee(double fee) {
        return fee;
    }
}
