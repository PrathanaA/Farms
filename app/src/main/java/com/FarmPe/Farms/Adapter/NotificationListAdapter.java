package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Bean.NotificationBean;
import com.FarmPe.Farms.R;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder>  {
    private List<NotificationBean> productList;
    Activity activity;
    Fragment selectedFragment;

    public LinearLayout linearLayout;
    public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public NotificationListAdapter(Activity activity, List<NotificationBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notification,view_text;
        public LinearLayout noti_layout;



        public MyViewHolder(View view) {
            super(view);

            noti_layout=view.findViewById(R.id.noti_layout);
            notification=view.findViewById(R.id.noti_text);
            view_text=view.findViewById(R.id.view_text);


        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.not_lyt, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NotificationBean products = productList.get(position);

        holder.notification.setText(products.getNotification());

        holder.view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.noti_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.notification.setTextColor(Color.parseColor("#000000"));
                holder.view_text.setVisibility(View.INVISIBLE);

            }
        });



    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}