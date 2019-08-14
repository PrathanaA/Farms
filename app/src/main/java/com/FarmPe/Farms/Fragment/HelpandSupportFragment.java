package com.FarmPe.Farms.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class HelpandSupportFragment extends Fragment {
    Fragment selectedFragment;

    LinearLayout back_feed;
    public static String status;
    Intent intent;

    SessionManager sessionManager;

    JSONObject lngObject;
    TextView editText,privacypolicytxt;

    WebView terms;
    public static HelpandSupportFragment newInstance() {
        HelpandSupportFragment fragment = new HelpandSupportFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_policy, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        terms=view.findViewById(R.id.web_terms);
        terms.loadUrl("http://farmpe.in/help-support.html");
        System.out.println("termssssss");
        privacypolicytxt=view.findViewById(R.id.toolbar_title);
        privacypolicytxt.setText("Help & Support");
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
        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            privacypolicytxt.setText(lngObject.getString("Help_Support"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}

