package com.payment.models;

/**
 * BankTransfer adalah turunan Payment untuk simulasi transfer antar rekening.
 */
public class BankTransfer extends Payment {

    private String bankName;
    private String destinationAccountNumber;

    public BankTransfer(
        String paymentId,
        double amount,
        User sender,
        User receiver,
        String bankName,
        String destinationAccountNumber
    ) {
        super(paymentId, amount, sender, receiver);
        this.bankName = bankName;
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    @Override
    public boolean validate() {
        return super.validate()
            && bankName != null
            && !bankName.isBlank()
            && destinationAccountNumber != null
            && !destinationAccountNumber.isBlank();
    }

    @Override
    public double calculateFee() {
        return 2_500;
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}
