package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Fragment.Farm_Edit_Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farms.Bean.FarmsImageBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FarmsHomeAdapter extends RecyclerView.Adapter<FarmsHomeAdapter.MyViewHolder>  {
    private List<FarmsImageBean> productList;
    Activity activity;
    JSONObject lngObject;
    SessionManager session;
    public LinearLayout linearLayout;


    public static CardView cardView;
    public FarmsHomeAdapter(Activity activity, List<FarmsImageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
       session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,edit;
        public TextView prod_price,duration,location;
       public LinearLayout linear_looking_main;



        public MyViewHolder(View view) {
            super(view);

            prod_price=view.findViewById(R.id.prod_price);

            location=view.findViewById(R.id.location);
            linear_looking_main=view.findViewById(R.id.linear_looking_main);

            image=view.findViewById(R.id.prod_img);
            edit=view.findViewById(R.id.edit);


        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_home_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FarmsImageBean products = productList.get(position);

        holder.prod_price.setText(products.getProd_price());

        holder.location.setText(products.getLocation()+", "+"Gadag ,Karnataka");




        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height_px =Resources.getSystem().getDisplayMetrics().heightPixels;
        int height_set=(int)(height_px*0.6);
        System.out.println("height&Width"+width_px+","+height_px);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width_px,height_set);
        holder.linear_looking_main.setLayoutParams(parms);




        try {
            lngObject = new JSONObject(session.getRegId("language"));




        } catch (JSONException e) {
            e.printStackTrace();
        }


        Glide.with(activity).load(products.getImage())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}