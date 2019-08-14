package com.FarmPe.Farms.Fragment;



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


import com.FarmPe.Farms.Adapter.NotificationAdapter;
import com.FarmPe.Farms.Bean.Notification_Home_Bean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public static List<Notification_Home_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static NotificationAdapter farmadapter;
    TextView toolbar_title;
    LinearLayout back_feed;
    Notification_Home_Bean notification_home_bean;
    Fragment selectedFragment;
    SessionManager sessionManager;
    JSONObject lngObject;
    JSONArray notifn_array;
    String location;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_noti);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);

        sessionManager = new SessionManager(getActivity());


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);




                    return true;
                }
                return false;
            }
        });


        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        farmadapter=new NotificationAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        try{

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("ToUserId",sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.Notification_HomePage, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("sdffdffds" + result);

                    try{

                        notifn_array = result.getJSONArray("NotificationList");

                            for(int i=0;i<notifn_array.length();i++){
                            JSONObject jsonObject1 = notifn_array.getJSONObject(i);
                            notification_home_bean = new Notification_Home_Bean(jsonObject1.getString("NotificationText"),jsonObject1.getString("Id"));
                            newOrderBeansList.add(notification_home_bean);

                        }

                            farmadapter.notifyDataSetChanged();



                    }catch (Exception e){
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
