package com.FarmPe.Farmer.Adapter;

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

import com.FarmPe.Farmer.Bean.Notification_Home_Bean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>  {
    private List<Notification_Home_Bean> productList;
    Activity activity;
    Fragment selectedFragment;

    public LinearLayout linearLayout;
   public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public NotificationAdapter(Activity activity, List<Notification_Home_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect;



        public MyViewHolder(View view) {
            super(view);
            prod_price=view.findViewById(R.id.prod_price);
            prod_name=view.findViewById(R.id.prod_name);
            duration=view.findViewById(R.id.duration);
            image=view.findViewById(R.id.image);

            //linearLayout=view.findViewById(R.id.dialog_list);
            //confirmbutton=view.findViewById(R.id.delivery2);
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
//        holder.prod_price.setText(products.getProd_price());
//        holder.prod_name.setText(products.getModelname()+" "+products.getHp());
//        holder.duration.setText(products.getDuration());

//
//        Glide.with(activity).load(products.getImage())
//
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}