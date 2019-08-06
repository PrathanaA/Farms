package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.LandingPageActivity;
import com.FarmPe.Farmer.Bean.AddPhotoBean;
import com.FarmPe.Farmer.Fragment.AddPhotoFragmentSub;
import com.FarmPe.Farmer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class  AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.MyViewHolder> {
    public static    List<AddPhotoBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static final int GET_FROM_GALLERY = 3;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 200;
    public LinearLayout linearLayout;
    public static LinearLayout next_arw;
    public static String first, looinkgId;
    public static CardView cardView;



    public AddPhotoAdapter(Activity activity, List<AddPhotoBean> moviesList) {
        this.productList = moviesList;
        this.activity = activity;
//        session=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView delete;
        public   ImageView imageView;
        public  TextView add_text_image;

        public MyViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.prod_img);
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
        //holder.agri_text.setText(products.getAgri_text());
        System.out.println("imagegegegegeg11111hhhhhhhhhhhhhposition" +position);


        if (position == productList.size()-1 ) {
            holder. imageView.setBackgroundColor(Color.parseColor("#40e6e6e6"));

            holder.add_text_image.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);

        }else {
            holder.delete.setVisibility(View.VISIBLE);

            //   add_text_image.setVisibility(View.GONE);
            holder.imageView.setImageBitmap(products.getImage_upload());
           // add_text_image.setText("");
        }
        holder.add_text_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("imagegegegegeg11111hhhhhhhhhhhhh" +productList.size());
                if (productList.size()<3){
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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