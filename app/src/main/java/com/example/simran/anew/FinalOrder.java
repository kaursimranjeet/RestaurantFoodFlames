package com.example.simran.anew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simran.anew.Common.Common;
import com.example.simran.anew.Database.Database;
import com.example.simran.anew.Model.Order;
import com.example.simran.anew.ViewHolder.OrderAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FinalOrder extends AppCompatActivity {
    private static double total;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrice;
    Button btnPlace;
    Button btnRequest;

    List<Order> finalOrder = new ArrayList<>();

    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Firebase
        database = FirebaseDatabase.getInstance();
        request=database.getReference("Requests");
        //Init
        recyclerView = (RecyclerView) findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.requestStaff);
       // btnRequest = (Button) findViewById(R.id.btnRequestStaff);

       /* btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent requestIntent = new Intent(FinalOrder.this, RequestStaff.class);
                startActivity(requestIntent);

            }});*/
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent qrIntent = new Intent(FinalOrder.this, RequestStaff.class);
                startActivity(qrIntent);

            }
        });

        loadListFood();
    }

    private void loadListFood() {
        finalOrder = new Database(this).getOrders();
        adapter = new OrderAdapter(finalOrder,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //Calculate total price
        total = 0;
        for(Order order:finalOrder)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }

    public static double getTotal()
    {
        return total;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteOrder(item.getOrder());
        return true;

    }

    private void deleteOrder(int position) {
        finalOrder.remove(position);
        new Database(this).cleanOrder();
        for(Order item:finalOrder)
            new Database(this).addToOrder(item);
        loadListFood();
    }


}
