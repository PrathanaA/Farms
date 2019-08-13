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

import com.FarmPe.Farmer.Fragment.Edit_Looking_For_Fragment;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FarmsImageAdapter extends RecyclerView.Adapter<FarmsImageAdapter.MyViewHolder>  {
    private List<FarmsImageBean> productList;
    Activity activity;
    Fragment selectedFragment;
    JSONObject lngObject;
    public LinearLayout linearLayout;
    public static LinearLayout next_arw;
    public static String first,looking_forId,model_id,timeline,looking_for,address;
    SessionManager session;
    boolean flag;
    public static CardView cardView;

    public FarmsImageAdapter(Activity activity, List<FarmsImageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,short_list_image,image_looking,edit;
        public TextView prod_price,prod_name,duration,farmer_name,location,connect;
        public LinearLayout shortlist_layout;

        public  LinearLayout linear_looking_main;


        public MyViewHolder(View view) {
            super(view);
            //agri_text=view.findViewById(R.id.store_agri);
           // item_2=view.findViewById(R.id.item_2);
            prod_price=view.findViewById(R.id.prod_price);
            prod_name=view.findViewById(R.id.prod_name);
            duration=view.findViewById(R.id.duration);
            farmer_name=view.findViewById(R.id.farmer_name);
            linear_looking_main=view.findViewById(R.id.linear_looking_main);


            image_looking=view.findViewById(R.id.image_looking);
            edit=view.findViewById(R.id.edit);


        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_img_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FarmsImageBean products = productList.get(position);

        try {
            //holder.agri_text.setText(products.getAgri_text());
            holder.prod_price.setText(products.getProd_price());
            holder.prod_name.setText(products.getModelname() + " " + products.getHp());
            //   holder.duration.setText(products.getDuration());
            // holder.farmer_name.setText(products.getFarmer_name());
            // holder.location.setText(products.getLocation());

            looking_forId = products.getId();
            model_id = products.getModelname();
            timeline = products.getDuration();
            address = products.getLocation();
        }catch (Exception e){

        }

System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+products.getId());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                looking_forId=products.getId();

          //      edit_request();

                selectedFragment = Edit_Looking_For_Fragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("looking1");
                transaction.commit();
            }
        });


        try {
            Glide.with(activity).load(products.getImage())
                    //  Glide.with(activity).load(R.drawable.tractor_sonalika)

                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image_looking);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        try {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height_px =Resources.getSystem().getDisplayMetrics().heightPixels;
        int height_set=(int)(height_px*0.4);
        System.out.println("height&Width"+width_px+","+height_px);

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width_px,height_set);
            holder.linear_looking_main.setLayoutParams(parms);

        } catch (
                Exception e) {
            e.printStackTrace();
        }


//
//        holder.shortlist_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flag==false){
//                   holder.short_list_image.setImageResource(R.drawable.ic_star_filled);
//                    flag=true;
//                }else{
//                    holder.short_list_image.setImageResource(R.drawable.ic_star);
//                    flag=false;
//                }
//            }
//        });


        try {
            lngObject = new JSONObject(session.getRegId("language"));
           // holder.connect.setText(lngObject.getString("connect"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   /* private void edit_request() {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",session.getRegId("userId"));
            jsonObject.put("ModelId",model_id);
            jsonObject.put("PurchaseTimeline",timeline);
            jsonObject.put("LookingForFinance","yes");
            jsonObject.put("AddressId",address);
            jsonObject.put("IsAgreed","true");
            jsonObject.put("LookingForDetailsId",looking_forId);


            Crop_Post.crop_posting(activity, Urls.Edit_Request, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.print("11111eeeee" + result);

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


    }
*/
    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}