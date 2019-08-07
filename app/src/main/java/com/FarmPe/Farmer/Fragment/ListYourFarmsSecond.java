package com.FarmPe.Farmer.Fragment;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.LoginActivity;
import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter2;
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


public class ListYourFarmsSecond extends Fragment {

    public static List<List_Farm_Bean> list_farm_beanList = new ArrayList<>();
    public static RecyclerView recyclerView;
    JSONArray farm_list2_array;
    List_Farm_Bean list_farm_bean;
    LinearLayout back_feed,linearLayout;
    List_Farm_Adapter2 farmadapter1;
    Fragment selectedFragment;
    String text_farm;
    public static String selectedRadio = "";
    public static boolean selected=false;

    TextView toolbar_title,continue_2,headin_name;


    int radiobutonStatus;


    public static ListYourFarmsSecond newInstance() {
        ListYourFarmsSecond fragment = new ListYourFarmsSecond();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_your_farm_recyc2, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_2=view.findViewById(R.id.continue_2);
        recyclerView=view.findViewById(R.id.recycler_2);
        headin_name = view.findViewById(R.id.headin_name);
        linearLayout = view.findViewById(R.id.linearLayout);

        selected=false;

        text_farm =  List_Farm_Adapter.listname;
        headin_name.setText("Select your " + text_farm);



        radiobutonStatus = getArguments().getInt("RB_S");

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+List_Farm_Adapter.selected_position);
                    selected=false;
                    selectedRadio="";

                    Bundle bundle = new Bundle();
                    bundle.putInt("RB_S", List_Farm_Adapter.selected_position);
                    selectedFragment = ListYourFarms.newInstance();
                    selectedFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });

       /* view.setFocusableInTouchMode(true);

        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+List_Farm_Adapter.selected_position);

                    Bundle bundle = new Bundle();
                    bundle.putInt("RB_S", List_Farm_Adapter.selected_position);
                    selectedFragment = ListYourFarms.newInstance();
                    selectedFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("list_farm");
                    transaction.commit();

                    return true;
                }

                return false;
            }
        });*/

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected=false;
                selectedRadio="";

                System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+List_Farm_Adapter.selected_position);

                Bundle bundle = new Bundle();
                bundle.putInt("RB_S", List_Farm_Adapter.selected_position);
                selectedFragment = ListYourFarms.newInstance();
                selectedFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });



        continue_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected){

                    Bundle bundle = new Bundle();
                    bundle.putString("status","default");
                    selectedFragment = ListYourFarmsThird.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    selectedFragment.setArguments(bundle);
                    transaction.addToBackStack("list_farm2");
                    transaction.commit();


                }else {

                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Please choose any one option", Snackbar.LENGTH_LONG);
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

                   // Toast.makeText(getActivity(),"Please Select",Toast.LENGTH_LONG).show();
                }



            }
        });


        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        farmadapter1 = new List_Farm_Adapter2(getActivity(),list_farm_beanList);
        recyclerView.setAdapter(farmadapter1);



        try{

            JSONObject jsonObject = new JSONObject();

            System.out.println("dasdasda" + List_Farm_Adapter.farm_listid);

            jsonObject.put("FarmCategoryId",List_Farm_Adapter.farm_listid);



            Crop_Post.crop_posting(getActivity(), Urls.Farm_Type_List, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("llllyyy" + result);

                    try{

                        list_farm_beanList.clear();
                        farm_list2_array = result.getJSONArray("FarmTypesList");
                        for(int i=0;i<farm_list2_array.length();i++) {

                            System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            if (i==radiobutonStatus){
                                JSONObject jsonObject1 = farm_list2_array.getJSONObject(i);
                                System.out.println("wwwww" + jsonObject1.getString("FarmCategoryId"));
                                list_farm_bean = new List_Farm_Bean(jsonObject1.getString("FarmType"), jsonObject1.getString("FarmTypeId"),true);
                                list_farm_beanList.add(list_farm_bean);

                                selected=true;

                            }else {
                                JSONObject jsonObject1 = farm_list2_array.getJSONObject(i);
                                System.out.println("wwwww" + jsonObject1.getString("FarmCategoryId"));
                                list_farm_bean = new List_Farm_Bean(jsonObject1.getString("FarmType"), jsonObject1.getString("FarmTypeId"),false);
                                list_farm_beanList.add(list_farm_bean);
                            }




                        }

                        farmadapter1.notifyDataSetChanged();

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
