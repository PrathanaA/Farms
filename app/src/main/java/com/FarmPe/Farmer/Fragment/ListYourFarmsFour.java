package com.FarmPe.Farmer.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.R;
import java.util.ArrayList;
import java.util.List;



public class ListYourFarmsFour extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstListYourFramsAdapter farmadapter;
    LinearLayout back_feed,main_layout,whatsapp,twitter,facebook,instagram;
    Fragment selectedFragment;
    String packageName;
    EditText farm_name,cont_person_name,mobile_no,email_id;
    public static String farm_name_string,cont_name,mob_no,email_id_strg;
    TextView toolbar_title,continue_4;
    public static String FACEBOOK_URL = "https://www.facebook.com/FarmPe-698463080607409/";
    public static String FACEBOOK_PAGE_ID = "FarmPe-698463080607409";


    public static ListYourFarmsFour newInstance() {
        ListYourFarmsFour fragment = new ListYourFarmsFour();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_fourth_layout_item, container, false);

        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_4=view.findViewById(R.id.continue_4);
        farm_name=view.findViewById(R.id.farm_name);
        cont_person_name=view.findViewById(R.id.cont_person_name);
        whatsapp=view.findViewById(R.id.whatsapp);
        twitter=view.findViewById(R.id.twitter);
        facebook=view.findViewById(R.id.facebook);
        instagram=view.findViewById(R.id.insta);
        mobile_no=view.findViewById(R.id.mobile_no);
     //   email_id=view.findViewById(R.id.email_id);
        main_layout=view.findViewById(R.id.main_layout);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        farm_name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30) });
        cont_person_name.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30) });
        //email_id.setFilters(new InputFilter[] {EMOJI_FILTER1,new InputFilter.LengthFilter(30) });


        Resources resources = getResources();
        PackageManager pm = getActivity().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        packageName = pm.queryIntentActivities(sendIntent, 0).toString();



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Bundle bundle = new Bundle();
                    bundle.putString("status","default");
                    selectedFragment = ListYourFarmsThird.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    selectedFragment.setArguments(bundle);
                    transaction.commit();

//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    fm.popBackStack("list_four", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("status","default");
                selectedFragment = ListYourFarmsThird.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.commit();


//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack("list_four", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });



        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (packageName.contains("com.whatsapp")) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey , you found one app \"FarmPeFarmer\" Tap https://play.google.com/store/apps/details?id=com.renewin.FarmPeFarmer to download the app!");

                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {



                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Whatsapp have not been installed", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();

                    }

                }else {
                   //Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT);


                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Whatsapp have not been installed", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();

                }

            }
        });


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (packageName.contains("com.facebook.katana")) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(getActivity());
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    //   startActivity(facebookIntent);
                    try {
                        startActivity(facebookIntent);
                    } catch (ActivityNotFoundException e) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Facebook is not installed on this device", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();

                    }
                }else {
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Facebook is not installed on this device", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                }
            }
        });


        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (packageName.contains("com.twitter")) {
                    Uri uri = Uri.parse("https://twitter.com/FarmPe_Official");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.twitter.android");
                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Twitter is not installed on this device", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();
                    }

                }else {
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Twitter is not installed on this device", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                }

            }
        });




//
//        twitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                try
//                {
//                    // Check if the Twitter app is installed on the phone.
//                    getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_TEXT, "Hey , you found one app \"FarmPeFarmer\" Tap https://play.google.com/store/apps/details?id=com.renewin.FarmPeFarmer to download the app!");
//
//                    startActivity(intent);
//
//                }
//                catch (Exception e)
//                {
//
//
//                    Snackbar snackbar = Snackbar
//                            .make(main_layout, "Twitter is not installed on this device ", Snackbar.LENGTH_LONG);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                    tv.setTextColor(Color.WHITE);
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    } else {
//                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                    }
//                    snackbar.show();
//
//                }
//
//            }
//        });
//


        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (packageName.contains("com.instagram")) {
                    Uri uri = Uri.parse("https://www.instagram.com/farmpe_official/");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.instagram.android");
                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
      /*      startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/farmpe_official/")));*/
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Instagram is not installed on this device", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();
                    }
                }else {
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Instagram is not installed on this device", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();
                }

            }
        });



        continue_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (farm_name.getText().toString().equals("") && cont_person_name.getText().toString().equals("") && mobile_no.getText().toString().equals("") ){
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Please enter all the details", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                }else if(farm_name.getText().toString().equals("")){

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Farm Name cannot be empty", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();



                }else if(farm_name.getText().toString().length()<2){

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Farm Name Should contain minimum Two characters", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();



                }else if(cont_person_name.getText().toString().equals("")){

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Person Name cannot be empty", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();





                }else if(cont_person_name.length()<2){

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Person Name Should contain minimum Two characters", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                }else if(mobile_no.getText().toString().length()<10) {

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Enter 10 digits mobile number", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();




//                }else if(email_id.getText().toString().equals("")) {
//
//                    Snackbar snackbar = Snackbar
//                            .make(main_layout, "Enter Your EmailID", Snackbar.LENGTH_LONG);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                    tv.setTextColor(Color.WHITE);
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    } else {
//                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                    }
//                    snackbar.show();
//
//
//                }else if ((!(email_id.getText().toString().equals(""))&&((! email_id.getText().toString().matches(emailPattern))))) {
//
//
//                    Snackbar snackbar = Snackbar
//                            .make(main_layout, "Enter a valid Email id", Snackbar.LENGTH_LONG);
//                    View snackbarView = snackbar.getView();
//                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                    tv.setTextColor(Color.WHITE);
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    } else {
//                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                    }
//                    snackbar.show();



                } else {
                    farm_name_string = farm_name.getText().toString();
                    cont_name = cont_person_name.getText().toString();
                    mob_no = mobile_no.getText().toString();
                    selectedFragment = ListYourFarmsFive.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();
                }
            }
        });



        return view;
    }


    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return FACEBOOK_URL;
            } else { //older versions of fb app
                return FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
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
//                for (int i = start; i < end; i++) {
//                    if (Character.isWhitespace(source.charAt(i))) {
//                        if (dstart == 0)
//                            return "";
//                    }
//                }
                // return null;
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





}
