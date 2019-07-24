package com.FarmPe.Farmer.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.EnterOTP;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Adapter.AddHpAdapter;
import com.FarmPe.Farmer.Adapter.FarmsImageAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.Farmer.Volly_class.VolleySingletonQuee;
import com.FarmPe.Farmer.volleypost.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.FarmPe.Farmer.Volly_class.Crop_Post.progressDialog;

public class Edit_Looking_For_Fragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddHpAdapter farmadapter;
    SessionManager sessionManager;
    public static String back;
    TextView toolbar_title,update_btn_txt;
    JSONObject lngObject;
    String toast_name,toast_mobile,toast_passwrd,toast_new_mobile,toast_minimum_toast,toast_update,toast_image;
    LinearLayout update_btn,linearLayout;
    private static int RESULT_LOAD_IMG = 1;
    Bitmap selectedImage,imageB;
    EditText profile_name,profile_phone,profile_mail,profile_passwrd;
    CircleImageView prod_img;

    TextView farmer_name,farmer_phone,farmer_email,farmer_loc,delete_req;
    LinearLayout back_feed;
    Fragment selectedFragment;


    public static Edit_Looking_For_Fragment newInstance() {
        Edit_Looking_For_Fragment fragment = new Edit_Looking_For_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmers_detail_page, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        farmer_name=view.findViewById(R.id.farmer_name);
        farmer_phone=view.findViewById(R.id.phone_no);
        farmer_email=view.findViewById(R.id.email);
        farmer_loc=view.findViewById(R.id.loc);
        prod_img=view.findViewById(R.id.prod_img);
        delete_req=view.findViewById(R.id.delete_req);
        sessionManager = new SessionManager(getActivity());




        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                back = "edit_back";

                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                // transaction.addToBackStack("looking");
                transaction.commit();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    back = "edit_back";

                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });


        delete_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId",sessionManager.getRegId("userId"));
                    jsonObject.put("Id",FarmsImageAdapter.looking_forId);


                    Crop_Post.crop_posting(getActivity(), Urls.Delete_Request, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.print("wwwwwefsdddd" + result);

                            try{

                                String status = result.getString("Status");
                                String message = result.getString("Message");


                                if(status.equals("1")){

                                    Snackbar snackbar = Snackbar
                                            .make(linearLayout,message, Snackbar.LENGTH_LONG);
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

                                    selectedFragment = LookingForFragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    transaction.commit();




                                }else{

                                    Toast.makeText(getActivity(), "Request Quotation not deleted", Toast.LENGTH_SHORT).show();
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
        });



//        try{
//
//            JSONObject jsonObject = new JSONObject();
//            JSONObject post_object = new JSONObject();
//
//            jsonObject.put("Id", FarmsImageAdapter.looking_forId);
//            post_object.put("objUser",jsonObject);
//            System.out.println("ggpgpgpg" + post_object);
//
//
//            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("ggpgpgpg" + result);
//
//                    try{
//
//                        JSONObject jsonObject1 = result.getJSONObject("user");
//                        String ProfileName1 = jsonObject1.getString("FullName");
//                        System.out.println("11111" + jsonObject1.getString("FullName"));
//                        String ProfilePhone = jsonObject1.getString("MaskedPhoneNo");
//                        String ProfileEmail = jsonObject1.getString("EmailId");
//                        String ProfileImage = jsonObject1.getString("ProfilePic");
//                        System.out.println("11111" + ProfileName1);
//
//
//
//                        farmer_name.setText(ProfileName1);
//                        farmer_phone.setText(ProfilePhone);
//                        farmer_email.setText(ProfileEmail);
//
//
//                        Glide.with(getActivity()).load(ProfileImage)
//
//                                .thumbnail(0.5f)
//                                //  .crossFade()
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .error(R.drawable.avatarmale)
//                                .into(prod_img);
//
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });






      /*  SharedPreferences myPrefrence = getActivity().getPreferences(MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        if(!imageS.equals("")) imageB = decodeToBase64(imageS);
        prod_img.setImageBitmap(imageB);
*/
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
          /*  char c = source.charAt(index);
            if (isCharAllowed(c))
                sb.append(c);
            else
                keepOriginal = false;*/
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
    private Bitmap decodeToBase64(String input) {

        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                selectedImage = BitmapFactory.decodeStream(imageStream);
                prod_img.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }else {
            Toast.makeText(getActivity(),toast_image,Toast.LENGTH_LONG).show();
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();



        //System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"+byteArrayOutputStream.toByteArray());

    }

    private void uploadImage(final Bitmap bitmap){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //  String resultResponse = new String(response.data);
                        selectedImage=null;

                        Toast.makeText(getActivity(),toast_update, Toast.LENGTH_SHORT).show();
                        selectedFragment = SettingFragment.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout,selectedFragment);
                        ft.commit();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //  params.put("UserId",sessionManager.getRegId("userId") );

                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("FullName",profile_name.getText().toString());
                params.put("PhoneNo",profile_phone.getText().toString());
                params.put("EmailId",profile_mail.getText().toString());
                params.put("Password",profile_passwrd.getText().toString());
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                // params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.e(TAG,"Imhereafaeftagsparams Imhereafaeftagsparams "+bitmap);

                if (bitmap==null){

                }else {
                    params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }
                Log.e(TAG,"Imhereafaeftagsparams "+params);
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    private String encodeToBase64(Bitmap image) {

        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    private void update_profile_details() {

        try{

            StringRequest postRequest = new StringRequest(Request.Method.POST, "http://3.17.6.57:8686/api/Auth/UpdateUserProfile",

                    new Response.Listener<String>()

                    {
                        @Override
                        public void onResponse(String response) {

                            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                                    null, true);
                            progressDialog.setContentView(R.layout.small_progress_bar);
                            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            Log.d("Response", response);

                            try{
                                //progressDialog.cancel();

                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject jsonObject1 = jsonObject.getJSONObject("Response");
                                String status = jsonObject1.getString("Status");

                                if(status.equals("2")){

                                    Toast.makeText(getActivity(), "Your Details Updated Successfully", Toast.LENGTH_SHORT).show();
                                    selectedFragment = SettingFragment.newInstance();
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout,selectedFragment);
                                    ft.commit();

                                }else{

                                    Toast.makeText(getActivity(), "Your Details Not Updated Successfully", Toast.LENGTH_SHORT).show();

                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.cancel();
                            // error
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();

                    params.put("UserId",sessionManager.getRegId("userId"));
                    params.put("FullName",profile_name.getText().toString());
                    params.put("PhoneNo",profile_phone.getText().toString());
                    params.put("EmailId",profile_mail.getText().toString());
                    params.put("Password",profile_passwrd.getText().toString());

                    return params;
                }
            };

            VolleySingletonQuee.getInstance(getActivity()).addToRequestQueue(postRequest);

//            StringRequest stringRequest=new StringRequest(new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//
//                }
//            })



        }catch (Exception e){
            e.printStackTrace();
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

            try {
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }
    }



}
