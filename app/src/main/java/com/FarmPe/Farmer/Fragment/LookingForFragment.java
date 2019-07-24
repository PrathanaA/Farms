package com.FarmPe.Farmer.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.FarmPe.Farmer.Adapter.FarmsImageAdapter;
import com.FarmPe.Farmer.Adapter.NotificationAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LookingForFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    private List<FarmsImageBean> searchresultAraaylist = new ArrayList<>();

    public static RecyclerView recyclerView;
    public static FarmsImageAdapter farmadapter;
    Fragment selectedFragment = null;
    boolean doubleBackToExitPressedOnce = false;
    String location;
    TextView filter_text,delete_req;
    SessionManager sessionManager;


    public static LookingForFragment newInstance() {
        LookingForFragment fragment = new LookingForFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.looking_for_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_looking);
        filter_text=view.findViewById(R.id.filter_text);
        delete_req =view.findViewById(R.id.delete_req);

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sessionManager = new SessionManager(getActivity());
      // System.out.println("bbbbbbbbbbbbbbbbb"+ "+1-333-444-5678".replaceAll("[^\\d\\+]", "").replaceAll("\\d(?=\\d{4})", "*"));
       System.out.println("bbbbbbbbbbbbbbbbb"+ "+1-333-444-5678".replaceAll("\\d{4}(?=\\d)", "*"));
      // System.out.println("bbbbbbbbbbbbbbbbbbbb"+ "+1-333-444-5678".replaceAll("\\d{4}(?=\\d)", "*"));

        LookingForList();

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






        filter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = Comming_soon_looking.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("looking_edit");
                transaction.commit();


            }
        });

/*

        FarmsImageBean img1=new FarmsImageBean(R.drawable.tractor_green,"Tractor Price","Mahindra Jivo 225 DL 20HP","","Immediately","Jagdish Kumar","Rampura Bahjoi","");
        newOrderBeansList.add(img1);

        FarmsImageBean img2=new FarmsImageBean(R.drawable.gyrovator,"Tractor Implements Price","Mahindra Jivo 225 DL 20HP","","1 Month","Jagdish Kumar","Rampura Bahjoi","");
        newOrderBeansList.add(img2);

        FarmsImageBean img3=new FarmsImageBean(R.drawable.tractor_green,"Tractor Price","Mahindra Jivo 225 DL 20HP","","Immediately","Jagdish Kumar","Rampura Bahjoi","");
        newOrderBeansList.add(img3);

        FarmsImageBean img4=new FarmsImageBean(R.drawable.tractor_red,"Tractor Price","Mahindra Jivo 225 DL 20HP","","Immediately","Jagdish Kumar","Rampura Bahjoi","");
        newOrderBeansList.add(img4);
        newOrderBeansList.add(img4);
        newOrderBeansList.add(img4);
        newOrderBeansList.add(img4);
        newOrderBeansList.add(img4);


        farmadapter=new FarmsImageAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);
*/

//
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
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // back_feed.setVisibility(View.GONE);
//                //title.setVisibility(View.GONE);
//                System.out.println("lknkknknknknknknknnk"+newText);
//                sorting(newText);
//
//
//                return false;
//            }
//        });



        return view;
    }
    private void LookingForList() {

        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.YourRequest, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("YourRequestttttttttttttttttt"+result);
                    JSONArray cropsListArray=null;

                    try {
                        cropsListArray=result.getJSONArray("LookingForList");
                        System.out.println("e     e e ddd"+cropsListArray.length());
                        for (int i=0;i<cropsListArray.length();i++){
                            JSONObject jsonObject1=cropsListArray.getJSONObject(i);
                            JSONObject jsonObject2=jsonObject1.getJSONObject("Address");

                            String model=jsonObject1.getString("Model");
                            String purchaseTimeline=jsonObject1.getString("PurchaseTimeline");
                            String image=jsonObject1.getString("ModelImage");
                            String id=jsonObject1.getString("CreatedBy");
                            String name=jsonObject2.getString("Name");
                            String city=jsonObject2.getString("City");
                            String state=jsonObject2.getString("State");
                            String hp_range=jsonObject1.getString("HorsePowerRange");
                            location=city+", "+state;

                         /*   if (city.equals("")){
                                location="Bangalore"+", "+state;
                            }else{
                                location=city+", "+state;
                            }
*/


                            System.out.println("madelslistt"+newOrderBeansList.size());

                            FarmsImageBean crops = new FarmsImageBean(image,"Tractor Price",model,hp_range,purchaseTimeline,name,location,id);
                            newOrderBeansList.add(crops);



                          /*  if(!latts.equals("") | !langgs.equals("")) {

                                CropListBean crops = new CropListBean(cropName, crop_variety, location, crop_grade,
                                        crop_quantity, crop_uom, crop_price, id, farmerId,
                                        UserName,latts,langgs,CropImg,category);
                                newOrderBeansList.add(crops);
                            }*/
                        }
                        farmadapter=new FarmsImageAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(farmadapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }





/*

        try {
            newOrderBeansList.clear();

            JSONObject userRequestjsonObject = new JSONObject();


          */
/*  JSONObject postjsonObject = new JSONObject();
            postjsonObject.put("objCropDetails", userRequestjsonObject);
*//*

            System.out.println("postObj"+userRequestjsonObject.toString());

            Login_post.login_posting(getActivity(), Urls.GetLookingForList,userRequestjsonObject,new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("cropsresult"+result);
                    JSONArray cropsListArray=null;
                    try {
                        cropsListArray=result.getJSONArray("LookingForList");
                        System.out.println("e     e e ddd"+cropsListArray.length());
                        for (int i=0;i<cropsListArray.length();i++){
                            JSONObject jsonObject1=cropsListArray.getJSONObject(i);
                            JSONObject jsonObject2=jsonObject1.getJSONObject("Address");


                            String model=jsonObject1.getString("Model");
                            String purchaseTimeline=jsonObject1.getString("PurchaseTimeline");
                            String image=jsonObject1.getString("ModelImage");
                            String id=jsonObject1.getString("CreatedBy");
                            String name=jsonObject2.getString("Name");
                            String city=jsonObject2.getString("City");
                            String state=jsonObject2.getString("State");
                            String hp_range=jsonObject1.getString("HorsePowerRange");

                            if (city.equals("")){
                                 location="Bangalore"+", "+state;
                            }else{
                                 location=city+", "+state;
                            }



                            System.out.println("madelslistt"+newOrderBeansList.size());

                            FarmsImageBean crops = new FarmsImageBean(image,"Tractor Price",model,hp_range,purchaseTimeline,name,location,id);
                            newOrderBeansList.add(crops);



                          */
/*  if(!latts.equals("") | !langgs.equals("")) {

                                CropListBean crops = new CropListBean(cropName, crop_variety, location, crop_grade,
                                        crop_quantity, crop_uom, crop_price, id, farmerId,
                                        UserName,latts,langgs,CropImg,category);
                                newOrderBeansList.add(crops);
                            }*//*

                        }
                        farmadapter=new FarmsImageAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(farmadapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }


    public  void sorting(String filter_text){

        searchresultAraaylist.clear();
        for (FarmsImageBean composeMsgOrderSecondBean: newOrderBeansList) {
            System.out.println("llllllllllllllll"+composeMsgOrderSecondBean.getProd_price());
            final String text = composeMsgOrderSecondBean.getProd_price().toLowerCase();
            final String text1 = composeMsgOrderSecondBean.getModelname().toLowerCase();
            final String text2 = composeMsgOrderSecondBean.getFarmer_name().toLowerCase();
            final String text3 = composeMsgOrderSecondBean.getLocation().toLowerCase();

            if (text.contains(filter_text)||text1.contains(filter_text)||text2.contains(filter_text)||text3.contains(filter_text)){

                searchresultAraaylist.add(composeMsgOrderSecondBean);
            }
        }

        farmadapter=new FarmsImageAdapter(getActivity(),searchresultAraaylist);
        recyclerView.setAdapter(farmadapter);

    }

}
