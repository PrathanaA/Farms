package com.FarmPe.Farms.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Adapter.SelectLanguageAdapter_SignUP;
import com.FarmPe.Farms.Bean.SelectLanguageBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.Login_post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    public static TextView create_acc, continue_sign_up, change_lang, popup_heading;
    LinearLayout back_feed;
    SessionManager sessionManager;
    public static EditText name, mobile_no, password, referal_code;
    String status, status_resp;
    JSONArray lng_array;
    Activity activity;
    TextView privacy_terms;
    JSONObject lngObject;
    public static TextInputLayout sign_name, sign_mobile, sign_pass;
    public static String mob_toast, passwrd_toast, minimum_character_toast, enter_all_toast, name_toast, mobile_registered_toast, toast_internet, toast_nointernet;

    List<SelectLanguageBean> language_arrayBeanList = new ArrayList<>();
    SelectLanguageBean selectLanguageBean;
    SelectLanguageAdapter_SignUP mAdapter;

    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onStop() {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }


    LinearLayout linearLayout;


    String localize_text;

    public static String contact, mob_contact;
    String refer;
    public static Dialog dialog;


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private void showSnack(boolean isConnected) {
        String message = null;
        int color = 0;
        if (isConnected) {
            if (connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;

                int duration=1000;
                Snackbar snackbar = Snackbar.make(linearLayout, toast_internet, duration);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                textView.setTextColor(Color.WHITE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                }

                snackbar.show();


                connectivity_check = false;
            }


        } else {
            message = "No Internet Connection";
            color = Color.RED;

            connectivity_check = true;

            int duration=1000;
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), toast_nointernet,duration);
            View sb = snackbar.getView();
            TextView textView = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
            textView.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
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
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        MyApplication.getInstance().setConnectivityListener(this);


    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_new);
        checkConnection();


        linearLayout = findViewById(R.id.linear_login);
        back_feed = findViewById(R.id.back_feed);

        create_acc = findViewById(R.id.toolbar_title);
        sign_mobile = findViewById(R.id.sign_mobile);
        sign_name = findViewById(R.id.sign_name);
        sign_pass = findViewById(R.id.sign_pass);
        continue_sign_up = findViewById(R.id.sign_up_continue);
        name = findViewById(R.id.name);

        mobile_no = findViewById(R.id.mobilesignup);
        password = findViewById(R.id.passsignup);
        change_lang = findViewById(R.id.change_lang);
        privacy_terms = findViewById(R.id.privacy_terms);


        sessionManager = new SessionManager(SignUpActivity.this);

        if (sessionManager.getRegId("language_name").equals("")) {

            change_lang.setText("English");

        } else {

            change_lang.setText(sessionManager.getRegId("language_name"));
        }


        setupUI(linearLayout);
        String[] localize = {"+91"};


        try {

            if ((sessionManager.getRegId("language")).equals("")) {
                getLang(1);

            } else {

                lngObject = new JSONObject(sessionManager.getRegId("language"));

                create_acc.setText(lngObject.getString("Register"));
                sign_name.setHint(lngObject.getString("FullName"));
                sign_mobile.setHint(lngObject.getString("PhoneNo"));
                sign_pass.setHint(lngObject.getString("Password"));

                continue_sign_up.setText(lngObject.getString("Register"));


                passwrd_toast = lngObject.getString("Enterpasswordoflength6characters");
                mob_toast = lngObject.getString("Entervalidmobilenumber");
                minimum_character_toast = lngObject.getString("NameShouldContainMinimum2Characters");
                enter_all_toast = lngObject.getString("EnterAllTextFields");
                name_toast = lngObject.getString("Enteryourname");
                mobile_registered_toast = lngObject.getString("Thismobilehasalreadyregistered");
                toast_internet = lngObject.getString("GoodConnectedtoInternet");
                toast_nointernet = lngObject.getString("NoInternetConnection");


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        privacy_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, Privacy_Activity.class);
                startActivity(intent);

            }
        });


        privacy_terms.setText("");
        SpannableString snt = new SpannableString("By Registering, you accept our Privacy policy and Terms of use.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, Privacy_Activity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {

                ds.setUnderlineText(true);
            }
        };
        snt.setSpan(clickableSpan, 31, snt.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacy_terms.setText(snt);

        privacy_terms.setMovementMethod(LinkMovementMethod.getInstance());
        privacy_terms.setHighlightColor(Color.TRANSPARENT);


        sessionManager = new SessionManager(this);


        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("jhfdyug");

                RecyclerView recyclerView;
                final TextView yes1, no1;
                final LinearLayout close_layout;
                System.out.println("aaaaaaaaaaaa");
                dialog = new Dialog(SignUpActivity.this);
                dialog.setContentView(R.layout.change_lang_login);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                close_layout = dialog.findViewById(R.id.close_layout);


                popup_heading = dialog.findViewById(R.id.popup_heading);


                try {
                    lngObject = new JSONObject(sessionManager.getRegId("language"));

                    popup_heading.setText(lngObject.getString("ChangeLanguage"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                recyclerView = dialog.findViewById(R.id.recycler_change_lang);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SignUpActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter = new SelectLanguageAdapter_SignUP(SignUpActivity.this, language_arrayBeanList);
                recyclerView.setAdapter(mAdapter);


                try {

                    JSONObject jsonObject = new JSONObject();

                    Crop_Post.crop_posting(SignUpActivity.this, Urls.Languages, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.print("111111ang" + result);
                            try {

                                language_arrayBeanList.clear();
                                lng_array = result.getJSONArray("LanguagesList");
                                for (int i = 0; i < lng_array.length(); i++) {
                                    JSONObject jsonObject1 = lng_array.getJSONObject(i);

                                    selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("Language"), jsonObject1.getInt("Id"), "");
                                    language_arrayBeanList.add(selectLanguageBean);


                                }

                                mAdapter.notifyDataSetChanged();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


                close_layout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        name.setFilters(new InputFilter[]{EMOJI_FILTER, new InputFilter.LengthFilter(30)});
        password.setFilters(new InputFilter[]{EMOJI_FILTER1, new InputFilter.LengthFilter(12)});


        continue_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localize_text = "+91";
                String name_text = name.getText().toString();

                contact = localize_text + mobile_no.getText().toString();
                System.out.println("connttaaactt" + contact);

                System.out.println("mmoobb" + mob_contact);
                String password_text = password.getText().toString();


                System.out.println("nameeee" + name);
                System.out.println("contacttttt" + contact);
                System.out.println("passss" + password);

                if (name_text.equals("") && mobile_no.getText().toString().equals("") && password_text.equals("")) {
                    System.out.println("enterrrr");

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enter_all_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                } else if (name_text.equals("")) {


                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, name_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                } else if (name_text.length() < 2) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, minimum_character_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                } else if (name_text.startsWith(" ")) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Name should not starts with space",duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                } else if (contact.equals("")) {


                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, passwrd_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                } else if (!(contact.length() == 13)) {


                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, mob_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                } else if (password.equals("")) {


                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Enter Your Password",duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                } else if (password.length() < 6) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, passwrd_toast,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                } else if (password_text.contains(" ")) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Password should not contain spaces",duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                } else {

                    register();

                }
            }
        });


    }

    private void getLang(int id) {

        try {


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id", id);

            System.out.print("iiidddddd" + id);

            Crop_Post.crop_posting(SignUpActivity.this, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try {

                        sessionManager.saveLanguage(result.toString());


                        String log_regi = result.getString("Register");
                        String log_name = result.getString("FullName");
                        String log_mobile = result.getString("PhoneNo");
                        String log_password = result.getString("Password");
                        String log_register = result.getString("Register");

                        passwrd_toast = result.getString("Enterpasswordoflength6characters");
                        mob_toast = result.getString("Entervalidmobilenumber");
                        name_toast = result.getString("Enteryourname");
                        mobile_registered_toast = result.getString("Thismobilehasalreadyregistered");
                        toast_internet = lngObject.getString("GoodConnectedtoInternet");
                        toast_nointernet = lngObject.getString("NoInternetConnection");


                        sign_name.setHint(log_name);
                        sign_mobile.setHint(log_mobile);
                        sign_pass.setHint(log_password);
                        create_acc.setText(log_regi);
                        continue_sign_up.setText(log_register);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            String specialChars = ".1/*!@#$%^&*()\"{}_[]|\\?/<>,.:-'';§£¥₹...%&+=€π|";
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL || type == Character.MATH_SYMBOL || specialChars.contains("" + source)) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    } else if (Character.isDigit(source.charAt(i))) {
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

/////////////////////////////////////////////////

    public static InputFilter EMOJI_FILTER1 = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);

            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }

                String filtered = "";
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isWhitespace(character)) {
                        filtered += character;
                    }
                }
                return filtered;

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


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void setupUI(View view) {


        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SignUpActivity.this);
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

            try {
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }
    }




    private void register() {

        try {

            // mobile=mob_no;
            JSONObject userRequestjsonObject = new JSONObject();
            JSONObject postjsonObject = new JSONObject();


            userRequestjsonObject.put("PhoneNo", contact);
            userRequestjsonObject.put("Password", password.getText().toString());
            userRequestjsonObject.put("DeviceId", "123456789");
            userRequestjsonObject.put("DeviceType", "Android");
            userRequestjsonObject.put("FullName", name.getText().toString());


            postjsonObject.putOpt("objUser", userRequestjsonObject);

            System.out.println("post_oobject" + postjsonObject);



            Login_post.login_posting(SignUpActivity.this, Urls.SIGNUP, postjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss" + result);
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject_resp = new JSONObject();

                    try {
                        if (result.isNull("user")) {
                            jsonObject_resp = result.getJSONObject("Response");
                            status_resp = jsonObject_resp.getString("Status");

                            int duration=1000;
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,mobile_registered_toast,duration);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this,R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            snackbar.show();


                        } else {
                            jsonObject = result.getJSONObject("user");
                            status = jsonObject.getString("OTP");
                            String userid = jsonObject.getString("Id");
                            System.out.println("useerrrriidd" + userid);
                            sessionManager.saveUserId(userid);
                            sessionManager.save_name(jsonObject.getString("FullName"), jsonObject.getString("PhoneNo"),jsonObject.getString("ProfilePic"));
                            Intent intent = new Intent(SignUpActivity.this, EnterOTP.class);
                            intent.putExtra("otpnumber", status);
                            startActivity(intent);
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


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);

    }
}

