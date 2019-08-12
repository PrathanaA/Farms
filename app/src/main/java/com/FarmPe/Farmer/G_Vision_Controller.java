package com.FarmPe.Farmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.LandingPageActivity;
import com.FarmPe.Farmer.Adapter.AddPhotoAdapter;
import com.FarmPe.Farmer.Bean.AddPhotoBean;
import com.FarmPe.Farmer.Fragment.ListYourFarmsFive;
import com.FarmPe.Farmer.Volly_class.PackageManagerUtils;
import com.FarmPe.Farmer.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.SafeSearchAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class G_Vision_Controller {

    // static variable single_instance of type Singleton
    private static G_Vision_Controller g_vision_controller_single_instance = null;
    Activity activity;
    Bitmap scaled_bitmap;
    SafeSearchAnnotation annotation;
    private static final int MAX_LABEL_RESULTS = 10;

    private static final String CLOUD_VISION_API_KEY = "AIzaSyASLfdH5Tr931zKrsdH2alWHPxMg6NzD-A";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private  String TAG="G_Vission_controller";
    String currentStatus;

  SessionManager sessionManager;
    private G_Vision_Controller() {
    }


    // static method to create instance of Singleton class
    public static G_Vision_Controller getInstance()
    {
        if (g_vision_controller_single_instance == null)
            g_vision_controller_single_instance = new G_Vision_Controller();

        return g_vision_controller_single_instance;
    }

    public Bitmap callCloudVision(final Bitmap bitmap,Activity activity,String currentStatus) {
        scaled_bitmap=scaleBitmapDown(bitmap,200);
        this.activity=activity;
        this.currentStatus=currentStatus;
        sessionManager=new SessionManager(activity);

        // Switch text to loading
        //    mImageDetails.setText("loading");

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(activity, prepareAnnotationRequest(scaled_bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
        return scaled_bitmap;
    }
    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = activity.getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(activity.getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            scaled_bitmap=bitmap;
            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("SAFE_SEARCH_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<Activity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        ProgressDialog progressDialog ;
        Activity activity2;

        LableDetectionTask(Activity activity1, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity1);
            mRequest = annotate;
            progressDialog  = new ProgressDialog(activity1 ,R.style.MyAlertDialogStyle);
            this.activity2=activity1;

        }

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Please wait");
            progressDialog.show();
            super.onPreExecute();
        }

//        progressDialog.setMessage(" Loading....Please wait");
//        progressDialog.show();

        @Override
        protected String doInBackground(Object... params) {
            try {

                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            System.out.println("llllllllllllllllllllllljjjjjjjjjjjjjjjjjj");

            Activity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                progressDialog.dismiss();

                if (result.equals("APPROVE")){
                    if (currentStatus.equals("farm")){
                        AddPhotoBean img1=new AddPhotoBean( scaled_bitmap);
                        AddPhotoAdapter.productList.add(0,img1);
                        ListYourFarmsFive.farmadapter.notifyDataSetChanged();
                    }else {


                        uploadImage(scaled_bitmap);


                    }




                   /* Toast.makeText(LandingPageActivity.this,"Image contain below details"+annotation.getViolence()+"\n"
                            +annotation.getSpoof()+"\n"+annotation.getMedical()+"\n"+annotation.getAdult(),Toast.LENGTH_LONG).show();*/


                }else {
                    Toast.makeText(activity,"Image is inappropriate, choose related image.",Toast.LENGTH_LONG).show();

                }




            }
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }


    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("");

        System.out.println("kkkkkkkkkkkkkkkkkkk"+response);
        annotation = response.getResponses().get(0).getSafeSearchAnnotation();
        if (annotation != null) {
            if (annotation.getAdult().equals("LIKELY")||annotation.getSpoof().equals("LIKELY")||annotation.getMedical().equals("LIKELY")||annotation.getViolence().equals("LIKELY")){
                message.append("DECLINE");




            }
            else  if (annotation.getAdult().equals("POSSIBLE")||annotation.getSpoof().equals("POSSIBLE")||annotation.getMedical().equals("POSSIBLE")||annotation.getViolence().equals("POSSIBLE")){
                message.append("DECLINE");

            }
            else  if (annotation.getAdult().equals("VERY_LIKELY")||annotation.getSpoof().equals("VERY_LIKELY")||annotation.getMedical().equals("VERY_LIKELY")||annotation.getViolence().equals("VERY_LIKELY")){
                message.append("DECLINE");

            }
            else {
                message.append("APPROVE");

            }



        } else {
            message.append("nothing");
        }

        return message.toString();
    }

    private void uploadImage(final Bitmap bitmap2){
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "",
                "Loading....Please wait.");

        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();

//                        Toast.makeText(getActivity(),"Profile Details Updated Successfully", Toast.LENGTH_SHORT).show();
//                        selectedFragment = SettingFragment.newInstance();
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.frame_layout,selectedFragment);
//                        ft.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(activity),error.getMessage(), Toast.LENGTH_SHORT).show();
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
        //adding the request to volley
        Volley.newRequestQueue(activity).add(volleyMultipartRequest);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}

