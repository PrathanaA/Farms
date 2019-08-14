package com.FarmPe.Farms.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farms.Activity.LoginActivity;
import com.FarmPe.Farms.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;


import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;


public class HomeMenuFragment extends Fragment implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    Fragment selectedFragment;
    public static DrawerLayout drawer;
    ImageView plus_sign_add;
    RelativeLayout menu;
    JSONArray get_address_array;
    LinearLayout update_acc_layout,your_farms_tab1,farmer_title;
    SessionManager sessionManager;
    public static CircleImageView prod_img,prod_img1;
    public static boolean isEng = false;

    String userid;
    Bitmap bitmap;
  public static  TextView home,settings,list_farm,farm_count,your_addrss,notifictn_count;
    public static TextView user_name_menu,phone_no;

    RelativeLayout notification_bell;
    JSONObject lngObject;
    static boolean fragloaded;
    String pickUPFrom;


    public static String onBack_status=null;



    public static HomeMenuFragment newInstance() {
        fragloaded =true;
        HomeMenuFragment fragment = new HomeMenuFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_navigation_menu_home, container, false);


        menu=view.findViewById(R.id.menu);
        home = view.findViewById(R.id.home);
        phone_no = view.findViewById(R.id.phone_no);

        farmer_title = view.findViewById(R.id.farmer_title);
        your_farms_tab1 = view.findViewById(R.id.your_farms_tab1);
        update_acc_layout=view.findViewById(R.id.update_acc_layout);
        notification_bell=view.findViewById(R.id.notification_bell);
        notifictn_count=view.findViewById(R.id.notifictn_count);
        settings=view.findViewById(R.id.settings);
        prod_img=view.findViewById(R.id.prod_img);
        prod_img1=view.findViewById(R.id.prod_img1);
        farm_count=view.findViewById(R.id.farm_count);






        your_addrss=view.findViewById(R.id.your_addrss);

        list_farm=view.findViewById(R.id.list_farm);


        plus_sign_add=view.findViewById(R.id.plus_sign_add);
        user_name_menu=view.findViewById(R.id.user_name_menu);
        sessionManager = new SessionManager(getActivity());
        userid=sessionManager.getRegId("userId");

        farmer_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });



        your_farms_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawers();
                selectedFragment = FarmsHomePageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        your_addrss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId",sessionManager.getRegId("userId"));
                    jsonObject.put("PickUpFrom",pickUPFrom);
                    System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));
                    System.out.println("ggggggggggaaaaaaa"+jsonObject);

                    Crop_Post.crop_posting(getActivity(), Urls.Get_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("ggggggggggaaaaaaa"+result);

                            try{
                                get_address_array = result.getJSONArray("UserAddressDetails");

                                if(get_address_array.length()== 0){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("navigation_from","HOME_FRAGMENT");
                                    selectedFragment = Add_New_Address_Fragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    selectedFragment.setArguments(bundle);
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    transaction.commit();

                                }else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("navigation_from","HOME_FRAGMENT");
                                    selectedFragment = You_Address_Fragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    selectedFragment.setArguments(bundle);
                                    transaction.commit();

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


        user_name_menu.setText(sessionManager.getRegId("name"));

        phone_no.setText(sessionManager.getRegId("phone").substring(3));




        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);


        System.out.println("lajfdhsjkd");



        if (LoginActivity.change_lang.getText().toString().equals("English")){
            isEng = true;
            Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
        }
        else{
            isEng = false;
            Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
        }



        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));





        } catch (JSONException e) {
            e.printStackTrace();
        }


        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println("hhhrtryur");

        if(onBack_status==null) {


            selectedFragment = FarmPe_Logo_Fragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();

        }




      else if(onBack_status.equals("no_request")){

            selectedFragment = FarmPe_Logo_Fragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();

        }
        else if(onBack_status.equals("farms")){

            selectedFragment = FarmsHomePageFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();

        }

      else{


        }


        prod_img1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "HOME_IMG");
                selectedFragment = FullScreenImageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });







        list_farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedFragment = ListYourFarmsFour.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                 transaction.addToBackStack("list_farm1");
                 transaction.commit();
                 drawer.closeDrawers();

                drawer.closeDrawers();

            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = SettingFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                drawer.closeDrawers();

            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);


            }
        });


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
                        String ProfileName = jsonObject1.getString("FullName");
                        String ProfilePhone = jsonObject1.getString("PhoneNo");
                        String ProfileEmail = jsonObject1.getString("EmailId");
                        String ProfileImage = jsonObject1.getString("ProfilePic");


                        user_name_menu.setText(ProfileName);
                        phone_no.setText(ProfilePhone.substring(3));



                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img);



                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img1);



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }






        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

               prod_img1.setImageBitmap(bitmap);
                prod_img.setImageBitmap(bitmap);
                uploadImage(getResizedBitmap(bitmap,100,100));
                Toast.makeText(getActivity(), "Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();


            }   catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadImage(final Bitmap bitmap2){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");

        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();


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
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();



                Log.e(TAG,"Im here " + params);
                if (bitmap2!=null) {
                    params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));

                }

                Log.e(TAG,"Im here " + params);
                return params;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("FullName",sessionManager.getRegId("name"));
                params.put("PhoneNo",sessionManager.getRegId("phone"));
                params.put("Password",sessionManager.getRegId("pass"));
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }

        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }


    public Bitmap getResizedBitmap(Bitmap bm1, int newWidth, int newHeight) {
        if (bm1==null){

            return null;
        }else {
            System.out.println("llllllllllllllllllllllllllllll"+newHeight);
            int width = bm1.getWidth();
            int height = bm1.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();

            matrix.postScale(scaleWidth, scaleHeight);


            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm1, 0, 0, width, height, matrix, false);

            return resizedBitmap;
        }

    }



}

