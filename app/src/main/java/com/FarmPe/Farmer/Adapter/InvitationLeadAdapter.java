package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.Farmer.Activity.LoginActivity;
import com.FarmPe.Farmer.Bean.ConnectionBean;
import com.FarmPe.Farmer.Bean.Invitation_Bean;
import com.FarmPe.Farmer.Fragment.InvitationsLeadsFragment;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import org.json.JSONObject;

import java.util.List;

public class InvitationLeadAdapter extends RecyclerView.Adapter<InvitationLeadAdapter.MyViewHolder>  {
    private List<Invitation_Bean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    LinearLayout linearLayout;

   public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    String invi_id;
    public InvitationLeadAdapter(Activity activity, List<Invitation_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,cancel_btn,accept_btn;

        public TextView farmer_name,profession,location;




        public MyViewHolder(View view) {
            super(view);
            farmer_name=view.findViewById(R.id.farmer_name);
            image=view.findViewById(R.id.farmer_img);
            location=view.findViewById(R.id.location);
            profession=view.findViewById(R.id.profession);
            linearLayout=view.findViewById(R.id.linearLayout);
            cancel_btn=view.findViewById(R.id.cancel_btn);
            accept_btn=view.findViewById(R.id.accept_btn);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invitations_lead_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Invitation_Bean products = productList.get(position);
      //holder.agri_text.setText(products.getAgri_text());

        invi_id = products.getId();

        holder.farmer_name.setText(products.getName());
        holder.profession.setText(products.getType());
        holder.location.setText(products.getAddress());


        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invi_id = products.getId();
                accepted();

            }
        });


        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invi_id = products.getId();

                rejected();

            }
        });

    }

    private void rejected() {

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
            jsonObject.put("Id",invi_id);
            jsonObject.put("IsAccepted",0);
            jsonObject.put("IsRejected",1);


            Crop_Post.crop_posting(activity, Urls.Invitn_accpt_cancel, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("hghjgj" + result);
                    try{

                        String status = result.getString("Status");

                        if(status.equals("1")){
                           // Toast.makeText(activity, "Your invitation rejected successfully", Toast.LENGTH_SHORT).show();

                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Your invitation rejected successfully", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(activity,R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();


                            selectedFragment = InvitationsLeadsFragment.newInstance();
                            FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();



                        }else{


                        }



                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private void accepted() {


        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
            jsonObject.put("Id",invi_id);
            jsonObject.put("IsAccepted",1);
            jsonObject.put("IsRejected",0);



            Crop_Post.crop_posting(activity, Urls.Invitn_accpt_cancel, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("aaaasdfsjh" + result);
                    try{

                        String status = result.getString("Status");

                        if(status.equals("1")){
                          //  Toast.makeText(activity, "Your invitation accepted successfully", Toast.LENGTH_SHORT).show();

                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Your invitation accepted successfully", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(activity,R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();

                            selectedFragment = InvitationsLeadsFragment.newInstance();
                            FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();




                        }else{


                        }


                    }catch(Exception e){
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
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}