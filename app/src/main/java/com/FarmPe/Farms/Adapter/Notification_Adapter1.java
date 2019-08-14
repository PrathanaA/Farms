package com.FarmPe.Farms.Adapter;

import android.app.Activity;
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

import com.FarmPe.Farms.Bean.Notification_recy_bean;

import com.FarmPe.Farms.Fragment.Notification_Recyc_Fragment;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.List;


public class Notification_Adapter1 extends RecyclerView.Adapter<Notification_Adapter1.MyViewHolder>  {
    private List<Notification_recy_bean> productList;
    Activity activity;
    Fragment selectedFragment;
    public LinearLayout linearLayout;
    public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    Boolean isTouched = false;
    SessionManager sessionManager;
    public Notification_Adapter1(Activity activity, List<Notification_recy_bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
       sessionManager=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView actninfo;
        public SwitchCompat switch1;



        public MyViewHolder(View view) {
            super(view);
            actninfo=view.findViewById(R.id.actninfo);

            image=view.findViewById(R.id.image);
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
                    enable_switch(products.getNoti_id());
                    FirebaseMessaging.getInstance().subscribeToTopic(products.getNoti_code());// to register in topic(subcribe)


                }else {

                    FirebaseMessaging.getInstance().unsubscribeFromTopic(products.getNoti_code());// to register in topic(unsubscribe)

                }


            }
        });

      /*  if (Notification_Recyc_Fragment.list.get(position).equals(products.getNoti_id())){
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(true);
            FirebaseMessaging.getInstance().subscribeToTopic(products.getNoti_code());

        }else {
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(false);


        }*/

//
//        JSONObject jsonObject = null;
//        try{
//
//           jsonObject = new JSONObject();
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        Crop_Post.crop_posting(activity, Urls.GET_NOTIFICATION, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("ffffffnn" + result);
//
//                    try{
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//





    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }



    private void enable_switch(String not_typeId) {

        try{

            JSONObject jsonObject1 = new JSONObject();
            JSONObject post_object1 = new JSONObject();

            jsonObject1.put("NotificationTypeId",not_typeId);
            jsonObject1.put("Id",sessionManager.getRegId("userId"));
            post_object1.put("objUser",jsonObject1);


            Crop_Post.crop_posting(activity, Urls.UPDATEUSERNOTIFICATIONSETTING, post_object1, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("notification_status" + result);

                    try{

 JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("NotificationTypeId");
                        System.out.println("notification_status" + ProfileName1);





                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }


}