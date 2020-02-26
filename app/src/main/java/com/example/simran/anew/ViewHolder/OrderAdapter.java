package com.example.simran.anew.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.simran.anew.Common.Common;
import com.example.simran.anew.Interface.ItemClickListener;
import com.example.simran.anew.Model.Order;
import com.example.simran.anew.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txt_order_name, txt_price;
    public ImageView img_order_count;

    private ItemClickListener itemClickListener;

    public void setTxt_order_name(TextView txt_order_name) {
        this.txt_order_name = txt_order_name;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);
        txt_order_name = (TextView)itemView.findViewById(R.id.order_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.order_item_Price);
        img_order_count = (ImageView)itemView.findViewById(R.id.order_item_count);

        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        contextMenu.setHeaderTitle("Select action");
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{
    private List<Order> listData = new ArrayList<>();
    private Context context;

    public OrderAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.BLUE);
        holder.img_order_count.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_order_name.setText(listData.get(position).getProductName());
    }
    @Override
    public int getItemCount()
    {
        return listData.size();
    }
}
