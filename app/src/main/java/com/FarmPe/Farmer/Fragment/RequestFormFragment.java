package com.FarmPe.Farmer.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Adapter.AddFirstAdapter;
import com.FarmPe.Farmer.Adapter.AddHpAdapter;
import com.FarmPe.Farmer.Adapter.AddModelAdapter;
import com.FarmPe.Farmer.Bean.Add_New_Address_Bean;
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

public class RequestFormFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    ArrayList<Add_New_Address_Bean> new_address_beanArrayList = new ArrayList<>();

    public static RecyclerView recyclerView;
    public static AddHpAdapter farmadapter;
    TextView toolbar_title,request,address_text;
    Fragment selectedFragment;
    RadioGroup radioGroup,radioGroup_finance;
    RadioButton radioButton,finance_yes,finance_no,radioButton1;
    LinearLayout back_feed,address_layout;
   // CheckBox check_box;
    SessionManager sessionManager;
    View view;
    String addId;
    String time_period;
    boolean finance;
    String finance_status;
    public static String back;
    LinearLayout linearLayout;
    public static int selectedId,selectedId_time_recent;
    int finance_selected,time_selected;
    Add_New_Address_Bean add_new_address_bean;
    TextView whenPurchase, lookingForFinance;
    JSONArray get_address_array;

    public static RequestFormFragment newInstance() {
        RequestFormFragment fragment = new RequestFormFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  view = inflater.inflate(R.layout.request_form, container, false);


        //Inflate the layout for this fragment or reuse the existing one
        final View view = getView() != null ? getView() :
                inflater.inflate(R.layout.request_form, container, false);






        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
      //  check_box=view.findViewById(R.id.check_box);
        whenPurchase = view.findViewById(R.id.whenPurchase);

        whenPurchase.setText("When are you planning to purchase "+ AddFirstFragment.tracter_title+"?");
        lookingForFinance = view.findViewById(R.id.lookingForFinance);
        lookingForFinance.setText("Are you looking for finance / loan for "+AddFirstFragment.tracter_title+" purchase?");

        address_layout=view.findViewById(R.id.address_layout);
        radioGroup=view.findViewById(R.id.radio_group_time);
        radioGroup_finance=view.findViewById(R.id.radioGroup_finance);
        request=view.findViewById(R.id.request);
        address_text=view.findViewById(R.id.address_text);
        linearLayout=view.findViewById(R.id.linearLayout);
        toolbar_title.setText("Request for Quotation");
        sessionManager=new SessionManager(getActivity());

        Bundle bundle=getArguments();
        if (bundle==null){
            gettingAddress();

        }else{
            finance_selected=bundle.getInt("selected_id2");
            time_selected=bundle.getInt("selected_id_time1");
            //  gettingAddress();

            // System.out.println("tiiiiimmmee"+time_selected);
            addId=bundle.getString("add_id");
            String stret_name=bundle.getString("streetname");
            address_text.setText(stret_name);
            radioGroup.check(bundle.getInt("selected_id_time1"));
            radioGroup_finance.check(finance_selected);

        }

        //check_box.setText("I agree that by clicking 'Request for "+AddFirstFragment.tracter_title+"' button, I am explicitly soliciting a call from FarmPe Farmer App users on my 'Mobile' in order to assist me with my "+AddFirstFragment.tracter_title+" purchase.");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("fourth", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

//        view.setFocusableInTouchMode(true);
////        view.requestFocus();
////        view.setOnKeyListener(new View.OnKeyListener() {
////
////
////            @Override
////            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
////                    FragmentManager fm = getActivity().getSupportFragmentManager();
////                    fm.popBackStack("fourth", FragmentManager.POP_BACK_STACK_INCLUSIVE);
////
////
////                    return true;
////                }
////                return false;
////            }
////        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("fourth", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });


        address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hrrrjjkj");
                Bundle bundle1=new Bundle();
                bundle1.putString("navigation_from","request");
                selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("request");
                selectedFragment.setArguments(bundle1);
                transaction.commit();
            }
        });


                request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = address_text.getText().toString();
                        if ((radioGroup.getCheckedRadioButtonId() == -1) && address_text.getText().toString().equals("") && radioGroup_finance.getCheckedRadioButtonId() == -1) {
                         //1aq   Toast.makeText(getActivity(), "Select All Fields", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Select All Fields", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();

                        } else if ((radioGroup.getCheckedRadioButtonId() == -1)) {

                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Select Timeline", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();

                        } else if (str.equalsIgnoreCase("")) {
                            //Toast.makeText(getActivity(), "Select Your Address", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Select Your Address", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();
                        }

                        else if((radioGroup_finance.getCheckedRadioButtonId()==-1)) {

                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,"Select finance", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tv.setTextColor(Color.WHITE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            } else {
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            snackbar.show();

                        }  else {

                            RequestForm();
                        }

                    }
                });

              /*  selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/


        radioGroup_finance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
                selectedId = radioGroup_finance.getCheckedRadioButtonId();
                radioButton = (RadioButton)view.findViewById(selectedId);
                System.out.println("checkinggg"+radioButton.getText().toString());
                finance_status=radioButton.getTag().toString();



            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId_time_recent = radioGroup.getCheckedRadioButtonId();
                System.out.println("radio_group_1"+selectedId_time_recent);
                radioButton1 = (RadioButton)view.findViewById(selectedId_time_recent);
                time_period=String.valueOf(radioButton1.getText());
                System.out.println("valueee"+time_period);
            }
        });

       /* final String value =
                ((RadioButton)view.findViewById(radioGroup.getCheckedRadioButtonId()))
                        .getText().toString();*/


       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               // Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
                System.out.println("valueee"+value);
            }
        });*/

        return view;
    }
    private void RequestForm() {




        System.out.println("purchase"+time_period);
        System.out.println("finance"+time_period);

        try {

            JSONObject userRequestjsonObject = new JSONObject();

            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            userRequestjsonObject.put("TractorId", AddModelAdapter.tractor_id);
            userRequestjsonObject.put("PurchaseTimeline", time_period);
            userRequestjsonObject.put("LookingForFinance", finance_status);
            userRequestjsonObject.put("AddressId", addId);
            userRequestjsonObject.put("ModelId", AddModelAdapter.tractor_id);
            userRequestjsonObject.put("IsAgreed", "True");
            userRequestjsonObject.put("LookingForDetailsId", AddFirstAdapter.looinkgId);


          /*  JSONObject postjsonObject = new JSONObject();
            postjsonObject.put("objCropDetails", userRequestjsonObject);
*/

            System.out.println("postObj"+userRequestjsonObject.toString());


            Login_post.login_posting(getActivity(), Urls.AddRequestForQuotation,userRequestjsonObject,new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("cropsresult"+result);

                    newOrderBeansList.clear();

                    try {
                        String status=result.getString("Status");
                        String message=result.getString("Message");


                       // Toast.makeText(getActivity(), "Your Request Added Successfully", Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar
                                .make(linearLayout,"Your Request Added Successfully", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }

                        snackbar.show();

                        //back = "add_back";

                       // HomeMenuFragment.onBack_status = "looking_frg";

                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void gettingAddress(){

        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            // jsonObject.put("PickUpFrom",pickUPFrom);
            System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.Get_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggggggggggaaaaaaa"+result);
                    try{
                        new_address_beanArrayList.clear();


                        get_address_array = result.getJSONArray("UserAddressDetails");
                        for(int i=0;i<get_address_array.length();i++){
                            JSONObject jsonObject1 = get_address_array.getJSONObject(i);


                            // add_new_address_bean = new Add_New_Address_Bean(jsonObject1.getString("Name"),jsonObject1.getString("StreeAddress"),jsonObject1.getString("StreeAddress1"),jsonObject1.getString("LandMark"),jsonObject1.getString("City"),jsonObject1.getString("Pincode"),jsonObject1.getString("MobileNo"),
                            //jsonObject1.getString("PickUpFrom"),jsonObject1.getString("State"),jsonObject1.getString("District"),jsonObject1.getString("Taluk"),jsonObject1.getString("Hoblie"),jsonObject1.getString("Village"),jsonObject1.getString("Id"),jsonObject1.getBoolean("IsDefaultAddress"));
                            // new_address_beanArrayList.add(add_new_address_bean);

                            if (jsonObject1.getBoolean("IsDefaultAddress")){
                                addId=jsonObject1.getString("Id");
                                address_text.setText(jsonObject1.getString("LandMark")+","+jsonObject1.getString("State")+","+jsonObject1.getString("City")+","+jsonObject1.getString("Pincode"));

                            }

                        }

                        // item_list = String.valueOf(new_address_beanArrayList.size());
                        //  address_list.setText(item_list+" " + ad_list );


                        // mAdapter.notifyDataSetChanged();




                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
