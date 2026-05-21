package com.payment.models.promo;

/**
 * Abstract class Promo — setiap jenis promo memiliki cara menghitung manfaat berbeda.
 */
public abstract class Promo {

    private final String promoCode;
    private final String description;

    protected Promo(String promoCode, String description) {
        this.promoCode = promoCode;
        this.description = description;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getPromoType();

    public abstract boolean isApplicable(double amount);

    public abstract double adjustAmount(double amount);

    public abstract double adjustFee(double fee);
}
