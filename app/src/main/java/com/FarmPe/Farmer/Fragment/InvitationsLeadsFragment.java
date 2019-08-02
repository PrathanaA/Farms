package com.FarmPe.Farmer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.InvitationLeadAdapter;
import com.FarmPe.Farmer.Bean.ConnectionBean;
import com.FarmPe.Farmer.Bean.Invitation_Bean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InvitationsLeadsFragment extends Fragment {

    public static List<Invitation_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static InvitationLeadAdapter farmadapter;
    Invitation_Bean invitation_bean;
    TextView toolbar_title,item_count;
    LinearLayout back_feed,main_layout;
    Fragment selectedFragment;
    SessionManager sessionManager;
    String item_size;
    JSONArray invitan_array;
    ImageView filter;


    public static InvitationsLeadsFragment newInstance() {
        InvitationsLeadsFragment fragment = new InvitationsLeadsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invitations_leads_recyc, container, false);
        recyclerView=view.findViewById(R.id.recy_connection_invi);
       // toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
      //  filter=view.findViewById(R.id.filter);
        main_layout=view.findViewById(R.id.main_layout);
        item_count=view.findViewById(R.id.item_count);
       // toolbar_title.setText("Select HP");
        sessionManager = new SessionManager(getActivity());




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

             /*   selectedFragment = HomeMenuFragment.newInstance();
                HomeMenuFragment.drawer.openDrawer(Gravity.START);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/

            }
        });


        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        farmadapter=new InvitationLeadAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);



        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.Invitation_Farm, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("fdfddsfds" + result);

                    try{

                        newOrderBeansList.clear();
                        invitan_array = result.getJSONArray("invitationList");
                        for(int i=0;i<invitan_array.length();i++){

                             JSONObject jsonObject1 = invitan_array.getJSONObject(i);
                             JSONObject jsonObject2 = jsonObject1.getJSONObject("Address");
                             invitation_bean = new Invitation_Bean(jsonObject1.getString("FullName"),jsonObject1.getString("RelatedDetail"),jsonObject1.getString("ProfilePic"),jsonObject1.getString("Id"),jsonObject2.getString("City"));
                             newOrderBeansList.add(invitation_bean);
                        }

                        farmadapter.notifyDataSetChanged();

                        if(newOrderBeansList.size()<=1){
                            item_size = String.valueOf(newOrderBeansList.size());
                            item_count.setText("You have " + item_size + " new invitation");

                        }else{

                            item_size = String.valueOf(newOrderBeansList.size());
                            item_count.setText("You have " + item_size + " new invitations");

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

}
