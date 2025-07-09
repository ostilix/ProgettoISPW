package com.nestaway.engineering.payment;

import java.security.SecureRandom;
import java.util.Random;

public class PayPalAPI extends PaymentAPI {

    public void processPayment(PaymentRequest request) {

        Random random = new SecureRandom();

        boolean paymentSuccessful = random.nextBoolean();
        if (request.getAmount() <= 0.0) {
            paymentResponse = false;
        } else {
            paymentResponse = paymentSuccessful;
        }

        notifyObservers();
    }

}
