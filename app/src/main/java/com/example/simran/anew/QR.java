package com.example.simran.anew;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.simran.anew.Database.Database;
import com.example.simran.anew.Model.Order;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class QR extends AppCompatActivity {
    private  Button payOdr;
    List<Order> finalOrder = new ArrayList<>();
    private ImageView imageView;
    private Button btnCreateQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_layout);

        btnCreateQr = findViewById(R.id.btnCreate);
        imageView = findViewById(R.id.imageView);
        payOdr = findViewById(R.id.payNow);

        btnCreateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String text  = getOrderList();

                if(text != null){
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);
                    } catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        payOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent qrIntent = new Intent(QR.this, Payment.class);
                startActivity(qrIntent);
                cleanOrder();

            }
        });
    }
    private void cleanOrder()
    {
        new Database(this).cleanOrder();
    }
    private String getOrderList()
    {
        finalOrder = new Database(this).getOrders();
        String text = "Name \t\t\t\t\t Quantity\n";
        for(Order order:finalOrder) {
            text += order.getProductName() + " \t\t " + order.getQuantity() + "\n";
        }
        return text;
    }
}
