package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.R;

import java.util.ArrayList;
import java.util.List;

public class ListYourFarmsThird extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstListYourFramsAdapter farmadapter;
    LinearLayout back_feed;
    Fragment selectedFragment;
    TextView toolbar_title,continue_3;




    public static ListYourFarmsThird newInstance() {
        ListYourFarmsThird fragment = new ListYourFarmsThird();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_third_layout_item, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_3=view.findViewById(R.id.continue_3);

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

        continue_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ListYourFarmsFour.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                // transaction.addToBackStack("looking");
                transaction.commit();
            }
        });


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
