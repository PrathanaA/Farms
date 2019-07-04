package com.FarmPe.Farmer.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Adapter.DistrictAdapter;
import com.FarmPe.Farmer.Adapter.HoblisAdapter;
import com.FarmPe.Farmer.Adapter.Sell_Location_Adapter;
import com.FarmPe.Farmer.Adapter.StateApdater;
import com.FarmPe.Farmer.Adapter.TalukAdapter;
import com.FarmPe.Farmer.Adapter.VillageAdapter;
import com.FarmPe.Farmer.Adapter.You_Address_Adapter;
import com.FarmPe.Farmer.Bean.StateBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Update_Address_Fragment extends Fragment {

    RecyclerView recyclerView;
    Sell_Location_Adapter mAdapter;

    static List<StateBean> stateBeanList = new ArrayList<>();
    static List<StateBean> districtBeanList = new ArrayList<>();
    static List<StateBean> talukBeanList = new ArrayList<>();
    static List<StateBean> hobliBeanList = new ArrayList<>();
    static List<StateBean> villageBeanList = new ArrayList<>();
    StateApdater stateApdater;
    DistrictAdapter districtAdapter;
    TalukAdapter talukAdapter;
    HoblisAdapter hoblisAdapter;
    VillageAdapter villageAdapter;
    LinearLayout linear_name, linear_mobile,linear_pincode,linear_city,linear_street,linear_house,linear_landmark;
    LinearLayout back_feed;

    JSONArray jsonArray,state_array,tal_array,hobli_array,village_array;
     StateBean stateBean;

     public static TextView update_address;
    Fragment selectedFragment = null;


    public static EditText name,mobile,pincode_no,house_numb,street_name,landmrk,city,state,taluk,hobli,district,village,select_address;
    String status,message;
    String Id;
    SessionManager sessionManager;
    public static Dialog grade_dialog;
    int selected_id,selected_id_time;



    public static Update_Address_Fragment newInstance() {
        Update_Address_Fragment fragment = new Update_Address_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_address, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        select_address = view.findViewById(R.id.up_select_address);
        name = view.findViewById(R.id.up_full_name);
        mobile = view.findViewById(R.id.up_mobile_no);
        back_feed = view.findViewById(R.id.back_feed);

        pincode_no = view.findViewById(R.id.up_pincode);
        house_numb = view.findViewById(R.id.up_house_no);
        street_name = view.findViewById(R.id.up_street);
        landmrk = view.findViewById(R.id.up_landmark_1);
        update_address = view.findViewById(R.id.update_address);

        state = view.findViewById(R.id.up_state);
        city = view.findViewById(R.id.up_city_1);
        district = view.findViewById(R.id.up_district_1);
        taluk = view.findViewById(R.id.up_taluk_1);
        hobli = view.findViewById(R.id.up_hobli_1);
        village = view.findViewById(R.id.up_village_1);


    /*   selected_id=RequestFormFragment.selectedId;
        selected_id_time=RequestFormFragment.selectedId_time_recent;*/

        //System.out.println("selecteddddd_iddd"+selected_id_time);

            name.setText(getArguments().getString("Addr_name"));
            mobile.setText(getArguments().getString("Addr_mobile"));
            pincode_no.setText(getArguments().getString("Addr_pincode"));
            house_numb.setText(getArguments().getString("Addr_Houseno"));
            street_name.setText(getArguments().getString("Addr_Street"));
            landmrk.setText(getArguments().getString("Addr_landmark"));
            city.setText(getArguments().getString("Addr_city"));


            state.setText(getArguments().getString("Addr_state"));
            district.setText(getArguments().getString("Addr_district"));
            taluk.setText(getArguments().getString("Addr_taluk"));
            hobli.setText(getArguments().getString("Addr_hobli"));
            village.setText(getArguments().getString("Addr_village"));
            select_address.setText(getArguments().getString("Addr_pickup_from"));

//
//        InputFilter filter = new InputFilter() {
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                String filtered = "";
//                for (int i = start; i < end; i++) {
//                    char character = source.charAt(i);
//                    if (!Character.isWhitespace(character)) {
//                        filtered += character;
//                    }
//                }
//                return filtered;
//            }
//
//        };
//
//        name.setFilters(new InputFilter[] { filter,new InputFilter.LengthFilter(30) });
       // confimp.setFilters(new InputFilter[] { filter ,new InputFilter.LengthFilter(12)});


        linear_name = view.findViewById(R.id.linea_name1);
        linear_mobile = view.findViewById(R.id.linea_mobile1);
       //linear_pincode= view.findViewById(R.id.linea_pincode1);
       // linear_house= view.findViewById(R.id.linear_houseno);
        linear_street= view.findViewById(R.id.linea_street);
       // linear_landmark= view.findViewById(R.id.linea_landmark);
        //linear_city= view.findViewById(R.id.linea_city);


        sessionManager = new SessionManager(getActivity());



       /* back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (You_Address_Fragment.address==null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("request", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("addresss", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });*/

        view.setFocusableInTouchMode(true);
        view.requestFocus(View.FOCUS_UP);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    selectedFragment = You_Address_Fragment.newInstance();
                    FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                   /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("request", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                }
                return false;
            }
        });


        /*name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                linear_layout_selection(linear_name,linear_mobile,linear_pincode,linear_house,linear_street,linear_landmark,linear_city);
                return false;
            }
        });


        mobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                linear_layout_selection(linear_mobile,linear_name,linear_pincode,linear_house,linear_landmark,linear_street,linear_city);

                return false;

            }
        });

        pincode_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(linear_pincode,linear_name,linear_mobile,linear_house,linear_landmark,linear_street,linear_city);
                return false;

            }
        });


        house_numb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(linear_house,linear_name,linear_mobile,linear_pincode,linear_street,linear_landmark,linear_city);
                return false;
            }
        });


        street_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(linear_street,linear_name,linear_mobile,linear_house,linear_landmark,linear_pincode,linear_city);
                return false;
            }
        });



        landmrk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(linear_landmark,linear_name,linear_mobile,linear_house,linear_street,linear_pincode,linear_city);
                return false;
            }
        });



        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(linear_city,linear_landmark,linear_name,linear_mobile,linear_house,linear_street,linear_pincode);
                return false;
            }
        });*/



        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.select_address_popup);

                ImageView image = (ImageView) dialog.findViewById(R.id.close_popup);
                final TextView home =(TextView)dialog.findViewById(R.id.home_1);
               //final TextView barn = (TextView)dialog.findViewById(R.id.barn) ;
                final TextView ware_house = (TextView)dialog.findViewById(R.id.ware_hus) ;
                final TextView farm = (TextView)dialog.findViewById(R.id.farm) ;
                final TextView others = (TextView)dialog.findViewById(R.id.othrs) ;

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(home.getText().toString());
                        dialog.dismiss();
                    }
                });


               /* barn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(barn.getText().toString());
                        dialog.dismiss();

                    }
                });*/

                ware_house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(ware_house.getText().toString());
                        dialog.dismiss();

                    }
                });

                farm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(farm.getText().toString());
                        dialog.dismiss();

                    }
                });

                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_address.setText(others.getText().toString());
                        dialog.dismiss();

                    }
                });


                dialog.show();

            }
        });


        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);

                TextView popup_heading = (TextView)grade_dialog.findViewById(R.id.popup_heading);
                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                RecyclerView recyclerView = grade_dialog.findViewById(R.id.recycler_view1);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                stateApdater= new StateApdater(stateBeanList,getActivity());
                recyclerView.setAdapter(stateApdater);

                popup_heading.setText("State");

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });


                try{

                  JSONObject jsonObject = new JSONObject();

                    Crop_Post.crop_posting(getActivity(), Urls.State, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("11111ssss" + result);


                            try{
                                stateBeanList.clear();
                                state_array = result.getJSONArray("StateList");
                                for(int i =0;i<state_array.length();i++){
                                    JSONObject jsonObject1 = state_array.getJSONObject(i);

                                    stateBean = new StateBean(jsonObject1.getString("State").trim(),jsonObject1.getString("StateId"));
                                    stateBeanList.add(stateBean);
                                }

                                sorting(stateBeanList);

                                stateApdater.notifyDataSetChanged();
                                grade_dialog.show();




                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });




                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });



        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);

                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                TextView popup_heading = (TextView)grade_dialog.findViewById(R.id.popup_heading);
                RecyclerView recyclerView = grade_dialog.findViewById(R.id.recycler_view1);

                popup_heading.setText("District");

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                districtAdapter= new DistrictAdapter( districtBeanList,getActivity());
                recyclerView.setAdapter(districtAdapter);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });




             try{

                    JSONObject jsonObject = new JSONObject();
                    JSONObject post_jsonobject = new JSONObject();
                    jsonObject.put("StateId",stateApdater.stateid);
                    post_jsonobject.put("Districtobj",jsonObject);

                    Crop_Post.crop_posting(getActivity(), Urls.Districts, post_jsonobject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("dddddddddddd11111" + result);
                            try{
                                districtBeanList.clear();
                                jsonArray = result.getJSONArray("DistrictList");
                                for(int i =0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    stateBean = new StateBean(jsonObject1.getString("District"),jsonObject1.getString("DistrictId"));
                                    districtBeanList.add(stateBean);
                                }

                                sorting(districtBeanList);


                                districtAdapter.notifyDataSetChanged();
                                grade_dialog.show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        taluk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);

                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                TextView popup_heading = (TextView)grade_dialog.findViewById(R.id.popup_heading);
                RecyclerView recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                popup_heading.setText("Taluk");

                talukAdapter = new TalukAdapter(talukBeanList,getActivity());
                recyclerView.setAdapter(talukAdapter);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });



                try{

                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonpost = new JSONObject();
                    jsonObject.put("DistrictId",DistrictAdapter.districtid);
                    jsonpost.put("Talukobj",jsonObject);

                    Crop_Post.crop_posting(getActivity(), Urls.Taluks, jsonpost, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("aaaaaaaaaaaaafffffffffffff"+result);
                            try{
                                talukBeanList.clear();
                                tal_array = result.getJSONArray("TalukList") ;
                                for(int i=0;i<tal_array.length();i++){
                                    JSONObject jsonObject1 = tal_array.getJSONObject(i);
                                    stateBean = new StateBean(jsonObject1.getString("Taluk"),jsonObject1.getString("TalukId"));
                                    talukBeanList.add(stateBean);

                                }
                                sorting(talukBeanList);

                                talukAdapter.notifyDataSetChanged();
                                grade_dialog.show();

                           }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


        hobli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);

                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                RecyclerView recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
                TextView popup_heading = (TextView)grade_dialog.findViewById(R.id.popup_heading);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                 popup_heading.setText("Hobli");

                hoblisAdapter = new HoblisAdapter( hobliBeanList,getActivity());
                recyclerView.setAdapter(hoblisAdapter);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });

                try{

                    final JSONObject jsonObject = new JSONObject();

                    JSONObject json_post = new JSONObject();
                    jsonObject.put("TalukId",TalukAdapter.talukid);
                    json_post.put("Hobliobj",jsonObject);

                    Crop_Post.crop_posting(getActivity(), Urls.Hoblis, json_post, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("hhhhhhhssssskljhgf" + result);

                            try{
                                hobliBeanList.clear();
                                hobli_array = result.getJSONArray("HobliList");
                                for(int i = 0;i<hobli_array.length();i++){
                                    JSONObject jsonObject3 = hobli_array.getJSONObject(i);
                                    stateBean = new StateBean(jsonObject3.getString("Hobli"),jsonObject3.getString("HobliId"));
                                    hobliBeanList.add(stateBean);

                                }
                                sorting(hobliBeanList);

                                hoblisAdapter.notifyDataSetChanged();
                                grade_dialog.show();



                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });



             village.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


                  grade_dialog= new Dialog(getActivity());
                  grade_dialog.setContentView(R.layout.select_variety_popup);

                  ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                  TextView popup_heading = (TextView)grade_dialog.findViewById(R.id.popup_heading);
                  RecyclerView recyclerView = grade_dialog.findViewById(R.id.recycler_view1);

                  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                  recyclerView.setLayoutManager(mLayoutManager);
                  final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                  layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                  recyclerView.setLayoutManager(layoutManager);
                  recyclerView.setItemAnimator(new DefaultItemAnimator());
                  villageAdapter = new VillageAdapter(villageBeanList,getActivity());
                  recyclerView.setAdapter(villageAdapter);

                  popup_heading.setText("Village");


                  image.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          grade_dialog.dismiss();
                      }
                  });


                  try{
                      JSONObject jsonObject = new JSONObject();
                      JSONObject post_Object = new JSONObject();
                      jsonObject.put("HobliId",hoblisAdapter.hobliid);
                      post_Object.put("Villageobj",jsonObject);

                      Crop_Post.crop_posting(getActivity(), Urls.Villages, post_Object, new VoleyJsonObjectCallback() {
                          @Override
                          public void onSuccessResponse(JSONObject result) {
                              System.out.println("111vvv" + result);

                              try{
                                  villageBeanList.clear();
                                  village_array = result.getJSONArray("VillageList");
                                  for(int i = 0;i<village_array.length();i++) {
                                      JSONObject jsonObject1 = village_array.getJSONObject(i);
                                      stateBean = new StateBean(jsonObject1.getString("Village"), jsonObject1.getString("VillagId"));
                                      villageBeanList.add(stateBean);
                                  }

                                  sorting(villageBeanList);

                                  villageAdapter.notifyDataSetChanged();
                                  grade_dialog.show();


                              }catch (Exception e){
                                  e.printStackTrace();
                              }

                          }
                      });


                  }catch (Exception e){
                      e.printStackTrace();
                  }

         }
         });



        update_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(select_address.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Select Address Type", Toast.LENGTH_SHORT).show();


                     }else if(name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();



                    }else if(mobile.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();


                   }else if(mobile.length()<10){
                    Toast.makeText(getActivity(), "Incorrect Mobile Number", Toast.LENGTH_SHORT).show();


                   }else if(house_numb.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Enter House No/Floor/building", Toast.LENGTH_SHORT).show();



                  }else if(street_name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Street Address", Toast.LENGTH_SHORT).show();



                   }else if(landmrk.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Landmark", Toast.LENGTH_SHORT).show();


                   }else if(city.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter City", Toast.LENGTH_SHORT).show();



                   }else if(pincode_no.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Pincode", Toast.LENGTH_SHORT).show();



                   }else if(pincode_no.length()<6){
                    Toast.makeText(getActivity(), "Enter a valid Pincode", Toast.LENGTH_SHORT).show();


                   }else if(state.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select State", Toast.LENGTH_SHORT).show();



                }else if(district.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select District", Toast.LENGTH_SHORT).show();



                }else if(taluk.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select Taluk", Toast.LENGTH_SHORT).show();



                }else if(hobli.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select Hobli", Toast.LENGTH_SHORT).show();



                }else if(village.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select Village", Toast.LENGTH_SHORT).show();


                }else {

                   ComposeCategory();

                }


            }
        });

        return view;

    }

    private void ComposeCategory() {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DistrictId",DistrictAdapter.districtid);
            jsonObject.put("HobliId", HoblisAdapter.hobliid);
            jsonObject.put("LandMark",landmrk.getText().toString());
            jsonObject.put("City",city.getText().toString());
            jsonObject.put("MobileNo",mobile.getText().toString());
            jsonObject.put("Name",name.getText().toString());
            jsonObject.put("PickUpFrom",select_address.getText().toString());
            jsonObject.put("Pincode",pincode_no.getText().toString());
            jsonObject.put("StateId",StateApdater.stateid);
            jsonObject.put("TalukId",TalukAdapter.talukid);
            jsonObject.put("VillageId", VillageAdapter.villageid);
            jsonObject.put("StreeAddress",house_numb.getText().toString());
            jsonObject.put("StreeAddress1",street_name.getText().toString());
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("Id", You_Address_Adapter.add_id);
            System.out.println("nnnnnnnnnnnnnnnaaaaaaaaa"+jsonObject);



            Crop_Post.crop_posting(getActivity(), Urls.Edit_Address, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("Edit_Addresssssssssssss" +result);

                    try{

                        status= result.getString("Status");
                        message = result.getString("Message");


                        if(status.equals("1")){
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                            selectedFragment = You_Address_Fragment.newInstance();
                            FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();



                        }else{

                            Toast.makeText(getActivity(),"Your Address not Updated ",Toast.LENGTH_SHORT).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void linear_layout_selection(LinearLayout selectdl1,LinearLayout l2,LinearLayout l3,LinearLayout l4, LinearLayout l5 ,LinearLayout l6 , LinearLayout l7){
        selectdl1.setBackgroundResource(R.drawable.border);
        l2.setBackgroundResource(R.drawable.border_1_layout);
        l3.setBackgroundResource(R.drawable.border_1_layout);
        l4.setBackgroundResource(R.drawable.border_1_layout);
        l5.setBackgroundResource(R.drawable.border_1_layout);
        l6.setBackgroundResource(R.drawable.border_1_layout);
        l7.setBackgroundResource(R.drawable.border_1_layout);

    }

    public void sorting(List<StateBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question
                return ((StateBean)state_name1).getName()
                        .compareTo(((StateBean)state_name2).getName());
            }
        });
    }

}

