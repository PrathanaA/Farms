package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FarmPe.Farms.Bean.AddTractorBean1;
import com.FarmPe.Farms.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage1_Adapter extends RecyclerView.Adapter<HomePage1_Adapter.MyViewHolder> {

    private List<AddTractorBean1> productList;
    Activity activity;


    public HomePage1_Adapter(Activity activity, List<AddTractorBean1> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;


        public MyViewHolder(View view) {
            super(view);

            image=view.findViewById(R.id.image);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.homepage_adapter_layout1, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddTractorBean1 products = productList.get(position);
      //  holder.image.setImageResource(products.getImage());


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
