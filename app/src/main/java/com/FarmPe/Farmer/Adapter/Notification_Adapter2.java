package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Bean.Notification_recy_bean;
import com.FarmPe.Farmer.Fragment.Notification_Recyc_Fragment;
import com.FarmPe.Farmer.R;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;


public class Notification_Adapter2 extends RecyclerView.Adapter<Notification_Adapter2.MyViewHolder>  {
    private List<Notification_recy_bean> productList;
    Activity activity;
    Fragment selectedFragment;
    Boolean isTouched = false;

    public LinearLayout linearLayout;
    public static LinearLayout next_arw;

    public static String first;
    public static CardView cardView;
    public Notification_Adapter2(Activity activity, List<Notification_recy_bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect,actninfo;
        public SwitchCompat switch1;



        public MyViewHolder(View view) {
            super(view);
            prod_price=view.findViewById(R.id.prod_price);
            prod_name=view.findViewById(R.id.prod_name);
            duration=view.findViewById(R.id.duration);
            image=view.findViewById(R.id.image);
            actninfo=view.findViewById(R.id.actninfo);
            switch1=view.findViewById(R.id.switch1);

            //linearLayout=view.findViewById(R.id.dialog_list);
            //confirmbutton=view.findViewById(R.id.delivery2);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificatn_btn_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Notification_recy_bean products = productList.get(position);

        holder.actninfo.setText(products.getNoti_txt());
        /*if (Notification_Recyc_Fragment.list.get(position).equals(products.getNoti_id())){
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(true);
            FirebaseMessaging.getInstance().subscribeToTopic(products.getNoti_code());


        }else {
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(false);


        }*/

        holder.switch1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouched = true;
                return false;
            }
        });

        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isTouched) {
                    isTouched = false;

                    if (isChecked) {
                        Notification_Recyc_Fragment.enable_all.setVisibility(View.VISIBLE);
                    } else {
                        Notification_Recyc_Fragment.enable_all.setVisibility(View.GONE);
                    }
                }
            }
        });

        holder.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.switch1.isChecked()){
                    FirebaseMessaging.getInstance().subscribeToTopic(products.getNoti_code());

                }else {

                }


            }
        });



    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}