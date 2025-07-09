package com.nestaway.controller.app;

import com.nestaway.engineering.payment.Observer;
import com.nestaway.engineering.payment.PayPalAPI;
import com.nestaway.engineering.payment.PaymentAPI;
import com.nestaway.engineering.payment.PaymentRequest;
import com.nestaway.model.Host;

public class OnlinePaymentController extends Observer {

    private PaymentAPI paymentAPI;

    private Boolean response;

    protected boolean payPayPal(Host host, Double amount, String reason) {
        PaymentRequest request = new PaymentRequest(host.getInfoPayPal(), amount, reason);
        paymentAPI = new PayPalAPI();
        paymentAPI.registerObserver(this);
        long startTime = System.currentTimeMillis();
        long timeout = 60000L;

        paymentAPI.processPayment(request);
        while(response == null) {
            if(System.currentTimeMillis() - startTime > timeout) {
                return false;
            }
        }
        return response;
    }

    @Override
    protected void update(){
        if (paymentAPI != null){
            response = paymentAPI.getResponse();
        }
    }
}


