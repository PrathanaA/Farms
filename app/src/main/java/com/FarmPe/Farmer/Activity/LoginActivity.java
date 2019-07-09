package com.FarmPe.Farmer.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.SelectLanguageAdapter2;
import com.FarmPe.Farmer.Bean.SelectLanguageBean;
import com.FarmPe.Farmer.DB.DatabaseHelper;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
     public static TextView register, log_in, forgot_pass,mob_text_signin;
     public static EditText mobile_no, pass;
     public static String mobile,loc_text;
     public String status,userId;
     boolean doubleBackToExitPressedOnce = false;

     List<SelectLanguageBean>language_arrayBeanList = new ArrayList<>();
     SelectLanguageBean selectLanguageBean;
     SelectLanguageAdapter2 mAdapter;

     public static TextInputLayout text_mobile,text_pass;

    ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }


     LinearLayout back_xlogin;
     LinearLayout coordinatorLayout;
     public static CheckBox remember_me;
     DatabaseHelper myDb;
    public static boolean connectivity_check;
    public static boolean isEng = false;
     TextInputLayout textInputLayout,textInputLayout_pass;
     public static  String password,mob_toast,mobile_string,pass_toast,toast_invalid,toast_click_back,toast_internet,toast_nointernet;
     EditText spn_localize;
     public static   JSONObject lngObject;
     JSONArray lng_array;
     Snackbar snackbar;
    String mob_no;
    SessionManager sessionManager;
    public static  Dialog dialog;
    public static TextView welcome_back, createaccount, change_lang,farmPe_title ,enterPassword, forgotPassword;


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
                View sbView2 = snackbar.getView();
                TextView textView = (TextView) sbView2.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
                textView.setTextColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                snackbar.show();
                snackbar.show();

                //setting connectivity to false only on executing "Good! Connected to Internet"
                connectivity_check=false;
            }

        }  else {
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
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);



    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        checkConnection();
        welcome_back = findViewById(R.id.welcome_back);
        createaccount = findViewById(R.id.create_acc);
        change_lang = findViewById(R.id.change_lang);
        text_mobile = findViewById(R.id.text_name);
        text_pass = findViewById(R.id.text_pass);



        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        log_in = findViewById(R.id.login_button);
        // setLocale("kn");
        forgot_pass =findViewById(R.id.forgot_pass_login);
        //farmPe_title =findViewById(R.id.farmPe_title);
        mobile_no = findViewById(R.id.mob_no);
        pass = findViewById(R.id.pass);
        //back_xlogin = view.findViewById(R.id.arrow_layout);
        coordinatorLayout =findViewById(R.id.main_layou1);
        remember_me = findViewById(R.id.remember_me);
        loc_text="+91";
        setupUI(coordinatorLayout);
        myDb = new DatabaseHelper(this);
        edittext_move(mobile_no, pass);



        remember_me.setChecked(true);

        if( sessionManager.getRegId("language_name").equals("")){

            change_lang.setText("English");

        }else{

            change_lang.setText(sessionManager.getRegId("language_name"));
        }
       // if(sessionManager.getLanguage())




        final InputFilter EMOJI_FILTER = new InputFilter() {
            @Override

            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int index = start; index < end; index++) {
                    int type = Character.getType(source.charAt(index));
                    if (type == Character.SURROGATE) {
                        return "";
                    }
                }
                return null;
            }
        };

        pass.setFilters(new InputFilter[]{EMOJI_FILTER});

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

        pass.setFilters(new InputFilter[] {filter,new InputFilter.LengthFilter(12) });
        // sessionManager = new SessionManager(this);
        // sessionManager.getRegId("lng_object");
        System.out.println("llllllllllll" + sessionManager.getRegId("language"));

        try {
            if ((sessionManager.getRegId("language")).equals("")){
                getLang(1);

            }else {


                lngObject = new JSONObject(sessionManager.getRegId("language"));

                System.out.println("llllllllllllkkkkkkkkkkkkkkk" +lngObject.getString("EnterPhoneNo"));

                //  createaccount.setHint(lngObject.getString("Createanaccount"));
                //mob_text_signin.setHint(lngObject.getString("Signintoyouraccount"));

                text_mobile.setHint(lngObject.getString("DigitMobileNumber"));
                text_pass.setHint(lngObject.getString("Password"));
                remember_me.setText(lngObject.getString("RememberMe"));
                forgot_pass.setText(lngObject.getString("ForgotPassword") + "?");
                log_in.setText(lngObject.getString("Login"));
                welcome_back.setText(lngObject.getString("Login"));
                createaccount.setText(lngObject.getString("Register"));
              //  farmPe_title.setText(lngObject.getString("FarmPe"));


                pass_toast = lngObject.getString("EnterPassword");
                mob_toast = lngObject.getString("EnterPhoneNo");
                toast_invalid = lngObject.getString("InvalidCredentials");
                toast_click_back = lngObject.getString("PleaseclickBACKagaintoexit");
                toast_internet = lngObject.getString("GoodConnectedtoInternet");
                toast_nointernet = lngObject.getString("NoInternetConnection");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordNew.class);
                startActivity(intent);
            }
        });


        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("jhfdyug");

                RecyclerView recyclerView;
                final TextView yes1,no1;
                final LinearLayout close_layout;

                System.out.println("aaaaaaaaaaaa");
                 dialog = new Dialog(LoginActivity.this);
                 dialog.setContentView(R.layout.change_lang_login);
                 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 dialog.setCancelable(false);

                close_layout =  dialog.findViewById(R.id.close_layout);
                recyclerView =  dialog.findViewById(R.id.recycler_change_lang);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LoginActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                mAdapter = new SelectLanguageAdapter2(LoginActivity.this, language_arrayBeanList);
                recyclerView.setAdapter(mAdapter);


                try{


                        JSONObject jsonObject = new JSONObject();

                        Crop_Post.crop_posting(LoginActivity.this, Urls.Languages, jsonObject, new VoleyJsonObjectCallback() {
                              @Override
                              public void onSuccessResponse(JSONObject result) {
                                  System.out.print("111111ang" + result);
                                  try{

                                       language_arrayBeanList.clear();
                                          lng_array = result.getJSONArray("LanguagesList");
                                          for(int i=0;i<lng_array.length();i++){
                                              JSONObject  jsonObject1 = lng_array.getJSONObject(i);

                                              selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("Language"),jsonObject1.getInt("Id"),"");
                                              language_arrayBeanList.add(selectLanguageBean);



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


//                SelectLanguageBean bean = new SelectLanguageBean("English", 1, "");
//                newOrderBeansList.add(bean);
//
//                SelectLanguageBean bean1 = new SelectLanguageBean("Hindi", 2, "");
//                newOrderBeansList.add(bean1);





                close_layout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });






        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (change_lang.getText().toString().equals("English")){
                    isEng = true;
                    Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
                }
                else{
                    isEng = false;
                    Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
                }
                mobile_string=mobile_no.getText().toString();
                mob_no =loc_text+ mobile_no.getText().toString();
                password = pass.getText().toString();
                System.out.println("pppppppaasssww" + mob_toast);
                System.out.println("pppppmmmmmw" + mobile_string);

                if (mobile_string.equals("")) {
                    mobile_no.requestFocus();

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, mob_toast, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();
//                    snackbar = Snackbar
//                            .make(coordinatorLayout,toast_mob, Snackbar.LENGTH_LONG);

                    //snackbar.setActionTextColor(R.color.colorAccent);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setTextColor(Color.RED);
//                    //tv.setText("abc");
//                    snackbar.show();
                    //  Toast.makeText(LoginActivity.this, "Enter Your Mobile Number", Toast.LENGTH_LONG).show();
//
//                }else if (loc_text == null) {
//                    snackbar = Snackbar
//                            .make(coordinatorLayout, "Please select country code", Snackbar.LENGTH_LONG);
//
//                    //snackbar.setActionTextColor(R.color.colorAccent);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setTextColor(Color.RED);
//                    //tv.setText("abc");
//                    snackbar.show();

                } else if (password.equals("")) {
                    pass.requestFocus();

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, pass_toast, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();

                } else if (password.contains(" ")) {
                    pass.requestFocus();

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Password should not contain spaces", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();


                } else {


                    try{

                        JSONObject jsonObject = new JSONObject();
                        JSONObject post_Object = new JSONObject();

                        jsonObject.put("UserName",mob_no);
                        jsonObject.put("Password",password);
                        post_Object.put("UserRequest",jsonObject);
                        System.out.println("postobjj"+post_Object);


                        Login_post.login_posting(LoginActivity.this, Urls.LOGIN,post_Object, new VoleyJsonObjectCallback()  {
                            @Override
                            public void onSuccessResponse(JSONObject result) {
                                System.out.println("111111user" + result);
                                try{
                                    JSONObject jsonObject;
                                    JSONObject userObject;

                                    jsonObject = result.getJSONObject("ResultObject");

                                    if(!(jsonObject.isNull("user"))){
                                        userObject = jsonObject.getJSONObject("user");
                                        status=jsonObject.getString("Status");
                                        userId=jsonObject.getString("UserId");

                                        System.out.println("useridddd"+userId);

                                        if(status.equals("1")){
                                            System.out.println("jdhyusulogin"+status);
                                            Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                                            startActivity(intent);
                                            sessionManager.createLoginSession(password,mob_no);

                                            sessionManager.save_name(userObject.getString("FullName"),userObject.getString("PhoneNo"),userObject.getString("ProfilePic"));
                                            sessionManager.saveUserId(userObject.getString("Id"));


                                            if(remember_me.isChecked()){

                                                if(!myDb.isEmailExists(mobile_no.getText().toString())){

                                                    AddData(mobile_no.getText().toString(),password);
                                                }
                                            }else {
                                                if(myDb.isEmailExists(mobile_no.getText().toString())){

                                                    DeleteData(mobile_no.getText().toString(),password);
                                                }
                                            }
                                        }

                                    } else{

                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, toast_invalid, Snackbar.LENGTH_LONG);
                                        View snackbarView = snackbar.getView();
                                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                        tv.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
                                        tv.setTextColor(Color.WHITE);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        } else {
                                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                        }
                                        snackbar.show();
                                        snackbar.show();


                                    }

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
        });

    }



    private void getLang(int id) {

        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id",id);


            System.out.print("iiidddddd"+ id);

            Crop_Post.crop_posting(LoginActivity.this, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try{
                        sessionManager.saveLanguage(result.toString());
                        String log_login = result.getString("Login");
                        String log_mobile = result.getString("DigitMobileNumber");
                        String log_password = result.getString("Password");
                        String log_remember_me = result.getString("RememberMe");
                        String log_forgot_passwrd = result.getString("ForgotPassword");
                        String log_register = result.getString("Register");
                      //  String log_title = result.getString("FarmPe");

                        mob_toast = result.getString("EnterPhoneNo");
                        pass_toast = result.getString("EnterPassword");
                        toast_invalid = result.getString("InvalidCredentials");
                        toast_click_back = result.getString("PleaseclickBACKagaintoexit");
                        toast_internet = result.getString("GoodConnectedtoInternet");
                        toast_nointernet = result.getString("NoInternetConnection");




                        remember_me.setText(log_remember_me);
                        log_in.setText(log_login);
                        text_mobile.setHint(log_mobile);
                        //farmPe_title.setText(log_title);

                        forgot_pass.setText(log_forgot_passwrd+"?");
                        text_pass.setHint(log_password);
                        welcome_back.setText(log_login);
                        createaccount.setText(log_register);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }




    }

    @Override
    public void onBackPressed() {

      //  finish();

        if (doubleBackToExitPressedOnce) {

            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.addCategory(Intent.CATEGORY_HOME);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent1);
              finish();                   }


        doubleBackToExitPressedOnce = true;

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout,toast_click_back, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setBackgroundColor(ContextCompat.getColor(LoginActivity.this,R.color.orange));
        tv.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        snackbar.show();
     //   Toast.makeText(getApplicationContext(), toast_click_back, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);


    }

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

                    // System.out.println("hhhhhhhhhhhhhhhhh"+buffer.append(res.getString(0)));

                    while (res.moveToNext()) {
                        buffer.append(res.getString(0) + "\n");
                    }
                    System.out.println("gggggggggggggggggggggjjjjjjjjjjj"+buffer.toString().trim());
                    et2.setText(buffer.toString().trim());
                    remember_me.setChecked(true);
                    System.out.println("wwwwwwwwwwwwwwwww"+et2);


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
        System.out.println("kkkkkkkkkkkkk"+userId);
        System.out.println("sssssssssssss"+pass);
        boolean isInserted = myDb.insertData(userId, pass);

        System.out.println("kkkkkkkkkkkkk"+userId);
        System.out.println("sssssssssssss"+pass);
        if (isInserted == true){
        } else{

        }
    }
    private void DeleteData(String userId,String pass) {
        System.out.println("kkkkkkkkkkkkk"+userId);
        System.out.println("sssssssssssss"+pass);
        //  boolean isInserted = myDb.insertData(userId, pass);
        myDb.deleteData(userId);

    }



    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
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