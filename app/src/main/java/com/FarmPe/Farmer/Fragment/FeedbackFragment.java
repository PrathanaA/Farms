package com.FarmPe.Farmer.Fragment;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.FarmPe.Farmer.Bean.AgriBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackFragment extends Fragment {
    Fragment selectedFragment;
    TextView send_otp, referal_code,feedback_type,submit,feedbacktxt;;
    LinearLayout back, more, whatsapp, insta, facebook, back_feed, twitter,linearLayout;
    public static String status,message;
    Intent intent;
    private ArrayAdapter<AgriBean> arrayAdapter;
    private ListView listView;
    String packageName;
    SessionManager sessionManager;
    EditText feedback_title,feedback_description;
    JSONObject lngObject;
    String fedback_title,feedtype,feeddesc,fd_sucess,fd_failure,enterallfields;
    public static String refer_code;
    RadioGroup fdType;
    String stat="3";

    private Context context;

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragment = new FeedbackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        feedback_title=view.findViewById(R.id.fd_title);
        feedback_title.setFilters(new InputFilter[]{EMOJI_FILTER});
        feedback_description=view.findViewById(R.id.fd_descp);
        feedback_description.setFilters(new InputFilter[]{EMOJI_FILTER});
        feedback_type=view.findViewById(R.id.fd_type);
        submit=view.findViewById(R.id.submit);
        linearLayout=view.findViewById(R.id.main_layout);
        fdType=view.findViewById(R.id.radio_group);

        feedbacktxt=view.findViewById(R.id.toolbar_title);
        //clickcopyurltxt=view.findViewById(R.id.clickcopyurl);
        sessionManager = new SessionManager(getActivity());







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


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });

        fdType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = group.findViewById(checkedId);

                if (radioButton.getTag().toString().equals("1")) {
                    stat = "1";


                } else if (radioButton.getTag().toString().equals("2")) {
                    stat = "2";


                }  else if (radioButton.getTag().toString().equals("3")) {
                    stat = "3";


                }else{


                }


            }

        });


        /*feedback_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.feedback_title_popup);
                final TextView app_suggest = (TextView) dialog.findViewById(R.id.tractor);
                final TextView app_technical = (TextView) dialog.findViewById(R.id.bus);

                final TextView others = (TextView)dialog.findViewById(R.id.othrs) ;
                ImageView image = (ImageView) dialog.findViewById(R.id.close_popup);

                final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;


                try {
                    lngObject = new JSONObject(sessionManager.getRegId("language"));
                    popuptxt.setText(lngObject.getString("FeedbackType"));
                    others.setText(lngObject.getString("Others"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                app_suggest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        feedback_type.setText(app_suggest.getText().toString());
                        // gettingAddress("Home");
                        dialog.dismiss();
                    }
                });

                app_technical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        feedback_type.setText(app_technical.getText().toString());
                        dialog.dismiss();
                        //   gettingAddress("Barn");

                    }
                });



                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        feedback_type.setText(others.getText().toString());
                        dialog.dismiss();
                        // gettingAddress("Others");


                    }
                });

                dialog.show();


            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feedback_type.getText().toString().equals("")&& feedback_title.getText().toString().equals("")&& feedback_description.getText().toString().equals("")){
                    //Toast.makeText(getActivity(), "Select Feedback Type", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, enterallfields, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();

                }else if(feedback_type.getText().toString().equals("")){
                    //Toast.makeText(getActivity(), "Select Feedback Type", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, feedtype, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();

                }else if(feedback_title.getText().toString().equals("")) {

                    Snackbar snackbar = Snackbar
                            .make(linearLayout, fedback_title, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();


                }else if(feedback_description.getText().toString().equals("")){

                    Snackbar snackbar = Snackbar
                            .make(linearLayout, feeddesc, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();

                }else {

                    Addfeddback();
                }


            }
        });

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            feedbacktxt.setText(lngObject.getString("FeedBack"));
            submit.setText(lngObject.getString("Submit"));
            feedback_type.setHint(lngObject.getString("FeedbackType"));
            feedback_title.setHint(lngObject.getString("FeedbackTitle"));
            feedback_description.setHint(lngObject.getString("FeedbackDescription"));

            feedtype=lngObject.getString("Selectfeedbacktype");
            fedback_title=lngObject.getString("Enterfeedbacktitle");
            feeddesc=lngObject.getString("Enterfeedbackdescription");
            fd_sucess=lngObject.getString("Feedbacksubmitttedsuccessfully");
            fd_failure=lngObject.getString("YourFeedbacknotSubmitted");
            enterallfields=lngObject.getString("EnterAllTextFields");


        } catch (
                JSONException e) {
            e.printStackTrace();
        }

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

    private void Addfeddback() {

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("FeedbackType",stat);
            jsonObject.put("FeedbackTitle",feedback_title.getText().toString());
            jsonObject.put("FeedbackDescription",feedback_description.getText().toString());
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));

            System.out.println("nnnnnnnnnnnnnnnaaaaaaaaa"+jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.AddFeedback, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("AddFeedbackkkkkkkkkkkkkkkkkkkkkkk"+result);

                    try{

                        status= result.getString("Status");
                        message = result.getString("Message");

                        if(!(status.equals("0"))){
                            //Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout, fd_sucess, Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.RED);
                            snackbar.show();

                            selectedFragment = SettingFragment.newInstance();
                            FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();


                        }else{

                           // Toast.makeText(getActivity(),"Your Feedback not Submitted ",Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout, fd_failure, Snackbar.LENGTH_LONG);
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
