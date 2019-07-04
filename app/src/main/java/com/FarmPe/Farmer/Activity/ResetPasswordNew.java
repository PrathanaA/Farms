package com.FarmPe.Farmer.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.Farmer.DB.DatabaseHelper;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class ResetPasswordNew extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
   LinearLayout back_reset_pass,linearLayout;
   TextView pass_submit,reset_text,to_continue_text;
    EditText passwd,conf_pass;
    SessionManager sessionManager;
    DatabaseHelper myDb;
    TextInputLayout conf_pass_txt,passwd_txt;
    String passwrd_toast,passwrd_length_toast,confirm_passwrd_toast,pass_not_matching_toast,toast_internet,toast_nointernet;
    TextInputLayout passwd1_text_input,conf_pass_textinput;
    String forgot_username,localize_text;

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
                Snackbar snackbar = Snackbar.make(linearLayout,toast_internet, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
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
            Snackbar.make(findViewById(android.R.id.content), toast_nointernet, Snackbar.LENGTH_LONG).show();

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
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        checkConnection();
        back_reset_pass=findViewById(R.id.arrow_reset_pass);
        pass_submit=findViewById(R.id.password_submit);
        passwd=findViewById(R.id.passwd1);
        conf_pass=findViewById(R.id.conf_pass1);
        passwd_txt=findViewById(R.id.passwd_txt);
        conf_pass_txt=findViewById(R.id.conf_pass_txt);
        linearLayout=findViewById(R.id.main_layout);
        reset_text=findViewById(R.id.reset);
        to_continue_text=findViewById(R.id.tocnt);
        myDb = new DatabaseHelper(this);

        edittext_move(ForgotPasswordNew.mobileno, passwd);

        setupUI(linearLayout);

        // next=findViewById(R.id.next);

        sessionManager = new SessionManager(this);
       // sessionManager.getRegId("lng_object");
        //System.out.println("llllllllllll" + sessionManager.getRegId("lng_object"));
        JSONObject lngObject;


        try {
            lngObject=new JSONObject(sessionManager.getRegId("language"));


            reset_text.setText(lngObject.getString("ResetPassword"));
            pass_submit.setText(lngObject.getString("Submit"));
            passwd_txt.setHint(lngObject.getString("NewPassword"));
            conf_pass_txt.setHint(lngObject.getString("Confirmthepassword"));
            passwrd_toast = lngObject.getString("EnterPassword");
            passwrd_length_toast = lngObject.getString("Enterpasswordoflength6characters");
            pass_not_matching_toast = lngObject.getString("Yourpasswordisnotmatching");
            confirm_passwrd_toast = lngObject.getString("Confirmthepassword");
            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        forgot_username=ForgotPasswordNew.forgot_mob_no.substring(3);

        back_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResetPasswordNew.this,Thank_U_New.class);
                startActivity(intent);
                finish();
            }
        });




       // passwd.setFilters(new InputFilter[]{EMOJI_FILTER});

        final InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String filtered = "";
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isWhitespace(character)) {
                        filtered += character;
                    }
                }
                return filtered;
            }
        };

        passwd.setFilters(new InputFilter[] {filter,new InputFilter.LengthFilter(12) });
        conf_pass.setFilters(new InputFilter[] {filter,new InputFilter.LengthFilter(12) });


        pass_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = passwd.getText().toString();
                final String confirmP = conf_pass.getText().toString();
                if (password.equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, passwrd_toast, Snackbar.LENGTH_LONG);
                    View snackbarView=snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                    //Toast.makeText(ResetPassword.this, "Enter The Password", Toast.LENGTH_LONG).show();
                    // passwrd.setError("Enter The Password");

                }else if (password.contains(" ")) {
                    passwd.requestFocus();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Password should not contain spaces", Snackbar.LENGTH_LONG);
                    View snackbarView=snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                }

                else if (password.length()<6){
                    passwd.requestFocus();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, passwrd_length_toast, Snackbar.LENGTH_LONG);
                    View snackbarView=snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                    //Toast.makeText(ResetPassword.this, "Enter Minimum 6 Characters", Toast.LENGTH_LONG).show();
                    // passwrd.setError("Enter Minimum 6 Character");

                }
                else if (confirmP.equals("")) {
                    conf_pass.requestFocus();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, confirm_passwrd_toast, Snackbar.LENGTH_LONG);
                    View snackbarView=snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                    //Toast.makeText(ResetPassword.this, "Confirm Your Password", Toast.LENGTH_LONG).show();
                    // confpass.setError("Confirm Your Password");


                }else if (!(password.equals(confirmP))){
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, pass_not_matching_toast, Snackbar.LENGTH_LONG);
                    View snackbarView=snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(ResetPasswordNew.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                    //Toast.makeText(ResetPassword.this, "Password Not Matching", Toast.LENGTH_LONG).show();
                    // Snackbar snackbar=Snackbar.make(v,"Password Not Matching",Snackbar.LENGTH_LONG);

                }
                else

                {
                    try {

                        localize_text = "+91";

                        System.out.println("aaaaaaaaaaaa");
                        JSONObject postjsonObject = new JSONObject();
                        JSONObject postjsonObject1 = new JSONObject();
                        postjsonObject.putOpt("UserName",localize_text + forgot_username);
                        postjsonObject.putOpt("Password", passwd.getText().toString());
                        postjsonObject1.putOpt("UserRequest",postjsonObject);
                        System.out.println("111111111111111111111111"+postjsonObject1);


                        Login_post.ChangePassword(ResetPasswordNew.this, postjsonObject1, new VoleyJsonObjectCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject result) {
                                System.out.println("nnnnnmnmrenewwwwwwwrenewwwwwww" + result.toString());
                                try {
                                    JSONObject responseobject = new JSONObject(result.toString());
                                    if (result.isNull("Result")) {
                                        System.out.println("jjjjjj");

                                        JSONObject user = responseobject.getJSONObject("ResultObject");
                                        String status=user.getString("Status");
                                        String message=user.getString("Message");
                                        System.out.println("renewwwwwww"+message);
                                        System.out.println("sttrenewwwwwwwrenewwwwwwwrenewwwwwww"+status);
                                        if (status.equals("1")){

                                            //sessionManager.createLoginSession(password, ForgotPassword.forgot_mob_no);
                                            System.out.println("qwertyuioaaa" + password + ForgotPasswordNew.forgot_mob_no);
                                            if (myDb.isEmailExists(ForgotPasswordNew.mob_trim)){
                                                myDb.updateContact(ForgotPasswordNew.mob_trim,conf_pass.getText().toString().trim());
                                            }
                                            System.out.println("hhhhhgggere" +message);
                                            Toast.makeText(ResetPasswordNew.this, message, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(ResetPasswordNew.this, LoginActivity.class);
                                            startActivity(intent);
                                           /* if (LoginFragment.rememberMe.isChecked()) {

                                                if (!myDb.isEmailExists(ForgotPasswordNew.forgot_mob_no)) {
                                                    AddData(ForgotPassword.forgot_mob_no, password);
                                                }

                                            }*/

                                        }else {
                                            Toast.makeText(ResetPasswordNew.this, "Password not Updated", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        JSONObject response = responseobject.getJSONObject("Response");
                                        Toast.makeText(ResetPasswordNew.this, response.getString("Errors"), Toast.LENGTH_SHORT).show();
                                    }


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
        Intent intent=new Intent(ResetPasswordNew.this,Thank_U_New.class);
        startActivity(intent);
        finish();
    }




    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
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

    public void edittext_move(final EditText et1, final EditText et2) {
        et1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (et1.getText().toString().length() == 10)     //size as per your requirement
                {


                    Cursor res = myDb.getAllData(et1.getText().toString().trim());
                    if (res.getCount() == 0) {

                        et2.requestFocus();


                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append(res.getString(0) + "\n");

                    }
                    et2.setText(buffer.toString().trim());


                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }
    private void AddData(String userId,String pass) {

        boolean isInserted = myDb.insertData(userId, pass);
        if (isInserted == true){
        } else{

        }
    }


    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ResetPasswordNew.this);
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

    public static void hideSoftKeyboard(Activity activity) {
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
