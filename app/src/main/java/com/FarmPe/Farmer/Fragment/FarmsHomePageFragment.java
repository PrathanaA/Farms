package com.FarmPe.Farmer.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FarmPe.Farmer.Adapter.FarmsHomeAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FarmsHomePageFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    private List<FarmsImageBean> pagination_list = new ArrayList<>();

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
        newOrderBeansList.clear();
        final GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(false);
        sessionManager=new SessionManager(getActivity());
        FarmsList();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    HomeMenuFragment.drawer.openDrawer(Gravity.START);
                    /*FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("list_farm1", FragmentManager.POP_BACK_STACK_INCLUSIVE);
*/
                    return true;
                }
                return false;
            }
        });


      /*  FarmsImageBean1 img1=new FarmsImageBean1(R.drawable.cow_dairy,"Amrutha Dairy Farm","Commertial Dairy Farming Training,Consulting Project Reporting","","","Jagdish Kumar","Halenahalli DoddaBallapura","");
        newOrderBeansList.add(img1);
        newOrderBeansList.add(img1);
        newOrderBeansList.add(img1);
        newOrderBeansList.add(img1);*/

       /* FarmsImageBean img2=new FarmsImageBean(R.drawable.gyrovator,"Tractor Implements Price","Mahindra Jivo 225 DL 20HP","1 Month","Jagdish Kumar","Rampura Bahjoi");
        newOrderBeansList.add(img2);

        FarmsImageBean img3=new FarmsImageBean(R.drawable.tractor_green,"Tractor Price","Mahindra Jivo 225 DL 20HP","Immediately","Jagdish Kumar","Rampura Bahjoi");
        newOrderBeansList.add(img3);

        FarmsImageBean img4=new FarmsImageBean(R.drawable.tractor_red,"Tractor Price","Mahindra Jivo 225 DL 20HP","Immediately","Jagdish Kumar","Rampura Bahjoi");
        newOrderBeansList.add(img4);*/


       /* farmadapter=new FarmsHomeAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);
*/
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                searchView.clearFocus();
//                System.out.println("lknkknknknknknknknnk");
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                return false;
//
//            }

//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextChange(String newText) {
//               // back_feed.setVisibility(View.GONE);
//                //title.setVisibility(View.GONE);
//                System.out.println("lknkknknknknknknknnk"+newText);
//                sorting(newText);
//
//
//                return false;
//            }
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

                            // List<ModelName> list = arrl.subList(x-10, x-1);
                            if ((newOrderBeansList.size()-pagination_list.size())<4){
                                canLoadMoreData=false;
                                pagination_list=newOrderBeansList.subList(0,newOrderBeansList.size());
                              /*  mAdapter = new Sell_Sub_Categories_Adapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(mAdapter)  ;*/
                                farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(farmadapter);

                            }else {
                                canLoadMoreData=true;

                                pagination_list=newOrderBeansList.subList(0,x-1);
                               /* mAdapter = new Sell_Sub_Categories_Adapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(mAdapter)  ;

*/
                                farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
                                recyclerView.setAdapter(farmadapter);
                            }


                            // newOrderedData("N","CU",bmmvendorstoreid,count1);
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
                    System.out.println("cropsresult"+result);
                    JSONArray cropsListArray=null;
                    try {
                        cropsListArray=result.getJSONArray("FarmsList");
                        System.out.println("e     e e ddd"+cropsListArray.length());
                        for (int i=0;i<cropsListArray.length();i++){
                            JSONObject jsonObject1=cropsListArray.getJSONObject(i);
                            String farm_name=jsonObject1.getString("FarmName");
                            String location=jsonObject1.getString("Location");
                            String image=jsonObject1.getString("FarmImages");
                            String id=jsonObject1.getString("Id");


                            System.out.println("madelslistt"+newOrderBeansList.size());

                            FarmsImageBean crops = new FarmsImageBean(image,farm_name,"","","Commertial Dairy Farming Training,Consulting Project Reporting","Jagdish Kumar",location,id);
                            newOrderBeansList.add(crops);




                        }

                        if (newOrderBeansList.size()<6){
                            pagination_list=newOrderBeansList.subList(0,newOrderBeansList.size());
                            farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
                            recyclerView.setAdapter(farmadapter);


                        }else {
                            pagination_list=newOrderBeansList.subList(0,6);
                            farmadapter=new FarmsHomeAdapter(getActivity(),pagination_list);
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
