package com.FarmPe.Farmer.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.FarmPe.Farmer.Bean.AgriBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PrivacyPolicyFragment extends Fragment {
    Fragment selectedFragment;
    TextView first_text, second_text;
    LinearLayout back, more, whatsapp, insta, facebook, back_feed, twitter;
    public static String status;
    Intent intent;
    private ArrayAdapter<AgriBean> arrayAdapter;
    private ListView listView;
    String packageName;
    SessionManager sessionManager;

    public static String refer_code;
    JSONObject lngObject;
    TextView editText,privacypolicytxt,first_textt,privacypolicytxt2,privacypolicytxt3,second_t,second_tx;
    private Context context;


    public static PrivacyPolicyFragment newInstance() {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_policy, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        privacypolicytxt=view.findViewById(R.id.toolbar_title);
        first_textt=view.findViewById(R.id.first_text);
        first_text=view.findViewById(R.id.second_text);
        second_text=view.findViewById(R.id.second_textt);
        privacypolicytxt2=view.findViewById(R.id.txtt);
        privacypolicytxt3=view.findViewById(R.id.tx);
        second_t=view.findViewById(R.id.second_te);
        second_tx=view.findViewById(R.id.second_tex);
        privacypolicytxt2.setVisibility(View.GONE);
        privacypolicytxt3.setVisibility(View.GONE);
        second_t.setVisibility(View.GONE);
        second_tx.setVisibility(View.GONE);
        sessionManager = new SessionManager(getActivity());
        first_text.setText("FarmPe and you are independent contractors, and no agency, partnership, joint venture, employee-employer or franchiser-franchisee relationship is intended or created by the Terms." +
                "\nFarmPe shall have the right to assign the Terms (including all of our rights, titles, benefits, interests, and obligations and duties in the Terms to any person or entity (including any affiliates of FarmPe). "+"\nYou may not assign, in whole or part, the Terms to any person or entity.");
        second_text.setText("These terms of use (the “Terms of Use”) govern your use of our “FarmPe” application for mobile and handheld devices (the “App”). The App referred to as the “Platform”."+"\nPlease read these Terms of Use carefully before you use the services. If you do not agree to these Terms of Use, you may not use the services on the Platform, and we request you to uninstall the App."+"\nBy installing, downloading or even merely using the Platform, you shall be contracting with \"FarmPe\" and you signify your acceptance to the Terms of Use and other FarmPe policies (including but not limited to the Cancellation & Refund Policy, Privacy Policy and Take Down Policy) as posted on the Platform from time to time, which takes effect on the date on which you download, install or use the Services, and create a legally binding arrangement to abide by the same."+"\nYou agree that FarmPe shall not be liable or responsible for the activities or consequences of use or misuse of any information that occurs under your display name in cases where You have failed to update Your revised mobile phone number and/or e-mail address on the Website."+"\nYou may not use the Services and may not accept the Terms if (a) you are not of legal age to form a binding contract with FarmPe.");
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
        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            privacypolicytxt.setText(lngObject.getString("PrivacyPolicy"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}

