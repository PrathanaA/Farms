package com.FarmPe.Farmer.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


import com.FarmPe.Farmer.Adapter.HomePage1_Adapter;
import com.FarmPe.Farmer.Adapter.HomePage_Adapter;
import com.FarmPe.Farmer.Bean.AddTractorBean1;
import com.FarmPe.Farmer.Bean.AddTractorBean2;
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

public class FarmPe_Logo_Fragment extends Fragment {
    Fragment selectedFragment;
    public static LinearLayout backfeed;

LinearLayout linearLayout;
    JSONObject lngObject;
    TextView reqst_count,farmlist_count,nameee;
    SessionManager sessionManager;
    public static String toast_click_back;
    boolean doubleBackToExitPressedOnce = false;
    JSONArray count_images_array;
    HomePage_Adapter homePage_adapter;
    HomePage1_Adapter homePage1_adapter;


    RecyclerView recyclerView,recyclerView1;
    public static List<AddTractorBean1> newOrderBeansList = new ArrayList<>();
    public static List<AddTractorBean2> newOrderBeansList2 = new ArrayList<>();

   AddTractorBean1 addTractorBean1;


    public static FarmPe_Logo_Fragment newInstance() {
        FarmPe_Logo_Fragment fragment = new FarmPe_Logo_Fragment();
        return fragment;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmepe_logo_layout, container, false);
     //   backfeed= view.findViewById(R.id.back_feed1);



        linearLayout= view.findViewById(R.id.layout);
        nameee= view.findViewById(R.id.nameee);
        sessionManager = new SessionManager(getActivity());
        recyclerView= view.findViewById(R.id.recylr_2);
        recyclerView1= view.findViewById(R.id.recylr_1);
        farmlist_count= view.findViewById(R.id.farmlist_count);
        reqst_count= view.findViewById(R.id.request_count);

        nameee.setText(sessionManager.getRegId("name"));

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            toast_click_back = lngObject.getString("PleaseclickBACKagaintoexit");



        } catch (JSONException e) {
            e.printStackTrace();
        }


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        // this.finishAffinity();
                        if (doubleBackToExitPressedOnce) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            getActivity().finish();
                            System.exit(0);
                        }
                        doubleBackToExitPressedOnce = true;
                        // Toast.makeText(getActivity().getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, toast_click_back, Snackbar.LENGTH_LONG);
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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce=false;
                            }
                        }, 3000);
                    }

                    return true;
                }
                return false;
            }
        });

        newOrderBeansList2.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
// recyclerView.addItemDecoration(new ItemDecorator( -80));

        AddTractorBean2 img1=new AddTractorBean2( R.drawable.tractor_red," ","");
        newOrderBeansList2.add(img1);

        AddTractorBean2 img2=new AddTractorBean2( R.drawable.gyrovator," ","");
        newOrderBeansList2.add(img2);

        AddTractorBean2 img3=new AddTractorBean2( R.drawable.tractor_green," ","");
        newOrderBeansList2.add(img3);


        homePage_adapter=new HomePage_Adapter(getActivity(),newOrderBeansList2);
        recyclerView.setAdapter(homePage_adapter);





        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm1 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(mLayoutManager_farm1);
// recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.addItemDecoration(new ItemDecorator( -80));

//        AddTractorBean1 img4=new AddTractorBean1( R.drawable.cow_dairy," ","");
//        newOrderBeansList.add(img4);
//
//        AddTractorBean1 img5=new AddTractorBean1( R.drawable.poultry," ","");
//        newOrderBeansList.add(img5);
//
//        AddTractorBean1 img6=new AddTractorBean1( R.drawable.tiger_pic," ","");
//        newOrderBeansList.add(img6);
//
//        AddTractorBean1 img7=new AddTractorBean1( R.drawable.cow," ","");
//        newOrderBeansList.add(img7);

        homePage1_adapter=new HomePage1_Adapter(getActivity(),newOrderBeansList);
        recyclerView1.setAdapter(homePage1_adapter);




        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.Home_Page_Count, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("fjfhffjcount" + result);

                    try{

                        int farm_count = result.getInt("FarmsCount");
                        int request_count = result.getInt("RFQCount");

                        count_images_array = result.getJSONArray("FarmImages");
                        for(int i = 0;i<count_images_array.length();i++){
                            AddTractorBean1 img4=new AddTractorBean1( count_images_array.getString(i)," ","");
                            newOrderBeansList.add(img4);

                        }


                        reqst_count.setText(request_count);
                        farmlist_count.setText(farm_count);

                        homePage1_adapter.notifyDataSetChanged();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }


        return view;
    }

    public class ItemDecorator extends RecyclerView.ItemDecoration{
        private  final int mSpace;

        public  ItemDecorator(int space) {
            this.mSpace = space;
        }
        @Override
        public  void  getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int position = parent.getChildAdapterPosition(view);
            if(position !=0)
                // outRect.top = mSpace;
                outRect.left = mSpace;
        }

    }

}

