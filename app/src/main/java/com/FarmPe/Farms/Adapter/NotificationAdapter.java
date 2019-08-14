package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Bean.Notification_Home_Bean;
import com.FarmPe.Farms.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>  {
    private List<Notification_Home_Bean> productList;
    Activity activity;
    Fragment selectedFragment;

    public LinearLayout linearLayout;

    public static CardView cardView;
    public NotificationAdapter(Activity activity, List<Notification_Home_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect;



        public MyViewHolder(View view) {
            super(view);
            prod_price=view.findViewById(R.id.prod_price);
            prod_name=view.findViewById(R.id.prod_name);

            image=view.findViewById(R.id.image);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Notification_Home_Bean products = productList.get(position);

        holder.prod_name.setText(products.getNotiftn_mess());


    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}