package com.FarmPe.Farmer.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.R;

import java.util.ArrayList;
import java.util.List;

public class ListYourFarmsFour extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstListYourFramsAdapter farmadapter;
    LinearLayout back_feed,main_layout;
    Fragment selectedFragment;
    EditText farm_name,cont_person_name,mobile_no,email_id;
    public static String farm_name_string,cont_name,mob_no,email_id_strg;
    TextView toolbar_title,continue_4;




    public static ListYourFarmsFour newInstance() {
        ListYourFarmsFour fragment = new ListYourFarmsFour();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_fourth_layout_item, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_4=view.findViewById(R.id.continue_4);
        farm_name=view.findViewById(R.id.farm_name);
        cont_person_name=view.findViewById(R.id.cont_person_name);
        mobile_no=view.findViewById(R.id.mobile_no);
        email_id=view.findViewById(R.id.email_id);
        main_layout=view.findViewById(R.id.main_layout);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = ListYourFarmsThird.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ListYourFarmsThird.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                // transaction.addToBackStack("looking");
                transaction.commit();
            }
        });

        continue_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (farm_name.getText().toString().equals("") || cont_person_name.getText().toString().equals("") || mobile_no.getText().toString().equals("") || email_id.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Please enter all the details", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    farm_name_string = farm_name.getText().toString();
                    cont_name = cont_person_name.getText().toString();
                    mob_no = mobile_no.getText().toString();
                    email_id_strg = email_id.getText().toString();
                    selectedFragment = ListYourFarmsFive.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();
                }
            }
        });



        return view;
    }



}
