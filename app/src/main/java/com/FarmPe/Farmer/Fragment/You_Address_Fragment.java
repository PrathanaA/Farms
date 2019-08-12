package com.FarmPe.Farmer.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.FarmPe.Farmer.Adapter.You_Address_Adapter;
import com.FarmPe.Farmer.Bean.Add_New_Address_Bean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class You_Address_Fragment extends Fragment {

    Fragment selectedFragment;
    TextView name,mobile_no,street_addrss,landmrk,pincode,add_new_address,select_address_type;
    EditText doc_number,doc_name;
    private RecyclerView recyclerView;
    public static TextView address_list;
    LinearLayout back_feed;
    You_Address_Adapter mAdapter;
    ImageView back_arrow;
    SessionManager sessionManager;
    public static String navigation_all;
    public static String item_list,address;

    TextView toolbar_titletxt;
    JSONObject lngObject;

    ArrayList<Add_New_Address_Bean> new_address_beanArrayList = new ArrayList<>();
    Add_New_Address_Bean add_new_address_bean;
    JSONArray get_address_array;
    LinearLayout back,select_add_address;
    String Id,ad_list;
    ImageView b_arrow;


    public static You_Address_Fragment newInstance() {
        You_Address_Fragment fragment = new You_Address_Fragment();
        return fragment;
    }



    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_recyc_layout, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        // getActivity().getActionBar().hide();

        name = view.findViewById(R.id.name1);
//        mobile_no= view.findViewById(R.id.mobile_no1);
//        street_addrss= view.findViewById(R.id.street_address1);
//        landmrk= view.findViewById(R.id.landmark1);
        back_feed = view.findViewById(R.id.back_feed);
        add_new_address=view.findViewById(R.id.new_address);
        select_add_address = view.findViewById(R.id.select_address);
        select_address_type = view.findViewById(R.id.address_type1);
        recyclerView = view.findViewById(R.id.recycler_2);
        address_list= view.findViewById(R.id.items);
        toolbar_titletxt = view.findViewById(R.id.toolbar_title);


        sessionManager = new SessionManager(getActivity());



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    if(getArguments().getString("navigation_from").equals("HOME_FRAGMENT")){

                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();


                    } else if(getArguments().getString("navigation_from").equals("SETTING_FRAG1")){

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack ("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }
                    return true;
                }
                return false;
            }
        });



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments().getString("navigation_from").equals("HOME_FRAGMENT")){

                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();


                }else if(getArguments().getString("navigation_from").equals("SETTING_FRAG1")){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // address="your_address";
                Bundle bundle = new Bundle();
                bundle.putString("navigation_from", "yu_ads_frg");
                selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("yu_ads_frg");
                transaction.commit();

            }
        });


        select_address_type.setText("All");


        select_address_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.select_address_popup);
                final TextView home = (TextView) dialog.findViewById(R.id.home_1);
               // final TextView barn = (TextView) dialog.findViewById(R.id.barn);
                final TextView ware_house = (TextView)dialog.findViewById(R.id.ware_hus) ;
                final TextView farm = (TextView)dialog.findViewById(R.id.farm) ;
                final TextView others = (TextView)dialog.findViewById(R.id.othrs) ;

                final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;
                try {
                    lngObject = new JSONObject(sessionManager.getRegId("language"));
                    popuptxt.setText(lngObject.getString("SelectanAddressType"));
                    home.setText(lngObject.getString("Home"));
                   // barn.setText(lngObject.getString("Barn"));
                    ware_house.setText(lngObject.getString("Warehouse"));
                    farm.setText(lngObject.getString("Farm"));
                    others.setText(lngObject.getString("Others"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ImageView image = (ImageView) dialog.findViewById(R.id.close_popup);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address_type.setText(home.getText().toString());
                        gettingAddress("Home");
                        dialog.dismiss();
                    }
                });

               /* barn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address_type.setText(barn.getText().toString());
                        dialog.dismiss();
                        gettingAddress("Barn");

                    }
                });*/

                ware_house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address_type.setText(ware_house.getText().toString());
                        dialog.dismiss();
                        gettingAddress("Warehouse");


                    }
                });

                farm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address_type.setText(farm.getText().toString());
                        dialog.dismiss();

                        gettingAddress("Farm");


                    }
                });

                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address_type.setText(others.getText().toString());
                        dialog.dismiss();
                        gettingAddress("Others");


                    }
                });

                dialog.show();

            }
        });


        mAdapter = new You_Address_Adapter(new_address_beanArrayList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        gettingAddress(" ");

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            toolbar_titletxt.setText(lngObject.getString("SelectYourAdress"));
            add_new_address.setText(lngObject.getString("AddNewAddress"));
           // select_address_type.setText(lngObject.getString("Home"));
            //ad_list=(lngObject.getString("addressesareaddedin"));


        } catch (JSONException e) {
            e.printStackTrace();
        }




        return view;
    }

    public void gettingAddress(final String pickUPFrom){

        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("PickUpFrom",pickUPFrom);
            System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.Get_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggggggggggaaaaaaa"+result);
                    try{
                        new_address_beanArrayList.clear();


                        get_address_array = result.getJSONArray("UserAddressDetails");
                        for(int i=0;i<get_address_array.length();i++){
                            JSONObject jsonObject1 = get_address_array.getJSONObject(i);


                            add_new_address_bean = new Add_New_Address_Bean(jsonObject1.getString("Name"),jsonObject1.getString("StreeAddress"),jsonObject1.getString("StreeAddress1"),jsonObject1.getString("LandMark"),jsonObject1.getString("City"),jsonObject1.getString("Pincode"),jsonObject1.getString("MobileNo"),
                                    jsonObject1.getString("PickUpFrom"),jsonObject1.getString("State"),jsonObject1.getString("District"),jsonObject1.getString("Taluk"),jsonObject1.getString("Hoblie"),jsonObject1.getString("Village"),jsonObject1.getString("Id"),jsonObject1.getBoolean("IsDefaultAddress"));
                            new_address_beanArrayList.add(add_new_address_bean);

                        }



                        if(new_address_beanArrayList.size()<=1){

                            item_list = String.valueOf(new_address_beanArrayList.size());
                            address_list.setText(item_list+" " + " Address is added" );

                        }else{

                            item_list = String.valueOf(new_address_beanArrayList.size());
                            address_list.setText(item_list+" " + " Addresses are added" );

                        }


                        mAdapter.notifyDataSetChanged();




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
