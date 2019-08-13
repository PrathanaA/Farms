package com.FarmPe.Farmer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.Notification_Adapter1;
import com.FarmPe.Farmer.Adapter.Notification_Adapter2;
import com.FarmPe.Farmer.Bean.Notification_recy_bean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notification_Recyc_Fragment extends Fragment {

    public static List<Notification_recy_bean> newOrderBeansList = new ArrayList<>();
    public static List<Notification_recy_bean> newmember_List = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static RecyclerView recyclerView1;
    public static Notification_Adapter1 farmadapter;
    public static Notification_Adapter2 farmadapter1;
    TextView toolbar_title;
    Notification_recy_bean notification_recy_bean;
    JSONArray noti_array;
    LinearLayout back_feed;
    Fragment selectedFragment;
    SessionManager sessionManager;
    JSONObject lngObject;
    String location;
   public static List<String> list;
   public static TextView switchCompat,enable_all;

    public static Notification_Recyc_Fragment newInstance() {
        Notification_Recyc_Fragment fragment = new Notification_Recyc_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_recyc, container, false);
        recyclerView=view.findViewById(R.id.recycler_noti);
        recyclerView1=view.findViewById(R.id.recycler_noti1);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        switchCompat=view.findViewById(R.id.switch1);
        enable_all=view.findViewById(R.id.enable);


        sessionManager = new SessionManager(getActivity());
        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            //  toolbar_title.setText(lngObject.getString("Notifications"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        switchCompat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);



                    return true;
                }
                return false;
            }
        });

//

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        GridLayoutManager mLayoutManager_farm1 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView1.setLayoutManager(mLayoutManager_farm1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        farmadapter = new Notification_Adapter1(getActivity(),newOrderBeansList);
        farmadapter1 =new Notification_Adapter2(getActivity(),newmember_List);

        recyclerView.setAdapter(farmadapter);

        recyclerView1.setAdapter(farmadapter1);


        notification_status();

         // enable_all();

          enable_all.setVisibility(View.GONE);



        return view;
    }

    private void enable_all() {

        try{

            JSONObject jsonObject1 = new JSONObject();
            JSONObject post_object1 = new JSONObject();

            jsonObject1.put("NotificationTypeId"," ");
            jsonObject1.put("Id",sessionManager.getRegId("userId"));
            post_object1.put("objUser",jsonObject1);


            Crop_Post.crop_posting(getActivity(), Urls.UPDATEUSERNOTIFICATIONSETTING, post_object1, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("notification_status" + result);

                    try{

                       /* JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("NotificationTypeId");
                        System.out.println("notification_status" + ProfileName1);


                        list = new ArrayList<String>(Arrays.asList(ProfileName1.split(",")));
                        getnot();
*/



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void notification_status(){



        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("notification_status" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("NotificationTypeId");
                        System.out.println("notification_statusnnnnnnnnnnnnnnnnnnnnnnnnnnnn" + ProfileName1);


                        list = new ArrayList<String>(Arrays.asList(ProfileName1.split(",")));
                         getnot();


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

 public void  getnot(){
    try{

        JSONObject jsonObject = new JSONObject();


        Crop_Post.crop_posting(getActivity(), Urls.GET_NOTIFICATION, jsonObject, new VoleyJsonObjectCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                System.out.println("ffffffnn" + result);


                try{
                    newmember_List.clear();
                    newOrderBeansList.clear();

                    noti_array = result.getJSONArray("NotificationMasterList");

                    for(int i = 0;i<noti_array.length();i++){
                        JSONObject jsonObject1 =  noti_array.getJSONObject(i);

                        if(jsonObject1.getBoolean("ToAll")) {

                            notification_recy_bean = new Notification_recy_bean(jsonObject1.getString("NotificationAction"), jsonObject1.getString("NotificationCode"), jsonObject1.getString("NotificationID"));
                            newOrderBeansList.add(notification_recy_bean);

                        }else{

                            notification_recy_bean = new Notification_recy_bean(jsonObject1.getString("NotificationAction"), jsonObject1.getString("NotificationCode"), jsonObject1.getString("NotificationID"));
                            newmember_List.add(notification_recy_bean);

                        }

                    }

                    farmadapter.notifyDataSetChanged();
                    farmadapter1.notifyDataSetChanged();


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }catch (Exception e){
        e.printStackTrace();
    }




}

}
