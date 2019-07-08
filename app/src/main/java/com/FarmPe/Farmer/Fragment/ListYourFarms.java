package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter;
import com.FarmPe.Farmer.Adapter.Notification_Adapter1;
import com.FarmPe.Farmer.Adapter.Notification_Adapter2;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.Bean.List_Farm_Bean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListYourFarms extends Fragment {

    public static List<List_Farm_Bean> list_farm_beanList = new ArrayList<>();
    public static RecyclerView recyclerView;
    List_Farm_Bean list_farm_bean;
    public static List_Farm_Adapter farmadapter;
    LinearLayout back_feed;
    JSONArray list_farm_array;
    Fragment selectedFragment;
    TextView toolbar_title,continue_1;


    public static ListYourFarms newInstance() {
        ListYourFarms fragment = new ListYourFarms();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_recyc_layout, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_1=view.findViewById(R.id.continue_1);
        recyclerView=view.findViewById(R.id.recycler_2);




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("list_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });




        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                // transaction.addToBackStack("looking");
                transaction.commit();
            }
        });


        continue_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ListYourFarmsSecond.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                 transaction.addToBackStack("list_farm1");
                transaction.commit();
            }
        });




        list_farm_beanList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        farmadapter = new List_Farm_Adapter(getActivity(),list_farm_beanList);

        recyclerView.setAdapter(farmadapter);


        try{

            JSONObject jsonObject = new JSONObject();

            Crop_Post.crop_posting(getActivity(), Urls.List_Your_Farms, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("lllllyyff" + result);

                    try{


                         list_farm_array = result.getJSONArray("FarmCategoryList");
                          for(int i=0;i<list_farm_array.length();i++){

                            JSONObject jsonObject1 = list_farm_array.getJSONObject(i);
                            System.out.println("wwwww" + jsonObject1.getString("FarmCategoryId"));
                            if (i==0){
                                list_farm_bean = new List_Farm_Bean(jsonObject1.getString("FarmCategory"),jsonObject1.getString("FarmCategoryId"),true);
                                list_farm_beanList.add(list_farm_bean);

                            }else{
                                list_farm_bean = new List_Farm_Bean(jsonObject1.getString("FarmCategory"),jsonObject1.getString("FarmCategoryId"),false);
                                list_farm_beanList.add(list_farm_bean);

                            }



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




       /* toolbar_title=view.findViewById(R.id.toolbar_title);

        final Bundle bundle=getArguments();

        toolbar_title.setText("Select Category");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bundle==null){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("looking", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();
                }


            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus(View.FOCUS_UP);
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (bundle==null){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("looking", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }else{
                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        // transaction.addToBackStack("looking");
                        transaction.commit();
                    }
                }
                return false;
            }
        });

        // AddLookigFor();
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AddTractorBean img1=new AddTractorBean(R.drawable.tractor_green,"Request for Quotation","");
        newOrderBeansList.add(img1);

        AddTractorBean img2=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img2);
        newOrderBeansList.add(img1);
        newOrderBeansList.add(img2);

        AddTractorBean img3=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img3);
        newOrderBeansList.add(img2);


        AddTractorBean img4=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img4);

        AddTractorBean img5=new AddTractorBean(R.drawable.tractor_red,"Legal","");
        newOrderBeansList.add(img5);

        AddTractorBean img6=new AddTractorBean(R.drawable.tractor_green,"Agricultural Banking","");
        newOrderBeansList.add(img6);

        AddTractorBean img7=new AddTractorBean(R.drawable.tractor_red,"Agricultural Insurance","");
        newOrderBeansList.add(img7);

        AddTractorBean img8=new AddTractorBean(R.drawable.tractor_red,"Rural Finance","");
        newOrderBeansList.add(img8);

        farmadapter=new AddFirstListYourFramsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);
*/



        return view;
    }



}
