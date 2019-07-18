package com.FarmPe.Farmer.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.ForgotPasswordNew;
import com.FarmPe.Farmer.Activity.LandingPageActivity;
import com.FarmPe.Farmer.Activity.SignUpActivity;
import com.FarmPe.Farmer.Activity.XLoginNew;
import com.FarmPe.Farmer.DB.DatabaseHelper;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {
    TextView register, log_in, forgot_pass,mob_text_signin;
     public static EditText mobile_no, pass;
     public static String mobile,loc_text;
     LinearLayout linearLayout;
     SessionManager sessionManager;
     public String status,userId;
     boolean doubleBackToExitPressedOnce = false;
     LinearLayout back_xlogin;
    LinearLayout coordinatorLayout;
    public static CheckBox remember_me;
    DatabaseHelper myDb;
    TextInputLayout textInputLayout,textInputLayout_pass;
    String password,toast_mob,mobile_string,pass_toast;
    EditText spn_localize;
    JSONObject lngObject;
    Snackbar snackbar;
    String mob_no;
    TextView welcome_back, createaccount, enterPhoneNo, enterPassword, forgotPassword;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in, container, false);
        // textInputLayout=view.findViewById(R.id.mob_textinput);
       // spn_localize=view.findViewById(R.id.spn_localize_signin);
       // textInputLayout_pass=view.findViewById(R.id.pass_input_text);
        linearLayout=view.findViewById(R.id.main_layout);
        welcome_back = view.findViewById(R.id.welcome_back);
        createaccount = view.findViewById(R.id.create_acc);
        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        log_in = view.findViewById(R.id.login_button);
        // setLocale("kn");
        forgot_pass = view.findViewById(R.id.forgot_pass_login);
        mobile_no = view.findViewById(R.id.mob_no);
        pass = view.findViewById(R.id.pass);
        //back_xlogin = view.findViewById(R.id.arrow_layout);
        coordinatorLayout = view.findViewById(R.id.main_layout);
        remember_me = view.findViewById(R.id.remember_me);

        loc_text="+91";
        setupUI(linearLayout);

        myDb = new DatabaseHelper(getActivity());

        edittext_move(mobile_no, pass);
        FirebaseMessaging.getInstance().subscribeToTopic("LKF");

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                  /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("xlogin", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                    Intent intent=new Intent(getActivity(), XLoginNew.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

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

        sessionManager = new SessionManager(getActivity());
        // sessionManager.getRegId("lng_object");
        System.out.println("llllllllllll" + sessionManager.getRegId("langdetails"));

        try {
            lngObject=new JSONObject(sessionManager.getRegId("langdetails"));
            welcome_back.setText(lngObject.getString("WelcomeBack"));
            mobile_no.setHint(lngObject.getString("EnterPhoneNo"));
            pass.setHint(lngObject.getString("EnterPassword"));
            toast_mob=lngObject.getString("EnterPhoneNo");
            pass_toast=lngObject.getString("EnterPassword");
            // mobile_no.setHint(lngObject.getString("EnterPhoneNo"));
            // textInputLayout.setHint(lngObject.getString("EnterPhoneNo"));
            //textInputLayout_pass.setHint(lngObject.getString("EnterPassword"));
            remember_me.setText(lngObject.getString("RememberMe"));
            forgot_pass.setText(lngObject.getString("ForgotPassword")+"?");
            log_in.setText(lngObject.getString("Login"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("ooooooooobb"+toast_mob);


        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordNew.class);
                startActivity(intent);
            }
        });


        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_string=mobile_no.getText().toString();
                mob_no =loc_text+ mobile_no.getText().toString();
                password = pass.getText().toString();

                if (mobile_string.equals("")) {
                    mobile_no.requestFocus();
                    Toast.makeText(getActivity(),"enter your mobile number", Toast.LENGTH_SHORT).show();
//
//                    snackbar = Snackbar
//                            .make(coordinatorLayout,toast_mob, Snackbar.LENGTH_LONG);
//
//                    //snackbar.setActionTextColor(R.color.colorAccent);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setTextColor(Color.RED);
//                    //tv.setText("abc");
//                    snackbar.show();
                    //Toast.makeText(Login.this, "Enter Your Mobile Number", Toast.LENGTH_LONG).show();

//                }else if (loc_text == null) {
//                    snackbar = Snackbar
//                            .make(coordinatorLayout, "Please select country code", Snackbar.LENGTH_LONG);
//
//                    //snackbar.setActionTextColor(R.color.colorAccent);
//                    Toast.makeText(get, "", Toast.LENGTH_SHORT).show();
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setTextColor(Color.RED);
//                    //tv.setText("abc");
//                    snackbar.show();

                } else if (password.equals("")) {
                    pass.requestFocus();
                    Toast.makeText(getActivity(),pass_toast, Toast.LENGTH_SHORT).show();
//                    Snackbar snackbar = Snackbar
//                            .make(coordinatorLayout, pass_toast, Snackbar.LENGTH_LONG);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setTextColor(Color.RED);
//                    snackbar.show();

                } else if (password.contains(" ")) {
                    pass.requestFocus();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Password should not contain spaces", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.RED);
                    snackbar.show();
                } else {



                    try{

                        JSONObject jsonObject = new JSONObject();
                        JSONObject post_Object = new JSONObject();


                        jsonObject.put("UserName",mob_no);
                        jsonObject.put("Password",password);
                        post_Object.put("UserRequest",jsonObject);

                        System.out.println("postobjj"+post_Object);

                        Login_post.login_posting(getActivity(), Urls.LOGIN,post_Object, new VoleyJsonObjectCallback()  {
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
                                            Intent intent = new Intent(getActivity(), LandingPageActivity.class);
                                            startActivity(intent);
                                            sessionManager.createLoginSession(password,mob_no);

                                            sessionManager.save_name(userObject.getString("FullName"),userObject.getString("PhoneNo"),userObject.getString("image"));
                                            sessionManager.saveUserId(userId);

                                            if(remember_me.isChecked()){

                                                if(!myDb.isEmailExists(mobile_no.getText().toString())){

                                                    AddData(mobile_no.getText().toString(),password);
                                                }
                                            }
                                        }


                                    } else{

                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, jsonObject.getString("Message"), Snackbar.LENGTH_LONG);
                                        View snackbarView = snackbar.getView();
                                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                        tv.setTextColor(Color.RED);
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


        return view;
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




    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
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
}