package com.FarmPe.Farmer.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.FarmPe.Farmer.Adapter.AddFirstListYourFramsAdapter;
import com.FarmPe.Farmer.Adapter.DistrictAdapter1;
import com.FarmPe.Farmer.Adapter.HoblisAdapter1;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter2;
import com.FarmPe.Farmer.Adapter.StateApdater1;
import com.FarmPe.Farmer.Adapter.TalukAdapter;
import com.FarmPe.Farmer.Adapter.TalukAdapter1;
import com.FarmPe.Farmer.Adapter.VillageAdapter;
import com.FarmPe.Farmer.Adapter.VillageAdapter1;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.Bean.StateBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListYourFarmsThird extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstListYourFramsAdapter farmadapter;
    LinearLayout back_feed,main_layout,search_bar;
    Fragment selectedFragment;
    String address_map;

    TextView toolbar_title,continue_3;
    public static TextView state,district,taluk,block,village,skip,current_loc;
    public static EditText street_add,pincode;
    public static Dialog grade_dialog;
    public static String search_status="status";
    private List<StateBean> searchresultAraaylist = new ArrayList<>();

    static List<StateBean> stateBeanList = new ArrayList<>();
    static List<StateBean> districtBeanList = new ArrayList<>();
    static List<StateBean> talukBeanList = new ArrayList<>();
    static List<StateBean> hobliBeanList = new ArrayList<>();
    static List<StateBean> villageBeanList = new ArrayList<>();
    ImageView left_arrw;
    StateApdater1 stateApdater;
    DistrictAdapter1 districtAdapter;
    TalukAdapter1 talukAdapter;
    HoblisAdapter1 hoblisAdapter;
    VillageAdapter1 villageAdapter;
    JSONArray jsonArray,state_array,tal_array,hobli_array,village_array;
    StateBean stateBean;
    EditText current_adds;

    public static String list_fams,list_farms_district,list_farms_taluk,list_farms_block,list_farms_village,street_string,pincode_string;


    public static ListYourFarmsThird newInstance() {
        ListYourFarmsThird fragment = new ListYourFarmsThird();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_farm_third_layout_item, container, false);
        //recyclerView=view.findViewById(R.id.recycler_what_looking);
        back_feed=view.findViewById(R.id.back_feed);
        continue_3=view.findViewById(R.id.continue_3);
        state=view.findViewById(R.id.state);
        district=view.findViewById(R.id.district);
        taluk=view.findViewById(R.id.taluk);
        block=view.findViewById(R.id.block);
      //  recyclerView=view.findViewById(R.id.recycler_view1);
       // village=view.findViewById(R.id.village);
        search_bar=view.findViewById(R.id.search_bar);
        current_adds=view.findViewById(R.id.current_adds);
        street_add=view.findViewById(R.id.street_add);
        left_arrw=view.findViewById(R.id.left_arrw);
        pincode=view.findViewById(R.id.pincode);
        main_layout=view.findViewById(R.id.main_layout);
        skip=view.findViewById(R.id.skip);
        current_loc=view.findViewById(R.id.current_loc);

        street_add.setFilters(new InputFilter[] {EMOJI_FILTER,new InputFilter.LengthFilter(30) });

        address_map = getArguments().getString("status");

        if (address_map.equals("default")){

            current_loc.setVisibility(View.VISIBLE);


        }else{
            current_adds.setText(address_map);
            current_adds.setVisibility(View.VISIBLE);

        }


        current_adds.setText(address_map);



        current_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = MapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("farm_third");
                transaction.commit();
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("RB_S", List_Farm_Adapter2.position_1);
                    selectedFragment = ListYourFarmsSecond.newInstance();
                    selectedFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("list_farm1");
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("RB_S",-1);
                selectedFragment = ListYourFarmsSecond.newInstance();
                selectedFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("list_farm1");
                transaction.commit();

            }
        });


        current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = MapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("farm_third");
                transaction.commit();

            }
        });



         skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                street_string = street_add.getText().toString();
                pincode_string = pincode.getText().toString();
                selectedFragment = ListYourFarmsFour.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("list_four");
                transaction.commit();
            }
        });




        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_status="state";
                list_fams="state";
                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);
              //`  EditText popup_heading = (EditText) grade_dialog.findViewById(R.id.popup_heading);
                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                LinearLayout close_layout = (LinearLayout) grade_dialog.findViewById(R.id.close_layout);
                EditText editText = (EditText) grade_dialog.findViewById(R.id.search_1);
                recyclerView = grade_dialog.findViewById(R.id.recycler_view1);

//                search_status="state";
//                search.setQueryHint("");



/*
                editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        // search.clearFocus();
                        System.out.println("lknkknknknknknknknnk");
             *//*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*//*
                        return false;

                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // back_feed.setVisibility(View.GONE);
                        //title.setVisibility(View.GONE);
                        System.out.println("lknkknknknknknknknnk"+newText);
                        sorting1(newText);


                        return false;
                    }
                });*/

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sorting1(s.toString());
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        // TODO Auto-generated method stub
                    }
                });


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                stateApdater = new StateApdater1(stateBeanList,getActivity());
                recyclerView.setAdapter(stateApdater);

            //    popup_heading.setHint("State");

                stateList();

                close_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });
            }
        });

        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_farms_district="district";

                search_status="district";

                grade_dialog = new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);

                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
             //   EditText popup_heading = (EditText)grade_dialog.findViewById(R.id.popup_heading);
                LinearLayout close_layout = (LinearLayout) grade_dialog.findViewById(R.id.close_layout);
                recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
                EditText editText = (EditText) grade_dialog.findViewById(R.id.search_1);


//                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//
//                        // search.clearFocus();
//                        System.out.println("lknkknknknknknknknnk");
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                        return false;
//
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        // back_feed.setVisibility(View.GONE);
//                        //title.setVisibility(View.GONE);
//                        System.out.println("lknkknknknknknknknnk"+newText);
//                        sorting1(newText);
//
//
//                        return false;
//                    }
//                });

             //   popup_heading.setHint("District");

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                districtAdapter= new DistrictAdapter1( districtBeanList,getActivity());
                recyclerView.setAdapter(districtAdapter);

                district();

                close_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });
            }
        });


        taluk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                list_farms_taluk="taluk";
                search_status="taluk";
                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);
                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
           //     EditText popup_heading = (EditText)grade_dialog.findViewById(R.id.popup_heading);
                LinearLayout close_layout = (LinearLayout) grade_dialog.findViewById(R.id.close_layout);
                recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
                EditText editText = (EditText)grade_dialog.findViewById(R.id.search_1);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//                editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//
//                        // search.clearFocus();
//                        System.out.println("lknkknknknknknknknnk");
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                        return false;
//
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        // back_feed.setVisibility(View.GONE);
//                        //title.setVisibility(View.GONE);
//                        System.out.println("lknkknknknknknknknnk"+newText);
//                        sorting1(newText);
//
//
//                        return false;
//                    }
//                });
//
//                popup_heading.setHint("Taluk");

                talukAdapter = new TalukAdapter1( talukBeanList,getActivity());
                recyclerView.setAdapter(talukAdapter);

                taluk_list();

                close_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });

            }
        });


        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_farms_block="block";
                search_status="block";
                grade_dialog= new Dialog(getActivity());
                grade_dialog.setContentView(R.layout.select_variety_popup);
                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
                recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
                EditText editText = (EditText) grade_dialog.findViewById(R.id.search_1);
                LinearLayout close_layout = (LinearLayout) grade_dialog.findViewById(R.id.close_layout);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                hoblisAdapter = new HoblisAdapter1( hobliBeanList,getActivity());
                recyclerView.setAdapter(hoblisAdapter);

                block_list();

                close_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grade_dialog.dismiss();
                    }
                });
            }
        });


//        village.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list_farms_village="village";
//                search_status="village";
//
//
//                grade_dialog= new Dialog(getActivity());
//                grade_dialog.setContentView(R.layout.select_variety_popup);
//                ImageView image = (ImageView) grade_dialog.findViewById(R.id.close_popup);
//                EditText popup_heading = (EditText)grade_dialog.findViewById(R.id.popup_heading);
//                recyclerView = grade_dialog.findViewById(R.id.recycler_view1);
//                LinearLayout close_layout = (LinearLayout) grade_dialog.findViewById(R.id.close_layout);
//                SearchView search = (SearchView) grade_dialog.findViewById(R.id.search_1);
//
//                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//
//                        // search.clearFocus();
//                        System.out.println("lknkknknknknknknknnk");
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                        return false;
//
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        // back_feed.setVisibility(View.GONE);
//                        //title.setVisibility(View.GONE);
//                        System.out.println("lknkknknknknknknknnk"+newText);
//                        sorting1(newText);
//
//
//                        return false;
//                    }
//                });
//
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                recyclerView.setLayoutManager(mLayoutManager);
//                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                villageAdapter = new VillageAdapter1(villageBeanList,getActivity());
//                recyclerView.setAdapter(villageAdapter);
//                popup_heading.setHint("Village");
//                village_list();
//                close_layout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        grade_dialog.dismiss();
//                    }
//                });
//            }
//
//        });


        continue_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((current_adds.getVisibility() == View.GONE) || (current_adds.getVisibility() == View.VISIBLE && current_adds.getText().toString().equals(""))) {

                  System.out.println("kjyhgvkhygf" + "onClick: nbvMHfgdfmgluhg");
                     if (street_add.getText().toString().equals("") && state.getText().toString().equals("") && district.getText().toString().equals("") && taluk.getText().toString().equals("") && block.getText().toString().equals("") && village.getText().toString().equals("") && village.getText().toString().equals("") && street_add.getText().toString().equals("") && pincode.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Please enter all the details", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();


//                    }  else if (street_add.getText().toString().equals("")) {
//                        Snackbar snackbar = Snackbar
//                                .make(main_layout, "Enter Street Address", Snackbar.LENGTH_LONG);
//                        View snackbarView = snackbar.getView();
//                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                        tv.setTextColor(Color.WHITE);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        } else {
//                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                        }
//                        snackbar.show();


                    }else if (state.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Select State", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();


                    }else if (district.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Select District", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();

                    }
                    else if (taluk.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Select Taluk", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();


                    }else if (block.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Select Block", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();

                    }else if (village.getText().toString().equals("")) {
                         Snackbar snackbar = Snackbar
                                 .make(main_layout, "Select Village", Snackbar.LENGTH_LONG);
                         View snackbarView = snackbar.getView();
                         TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                         tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                         tv.setTextColor(Color.WHITE);
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                             tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                         } else {
                             tv.setGravity(Gravity.CENTER_HORIZONTAL);
                         }
                         snackbar.show();

//                    }else if (pincode.getText().toString().equals("")) {
//                        Snackbar snackbar = Snackbar
//                                .make(main_layout, "Enter Pincode", Snackbar.LENGTH_LONG);
//                        View snackbarView = snackbar.getView();
//                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
//                        tv.setTextColor(Color.WHITE);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        } else {
//                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                        }
//                        snackbar.show();
//                    }
                     }
                    else if (pincode.getText().toString().length() < 6) {
                        // Toast.makeText(getActivity(), "Enter a valid Pincode", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar
                                .make(main_layout, "Enter 6 Digit Pincode", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();
                    } else {
                        street_string = street_add.getText().toString();
                        pincode_string = pincode.getText().toString();
                        selectedFragment = ListYourFarmsFour.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.addToBackStack("list_four");
                        // transaction.addToBackStack("looking");
                        transaction.commit();
                    }
                } else {
                    selectedFragment = ListYourFarmsFour.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("list_four");
                    transaction.commit();
                }
            }
        });

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
      /*  char c = source.charAt(index);
        if (isCharAllowed(c))
            sb.append(c);
        else
            keepOriginal = false;*/
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





    private void village_list() {
        try{
            JSONObject jsonObject = new JSONObject();
            JSONObject post_Object = new JSONObject();
            jsonObject.put("HobliId",HoblisAdapter1.hobliid);
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

                     //   sorting(villageBeanList);

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


    private void stateList() {
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

                        //sorting(stateBeanList);

                        stateApdater.notifyDataSetChanged();
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


    private void district() {
        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("StateId",StateApdater1.stateid);
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

    private void taluk_list() {
        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonpost = new JSONObject();
            jsonObject.put("DistrictId",DistrictAdapter1.districtid);
            jsonpost.put("Talukobj",jsonObject);
            System.out.println("111qqqq" + jsonObject);


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
    private void block_list() {
        try{

            final JSONObject jsonObject = new JSONObject();

            JSONObject json_post = new JSONObject();
            jsonObject.put("TalukId",TalukAdapter1.talukid);
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


    public  void sorting1(String filter_text){
        System.out.println("lllllllllllllllljjjjjjj"+stateBeanList.size());

        final String text = filter_text.toLowerCase();



        if (search_status.equals("state")){
            searchresultAraaylist.clear();
            for (int i = 0; i < stateBeanList.size(); i++) {

                if (stateBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(stateBeanList.get(i));

                }
            }
            stateApdater = new StateApdater1(searchresultAraaylist,getActivity());
            recyclerView.setAdapter(stateApdater);
        }
        else if (search_status.equals("district")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < districtBeanList.size(); i++) {

                if (districtBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(districtBeanList.get(i));

                }
            }
            districtAdapter = new DistrictAdapter1(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(districtAdapter);

            // Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
        }
        else if (search_status.equals("taluk")){
            searchresultAraaylist.clear();
            for (int i = 0; i < talukBeanList.size(); i++) {

                if (talukBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(talukBeanList.get(i));

                }
            }
            talukAdapter = new TalukAdapter1(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(talukAdapter);
        }

        else if (search_status.equals("block")){
            searchresultAraaylist.clear();
            for (int i = 0; i < hobliBeanList.size(); i++) {

                if (hobliBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(hobliBeanList.get(i));

                }
            }
            hoblisAdapter = new HoblisAdapter1(searchresultAraaylist, getActivity());
            recyclerView.setAdapter(hoblisAdapter);
        }
        else if (search_status.equals("village")){
            searchresultAraaylist.clear();
            for (int i = 0; i < villageBeanList.size(); i++) {

                if (villageBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(villageBeanList.get(i));

                }
            }
//                    villageAdapter = new VillageAdapter(sdearcstateBeanList);
            recyclerView.setAdapter(villageAdapter);
        }

    }



}
