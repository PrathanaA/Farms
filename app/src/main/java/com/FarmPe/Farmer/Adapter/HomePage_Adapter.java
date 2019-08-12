package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.FarmPe.Farmer.Bean.AddPhotoBean;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.Bean.AddTractorBean1;
import com.FarmPe.Farmer.Bean.AddTractorBean2;
import com.FarmPe.Farmer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class HomePage_Adapter extends RecyclerView.Adapter<HomePage_Adapter.MyViewHolder> {

    private List<AddTractorBean2> productList;
    Activity activity;


    public HomePage_Adapter(Activity activity, List<AddTractorBean2> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);

            image=view.findViewById(R.id.image);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.homepage_adapter_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddTractorBean2 products = productList.get(position);
        //holder.image.setImageResource(products.getImage());

        Glide.with(activity).load(products.getImage())

                .thumbnail(0.5f)
                //  .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatarmale)
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
