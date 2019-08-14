package com.FarmPe.Farms.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.R;

public class Farm_Edit_Fragment extends Fragment {
    View view;
    ImageView imageView,b_arrow;
    TextView name,location;
    LinearLayout back_feed;


    public static Farm_Edit_Fragment newInstance() {
        Farm_Edit_Fragment fragment = new Farm_Edit_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_farmds,container,false);
        imageView = view.findViewById(R.id.image);

        name = view.findViewById(R.id.name);
        b_arrow = view.findViewById(R.id.b_arrow);



        location = view.findViewById(R.id.location);
        back_feed = view.findViewById(R.id.back_feed);


        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    HomeMenuFragment.onBack_status = "farms";

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("edit_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));

                HomeMenuFragment.onBack_status = "farms";

                FragmentManager fm = getActivity().getSupportFragmentManager();

                fm.popBackStack("edit_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });



        return view;


    }

}
