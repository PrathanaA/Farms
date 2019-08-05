package com.FarmPe.Farmer.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.ConnectionAdapter;
import com.FarmPe.Farmer.Adapter.InvitationLeadAdapter;
import com.FarmPe.Farmer.Bean.ConnectionBean;
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

public class ConnectionsFragment extends Fragment {

    public static List<ConnectionBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    ConnectionAdapter farmadapter;
    TextView toolbar_title;
    ConnectionBean connectionBean;

    JSONArray connectn_array;

    LinearLayout back_feed,main_layout;
    Fragment selectedFragment;
    ImageView filter;
    SessionManager sessionManager;

    public static ConnectionsFragment newInstance() {
        ConnectionsFragment fragment = new ConnectionsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connections_list_recy, container, false);
        recyclerView=view.findViewById(R.id.recy_connection);
       // toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        filter=view.findViewById(R.id.filter);
        main_layout=view.findViewById(R.id.main_layout);
       // toolbar_title.setText("Select HP");

        sessionManager=new SessionManager(getActivity());



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("aaaaaaaaaaaa");
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.filter_connection);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                dialog.show();
                dialog.setCanceledOnTouchOutside(true);


            }
        });


        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        farmadapter = new ConnectionAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);


        ConnectionList();

        return view;
    }


    private void ConnectionList() {

        try{

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.Get_Connection_List, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("fsfsdfcc" + result);

                    try{
                         newOrderBeansList.clear();
                        connectn_array = result.getJSONArray("connectionList");
                        for(int i = 0;i<connectn_array.length();i++){

                            JSONObject jsonObject1 = connectn_array.getJSONObject(i);
                            JSONObject jsonObject2 =jsonObject1.getJSONObject("Address");
                            connectionBean = new ConnectionBean(jsonObject1.getString("FullName"),jsonObject1.getString("RelatedDetail"),jsonObject2.getString("City"),jsonObject2.getString("State"),jsonObject1.getString("ProfilePic"),jsonObject1.getString("Id"),jsonObject1.getString("PhoneNo"));
                        newOrderBeansList.add(connectionBean);

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


    }



}
