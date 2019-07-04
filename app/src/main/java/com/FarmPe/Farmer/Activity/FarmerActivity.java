package com.FarmPe.Farmer.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Adapter.AddHpAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FarmerActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddHpAdapter farmadapter;
    TextView toolbar_title;
    Fragment selectedFragment;
    String toast_internet,toast_nointernet;
    JSONObject lngObject;
    SessionManager sessionManager;
    LinearLayout back_feed,next_layout,main_layout;
    CheckBox check_box;


    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private void showSnack(boolean isConnected) {
        String message = null;
        int color=0;
        if (isConnected) {
            if(connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;
                Snackbar snackbar = Snackbar.make(main_layout,toast_internet, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(FarmerActivity.this,R.color.orange));
                textView.setTextColor(Color.WHITE);
                snackbar.show();

                //setting connectivity to false only on executing "Good! Connected to Internet"
                connectivity_check=false;
            }

        } else {
            message = "No Internet Connection";
            color = Color.RED;
            //setting connectivity to true only on executing "Sorry! Not connected to internet"
            connectivity_check=true;
            // Snackbar snackbar = Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_LONG);
            Snackbar.make(findViewById(android.R.id.content),toast_nointernet, Snackbar.LENGTH_LONG).show();

          /*  View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();*/
        }
    }



    @Override
    public void onResume() {

        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);
        // register connection status listener


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_request);
        checkConnection();
        sessionManager = new SessionManager(this);

        back_feed=findViewById(R.id.back_feed);
        main_layout=findViewById(R.id.main_layout);
        toolbar_title=findViewById(R.id.toolbar_title);
      //  check_box=findViewById(R.id.check_box);
        next_layout=findViewById(R.id.next_layout);
        toolbar_title.setText("Request for Quotation");

       // check_box.setText("I agree that by clicking 'Request for Tractor Price' button, I am explicitly soliciting a call from Xohri App users on my 'Mobile' in order to assist me with my tractor purchase.");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FarmerActivity.this,FarmerUser.class);
                startActivity(intent);
            }
        });


        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));



            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");



        } catch (JSONException e) {
            e.printStackTrace();
        }




        next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FarmerActivity.this,FarmerActivityNext.class);
                startActivity(intent);
                /*selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = FarmerActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("request");
                transaction.commit();*/
            }
        });

}

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);

    }
}