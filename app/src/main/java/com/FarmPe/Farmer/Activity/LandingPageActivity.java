package com.FarmPe.Farmer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Adapter.AddPhotoAdapter;
import com.FarmPe.Farmer.Bean.AddPhotoBean;
import com.FarmPe.Farmer.Fragment.HomeMenuFragment;
import com.FarmPe.Farmer.Fragment.ListYourFarmsFive;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class LandingPageActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    public static TextView name,variety,loc,grade,quantity,uom,price,add_cart,prof_name,buy_now;
    Fragment selectedFragment = null;
    public static ImageView cart_img;
    public static BottomSheetBehavior mBottomSheetBehavior6;
    View Profile;
    JSONObject lngObject;
    String toast_internet,toast_nointernet;
    CoordinatorLayout coordinate_layout;
    SessionManager sessionManager;
    public static Bitmap selectedImage;

    public  static Activity activity;
    public static String toast_click_back;
    boolean doubleBackToExitPressedOnce = false;



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
                Snackbar snackbar = Snackbar.make(coordinate_layout,toast_internet, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(LandingPageActivity.this,R.color.orange));
                textView.setTextColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                }
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
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), toast_nointernet, Snackbar.LENGTH_LONG);
            View sb = snackbar.getView();
            TextView textView = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
            textView.setBackgroundColor(ContextCompat.getColor(LandingPageActivity.this, R.color.orange));
            textView.setTextColor(Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }


            snackbar.show();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("priyanka"+requestCode);
        System.out.println("priyanka"+resultCode);



        if (requestCode == 200) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                //profile_image.setImageBitmap(selectedImage);
                AddPhotoBean img1=new AddPhotoBean( selectedImage);
                if (!(selectedImage==null)){
                    AddPhotoAdapter.productList.add(0,img1);
                    ListYourFarmsFive.farmadapter.notifyDataSetChanged();


                }else {
                    Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {

        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        checkConnection();
        name=findViewById(R.id.selling_item_name);
        coordinate_layout=findViewById(R.id.coordinator);
       // loc=findViewById(R.id.loc);
       // quantity=findViewById(R.id.quantity);
        price=findViewById(R.id.price);
        add_cart=findViewById(R.id.add_cart);
        buy_now=findViewById(R.id.buy_now);
       // prof_name=findViewById(R.id.prof_name);
        cart_img=findViewById(R.id.cart_img);

        sessionManager = new SessionManager(this);
        activity= this;

        System.out.println("landiiiiiing");

      // getWindow().setStatusBarColor(Color.parseColor("#000000"));

        Window window = activity.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimaryDark));
        LandingPageActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);




        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            toast_click_back = lngObject.getString("PleaseclickBACKagaintoexit");
            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");



        } catch (JSONException e) {
            e.printStackTrace();
        }



        System.out.println("landiiiiiing");

        selectedFragment = HomeMenuFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

        Profile = findViewById(R.id.profile_view);

        mBottomSheetBehavior6 = BottomSheetBehavior.from(Profile);

        mBottomSheetBehavior6.setPeekHeight(0);

        mBottomSheetBehavior6.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior6.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                }
                else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                }
                else if (newState == BottomSheetBehavior.STATE_HIDDEN) {

                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }

        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//            startActivity(intent);
//            activity.finish();
//            System.exit(0);                    }
//
//        doubleBackToExitPressedOnce = true;
//        Snackbar snackbar = Snackbar
//                .make(coordinate_layout,toast_click_back, Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
//        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//        tv.setBackgroundColor(ContextCompat.getColor(LandingPageActivity.this,R.color.orange));
//        tv.setTextColor(Color.WHITE);
//        snackbar.show();
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 3000);
//
//    }
//

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);

    }
}
