package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Bean.NearByHorizontalBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import java.util.List;

public class NearByHorizontalAdapter extends RecyclerView.Adapter<NearByHorizontalAdapter.MyViewHolder>  {
    private List<NearByHorizontalBean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    String userid;
    public static String item_name,item_variety,item_price,item_uom,item_quantity,id;
    public static int farmer_id;

    //    SessionManager session;

    public NearByHorizontalAdapter(Activity activity, List<NearByHorizontalBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;
        public LinearLayout item;
        public MyViewHolder(View view) {
              super(view);
              imageView=view.findViewById(R.id.image_near);

        }

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nearby_horizontal_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NearByHorizontalBean products = productList.get(position);
        Glide.with(activity).load(products.getImage())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);



    }



    @Override
    public int getItemCount() {

        System.out.println("e     e e "+productList.size());

        return productList.size();
    }

}