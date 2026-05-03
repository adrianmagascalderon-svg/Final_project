package com.mycompany.pethotel;

public class BookingPayment extends PaymentFramework {

    public BookingPayment(String transactionId, double baseAmount, double discountRate) {
        super(transactionId, baseAmount, discountRate);
    }

    @Override
    public boolean validatePayment() {
        return baseAmount > 0;
    }

    @Override
    public void finalizeTransaction() {
        transactionFinalized = true;
        System.out.println("Transaction completed.");
        System.out.printf("Final Amount: ₱%.2f\n", getFinalAmount());
    }

    @Override
    public String getPaymentContextLabel() {
        return "Booking Payment";
    }
}
