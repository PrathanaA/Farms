package com.FarmPe.Farmer.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout looking_for,farms,farmer;
    View looking_view,farms_view,farmer_view;
    Fragment selectedFragment;
    JSONObject lngObject;


    SessionManager sessionManager;

    TextView farmer1,farms1,looking_for1;
  public static NestedScrollView scrollView;


    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_main, container, false);
        looking_for=view.findViewById(R.id.looking_for);
        farms=view.findViewById(R.id.farms);
       // farmer=view.findViewById(R.id.farmer);
        looking_view=view.findViewById(R.id.looking_view);
        farms_view=view.findViewById(R.id.farms_view);

       // farm1=view.findViewById(R.id.farms_view);
        //farmer_view=view.findViewById(R.id.farmer_view);
        looking_for1 = view.findViewById(R.id.looking_for1);
        farms1 = view.findViewById(R.id.farms1);
        //farmer1 = view.findViewById(R.id.farmer1);
        sessionManager = new SessionManager(getActivity());

//
//        looking_for1=view.findViewById(R.id.looking_for1);
//        farm1=view.findViewById(R.id.farm1);
//        farmer1=view.findViewById(R.id.farmer1);
//

       scrollView=view.findViewById(R.id.scroll);

        farms_view.setVisibility(View.INVISIBLE);
       // farmer_view.setVisibility(View.INVISIBLE);
        farms1.setTextColor(Color.parseColor("#8c8c8c"));
       // farmer1.setTextColor(Color.parseColor("#8c8c8c"));


     /*  scrollView.post(new Runnable() {

            @Override
            public void run() {
               scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });*/

        selectedFragment = LookingForFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_dashboard, selectedFragment);
        transaction.commit();


       /* try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            looking_for1.setText(lngObject.getString("Lookingfor"));
            farms1.setText(lngObject.getString("Farms"));
           // farmer1.setText(lngObject.getString("Farmer"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

*/

        looking_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farms_view.setVisibility(View.INVISIBLE);
               // farmer_view.setVisibility(View.INVISIBLE);
                looking_view.setVisibility(View.VISIBLE);


                farms1.setTextColor(Color.parseColor("#8c8c8c"));
                //farmer1.setTextColor(Color.parseColor("#8c8c8c"));
                looking_for1.setTextColor(Color.parseColor("#ffffff"));

                selectedFragment = LookingForFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_dashboard, selectedFragment);
                transaction.commit();


            }
        });

        farms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                looking_view.setVisibility(View.INVISIBLE);
                //farmer_view.setVisibility(View.INVISIBLE);
                farms_view.setVisibility(View.VISIBLE);
                looking_for1.setTextColor(Color.parseColor("#8c8c8c"));
               // farmer1.setTextColor(Color.parseColor("#8c8c8c"));
                farms1.setTextColor(Color.parseColor("#ffffff"));



                selectedFragment = FarmsHomePageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_dashboard, selectedFragment);
                transaction.commit();
            }
        });

        /*farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farms_view.setVisibility(View.INVISIBLE);
                looking_view.setVisibility(View.INVISIBLE);
                farmer_view.setVisibility(View.VISIBLE);
                farms1.setTextColor(Color.parseColor("#8c8c8c"));
                looking_for1.setTextColor(Color.parseColor("#8c8c8c"));
                farmer1.setTextColor(Color.parseColor("#ffffff"));



                selectedFragment = FarmerFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_dashboard, selectedFragment);
                transaction.commit();
            }
        });
*/
        return view;
    }



}
