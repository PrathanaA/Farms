package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.zip.Inflater;

public class Farm_Edit_Fragment extends Fragment {
    View view;
    ImageView imageView, close;
    TextView name, model_name,product_name,duration,pro_hp,product_id,location;
    LinearLayout back_feed;


    public static Farm_Edit_Fragment newInstance() {
        Farm_Edit_Fragment fragment = new Farm_Edit_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_farmds,container,false);
        imageView = view.findViewById(R.id.image);
        model_name = view.findViewById(R.id.model_namename);
        name = view.findViewById(R.id.name);
        product_name = view.findViewById(R.id.pro_name);

        duration = view.findViewById(R.id.duration);
        pro_hp = view.findViewById(R.id.pro_hp);
        product_id = view.findViewById(R.id.pro_id);
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

                HomeMenuFragment.onBack_status = "farms";

                FragmentManager fm = getActivity().getSupportFragmentManager();

                fm.popBackStack("edit_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });



//        Bundle b = getArguments();
//        location.setText(b.getString("location"));
//        name.setText(b.getString("farmer_name"));
//        duration.setText(b.getString("duration"));
//        pro_hp.setText(b.getString("location"));
//        product_id.setText(b.getString("pro_id"));
//        pro_hp.setText(b.getString("pro_hp"));
//        product_name.setText(b.getString("product_name"));
//        model_name.setText(b.getString("model_name"));

//
//        Glide.with(getActivity()).load(b.getString("image"))
//             .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageView);

        return view;


    }

}
