package com.payment.models.payment;

import com.payment.models.user.User;

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

    @Override
    protected boolean validate() {
        return super.validate()
            && bankName != null
            && !bankName.isBlank()
            && destinationAccountNumber != null
            && !destinationAccountNumber.isBlank();
    }

    @Override
    protected String getPaymentMethod() {
        return "Bank Transfer";
    }
}
