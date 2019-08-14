package com.FarmPe.Farms.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Volly_class.Login_post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;




public class ForgotPasswordNew extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    TextView forgot_submit, forgot_pass_text, forgt_pass_detail;
    LinearLayout forgot_back;
    public static EditText mobileno;
    SessionManager sessionManager;

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

                int duration=1000;
                Snackbar snackbar = Snackbar.make(coordinatorLayout,toast_internet, duration);
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


                connectivity_check=false;
            }

        } else {
            message = "No Internet Connection";
            color = Color.RED;

            connectivity_check=true;

            int duration=1000;
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), toast_nointernet, duration);
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

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

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

        forgt_pass_detail = findViewById(R.id.tocnt);

        setupUI(coordinatorLayout);


        sessionManager = new SessionManager(ForgotPasswordNew.this);




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

                            int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout,toast_mobile, duration);
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

                        } else if (mobileno.length() <= 9) {

                            int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout,toast_mob_digits, duration);

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

                                            otp = result.getString("OTP");
                                            forgot_mob_no = result.getString("UserName");
                                            mob_trim=forgot_mob_no.substring(3);
                                            Message = result.getString("Message");
                                            status= result.getInt("Status");

                                            if(status==0){

                                                int duration=1000;
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_not_registered, duration);

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

                                                int duration=1000;
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_exceeded,duration);

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

                                                int duration=1000;
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, toast_number_exceeded, duration);
                                                System.out.println("sfdsfds" + Message);

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

                                                startActivity(intent);
                                            }
                                            System.out.println("for_ottpp" + otp);


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

        finish();

        Intent intent=new Intent(ForgotPasswordNew.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void setupUI(View view) {

        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ForgotPasswordNew.this);
                    return false;
                }

            });
        }


        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity)  {


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