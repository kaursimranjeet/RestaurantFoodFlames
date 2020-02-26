package com.example.simran.anew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.simran.anew.Database.Database;
import com.example.simran.anew.Model.Order;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class Payment extends AppCompatActivity
{
    private double total = FinalOrder.getTotal();
    PayPalConfiguration configuration;
    String paypalClientId = "AZ5sOgWm7s3IxKiqvwuRe4BXo8edCvqljbZXF8NfhY5daIpmJYGbVe4WtJ9N-FqX5EEMi2wWDqh7VXqp"; //the id is the link to the paypal account , we have to create an app and get its id
    Intent service;
    TextView response;
    int paypalRequestCode = 777;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.payment);
            response = (TextView) findViewById(R.id.response);

            configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(paypalClientId);
            service = new Intent(this, PayPalService.class);
            service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
            startService(service);
        }

        void pay(View view) {

            PayPalPayment payment = new PayPalPayment(new BigDecimal (total), "AUD", "Order payment", PayPalPayment.PAYMENT_INTENT_SALE);


            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, paypalRequestCode);
        }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == paypalRequestCode)
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);


                if(confirmation !=null) {

                    String state = confirmation.getProofOfPayment().getState();
                    if(state.equals("approved"))
                    {
                        response.setText("payment approved");
                    }
                    else
                    {
                        response.setText("error in payment");
                    }
                }
                else {
                    response.setText("confirmation is null");
                }


                }

                }
            }

    }


