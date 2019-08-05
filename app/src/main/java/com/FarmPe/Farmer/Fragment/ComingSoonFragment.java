package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.FarmPe.Farmer.R;

public class ComingSoonFragment extends Fragment {
    Fragment selectedFragment;
   public LinearLayout backfeed1;

    public static ComingSoonFragment newInstance() {
        ComingSoonFragment fragment = new ComingSoonFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comming_layout, container, false);
        backfeed1= view.findViewById(R.id.back_feed1);







        backfeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HomeMenuFragment.onBack_status = "farms";

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("your_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);



            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    HomeMenuFragment.onBack_status = "farms";

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("your_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });











        return view;
    }
}

