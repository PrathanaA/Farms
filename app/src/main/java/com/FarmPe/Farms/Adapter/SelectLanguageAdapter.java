package com.FarmPe.Farms.Adapter;

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

import com.FarmPe.Farms.Activity.LoginActivity;
import com.FarmPe.Farms.Bean.SelectLanguageBean;
import com.FarmPe.Farms.Fragment.ChangeLanguageFragment;
import com.FarmPe.Farms.Fragment.HomeMenuFragment;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.List;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.MyViewHolder>  {
    private List<SelectLanguageBean> productList;

    Activity activity;

    Fragment selectedFragment;

    SessionManager sessionManager;
    public static int selected_position=0;

    public static CardView cardView;
    public SelectLanguageAdapter(Activity activity, List<SelectLanguageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
        sessionManager=new SessionManager(activity);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView language_name;
        public LinearLayout submit_langu;
        public ImageView right_img,lang_image;


        public MyViewHolder(View view) {
            super(view);
            language_name=view.findViewById(R.id.lang_text);
            submit_langu=view.findViewById(R.id.submit_langu_layout);
            right_img=view.findViewById(R.id.right_img);
            lang_image = view.findViewById(R.id.lang_icon);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_item, parent, false);
        return new MyViewHolder(itemView);

    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SelectLanguageBean products = productList.get(position);


        if (LoginActivity.isEng && position == 0){
            holder.right_img.setImageResource(R.drawable.ic_verified_filled_grey_white);
            LoginActivity.isEng = false;

        }else if(HomeMenuFragment.isEng && position==0){

            holder.right_img.setImageResource(R.drawable.ic_verified_filled_grey_white);
            HomeMenuFragment.isEng = false;

        }
        else {
            if (sessionManager.getRegId("language_name").equals(products.getVendor())) {
                holder.right_img.setImageResource(R.drawable.ic_verified_filled_grey_white);

            } else {

                holder.right_img.setImageResource(R.drawable.filled_grey_circle);


            }
        }
        holder.language_name.setText(products.getVendor());



       System.out.println("fsddsd" +products.getImageicon());

        Glide.with(activity).load(products.getImageicon())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.lang_image);


        holder.submit_langu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                sessionManager.saveLanguage_name(products.getVendor());
                getLang(products.getLanguageid());

            }
        });
    }



    private void getLang(int id) {

        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id",id);


            System.out.print("iiidddddd"+ id);

            Crop_Post.crop_posting(activity, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try{

                        sessionManager.saveLanguage(result.toString());

                        String lang_title1 = result.getString("ChangeLanguage");

                        ChangeLanguageFragment.lang_title.setText(lang_title1);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}