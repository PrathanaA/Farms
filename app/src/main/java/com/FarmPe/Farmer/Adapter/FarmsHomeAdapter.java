package com.FarmPe.Farmer.Adapter;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.Fragment.ComingSoonFragment;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FarmsHomeAdapter extends RecyclerView.Adapter<FarmsHomeAdapter.MyViewHolder>  {
    private List<FarmsImageBean> productList;
    Activity activity;
    Fragment selectedFragment;
    JSONObject lngObject;
    SessionManager session;
    public LinearLayout linearLayout;
   public static LinearLayout next_arw;
    public static String first;
    //    SessionManager session;
    public static CardView cardView;
    public FarmsHomeAdapter(Activity activity, List<FarmsImageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
       session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect;
       public LinearLayout edit,linear_looking_main;



        public MyViewHolder(View view) {
            super(view);
            //agri_text=view.findViewById(R.id.store_agri);
           // item_2=view.findViewById(R.id.item_2);
            prod_price=view.findViewById(R.id.prod_price);
          //  prod_name=view.findViewById(R.id.prod_name);
         //   duration=view.findViewById(R.id.duration);
          //  farmer_name=view.findViewById(R.id.farmer_name);
            location=view.findViewById(R.id.location);
            linear_looking_main=view.findViewById(R.id.linear_looking_main);
          //  connect=view.findViewById(R.id.connect);
            image=view.findViewById(R.id.prod_img);
            edit=view.findViewById(R.id.edit);
          //  session=new SessionManager(activity);
            //linearLayout=view.findViewById(R.id.dialog_list);
            //confirmbutton=view.findViewById(R.id.delivery2);

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
      //holder.agri_text.setText(products.getAgri_text());
        holder.prod_price.setText(products.getProd_price());
      //  holder.prod_name.setText(products.getModelname());
       // holder.duration.setText(products.getDuration());
      //  holder.farmer_name.setText(products.getFarmer_name());
        holder.location.setText(products.getLocation()+", "+"Gadag Karnataka");




        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height_px =Resources.getSystem().getDisplayMetrics().heightPixels;
        int height_set=(int)(height_px*0.6);
        System.out.println("height&Width"+width_px+","+height_px);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width_px,height_set);
        holder.linear_looking_main.setLayoutParams(parms);



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("your_farm");
                transaction.commit();

//                Bundle bundle=new Bundle();
//                bundle.putString("farm_name",products.getProd_price());
//                selectedFragment = FarmsDetailsFragment.newInstance();
//                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.addToBackStack("connect");
//                selectedFragment.setArguments(bundle);
//                transaction.commit();
            }
        });



        try {
            lngObject = new JSONObject(session.getRegId("language"));


           // holder.connect.setText(lngObject.getString("connect"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

     //   holder.duration.setVisibility(View.GONE);
       // Glide.with(activity).load(products.getImage())
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