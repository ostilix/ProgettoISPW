package com.nestaway.engineering.payment;

public class PaymentRequest {
    private String recipient;
    private Double amount;
    private String reason;

    public PaymentRequest(String recipient, Double amount, String reason) {
        this.recipient = recipient;
        this.amount = amount;
        this.reason = reason;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
