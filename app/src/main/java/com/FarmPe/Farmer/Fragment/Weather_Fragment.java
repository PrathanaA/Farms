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
import com.FarmPe.Farmer.Adapter.Weather_Adapter;
import com.FarmPe.Farmer.Bean.Notification_recy_bean;
import com.FarmPe.Farmer.Bean.StateBean;
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

public class Weather_Fragment extends Fragment {

    public static List<StateBean> stateBeanArrayList = new ArrayList<>();

    public static RecyclerView recyclerView;

    public static Weather_Adapter weather_adapter;


    public static Weather_Fragment newInstance() {
        Weather_Fragment fragment = new Weather_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_layout, container, false);
        recyclerView=view.findViewById(R.id.recycle_1);





        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager_farm);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        StateBean stateBean = new StateBean("London","");
        StateBean stateBean1 = new StateBean("Bangalore","");
        StateBean stateBean2= new StateBean("Austria","");
        StateBean stateBean3 = new StateBean("Australia","");
        StateBean stateBean4 = new StateBean("Germany","");


        stateBeanArrayList.add(stateBean);
        stateBeanArrayList.add(stateBean1);
        stateBeanArrayList.add(stateBean2);
        stateBeanArrayList.add(stateBean3);
        stateBeanArrayList.add(stateBean4);


        weather_adapter = new Weather_Adapter(stateBeanArrayList,getActivity());


        recyclerView.setAdapter(weather_adapter);







        return view;
    }


    }


