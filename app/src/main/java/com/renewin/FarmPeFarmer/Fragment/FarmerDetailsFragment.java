package com.renewin.FarmPeFarmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.renewin.FarmPeFarmer.Adapter.FarmerImageAdapter;
import com.renewin.FarmPeFarmer.Adapter.NearByHorizontalAdapter;
import com.renewin.FarmPeFarmer.Adapter.NotificationAdapter;
import com.renewin.FarmPeFarmer.Bean.NearByHorizontalBean;
import com.renewin.FarmPeFarmer.Bean.NotificationBean;
import com.renewin.FarmPeFarmer.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FarmerDetailsFragment extends Fragment {

    public static List<NotificationBean> newOrderBeansList = new ArrayList<>();
    public static List<NearByHorizontalBean> newOrderBeansList_horizontal = new ArrayList<>();
    public static RecyclerView recyclerView,recyclerView_horizontal;
    TextView toolbar_title,farmer_name,farmer_phone,farmer_email,farmer_loc;
    LinearLayout back_feed;
    RelativeLayout menu;
    CircleImageView prod_img;
    NearByHorizontalAdapter madapter;
    public static NotificationAdapter farmadapter;




    public static FarmerDetailsFragment newInstance() {
        FarmerDetailsFragment fragment = new FarmerDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmers_detail_page, container, false);
       // recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        menu=view.findViewById(R.id.menu);
        farmer_name=view.findViewById(R.id.farmer_name);
        farmer_phone=view.findViewById(R.id.phone_no);
        farmer_email=view.findViewById(R.id.email);
        farmer_loc=view.findViewById(R.id.loc);
        prod_img=view.findViewById(R.id.prod_img);

        Glide.with(getActivity()).load(FarmerImageAdapter.farmer_image)

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatarmale)
                .into(prod_img);

        farmer_name.setText(FarmerImageAdapter.farmer_name1);
        farmer_phone.setText(FarmerImageAdapter.farmer_phn);
        farmer_email.setText(FarmerImageAdapter.farmer_emil);
        farmer_loc.setText(FarmerImageAdapter.farmer_loc);


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("farmer", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });





        return view;
    }



}
