package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Bean.FarmsImageBean1;
import com.FarmPe.Farmer.Fragment.FarmerDetailsFragment;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FarmerImageAdapter extends RecyclerView.Adapter<FarmerImageAdapter.MyViewHolder>  {
    private List<FarmsImageBean1> productList;
    Activity activity;
    Fragment selectedFragment;
    JSONObject lngObject;
    SessionManager session;

    public LinearLayout linearLayout;
   public static LinearLayout next_arw;
    public static String first,farmer_name1,farmer_phn,farmer_loc,farmer_emil,farmer_image;
    //    SessionManager session;
    public static CardView cardView;
    public FarmerImageAdapter(Activity activity, List<FarmsImageBean1> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect;


        public MyViewHolder(View view) {
            super(view);
            //agri_text=view.findViewById(R.id.store_agri);
           // item_2=view.findViewById(R.id.item_2);
            prod_price=view.findViewById(R.id.prod_price);
          //  prod_name=view.findViewById(R.id.prod_name);
          //  duration=view.findViewById(R.id.duration);
           // farmer_name=view.findViewById(R.id.farmer_name);
            location=view.findViewById(R.id.location);
            connect=view.findViewById(R.id.connect);
            image=view.findViewById(R.id.prod_img);
            //linearLayout=view.findViewById(R.id.dialog_list);
            //confirmbutton=view.findViewById(R.id.delivery2);


            session=new SessionManager(activity);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farmer_img_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FarmsImageBean1 products = productList.get(position);
      //holder.agri_text.setText(products.getAgri_text());
        holder.prod_price.setText(products.getProd_price());
      //  holder.prod_name.setText(products.getModelname());
      //  holder.duration.setText(products.getDuration());
       // holder.farmer_name.setText(products.getFarmer_name());
        holder.location.setText(products.getHp()+", "+products.getModelname());

       // holder.duration.setVisibility(View.GONE);

        String imageeeee=products.getImage();
        System.out.println("imageeee"+imageeeee);
       /* Glide.with(activity).load("https://renewin.sharepoint.com/:i:/s/WarehouseXD/EScFH2v3KU9FgBBt69aJF0kBJQP45yrm8xGX5TcD-7MgLw?e=mWhwOc")

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
*/



        try {
            lngObject = new JSONObject(session.getRegId("language"));


            holder.connect.setText(lngObject.getString("connect"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Glide.with(activity).load(products.getImage())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatarmale)
                .into(holder.image);

        holder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farmer_name1=products.getProd_price();
                farmer_phn=products.getMobile_no();
                farmer_emil=products.getEmail();
                farmer_loc=products.getLocation();
                farmer_image=products.getImage();
                selectedFragment = FarmerDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("farmer");
                transaction.commit();
            }
        });

      /*  Glide.with(activity)
                .load(products.getImage())
                .asBitmap()
                .transform(new CircularTransformation())
                .into(holder.image);*/


    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}