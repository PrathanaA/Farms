package com.FarmPe.Farms.Volly_class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class Crop_Post {

    private static Handler handler = new Handler();
    public static StringRequest stringRequest;
    public static ProgressDialog progressDialog;
    public static JSONObject jsonObject, questionsRequest;
    static int pStatus = 0;

    public static void crop_posting(Activity activity, String url, JSONObject postObject, final VoleyJsonObjectCallback callback) {
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");

//        final ProgressDialog progressDialog = new ProgressDialog(activity ,R.style.MyAlertDialogStyle);
//        progressDialog.setMessage(" Loading....Please wait");
//        progressDialog.show();



        // progressDialog.setContentView(R.layout.small_progress_bar);


       // progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        System.out.println("ggggggggggggggggggggBookingAppointmentObj" + postObject);

        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, postObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();

//{"Status":"1","Message":"New KYC Added Succesfully"}
                        try {
                            System.out.println("jsonobjectAppointments" + jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobjectAppointments" + volleyError.getMessage());
                        progressDialog.cancel();
                        //  volleyError.printStackTrace();

                    }
                });


        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 1000,0,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    }








