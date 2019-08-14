package com.FarmPe.Farms.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farms.DB.DatabaseHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.FarmPe.Farms.Bean.FarmsImageBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.Farms.volleypost.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.e;

public class UpdateAccDetailsFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;

    SessionManager sessionManager;
    TextView toolbar_title,update_btn_txt;
    JSONObject lngObject;
    String toast_name,toast_mobile,toast_passwrd,toast_new_mobile,toast_minimum_toast,toast_update,toast_image;
    LinearLayout update_btn,linearLayout;

    EditText profile_name,profile_phone,profile_mail,profile_passwrd;
    CircleImageView prod_img;
    private static int RESULT_LOAD_IMG = 1;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    DatabaseHelper myDb;
    ImageView name_tick,phone_tick,pass_tick,b_arrow;
    LinearLayout back_feed;
    Fragment selectedFragment;


    public static UpdateAccDetailsFragment newInstance() {
        UpdateAccDetailsFragment fragment = new UpdateAccDetailsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_acc_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        name_tick = view.findViewById(R.id.name_tick);
        phone_tick = view.findViewById(R.id.phone_tick);
        pass_tick = view.findViewById(R.id.pass_tick);

        toolbar_title = view.findViewById(R.id.toolbar_title);
        update_btn_txt = view.findViewById(R.id.update_btn_txt);
        back_feed = view.findViewById(R.id.back_feed);
        profile_name = view.findViewById(R.id.profile_name);
        profile_phone = view.findViewById(R.id.profile_phone);
        profile_mail = view.findViewById(R.id.profile_mail);
        profile_passwrd = view.findViewById(R.id.profile_passwrd);
        prod_img = view.findViewById(R.id.prod_imgg);
        update_btn = view.findViewById(R.id.update_btn);
        linearLayout = view.findViewById(R.id.main_layout);
        b_arrow=view.findViewById(R.id.b_arrow);
        sessionManager = new SessionManager(getActivity());
        myDb = new DatabaseHelper(getActivity());

        setupUI(linearLayout);

        prod_img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("status","ACC_IMG");
                selectedFragment = FullScreenImageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.commit();

            }
        });

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });


        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            toolbar_title.setText(lngObject.getString("UpdateYourAccountDetails"));
            update_btn_txt.setText(lngObject.getString("Update"));
            profile_name.setHint(lngObject.getString("Enteryourname"));
            profile_phone.setHint(lngObject.getString("EnterPhoneNo"));
            profile_mail.setHint(lngObject.getString("Enteryourmailid"));



            profile_passwrd.setHint(lngObject.getString("Password"));
            toast_name = (lngObject.getString("Enteryourname"));
            toast_mobile = (lngObject.getString("Pleaseenter10digitsmobilenumber"));
            toast_passwrd = (lngObject.getString("Enterpasswordoflength6characters"));
            toast_new_mobile = (lngObject.getString("EnterPhoneNo"));
            toast_minimum_toast = (lngObject.getString("NameShouldContainMinimum2Characters"));
            toast_update = (lngObject.getString("YourDetailsUpdatedSuccessfully"));
            toast_image = (lngObject.getString("YouhaventpickedImage"));


        } catch (JSONException e) {
            e.printStackTrace();
        }






        final InputFilter filter1 = new InputFilter() {
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

        profile_passwrd.setFilters(new InputFilter[] {filter1,new InputFilter.LengthFilter(12) });



        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggpgpgpg" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("FullName");
                        System.out.println("11111" + jsonObject1.getString("FullName"));
                        String ProfilePhone = jsonObject1.getString("PhoneNo");
                        String ProfileEmail = jsonObject1.getString("EmailId");
                        String ProfileImage = jsonObject1.getString("ProfilePic");
                        System.out.println("11111" + ProfileName1);



                        profile_name.setText(ProfileName1);
                        profile_phone.setText(ProfilePhone);
                        profile_mail.setText(ProfileEmail);

                        profile_name.setFilters(new InputFilter[]{EMOJI_FILTER});
                        profile_phone.setFilters(new InputFilter[]{EMOJI_FILTER});
                        profile_mail.setFilters(new InputFilter[]{EMOJI_FILTER});

                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                //  .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img);


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }




        profile_passwrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profile_passwrd.length()<6){
                    pass_tick.setVisibility(View.GONE);


                }else{
                    pass_tick.setVisibility(View.VISIBLE);

                }

            }
        });

        profile_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(profile_name.getText().toString().length()>=2){
                    name_tick.setVisibility(View.VISIBLE);
                }

                else{

                    name_tick.setVisibility(View.INVISIBLE);
                }
            }
        });

        profile_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(profile_phone.getText().toString().length()!=13){
                    phone_tick.setVisibility(View.INVISIBLE);
                }
                else{
                    phone_tick.setVisibility(View.VISIBLE);


                }

                if (profile_phone.getText().toString().length()<3){
                    profile_phone.setText("+91");
                    profile_phone.setSelection(profile_phone.getText().toString().length());
                }
            }
        });



        profile_passwrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                if(profile_passwrd.getText().toString().length()<=12 && profile_passwrd.getText().toString().length()>=6){
                    pass_tick.setVisibility(View.VISIBLE);
                }
                else{
                    pass_tick.setVisibility(View.INVISIBLE);
                }
            }
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   System.out.println("nnbchcxbhchvcvccgcv"+profile_passwrd.getText().toString());
                if(profile_name.getText().toString().equals("")) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, toast_name,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                } else if(profile_name.getText().toString().length()<2) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, toast_minimum_toast, duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                }else if(profile_phone.getText().toString().equals("")) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, toast_new_mobile,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                }else if(profile_phone.getText().toString().length()<10) {

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, toast_mobile,duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                }else if((!profile_passwrd.getText().toString().equals("")&&(profile_passwrd.getText().toString().length()<6))){

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, toast_passwrd, duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                } else  {

                    uploadImage(getResizedBitmap(bitmap,100,100));
                }

            }
        });


        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {


            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                prod_img.setImageBitmap(bitmap);

                int duration=1000;
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Your Changed Your Profile Photo", duration);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                tv.setTextColor(Color.WHITE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                snackbar.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadImage(final Bitmap bitmap){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");

        progressDialog.show();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();


                        if(profile_passwrd.getText().toString().length()<=12 && profile_passwrd.getText().toString().length()>=6){

                            if(!myDb.isEmailExists(profile_phone.getText().toString())) {

                                AddData(profile_phone.getText().toString(), profile_passwrd.getText().toString());
                            }
                        }

                        else{

                        }

                        HomeMenuFragment.prod_img.setImageBitmap(bitmap);
                        HomeMenuFragment.prod_img1.setImageBitmap(bitmap);



                        int duration=1000;

                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Profile Details Updated Successfully", duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();



                        selectedFragment = SettingFragment.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout,selectedFragment);
                        ft.commit();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("FullName",profile_name.getText().toString());
                params.put("PhoneNo",profile_phone.getText().toString());
                params.put("EmailId","abcd@gmail.com");


                params.put("Password",profile_passwrd.getText().toString());
                Log.e(TAG,"afaeftagsparams"+params);


                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                Log.e(TAG,"Im here " + params);
                if (bitmap!=null) {
                    params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
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


        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
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






    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        if (bm==null){

            return null;
        }else {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        }

    }

}

