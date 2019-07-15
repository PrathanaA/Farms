package com.FarmPe.Farmer.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;




public class ForgotPasswordNew extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    TextView forgot_submit, forgot_pass_text, forgt_pass_detail, mob_text_forgot,tocnt;
    LinearLayout forgot_back;
    public static EditText mobileno;
    SessionManager sessionManager;
    Fragment selectedFragment;
    public static String otp, forgot_mob_no, Message,mob_trim;
    LinearLayout coordinatorLayout;
    int status;

    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }

    JSONObject lngObject;
    TextInputLayout emter_pasword;
    EditText spn_localize;
    String localize_text,toast_mobile,toast_valid_number,toast_mob_digits,toast_number_not_registered,toast_number_exceeded,toast_internet,toast_nointernet;


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
                Snackbar snackbar = Snackbar.make(coordinatorLayout,toast_internet, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
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
            textView.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this, R.color.orange));
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
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        checkConnection();


        forgot_back = findViewById(R.id.back_feed);
        forgot_submit = findViewById(R.id.forgot_submit);
        mobileno = findViewById(R.id.mobile_no);
        coordinatorLayout = findViewById(R.id.linear_login);
        forgot_pass_text = findViewById(R.id.forgot);
    //    spn_localize = findViewById(R.id.spn_localize_forgot);
        //emter_pasword=findViewById(R.id.emter_pasword);
        forgt_pass_detail = findViewById(R.id.tocnt);
     //   mob_text_forgot = findViewById(R.id.mob_text_forgot);
        setupUI(coordinatorLayout);

        // next=findViewById(R.id.next);
        sessionManager = new SessionManager(ForgotPasswordNew.this);
       // sessionManager.getRegId("lng_object");
        //System.out.println("llllllllllll" + sessionManager.getRegId("lng_object"));



        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            forgt_pass_detail.setText(lngObject.getString("ForgotPasswordText"));
            mobileno.setHint(lngObject.getString("DigitMobileNumber"));
            forgot_pass_text.setText(lngObject.getString("ForgotPassword") + "?");
            forgot_submit.setText(lngObject.getString("ResetPassword"));


            toast_mobile = lngObject.getString("EnterPhoneNo");
            toast_valid_number = lngObject.getString("ResetPassword");
            toast_mob_digits = lngObject.getString("Pleaseenter10digitsmobilenumber");
            toast_number_not_registered = lngObject.getString("Yournumberisnotregistered");
            toast_number_exceeded = lngObject.getString("Youhaveexceededthelimitofresendingotp");
            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        coordinatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        forgot_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordNew.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

                forgot_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mobileno.getText().toString().equals("")) {

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout,toast_mobile, Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
                            tv.setTextColor(Color.WHITE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();
                          //  Toast.makeText(ForgotPasswordNew.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                        } else if (mobileno.length() <= 9) {
                            //  mobile.setError("Please enter 10 digits mobile number");
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout,toast_mob_digits, Snackbar.LENGTH_LONG);
                            //snackbar.setActionTextColor(R.color.colorAccent);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();

                        } else {
                            try {
                                localize_text="+91";
                                JSONObject postjsonObject = new JSONObject();
                                postjsonObject.put("UserName", localize_text + mobileno.getText().toString());
                                System.out.println("aaaaaaaaaaaa" + postjsonObject);
                                Login_post.Forgot_Passsword(ForgotPasswordNew.this, postjsonObject, new VoleyJsonObjectCallback() {
                                    @Override
                                    public void onSuccessResponse(JSONObject result) {
                                        System.out.println("nnnnnmnm" + result.toString());
                                        try {
                                            // System.out.println("nnnnnmnm" + result.toString());
                                            otp = result.getString("OTP");
                                            forgot_mob_no = result.getString("UserName");
                                            mob_trim=forgot_mob_no.substring(3);
                                            Message = result.getString("Message");
                                            status= result.getInt("Status");

                                            if(status==0){
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_not_registered, Snackbar.LENGTH_LONG);
                                                //snackbar.setActionTextColor(R.color.colorAccent);
                                                View snackbarView = snackbar.getView();
                                                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                                tv.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
                                                tv.setTextColor(Color.WHITE);

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                } else {
                                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                                }
                                                snackbar.show();

                                            }else if (status==2){
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_exceeded, Snackbar.LENGTH_LONG);
                                                //snackbar.setActionTextColor(R.color.colorAccent);
                                                View snackbarView = snackbar.getView();
                                                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                                tv.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
                                                tv.setTextColor(Color.WHITE);
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                } else {
                                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                                }
                                                snackbar.show();
                                            }

                                            else{

                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_exceeded, Snackbar.LENGTH_LONG);
                                                System.out.println("sfdsfds" + Message);
                                                //snackbar.setActionTextColor(R.color.colorAccent);
                                                View snackbarView = snackbar.getView();
                                                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                                tv.setBackgroundColor(ContextCompat.getColor(ForgotPasswordNew.this,R.color.orange));
                                                tv.setTextColor(Color.WHITE);

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                } else {
                                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                                }
                                                snackbar.show();

                                                System.out.println("ffffff" + Message);
                                                Intent intent = new Intent(ForgotPasswordNew.this, Thank_U_New.class);
                                                intent.putExtra("otp_forgot", otp);
                                                //intent.putExtra("forgot_mob_no", forgot_mob_no);
                                                startActivity(intent);
                                            }
                                            System.out.println("for_ottpp" + otp);
                                           /* if (Message.equals("You have Exceeded the limit of resending OTP")) {
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, "You have Exceeded the limit of resending OTP,Please wait for 24 hours to get OTP", Snackbar.LENGTH_LONG);
                                                //snackbar.setActionTextColor(R.color.colorAccent);
                                                View snackbarView = snackbar.getView();
                                                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                                tv.setTextColor(Color.RED);
                                                snackbar.show();
                                            } else {
                                                Intent intent = new Intent(ForgotPassword.this, Thank_U.class);
                                                intent.putExtra("otp_forgot", otp);
                                                //intent.putExtra("forgot_mob_no", forgot_mob_no);
                                                startActivity(intent);
                                            }*/

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }


    @Override
    public void onBackPressed() {
        //System.exit(0);
        finish();

        Intent intent=new Intent(ForgotPasswordNew.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ForgotPasswordNew.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity)  {
 /*InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);*/

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try{
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }catch(AssertionError e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}