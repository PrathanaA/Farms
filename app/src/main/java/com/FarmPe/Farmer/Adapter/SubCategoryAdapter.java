package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Bean.CategoryBean;
import com.FarmPe.Farmer.R;


import java.util.Date;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>  {
    private List<CategoryBean> productList;
    Activity activity;
    Fragment selectedFragment;
    Date o_date;
    public LinearLayout item;
   public static String preview_crop;
    public  String vegg_name;
    TextView subname;
    public static String sub_cat;
    //    SessionManager session;
    public static CardView cardView;
    public SubCategoryAdapter(Activity activity, List<CategoryBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView agri_text;
        public ImageView sub_category_img;



        public MyViewHolder(View view) {
            super(view);
            //agri_text=view.findViewById(R.id.store_agri);
           // item_2=view.findViewById(R.id.item_2);
            subname=view.findViewById(R.id.name);
            item=view.findViewById(R.id.item);
            sub_category_img=view.findViewById(R.id.sub_category_img);
            //confirmbutton=view.findViewById(R.id.delivery2);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_cat_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CategoryBean products = productList.get(position);
      //holder.agri_text.setText(products.getAgri_text());
        subname.setText(products.getCategory());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview_crop="cropsList";
                vegg_name=subname.getText().toString();

            }
        });

       /* Glide.with(activity).load(products.getSub_cat_img())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.sub_category_img);*/

    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}