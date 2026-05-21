package com.payment.models.payment;

import com.payment.models.promo.CashbackPromo;
import com.payment.models.promo.Promo;
import com.payment.models.user.MerchantUser;
import com.payment.models.user.User;

/**
 * Abstract class Payment — tidak dapat di-instantiate langsung karena setiap
 * metode pembayaran memiliki perhitungan biaya dan validasi berbeda. Subclass
 * wajib mengimplementasikan {@link #calculateFee()}, {@link #validate()}, dan
 * {@link #getPaymentMethod()}.
 */
public abstract class Payment {

    private static final String ITALIC_LIGHT_GRAY = "\u001B[3;38;5;250m";
    private static final String BOLD_WHITE        = "\u001B[1;38;5;15m";
    private static final String RESET             = "\u001B[0m";

    private String paymentId;
    private double amount;
    private User sender;
    private User receiver;
    private String status;
    private Promo promo;

    public Payment(String paymentId, double amount, User sender, User receiver) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.status = "PENDING";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public Promo getPromo() {
        return promo;
    }

    public final void execute() {
        if (!validate()) {
            setStatus("FAILED");
            System.out.println("Gagal: Data transaksi tidak valid.");
            return;
        }

        double effectiveAmount = amount;
        double fee = calculateFee();

        if (promo != null) {
            if (!promo.isApplicable(amount)) {
                setStatus("FAILED");
                System.out.println("Gagal: Promo tidak berlaku untuk nominal ini.");
                return;
            }
            effectiveAmount = promo.adjustAmount(amount);
            fee = promo.adjustFee(fee);
            System.out.println("Info: Promo " + promo.getPromoCode() + " (" + promo.getPromoType() + ") diterapkan.");
        }

        double totalDebit = effectiveAmount + fee;
        double cashback = sender.calculateCashback(effectiveAmount);

        if (promo instanceof CashbackPromo cashbackPromo) {
            cashback += cashbackPromo.calculateBonusCashback(effectiveAmount);
        }

        if (!receiver.canHoldAdditional(effectiveAmount)) {
            setStatus("FAILED");
            System.out.println("Gagal: Saldo penerima melebihi batas kapasitas dompet.");
            return;
        }

        if (!sender.hasSufficientBalance(totalDebit)) {
            setStatus("FAILED");
            sender.pay(totalDebit);
            return;
        }

        double balanceAfterPayment = sender.getBalance() - totalDebit;
        if (cashback > 0 && balanceAfterPayment + cashback > sender.getTransactionLimit()) {
            setStatus("FAILED");
            System.out.println("Gagal: Cashback melebihi batas kapasitas dompet pengirim.");
            return;
        }

        sender.pay(totalDebit);

        if (receiver instanceof MerchantUser) {
            ((MerchantUser) receiver).receivePayment(effectiveAmount);
        } else {
            receiver.receiveTransfer(effectiveAmount);
        }

        if (cashback > 0) {
            sender.receiveCashback(cashback);
        }

        setStatus("SUCCESS");
        printReceipt();
    }

    protected boolean validateCommonFields() {
        return paymentId != null
            && !paymentId.isBlank()
            && amount > 0
            && sender != null
            && receiver != null
            && sender != receiver;
    }

    public abstract boolean validate();

    public abstract double calculateFee();

    public abstract String getPaymentMethod();

    public void printReceipt() {
        double effectiveAmount = amount;
        double fee = calculateFee();
        double promoCashback = 0;

        if (promo != null && promo.isApplicable(amount)) {
            effectiveAmount = promo.adjustAmount(amount);
            fee = promo.adjustFee(fee);
            if (promo instanceof CashbackPromo cashbackPromo) {
                promoCashback = cashbackPromo.calculateBonusCashback(effectiveAmount);
            }
        }

        double totalDebit = effectiveAmount + fee;
        double totalCashback = sender.calculateCashback(effectiveAmount) + promoCashback;

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println(BOLD_WHITE + "║ * RECEIPT PEMBAYARAN                                          ║" + RESET);
        System.out.println(ITALIC_LIGHT_GRAY + "║    Terima kasih telah menggunakan layanan kami                ║" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║    Payment ID : " + BOLD_WHITE + String.format("%-46s", paymentId) +  RESET + "║");
        System.out.println("║    Method     : " + BOLD_WHITE + String.format("%-46s", getPaymentMethod()) +  RESET + "║");
        System.out.println("║    Sender     : " + BOLD_WHITE + String.format("%-46s", sender.getName()) +  RESET + "║");
        System.out.println("║    Receiver   : " + BOLD_WHITE + String.format("%-46s", receiver.getName()) +  RESET + "║");
        System.out.println("║    Amount     : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", effectiveAmount)) +  RESET + "║");
        System.out.println("║    Fee        : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", fee)) +  RESET + "║");
        System.out.println("║    Total Debit: Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", totalDebit)) +  RESET + "║");
        System.out.println("║    Cashback   : Rp" + BOLD_WHITE + String.format("%-44s", String.format("%,.0f", totalCashback)) +  RESET + "║");
        if (promo != null) {
            System.out.println("║    Promo      : " + BOLD_WHITE + String.format("%-46s", promo.getPromoCode()) +  RESET + "║");
        }
        System.out.println("║    Status     : " + BOLD_WHITE + String.format("%-46s", status) +  RESET + "║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
