package com.FarmPe.Farms.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farms.R;
import com.FarmPe.Farms.ReadSms;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.SmsListener;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Login_post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class Thank_U_New extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

   LinearLayout back_thank_u;
   TextView thanktu_submit,otp_text,thank_title,resend_otp;
   EditText enter_otp;
    JSONObject lngObject;
    public  static String toast_otp,toast_invalid_otp,toast_internet,toast_nointernet;
    public  static String otp_get_text,sessionId;
    LinearLayout linearLayout;
    private Context mContext=Thank_U_New.this;
    private static final int REQUEST=1;
   BroadcastReceiver receiver;
    SessionManager sessionManager;
    String toast_number_exceeded;



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

                int duration=1000;
                Snackbar snackbar = Snackbar.make(linearLayout,toast_internet, duration);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this,R.color.orange));
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

            connectivity_check = true;

            int duration=1000;
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), toast_nointernet,duration);
            View sb = snackbar.getView();
            TextView textView = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
            textView.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this, R.color.orange));
            textView.setTextColor(Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }


            snackbar.show();


        }
    }



    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(Thank_U_New.this).registerReceiver(receiver, new IntentFilter("otp_forgot"));


        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkConnection();
        setContentView(R.layout.thank_you_otp);
        linearLayout=findViewById(R.id.main_layout);
        back_thank_u=findViewById(R.id.arrow_thank_u);
        thanktu_submit=findViewById(R.id.thanktu_submit);
        enter_otp=findViewById(R.id.otp_forgot_pass);
        otp_text=findViewById(R.id.thanktu);
        thank_title=findViewById(R.id.thank);

        resend_otp=findViewById(R.id.f_resend);





        setupUI(linearLayout);
        sessionId= getIntent().getStringExtra("otp_forgot");
        sessionManager = new SessionManager(this);




        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            thanktu_submit.setText(lngObject.getString("SendOTP"));
            thank_title.setText(lngObject.getString("OneTimePassword"));
            otp_text.setText(lngObject.getString("PleaseentertheOTPbelowtoresetpassword"));
            enter_otp.setHint(lngObject.getString("EntertheOTP"));
            toast_otp = lngObject.getString("EntertheOTP");
            toast_invalid_otp = lngObject.getString("InvalidOTP");
            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");
            toast_number_exceeded = lngObject.getString("Youhaveexceededthelimitofresendingotp");
            resend_otp.setText(lngObject.getString("Resend"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        back_thank_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Thank_U_New.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ReadSms.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) { // autofetching

                System.out.println("autofetch_msg"+messageText);
                enter_otp.setText(messageText);

            }
        });




        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    JSONObject postjsonObject = new JSONObject();
                    postjsonObject.put("UserName", ForgotPasswordNew.forgot_mob_no );
                    System.out.println("rrrrrrrrrrrrrrrrrrrr" + postjsonObject);

                    Login_post.login_posting(Thank_U_New.this, Urls.Forgot_Password, postjsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("kkkkkkkkkkkkkkkkkkkkkkkk" + result.toString());

                            try{

                                String  Otp = result.getString("OTP");
                                sessionId=Otp;
                                String  Message = result.getString("Message");
                                int  status= result.getInt("Status");

                                if (status==1){

                                    int duration=1000;
                                    Snackbar snackbar = Snackbar
                                            .make(linearLayout,Message,duration);

                                    View snackbarView = snackbar.getView();
                                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                    tv.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this,R.color.orange));
                                    tv.setTextColor(Color.WHITE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    } else {
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                    }
                                    snackbar.show();
                                }else  if (status==2){

                                    int duration=1000;
                                    Snackbar snackbar = Snackbar
                                            .make(linearLayout,toast_number_exceeded,duration);

                                    View snackbarView = snackbar.getView();
                                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                    tv.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this,R.color.orange));
                                    tv.setTextColor(Color.WHITE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    } else {
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                    }
                                    snackbar.show();
                                }

                                else {
                                    Toast.makeText(Thank_U_New.this, Message, Toast.LENGTH_LONG).show();
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });





        thanktu_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_get_text=enter_otp.getText().toString();
                if (otp_get_text.equals("")){

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout,toast_otp,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {

                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();

                }else {
                    if (otp_get_text.equals(sessionId)){
                        Intent intent=new Intent(Thank_U_New.this,ResetPasswordNew.class);
                        startActivity(intent);
                    }else{

                        int duration=1000;
                        Snackbar snackbar = Snackbar
                                .make(linearLayout,toast_invalid_otp,duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(Thank_U_New.this,R.color.orange));
                        tv.setTextColor(Color.WHITE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {


        Intent intent=new Intent(Thank_U_New.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }




    public void setupUI(View view) {


        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Thank_U_New.this);
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

    public static void hideSoftKeyboard(Activity activity) {


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
