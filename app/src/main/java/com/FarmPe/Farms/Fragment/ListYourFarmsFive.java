package com.FarmPe.Farms.Fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Activity.LandingPageActivity;
import com.FarmPe.Farms.Adapter.AddPhotoAdapter;
import com.FarmPe.Farms.Adapter.DistrictAdapter;
import com.FarmPe.Farms.Adapter.HoblisAdapter;
import com.FarmPe.Farms.Adapter.StateApdater;
import com.FarmPe.Farms.Adapter.TalukAdapter;
import com.FarmPe.Farms.Adapter.VillageAdapter;
import com.FarmPe.Farms.Bean.AddPhotoBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListYourFarmsFive extends Fragment {

    public static List<AddPhotoBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddPhotoAdapter farmadapter;
    LinearLayout back_feed,add_photo_layout2,add_photo_layout1;
    Fragment selectedFragment;
    TextView toolbar_title,continue_update,skip;
    Bitmap bitmap = null;
    LinearLayout linearLayout;
    public static final int GET_FROM_GALLERY = 3;
    ImageView b_arrow;
    SessionManager sessionManager;
    String home;
    String json_string,json_address_string;


    public static ListYourFarmsFive newInstance() {
        ListYourFarmsFive fragment = new ListYourFarmsFive();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_fifth_layout_item, container, false);
        recyclerView=view.findViewById(R.id.recycler_photo);
        back_feed=view.findViewById(R.id.back_feed);
        b_arrow=view.findViewById(R.id.b_arrow);
        continue_update=view.findViewById(R.id.continue_update);
        linearLayout=view.findViewById(R.id.linearLayout);

        sessionManager=new SessionManager(getActivity());



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("farm_third", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });





        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Bundle bundle = new Bundle();
                    bundle.putString("status","default");
                    selectedFragment = ListYourFarmsThird.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    selectedFragment.setArguments(bundle);
                    transaction.commit();


                    return true;
                }
                return false;
            }
        });




        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        AddPhotoBean img1=new AddPhotoBean( LandingPageActivity.selectedImage);
        newOrderBeansList.add(img1);
        farmadapter=new AddPhotoAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);




          continue_update.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

 System.out.println("llllllllllllllllllllllllllll"+AddPhotoAdapter.productList.size());

              if(AddPhotoAdapter.productList.size()==1) {

                  int duration=1000;
                  Snackbar snackbar = Snackbar
                          .make(linearLayout, "Upload atleast one images",duration);
                  View snackbarView = snackbar.getView();
                  TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                  tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                  tv.setTextColor(Color.WHITE);

                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                  } else {
                      tv.setGravity(Gravity.CENTER_HORIZONTAL);
                  }

                  snackbar.show();

              }


              else {
                  uploadImage(LandingPageActivity.selectedImage);

                  int duration=1000;
                  Snackbar snackbar = Snackbar
                          .make(linearLayout,"Your Details Updated Successfully",duration);
                  View snackbarView = snackbar.getView();
                  TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                  tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                  tv.setTextColor(Color.WHITE);

                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                      tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                  } else {
                      tv.setGravity(Gravity.CENTER_HORIZONTAL);
                  }

                  snackbar.show();

                  selectedFragment = HomeMenuFragment.newInstance();
                  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                  transaction.replace(R.id.frame_layout, selectedFragment);
                  transaction.commit();

              }

           }
          });




        return view;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    private void uploadImage(final Bitmap bitmap){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");

        progressDialog.show();


        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_location=new JSONObject();
        JSONObject jsonObject_address=new JSONObject();

        try {
//            jsonObject.put("FarmCategoryId", List_Farm_Ad);
//            jsonObject.put("FarmTypeId", List_Farm_Adapter2.farm_type_id);
            jsonObject.put("FarmName",ListYourFarmsFour.farm_name_string);
            jsonObject.put("ContactPersonName",ListYourFarmsFour.cont_name);
            jsonObject.put("MobileNumber",ListYourFarmsFour.mob_no);
            jsonObject.put("EmailId",ListYourFarmsFour.email_id_strg);
            jsonObject.put("Id","0");
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
            jsonObject_location.put("Id",0);
            jsonObject_location.put("Latitude","13.21321");
            jsonObject_location.put("Longitude","33.21321");
            jsonObject.put("FarmLocation",jsonObject_location);
            jsonObject_address.put("Id","0");
            jsonObject_address.put("StreeAddress",ListYourFarmsThird.street_string);
            jsonObject_address.put("StateId", StateApdater.stateid);
            jsonObject_address.put("DistrictId", DistrictAdapter.districtid);
            jsonObject_address.put("TalukId", TalukAdapter.talukid);
            jsonObject_address.put("HobliId", HoblisAdapter.hobliid);
            jsonObject_address.put("VillageId", VillageAdapter.villageid);
            jsonObject_address.put("Pincode",ListYourFarmsThird.pincode_string);

            jsonObject.put("FarmAddress",jsonObject_address);

            json_string=jsonObject.toString();

            System.out.println("bdjvknjvhvbbbbbbbbbbbbbbbbbbbbb"+json_string);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.AddUpdateFarms,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        progressDialog.dismiss();

                      System.out.println("bdjvknjvhv"+response);
                      if (home==null){


                      }else{
                          selectedFragment = HomeMenuFragment.newInstance();
                          FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                          transaction.replace(R.id.frame_layout, selectedFragment);

                          transaction.commit();
                      }




                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                    }
                }) {



            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
              long imagename = System.currentTimeMillis();
               params.put("farmimage", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));




                return params;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("farmsdetail",json_string);
                return params;
            }

        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(2000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }



}
