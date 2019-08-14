package com.FarmPe.Farms.Volly_class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.FarmPe.Farms.Urls;


import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class Login_post {
    public static StringRequest stringRequest;
    public static ProgressBar progressBar;

    public static void login_posting(final Activity activity, String url, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");
     //   progressDialog.setContentView(R.layout.small_progress_bar);

   //     progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("1obj1obj"+jsonObject);


                        try {
                            System.out.println("objecttttt"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }

    public static void Forgot_Passsword(final Activity activity, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");
      //  progressDialog.setContentView(R.layout.small_progress_bar);

      //  progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Urls.Forgot_Password, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }

    public static void VerifyOTP(final Activity activity, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");
       // progressDialog.setContentView(R.layout.small_progress_bar);

   //     progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Urls.                                                                                                                                                                                                                                                                                                                                                             VerifyOTPNewUser, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }


    public static void ChangePassword(final Activity activity, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");
      //  progressDialog.setContentView(R.layout.small_progress_bar);
     //   progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Urls.ChangePassword, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }


    public static void SignUp(final Activity activity, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");
       // progressDialog.set(ColorStateList.valueOf(Color.RED));
      //  progressDialog.setContentView(R.layout.small_progress_bar);

      //  progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Urls.SIGNUP, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }

   /* public static void Forgot_Passsword(final Activity activity, String url, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                null,true);
        progressDialog.setContentView(R.layout.small_progress_bar);

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }
*/


   /* public static void VerifyOTP(final Activity activity, final JSONObject jsonObject, final VoleyJsonObjectCallback callback){


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                null,true);
        progressDialog.setContentView(R.layout.small_progress_bar);

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Urls.LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.cancel();
                        System.out.println("jsonobject"+jsonObject);


                        try {
                            System.out.println("jsonobject"+jsonObject);
                            callback.onSuccessResponse(jsonObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("jsonobject"+volleyError);

                        progressDialog.cancel();

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(60),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingletonQuee.getInstance(activity).addToRequestQueue(jobReq);

    }*/
}