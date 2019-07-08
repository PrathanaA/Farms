package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.R;

import java.util.ArrayList;
import java.util.List;



public class ListYourFarmsFour extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstListYourFramsAdapter farmadapter;
    LinearLayout back_feed;
    Fragment selectedFragment;
    TextView toolbar_title,continue_4;
    EditText farm_name,contact_person,email;




    public static ListYourFarmsFour newInstance() {
        ListYourFarmsFour fragment = new ListYourFarmsFour();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_fourth_layout_item, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_4=view.findViewById(R.id.continue_4);

        farm_name=view.findViewById(R.id.name);
        contact_person=view.findViewById(R.id.contact);
        email=view.findViewById(R.id.email);

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("lookingThird", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("lookingThird", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });


        continue_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ListYourFarmsFive.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
              transaction.addToBackStack("lookingFourth");
                transaction.commit();
            }
        });


       /* toolbar_title=view.findViewById(R.id.toolbar_title);

        final Bundle bundle=getArguments();

        toolbar_title.setText("Select Category");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bundle==null){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("looking", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();
                }


            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus(View.FOCUS_UP);
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (bundle==null){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("looking", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }else{
                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        // transaction.addToBackStack("looking");
                        transaction.commit();
                    }
                }
                return false;
            }
        });

        // AddLookigFor();
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AddTractorBean img1=new AddTractorBean(R.drawable.tractor_green,"Request for Quotation","");
        newOrderBeansList.add(img1);

        AddTractorBean img2=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img2);
        newOrderBeansList.add(img1);
        newOrderBeansList.add(img2);

        AddTractorBean img3=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img3);
        newOrderBeansList.add(img2);


        AddTractorBean img4=new AddTractorBean(R.drawable.gyrovator,"Business Plans","");
        newOrderBeansList.add(img4);

        AddTractorBean img5=new AddTractorBean(R.drawable.tractor_red,"Legal","");
        newOrderBeansList.add(img5);

        AddTractorBean img6=new AddTractorBean(R.drawable.tractor_green,"Agricultural Banking","");
        newOrderBeansList.add(img6);

        AddTractorBean img7=new AddTractorBean(R.drawable.tractor_red,"Agricultural Insurance","");
        newOrderBeansList.add(img7);

        AddTractorBean img8=new AddTractorBean(R.drawable.tractor_red,"Rural Finance","");
        newOrderBeansList.add(img8);

        farmadapter=new AddFirstListYourFramsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);
*/
        farm_name.setFilters(new InputFilter[]{EMOJI_FILTER,new InputFilter.LengthFilter(30)});
        contact_person.setFilters(new InputFilter[]{EMOJI_FILTER,new InputFilter.LengthFilter(30)});
        email.setFilters(new InputFilter[]{EMOJI_FILTER,new InputFilter.LengthFilter(30)});



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
