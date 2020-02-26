package com.example.simran.anew;


import android.app.PendingIntent;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RequestStaff extends AppCompatActivity{

    Button btnSend;
    Button plcOrder;
    EditText tvMessage;
    EditText tvNumber;

    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView inTxt = (TextView) findViewById(R.id.textMsg);
            //message goes here
            inTxt.setText(intent.getExtras().getString("message"));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_staff);

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        btnSend = (Button) findViewById(R.id.btnSend);
        plcOrder = (Button) findViewById(R.id.placeOrder);
        tvMessage = (EditText) findViewById(R.id.tvMessage);
        tvNumber = (EditText) findViewById(R.id.tvNumber);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMsg = tvMessage.getText().toString();
                String theNumber = tvNumber.getText().toString();
                sendMsg(theNumber, myMsg);

            }
        });

        plcOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent qrIntent = new Intent(RequestStaff.this, QR.class);
                startActivity(qrIntent);

            }
        });
    }
    protected void sendMsg(String theNumber, String myMsg){
        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0 );
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED),0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(theNumber, null, myMsg, sentPI, deliveredPI);
    }
    @Override
    protected void onResume(){
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }
    @Override
    protected void onPause(){
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}
