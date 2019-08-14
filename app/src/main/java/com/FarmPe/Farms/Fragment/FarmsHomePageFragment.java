package com.FarmPe.Farms.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.FarmPe.Farms.Adapter.FarmsHomeAdapter;
import com.FarmPe.Farms.Bean.FarmsImageBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Login_post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FarmsHomePageFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    private List<FarmsImageBean> pagination_list = new ArrayList<>();
    TextView filter_text;
    TextView title;
    Fragment selectedFragment = null;

    public static RecyclerView recyclerView;
    public static FarmsHomeAdapter farmadapter;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private List<FarmsImageBean> searchresultAraaylist = new ArrayList<>();

    boolean canLoadMoreData = true; // make this variable false while your web service call is going on.
    int count1 = 1;
    SessionManager sessionManager;



    public static FarmsHomePageFragment newInstance() {
        FarmsHomePageFragment fragment = new FarmsHomePageFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.looking_for_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_looking);
        filter_text = view.findViewById(R.id.filter_text);





        title = view.findViewById(R.id.toolbar_title);
        title.setText("Your Farms");
        newOrderBeansList.clear();


        final GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(false);
        sessionManager=new SessionManager(getActivity());





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



        FarmsList();



        filter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeMenuFragment.onBack_status="farms";


                selectedFragment = Comming_soon_farms.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("looking_edit1");
                transaction.commit();


            }
        });



//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                System.out.println("recyclerCount"+dx);
                System.out.println("recyclerdy"+dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager_farm.getChildCount();
                    totalItemCount = mLayoutManager_farm.getItemCount();
                    pastVisiblesItems = mLayoutManager_farm.findFirstVisibleItemPosition();
                    if (canLoadMoreData)
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            canLoadMoreData = false;
                            //Log.v("...", "Last Item Wow !");
                            count1++;
                            int x=count1*10;
                            System.out.println("llllllllllllllllllllllll"+x);
                            System.out.println("llllllllllllllllllllllll"+pagination_list.size());
                            System.out.println("llllllllllllllllllllllll"+pagination_list.size());


                            if ((newOrderBeansList.size()-pagination_list.size())<=4){
                                canLoadMoreData=false;
                                pagination_list=newOrderBeansList.subList(0,newOrderBeansList.size());

                                farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(farmadapter);

                            }else {
                                canLoadMoreData=true;
                                try {
                                    pagination_list=newOrderBeansList.subList(0,x-1);

                                }catch (Exception e){
                                    canLoadMoreData=false;

                                }



                                farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(farmadapter);
                            }



                        }
                }
            }
        });
        return view;
    }
    private void FarmsList() {

        try {
            newOrderBeansList.clear();

            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));





            Login_post.login_posting(getActivity(), Urls.GetFarmsListByUserId,userRequestjsonObject,new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("cropsresult" + result);
                    JSONArray cropsListArray = null;




                        try {
                            cropsListArray = result.getJSONArray("FarmsList");
                            System.out.println("e     e e ddd" + cropsListArray.length());

                            if (cropsListArray.length() == 0) {
                                selectedFragment = No_List_Farms_Fragment.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();

                            } else {

                                for (int i = 0; i < cropsListArray.length(); i++) {
                                    JSONObject jsonObject1 = cropsListArray.getJSONObject(i);
                                    String farm_name = jsonObject1.getString("FarmName");

                                    String image = jsonObject1.getString("FarmImages");
                                    String id = jsonObject1.getString("Id");
                                    String village = jsonObject1.getJSONObject("FarmAddress").getString("Village");
                                    System.out.println("madelslistt" + newOrderBeansList.size());

                                    FarmsImageBean crops = new FarmsImageBean(image, farm_name, "", "", "Commertial Dairy Farming Training,Consulting Project Reporting", "Jagdish Kumar", village, id);
                                    newOrderBeansList.add(crops);


                                }
                            }

                            if (newOrderBeansList.size() < 6) {
                                pagination_list = newOrderBeansList.subList(0, newOrderBeansList.size());
                                farmadapter = new FarmsHomeAdapter(getActivity(), pagination_list);
                                recyclerView.setAdapter(farmadapter);


                            } else {
                                pagination_list = newOrderBeansList.subList(0, 6);
                                farmadapter = new FarmsHomeAdapter(getActivity(), pagination_list);
                                recyclerView.setAdapter(farmadapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public  void sorting(String filter_text){

        searchresultAraaylist.clear();
        for (FarmsImageBean composeMsgOrderSecondBean: pagination_list) {
            System.out.println("llllllllllllllll"+composeMsgOrderSecondBean.getProd_price());
            final String text = composeMsgOrderSecondBean.getProd_price().toLowerCase();
            final String text2 = composeMsgOrderSecondBean.getFarmer_name().toLowerCase();
            final String text3 = composeMsgOrderSecondBean.getLocation().toLowerCase();

            if (text.contains(filter_text)||text2.contains(filter_text)||text3.contains(filter_text)){

                searchresultAraaylist.add(composeMsgOrderSecondBean);
            }
        }

        farmadapter=new FarmsHomeAdapter(getActivity(),searchresultAraaylist);
        recyclerView.setAdapter(farmadapter);

    }

}
