package com.FarmPe.Farms.Fragment;


import android.app.Dialog;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farms.Adapter.DistrictAdapter;
import com.FarmPe.Farms.Adapter.HoblisAdapter;
import com.FarmPe.Farms.Adapter.StateApdater;
import com.FarmPe.Farms.Adapter.TalukAdapter;
import com.FarmPe.Farms.Adapter.VillageAdapter;
import com.FarmPe.Farms.Adapter.You_Address_Adapter;
import com.FarmPe.Farms.Bean.StateBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class  Add_New_Address_Fragment extends Fragment {

    RecyclerView recyclerView;


    static List<StateBean> stateBeanList = new ArrayList<>();
    static List<StateBean> districtBeanList = new ArrayList<>();
    static List<StateBean> talukBeanList = new ArrayList<>();
    static List<StateBean> hobliBeanList = new ArrayList<>();
    static List<StateBean> villageBeanList = new ArrayList<>();
    private List<StateBean> searchresultAraaylist = new ArrayList<>();
    StateApdater stateApdater;
    DistrictAdapter districtAdapter;
    TalukAdapter talukAdapter;
    HoblisAdapter hoblisAdapter;
    VillageAdapter villageAdapter;


    public static DrawerLayout drawer;

    LinearLayout back_feed;
    TextView toolbar_titletxt;
    JSONArray jsonArray,state_array,tal_array,hobli_array,village_array;
    StateBean stateBean;
    String new_add_toast;
    EditText search;
    public static String search_status="status";
    public static TextView add_new_address;
    Fragment selectedFragment = null;
    String selected_addresstype;
    JSONObject lngObject;
    LinearLayout linearLayout;
    String s_addtype,entername,entermno,inncrtmno,enterstreetad,enterpincode,selectstate,selectdistrict,selecttaluk,selecthobli,selectvillage,newaddressadded,addnotadded ;
    public static EditText name,mobile,pincode_no,street_name,state,taluk,hobli,district,village,select_address;
    String status,message;
    String Id;
    SessionManager sessionManager;
    public static Dialog grade_dialog;
    int selected_id,selected_id_time;


    public static Add_New_Address_Fragment newInstance() {
        Add_New_Address_Fragment fragment = new Add_New_Address_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_your_region_layout, container, false);

       getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



         select_address = view.findViewById(R.id.select_address);
         name = view.findViewById(R.id.full_name);
         mobile = view.findViewById(R.id.mobile_no);
         back_feed = view.findViewById(R.id.back_feed);
         pincode_no = view.findViewById(R.id.pincode);

        street_name = view.findViewById(R.id.street);



        add_new_address = view.findViewById(R.id.add_address);
        recyclerView = view.findViewById(R.id.recycler_view);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);
        state = view.findViewById(R.id.state);

        district = view.findViewById(R.id.district_1);
        taluk = view.findViewById(R.id.taluk_1);
        hobli = view.findViewById(R.id.hobli_1);
     //   village = view.findViewById(R.id.village_1);
        search = view.findViewById(R.id.search);

        linearLayout = view.findViewById(R.id.profile_view);

        toolbar_titletxt=view.findViewById(R.id.toolbar_title);



        System.out.println("selecteddddd_iddd"+selected_id_time);


        name.setText(getArguments().getString("Addr_name"));
        System.out.println("selecteddddd_idddnz"+getArguments().getString("Addr_name"));
        mobile.setText(getArguments().getString("Addr_mobile"));
        pincode_no.setText(getArguments().getString("Addr_pincode"));

        street_name.setText(getArguments().getString("Addr_Street"));


        state.setText(getArguments().getString("Addr_state"));
        district.setText(getArguments().getString("Addr_district"));
        taluk.setText(getArguments().getString("Addr_taluk"));
        hobli.setText(getArguments().getString("Addr_hobli"));

        select_address.setText(getArguments().getString("Addr_pickup_from"));
        selected_addresstype = getArguments().getString("Addr_pickup_from");


        name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30)});
        street_name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30)});



        sessionManager = new SessionManager(getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("lllllllllllllllllllllllll"+getArguments().getString("navigation_from"));

                if (getArguments().getString("navigation_from").equals("yu_ads_frg")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("yu_ads_frg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else if(getArguments().getString("navigation_from").equals("your_add")){

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("your_add", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }else if(getArguments().getString("navigation_from").equals("SETTING_FRAG")){

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                }else if(getArguments().getString("navigation_from").equals("HOME_FRAGMENT")){


                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();

                } else if(getArguments().getString("navigation_from").equals("edit_lokng_frg")){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("edit", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("request", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });






        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    System.out.println("lllllllllllllllllllllllll"+getArguments().getString("navigation_from"));
                    if (getArguments().getString("navigation_from").equals("yu_ads_frg")) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("yu_ads_frg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }else if(getArguments().getString("navigation_from").equals("your_add")){

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("your_add", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }else if(getArguments().getString("navigation_from").equals("SETTING_FRAG")){

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }else if(getArguments().getString("navigation_from").equals("HOME_FRAGMENT")){


                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();


                    }else if(getArguments().getString("navigation_from").equals("edit_lokng_frg")){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("edit", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    } else{
                       FragmentManager fm = getActivity().getSupportFragmentManager();
                       fm.popBackStack("request", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }

                    return true;
                }
                return false;
            }
        });



        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.select_address_popup);

                ImageView image = (ImageView) dialog.findViewById(R.id.close_popup);
                final TextView home =(TextView)dialog.findViewById(R.id.home_1);
                final TextView ware_house = (TextView)dialog.findViewById(R.id.ware_hus) ;
                final TextView farm = (TextView)dialog.findViewById(R.id.farm) ;
                final TextView others = (TextView)dialog.findViewById(R.id.othrs) ;
                final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;

                try {
                    lngObject = new JSONObject(sessionManager.getRegId("language"));
                    popuptxt.setText(lngObject.getString("SelectanAddressType"));
                    home.setText(lngObject.getString("Home"));
                    ware_house.setText(lngObject.getString("Warehouse"));
                    farm.setText(lngObject.getString("Farm"));
                    others.setText(lngObject.getString("Others"));
                    new_add_toast = (lngObject.getString("NewAddressaddedsuccessfully"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(home.getText().toString());

                        selected_addresstype = "Home";

                        dialog.dismiss();
                    }
                });



                ware_house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(ware_house.getText().toString());
                        selected_addresstype = "Warehouse";
                        dialog.dismiss();

                    }
                });

                farm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(farm.getText().toString());
                        selected_addresstype = "Farm";
                        dialog.dismiss();

                    }
                });

                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(others.getText().toString());
                        selected_addresstype = "Others";
                        dialog.dismiss();

                    }
                });


                dialog.show();

            }
        });






        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });





        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search_status="state";
                search.setText("");


                stateBeanList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                stateApdater = new StateApdater(stateBeanList,getActivity());

                recyclerView.setAdapter(stateApdater);

                prepareStateData();


            }
        });


        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);

                search_status="district";
                search.setText("");


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                districtAdapter= new DistrictAdapter( districtBeanList,getActivity());
                recyclerView.setAdapter(districtAdapter);



                prepareDistricData();
            }
        });


        taluk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);

                search_status="taluk";
                search.setText("");

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                talukAdapter = new TalukAdapter( talukBeanList,getActivity());
                recyclerView.setAdapter(talukAdapter);
                prepareTalukData();

            }
        });

        hobli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);

                search_status="hobli";
                search.setText("");

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                hoblisAdapter = new HoblisAdapter( hobliBeanList,getActivity());
                recyclerView.setAdapter(hoblisAdapter);

                prepareHobliData();


            }
        });

//        village.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                // submit.setVisibility(View.VISIBLE);
//
//                drawer.openDrawer(GravityCompat.END);
//                search_status="village";
//                search.setQuery("",false);
//             //   search.setQueryHint("");
//                // stateBeanList.clear();
//
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                recyclerView.setLayoutManager(mLayoutManager);
//                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                villageAdapter = new VillageAdapter(villageBeanList,getActivity());
//                recyclerView.setAdapter(villageAdapter);
//
//                prepareVillageData();
//
////                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
////                recyclerView.setLayoutManager(mLayoutManager);
////
////                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
////                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////                recyclerView.setLayoutManager(layoutManager);
////                recyclerView.setItemAnimator(new DefaultItemAnimator());
////                //    villageAdapter = new VillageAdapter(villageBeanList);
////                recyclerView.setAdapter(villageAdapter);
////
////                prepareVillageData();
//
//
//            }
//        });





        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(select_address.getText().toString().equals("")){

                    int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, s_addtype,duration);
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



                }else if(name.getText().toString().equals("")) {
                    int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, entername,duration);
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




                }else if(mobile.getText().toString().equals("")){
                    int duration = 1000;

                    Snackbar snackbar = Snackbar
                            .make(linearLayout, entermno,duration);
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

                }else if(mobile.length()<10){
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, inncrtmno,duration);
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

                }/*else if(house_numb.getText().toString().equals("")){
                    // Toast.makeText(getActivity(), "Enter House No/Floor/building", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enterhno, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();


                }*/else if(street_name.getText().toString().equals("")) {
                    //Toast.makeText(getActivity(), "Enter Street Address", Toast.LENGTH_SHORT).show();
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enterstreetad,duration);
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


                }else if(pincode_no.getText().toString().equals("")) {
                    // Toast.makeText(getActivity(), "Enter Pincode", Toast.LENGTH_SHORT).show();
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enterpincode,duration);
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


                }else if(pincode_no.length()<6){
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enterpincode,duration);
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


                }else if(state.getText().toString().equals("")) {
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, selectstate,duration);
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


                }else if(district.getText().toString().equals("")) {
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, selectdistrict,duration);
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


                }else if(taluk.getText().toString().equals("")) {
                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, selecttaluk,duration);
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




                }else {

                    ComposeCategory();

                }

            }
        });



        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            toolbar_titletxt.setText(lngObject.getString("AddNewAddress"));
            select_address.setHint(lngObject.getString("SelectanAddressType"));
            name.setHint(lngObject.getString("FullName"));
            mobile.setHint(lngObject.getString("PhoneNo"));
            street_name.setHint(lngObject.getString("Colony_Street_Locality"));

            pincode_no.setHint(lngObject.getString("Pincode"));
            state.setHint(lngObject.getString("State"));

            district.setHint(lngObject.getString("District"));

            add_new_address.setText(lngObject.getString("AddAddress"));

            s_addtype = lngObject.getString("SelectanAddressType");
            entername = lngObject.getString("Enteryourname");
            entermno = lngObject.getString("EnterPhoneNo");
            inncrtmno = lngObject.getString("Entervalidmobilenumber");

            enterstreetad = lngObject.getString("EnterStreetaddress");

            enterpincode = lngObject.getString("Enterpincode");
            selectstate = lngObject.getString("Selectstate");
            selectdistrict = lngObject.getString("SelectDistrict");
            selecttaluk = lngObject.getString("SelectTaluk");
            selecthobli = lngObject.getString("Selecthobli");

            newaddressadded = lngObject.getString("NewAddressaddedsuccessfully");

            addnotadded = lngObject.getString("YourAddressnotAdded");
        } catch (JSONException e) {
            e.printStackTrace();
        }






        return view;

    }

    private void prepareTalukData() {


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonpost = new JSONObject();
            jsonObject.put("DistrictId",DistrictAdapter.districtid);
            jsonpost.put("Talukobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Taluks, jsonpost, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("aaaaaaaaaaaaafffffffffffff"+result);
                    try{
                        talukBeanList.clear();
                        tal_array = result.getJSONArray("TalukList") ;
                        for(int i=0;i<tal_array.length();i++){
                            JSONObject jsonObject1 = tal_array.getJSONObject(i);
                            stateBean = new StateBean(jsonObject1.getString("Taluk"),jsonObject1.getString("TalukId"));
                            talukBeanList.add(stateBean);

                        }
                        sorting(talukBeanList);

                        talukAdapter.notifyDataSetChanged();
                        // grade_dialog.show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void prepareHobliData() {


        try{

            final JSONObject jsonObject = new JSONObject();

            JSONObject json_post = new JSONObject();
            jsonObject.put("TalukId",TalukAdapter.talukid);
            json_post.put("Hobliobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Hoblis, json_post, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("hhhhhhhssssskljhgf" + result);

                    try{
                        hobliBeanList.clear();
                        hobli_array = result.getJSONArray("HobliList");
                        for(int i = 0;i<hobli_array.length();i++){
                            JSONObject jsonObject3 = hobli_array.getJSONObject(i);
                            stateBean = new StateBean(jsonObject3.getString("Hobli"),jsonObject3.getString("HobliId"));
                            hobliBeanList.add(stateBean);

                        }
                        sorting(hobliBeanList);

                        hoblisAdapter.notifyDataSetChanged();
                        //  grade_dialog.show();



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    private void prepareVillageData() {
//
//
//        try{
//            JSONObject jsonObject = new JSONObject();
//            JSONObject post_Object = new JSONObject();
//            jsonObject.put("HobliId",hoblisAdapter.hobliid);
//            post_Object.put("Villageobj",jsonObject);
//
//            Crop_Post.crop_posting(getActivity(), Urls.Villages, post_Object, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("111vvv" + result);
//
//                    try{
//                        villageBeanList.clear();
//                        village_array = result.getJSONArray("VillageList");
//                        for(int i = 0;i<village_array.length();i++) {
//                            JSONObject jsonObject1 = village_array.getJSONObject(i);
//                            stateBean = new StateBean(jsonObject1.getString("Village"), jsonObject1.getString("VillagId"));
//                            villageBeanList.add(stateBean);
//                        }
//
//                        sorting(villageBeanList);
//
//                        villageAdapter.notifyDataSetChanged();
//                        //   grade_dialog.show();
//
//
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    private void prepareStateData() {


        try{

            JSONObject jsonObject = new JSONObject();

            Crop_Post.crop_posting(getActivity(), Urls.State, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("11111ssss" + result);


                    try{
                        stateBeanList.clear();
                        state_array = result.getJSONArray("StateList");
                        for(int i =0;i<state_array.length();i++){
                            JSONObject jsonObject1 = state_array.getJSONObject(i);

                            stateBean = new StateBean(jsonObject1.getString("State").trim(),jsonObject1.getString("StateId"));
                            stateBeanList.add(stateBean);
                        }
                        sorting(stateBeanList);

                        stateApdater.notifyDataSetChanged();
                        grade_dialog.show();



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private void prepareDistricData() {



        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("StateId",stateApdater.stateid);
            post_jsonobject.put("Districtobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Districts, post_jsonobject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dddddddddddd11111" + result);
                    try{
                        districtBeanList.clear();
                        jsonArray = result.getJSONArray("DistrictList");
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            stateBean = new StateBean(jsonObject1.getString("District"),jsonObject1.getString("DistrictId"));
                            districtBeanList.add(stateBean);
                        }

                           sorting(districtBeanList);


                        districtAdapter.notifyDataSetChanged();
                        grade_dialog.show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ComposeCategory() {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DistrictId",DistrictAdapter.districtid);
            jsonObject.put("HobliId", HoblisAdapter.hobliid);

            jsonObject.put("MobileNo",mobile.getText().toString());
            jsonObject.put("Name",name.getText().toString());
            jsonObject.put("PickUpFrom",selected_addresstype);
            jsonObject.put("Pincode",pincode_no.getText().toString());
            jsonObject.put("StateId",StateApdater.stateid);
            jsonObject.put("TalukId",TalukAdapter.talukid);
           // jsonObject.put("VillageId", VillageAdapter.villageid);
            // jsonObject.put("StreeAddress",house_numb.getText().toString());
            jsonObject.put("StreeAddress1",street_name.getText().toString());
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("Add_New_AddresssssssssssssssssjsonObject"+jsonObject);
            if(getArguments().getString("navigation_from").equals("your_add")){

                jsonObject.put("Id", You_Address_Adapter.add_id);


            } else{


            }





            Crop_Post.crop_posting(getActivity(), Urls.Add_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    Bundle bundle=new Bundle();

                    System.out.println("Add_New_Addressssssssssssssssslllllllllllllllllllllll"+result);
                    try{

                        status= result.getString("Status");
                        message = result.getString("Message");

                        bundle.putString("add_id",status);

                        bundle.putInt("selected_id2",selected_id);
                        bundle.putInt("selected_id_time1",selected_id_time);


                        if(!(status.equals("0"))){


                            if (getArguments().getString("navigation_from").equals("yu_ads_frg")) {
                                int duration=1000;
                                Snackbar snackbar = Snackbar
                                        .make(linearLayout, newaddressadded,duration);
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


                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.popBackStack("yu_ads_frg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                            else if (getArguments().getString("navigation_from").equals("SETTING_FRAG")) {
                                int duration=1000;
                                Snackbar snackbar = Snackbar
                                        .make(linearLayout, newaddressadded,duration);
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

                                selectedFragment = You_Address_Fragment.newInstance();
                                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();


                            }
                            else if(getArguments().getString("navigation_from").equals("your_add")){
                                int duration=1000;
                                Snackbar snackbar1 = Snackbar
                                        .make(linearLayout, "Address updated Successfully",duration);
                                View snackbarView1 = snackbar1.getView();
                                TextView tv1 = (TextView) snackbarView1.findViewById(android.support.design.R.id.snackbar_text);
                                tv1.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                                tv1.setTextColor(Color.WHITE);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                } else {
                                    tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                                }
                                snackbar1.show();

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.popBackStack("your_add", FragmentManager.POP_BACK_STACK_INCLUSIVE);



                            }else {
                                int duration=1000;
                                Snackbar snackbar = Snackbar
                                        .make(linearLayout, newaddressadded,duration);
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


                            }



                        }else{

                            int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout, addnotadded,duration);
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





    public void sorting(List<StateBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question
                return ((StateBean)state_name1).getName()
                        .compareTo(((StateBean)state_name2).getName());
            }
        });
    }

    public static InputFilter  EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            String specialChars = ".1/*!@#$%^&*()\"{}_[]|\\?/<>,.:-'';§£¥₹...%&+=€π|";
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL||type==Character.MATH_SYMBOL||specialChars.contains("" + source)) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }else if(Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };






    public  void sorting(String filter_text){
        System.out.println("lllllllllllllllljjjjjjj"+stateBeanList.size());

        final String text = filter_text.toLowerCase();



        if (search_status.equals("state")){
            searchresultAraaylist.clear();
            for (int i = 0; i < stateBeanList.size(); i++) {

                if (stateBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(stateBeanList.get(i));

                }
            }
            stateApdater = new StateApdater(searchresultAraaylist,getActivity());
            recyclerView.setAdapter(stateApdater);
        }
        else if (search_status.equals("district")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < districtBeanList.size(); i++) {

                if (districtBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(districtBeanList.get(i));

                }
            }
            districtAdapter = new DistrictAdapter(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(districtAdapter);


        }
        else if (search_status.equals("taluk")){
            searchresultAraaylist.clear();
            for (int i = 0; i < talukBeanList.size(); i++) {

                if (talukBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(talukBeanList.get(i));

                }
            }
            talukAdapter = new TalukAdapter(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(talukAdapter);
        }

        else if (search_status.equals("hobli")){
            searchresultAraaylist.clear();
            for (int i = 0; i < hobliBeanList.size(); i++) {

                if (hobliBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(hobliBeanList.get(i));

                }
            }
            hoblisAdapter = new HoblisAdapter(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(hoblisAdapter);
        }
//        else if (search_status.equals("village")){
//            searchresultAraaylist.clear();
//            for (int i = 0; i < villageBeanList.size(); i++) {
//
//                if (villageBeanList.get(i).getName().toLowerCase().contains(text)) {
//                    searchresultAraaylist.add(villageBeanList.get(i));
//
//                }
//            }
////                    villageAdapter = new VillageAdapter(sdearcstateBeanList);
//            recyclerView.setAdapter(villageAdapter);
//        }

    }
}



