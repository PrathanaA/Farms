package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Adapter.NotificationAdapter;
import com.FarmPe.Farmer.Adapter.NotificationListAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.Bean.NotificationBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationList extends Fragment {

    public static List<NotificationBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static NotificationListAdapter farmadapter;
    TextView toolbar_title;
    LinearLayout back_feed;
    Fragment selectedFragment;
    SessionManager sessionManager;
    JSONObject lngObject;

    public static NotificationList newInstance() {
        NotificationList fragment = new NotificationList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_noti);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);

        sessionManager = new SessionManager(getActivity());
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

              /*else if(getArguments().getString("navigation_from").equals("setting")){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }*/
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack ("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    /* else if(getArguments().getString("navigation_from").equals("setting")){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }*/
                   /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/


                    return true;
                }
                return false;
            }
        });



        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


       /* newOrderBeansList.add(img6);
        newOrderBeansList.add(img6);*/


        farmadapter=new NotificationListAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            toolbar_title.setText(lngObject.getString("Notifications"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            //  toolbar_title.setText(lngObject.getString("Notifications"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("ToUserId",sessionManager.getRegId("userId"));
            System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.GET_NOTIFICATIONLIST, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("YourRequestttttttttttttttttt"+result);
                    JSONArray cropsListArray=null;

                    try {
                        cropsListArray=result.getJSONArray("NotificationList");
                        System.out.println("eeeddd"+cropsListArray.length());
                        for (int i=0;i<cropsListArray.length();i++){
                            JSONObject jsonObject1=cropsListArray.getJSONObject(i);
//                            JSONObject jsonObject2=jsonObject1.getJSONObject("Address");

                            String model=jsonObject1.getString("NotificationText");


                         /*   if (city.equals("")){
                                location="Bangalore"+", "+state;
                            }else{
                                location=city+", "+state;
                            }
*/


                            System.out.println("madelslistt"+newOrderBeansList.size());
                            NotificationBean img1=new NotificationBean(model);
                            newOrderBeansList.add(img1);


                            //FarmsImageBean crops = new FarmsImageBean(image,"Tractor Price",model,hp_range,purchaseTimeline,name,location,id);
                          //  newOrderBeansList.add(crops);



                          /*  if(!latts.equals("") | !langgs.equals("")) {

                                CropListBean crops = new CropListBean(cropName, crop_variety, location, crop_grade,
                                        crop_quantity, crop_uom, crop_price, id, farmerId,
                                        UserName,latts,langgs,CropImg,category);
                                newOrderBeansList.add(crops);
                            }*/
                        }
                       // farmadapter=new NotificationAdapter(getActivity(),newOrderBeansList);
                       // recyclerView.setAdapter(farmadapter);

                        farmadapter=new NotificationListAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(farmadapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }



}
