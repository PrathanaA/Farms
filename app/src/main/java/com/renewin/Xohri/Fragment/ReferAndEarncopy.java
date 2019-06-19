package com.renewin.Xohri.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.renewin.Xohri.Bean.AgriBean;
import com.renewin.Xohri.R;
import com.renewin.Xohri.SessionManager;
import com.renewin.Xohri.Urls;
import com.renewin.Xohri.Volly_class.Crop_Post;
import com.renewin.Xohri.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReferAndEarncopy extends Fragment {
    Fragment selectedFragment;
    TextView send_otp,referal_code;
    LinearLayout back, more,whatsapp,insta,facebook,back_feed,twitter;
    public static String status;
    Intent intent;
    private ArrayAdapter<AgriBean> arrayAdapter;
    private ListView listView;
    String packageName;
    SessionManager sessionManager;

  public static   String refer_code;

  TextView editText;
    private Context context;

    public static ReferAndEarncopy newInstance() {
        ReferAndEarncopy fragment = new ReferAndEarncopy();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refer_earn, container, false);
      //  getActivity().getActionBar().hide();

        whatsapp=view.findViewById(R.id.whatsapp);
        facebook=view.findViewById(R.id.facebook);
        insta=view.findViewById(R.id.insta);
        back_feed=view.findViewById(R.id.back_feed);
        twitter=view.findViewById(R.id.twitter);
        referal_code=view.findViewById(R.id.refer_code);

sessionManager = new SessionManager(getActivity());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);



                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = My_Account_Fragment.newInstance();
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();


            }
        });





        Resources resources = getResources();
        PackageManager pm = getActivity().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        packageName = pm.queryIntentActivities(sendIntent, 0).toString();


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject postjsonObject = new JSONObject();

            jsonObject.put("Id", sessionManager.getRegId("userId"));
            postjsonObject.put("objUser",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Refferal_Code, postjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("Refferal_Codeeeeeeeeeeeeeeeeeee" +result);

                    try{

                        JSONObject jsonObject1;
                        jsonObject1 = result.getJSONObject("user");

                        refer_code= jsonObject1.getString("RefferalCode");

                        referal_code.setText(refer_code);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkonclick");
                try
                {
                    // Check if the Twitter app is installed on the phone.
                    getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey,you found one app \"Xohri_Seller\" all your  need  to download the app & " + "use my invite code "+refer_code +" to get 79Rs on app install!");

                    startActivity(intent);

                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Twitter is not installed on this device",Toast.LENGTH_LONG).show();

                }
            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (packageName.contains("com.whatsapp")) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey,you found one app \"Xohri_Seller\" all your  need .Tap https://play.google.com/store/apps/details?id=com.renewin.xohrifarmer to download the app & " + "use my invite code "+refer_code +" to get 79Rs on app install!");
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT);
                    }

                }else {
                    Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT);

                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try
                {
                    // Check if the Twitter app is installed on the phone.
                    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey,you found one app \"Xohri_Seller\" all your  need  to download the app & " + "use my invite code "+refer_code +" to get 79Rs on app install!");
                    startActivity(intent);

                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Facebook is not installed on this device",Toast.LENGTH_LONG).show();

                }



            }
        });


        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (packageName.contains("com.instagram")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,you found one app \"Xohri_Seller\" all your  need  to download the app & " + "use my invite code "+refer_code +" to get 79Rs on app install!");

                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.instagram.android");
                    try {
                        startActivity(sendIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "Please install instagram ", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Please install instagram ", Toast.LENGTH_LONG).show();

                }
            }
        });


        //back = view.findViewById(R.id.back);
        more = view.findViewById(R.id.more);

      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
*/

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntentSpecificApps();
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });


        /*send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = WalletLinkOTP.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home1, selectedFragment);
                transaction.addToBackStack("menu");
                transaction.commit();
            }
        });
*/
        return view;
    }

    public void shareIntentSpecificApps() {
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            System.out.println("Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;


                if (packageName.contains("com.twitter.android") || packageName.contains("com.facebook.katana")
                        || packageName.contains("com.whatsapp") || packageName.contains("com.google.android.apps.plus")
                        || packageName.contains("com.google.android.talk") || packageName.contains("com.slack")
                        || packageName.contains("com.google.android.gm") || packageName.contains("com.facebook.orca")
                        || packageName.contains("com.yahoo.mobile") || packageName.contains("com.skype.raider")
                        || packageName.contains("com.android.mms") || packageName.contains("com.linkedin.android")
                        || packageName.contains("com.google.android.apps.messaging")) {
                    Intent intent = new Intent();

                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.putExtra("AppName", resInfo.loadLabel(pm).toString());
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey,you found one app \"Xohri_Seller\" all your  need  to download the app & " + "use my invite code "+refer_code +" to get 79Rs on app install!");
                    //intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }
            }
            if (!targetShareIntents.isEmpty()) {
                Collections.sort(targetShareIntents, new Comparator<Intent>() {
                    @Override
                    public int compare(Intent o1, Intent o2) {
                        return o1.getStringExtra("AppName").compareTo(o2.getStringExtra("AppName"));
                    }
                });
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            } else {
                Toast.makeText(getActivity(), "No app to share.", Toast.LENGTH_LONG).show();
            }
        }
    }
}

