package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farms.Bean.AddPhotoBean;
import com.FarmPe.Farms.R;

import java.util.List;

public class  AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.MyViewHolder> {
    public static    List<AddPhotoBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static final int GET_FROM_GALLERY = 3;

    private static int RESULT_LOAD_IMG = 100;
    public LinearLayout linearLayout;

    public static CardView cardView;



    public AddPhotoAdapter(Activity activity, List<AddPhotoBean> moviesList) {
        this.productList = moviesList;
        this.activity = activity;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView delete;
        public   ImageView imageView;
        public  TextView add_text_image;
public FrameLayout image;
        public MyViewHolder(View view) {
            super(view);


            imageView = view.findViewById(R.id.prod_img);
            image= view.findViewById(R.id.imgs);
            add_text_image = view.findViewById(R.id.add_text_image);
            delete = view.findViewById(R.id.filter_check);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_fifth_recy_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddPhotoBean products = productList.get(position);

        System.out.println("imagegegegegeg11111hhhhhhhhhhhhhposition" +position);


        if (position == productList.size()-1 ) {
            holder. imageView.setBackgroundColor(Color.parseColor("#40e6e6e6"));

            holder.add_text_image.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);

        }else {
            holder.delete.setVisibility(View.VISIBLE);

            holder.imageView.setImageBitmap(products.getImage_upload());

        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("imagegegegegeg11111hhhhhhhhhhhhh" +productList.size());
                if (productList.size()<3){
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                    activity.startActivityForResult(i, RESULT_LOAD_IMG);

                }else {
                    Toast.makeText(activity,"You can't upload more than two images",Toast.LENGTH_LONG).show();
                }
                }


        });
        System.out.println("imagegegegegeg11111" + products.getImage_upload());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //remove list
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());

            }
        });
    }




    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh" + productList.size());
        return productList.size();
    }


}