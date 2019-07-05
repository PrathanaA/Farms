package com.FarmPe.Farmer.Adapter;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.Farmer.Bean.Add_New_Address_Bean;
import com.FarmPe.Farmer.Fragment.Add_New_Address_Fragment;
import com.FarmPe.Farmer.Fragment.You_Address_Fragment;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Utils;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class You_Address_Adapter extends RecyclerView.Adapter<You_Address_Adapter.MyViewHolder>{


    private List<Add_New_Address_Bean> productList;
    Activity activity;
    Fragment selectedFragment = null;
    String status,message,status1,message1;
    Date o_date;
    SessionManager sessionManager;
    public static TextView name,variety,loc,grade,quantity,uom,price,edit;
    public static String add_id;
    public static CardView cardView;
    JSONObject lngObject;
    LinearLayout linearLayout;
    String deleted,addlist ;


    public You_Address_Adapter(List<Add_New_Address_Bean> moviesList, Activity activity) {

        this.productList = moviesList;
        this.activity=activity;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,mobile_no,street_addrss,landmrk,city_1,edit_1,delete_1,default_1,default_add;
        LinearLayout add_new_adress;
        View view1;

        public MyViewHolder(View view) {
            super(view);


            name = view.findViewById(R.id.name1);
            mobile_no= view.findViewById(R.id.mobile_no1);
            street_addrss= view.findViewById(R.id.street_address1);
            // landmrk= view.findViewById(R.id.landmark1);
            city_1= view.findViewById(R.id.city_1);
            edit_1= view.findViewById(R.id.edit_1);
            delete_1 = view.findViewById(R.id.delete_1);
            default_1 = view.findViewById(R.id.default_1);
            default_add = view.findViewById(R.id.default_add);
            linearLayout = view.findViewById(R.id.bottom_sheet1);
            //  view1 = view.findViewById(R.id.view1);

            add_new_adress = view.findViewById(R.id.linear_frame);

            sessionManager = new SessionManager(activity);

        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Add_New_Address_Bean products = productList.get(position);


        if(products.getDefault_addr()){
            System.out.println("11111ddd" + products.getDefault_addr());

            holder.default_add.setVisibility(View.VISIBLE);
            holder.default_1.setVisibility(View.GONE);
            // holder.view1.setVisibility(View.GONE);

        }else{

            holder.default_add.setVisibility(View.GONE);
            holder.default_1.setVisibility(View.VISIBLE);

        }


        add_id =products.getAdd_id();

        System.out.println("1123213213" + products.getAdd_id() );
        System.out.println("1123213213name" + products.getAdd_name() );


        holder.name.setText(products.getAdd_name());
        holder.mobile_no.setText("Phone No - " + products.getAdd_mobile());
        holder.street_addrss.setText(products.getAdd_street());
        holder.city_1.setText(products.getAdd_pincode());
        // holder.landmrk.setText(products.getAdd_landmark());



        holder.edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_id =products.getAdd_id();

                Bundle bundle = new Bundle();
                bundle.putString("Addr_name",products.getAdd_name());
                bundle.putString("Addr_mobile",products.getAdd_mobile());
                bundle.putString("Addr_pincode",products.getAdd_pincode());
                bundle.putString("Addr_Street",products.getAdd_street());
                // bundle.putString("Addr_Houseno",products.getAdd_door_no());
                // bundle.putString("Addr_landmark",products.getAdd_landmark());
                //bundle.putString("Addr_city",products.getAdd_city());

                bundle.putString("Addr_state",products.getAdd_state());
                bundle.putString("Addr_district",products.getAdd_district());
                bundle.putString("Addr_taluk",products.getAdd_taluk());
                bundle.putString("Addr_hobli",products.getAdd_hobli());
                bundle.putString("Addr_village",products.getAdd_village());
                bundle.putString("Addr_pickup_from",products.getAdd_pickup_frm());
                System.out.println("edittttttttttttttttttttttttttttttttttttttttttt"+bundle);
                bundle.putString("navigation_from","your_add");

                selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("your_add");
                transaction.commit();

            }
        });



        holder.delete_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView yes1,no1;
                System.out.println("aaaaaaaaaaaa");
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.address_delete_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(Utils.setCancleable);


                ImageView image = dialog.findViewById(R.id.close_popup);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                no1 =  dialog.findViewById(R.id.no_1);
                no1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes1 =  dialog.findViewById(R.id.yes_1);

                try {


                    lngObject = new JSONObject(sessionManager.getRegId("language"));
                    // popuptxt.setText(lngObject.getString("SelectanAddressType"));
                    yes1.setText(lngObject.getString("Confirm"));
                    no1.setText(lngObject.getString("Cancel"));
                    deleted=lngObject.getString("Addressdeletedsuccessfully");
                   // addlist=lngObject.getString("addressesareaddedin");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                yes1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        add_id =products.getAdd_id();

                        try{
                            JSONObject jsonObject  = new JSONObject();
                            jsonObject.put("Id",add_id);
                            jsonObject.put("UserId",sessionManager.getRegId("userId"));

                            Crop_Post.crop_posting(activity, Urls.Delete_Address_Details, jsonObject, new VoleyJsonObjectCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    System.out.println("111111dddd" + result);

                                    try{

                                        status = result.getString("Status");
                                        message = result.getString("Message");

                                        if(status.equals("1")){

                                            // Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();

                                            Snackbar snackbar = Snackbar
                                                    .make(linearLayout, deleted, Snackbar.LENGTH_LONG);
                                            View snackbarView = snackbar.getView();
                                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                            tv.setBackgroundColor(ContextCompat.getColor(activity,R.color.orange));
                                            tv.setTextColor(Color.WHITE);
                                            snackbar.show();



                                            productList.remove(position);
                                            notifyDataSetChanged();

                                            You_Address_Fragment.address_list.setText(productList.size() + "Addresses are added");

                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });



        holder.default_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                add_id =products.getAdd_id();

                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Id",add_id);
                    jsonObject.put("UserId",sessionManager.getRegId("userId"));

                    Crop_Post.crop_posting(activity, Urls.Default_Address, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("uuuuuaaaaaa" + result);

                            try{

                                status1 = result.getString("Status");
                                message1 = result.getString("Message");

                                if(status1.equals("1")){

                                    Toast.makeText(activity,message1, Toast.LENGTH_SHORT).show();

                                    selectedFragment = You_Address_Fragment.newInstance();
                                    FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    transaction.commit();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });



                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            holder.edit_1.setText(lngObject.getString("Edit"));
            holder.delete_1.setText(lngObject.getString("Delete"));
            holder.default_1.setText(lngObject.getString("setasdefault"));
            holder.default_add .setText(lngObject.getString("defaultaddressfordelivery"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}

