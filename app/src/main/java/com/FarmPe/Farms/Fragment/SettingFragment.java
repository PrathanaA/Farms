package com.FarmPe.Farms.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Bean.FarmsImageBean;
import com.FarmPe.Farms.R;
import com.FarmPe.Farms.SessionManager;
import com.FarmPe.Farms.Urls;
import com.FarmPe.Farms.Volly_class.Crop_Post;
import com.FarmPe.Farms.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed,logout_layout,noti_setting,refer_earn,feedback,change_lang,policy,acc_info,your_address;
    Fragment selectedFragment;
    TextView notificatn,change_language,your_addresss,acc_info1,refer_ern,feedbk,help_1,abt_frmpe,polic_1,logot,setting_tittle;
    SessionManager sessionManager;
    JSONObject lngObject;
    JSONArray get_address_array;
    ImageView b_arrow;
    String pickUPFrom;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        logout_layout=view.findViewById(R.id.logout_layout);
        noti_setting=view.findViewById(R.id.noti_setting);
        refer_earn=view.findViewById(R.id.refer_earn);
        feedback=view.findViewById(R.id.feedback);
        change_lang=view.findViewById(R.id.change_lang);
        policy=view.findViewById(R.id.policy);


        acc_info=view.findViewById(R.id.acc_info);
        your_address=view.findViewById(R.id.ur_address);
        notificatn=view.findViewById(R.id.notificatn);
        change_language=view.findViewById(R.id.change_language);
        your_addresss=view.findViewById(R.id.your_addresss);
        acc_info1=view.findViewById(R.id.acc_info1);
        refer_ern=view.findViewById(R.id.refer_ern);
        feedbk=view.findViewById(R.id.feedbk);
        help_1=view.findViewById(R.id.help_1);
        abt_frmpe=view.findViewById(R.id.abt_frmpe);
        polic_1=view.findViewById(R.id.polic_1);
        logot=view.findViewById(R.id.logot);




        sessionManager = new SessionManager(getActivity());

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });



        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));


            acc_info1.setText(lngObject.getString("AccountInfo"));
            your_addresss.setText(lngObject.getString("YourAddress"));

            change_language.setText(lngObject.getString("ChangeLanguage"));
            polic_1.setText(lngObject.getString("PrivacyPolicy"));

            help_1.setText(lngObject.getString("Help_Support"));
            abt_frmpe.setText(lngObject.getString("AboutFarmPe"));
            feedbk.setText(lngObject.getString("FeedBack"));
            notificatn.setText(lngObject.getString("Notifications"));
            logot.setText(lngObject.getString("Logout"));




        } catch (JSONException e) {
            e.printStackTrace();
        }





       logout_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final TextView yes1,no1,text_desctxt,popup_headingtxt;
               final LinearLayout close_layout;
               System.out.println("aaaaaaaaaaaa");
               final Dialog dialog = new Dialog(getContext());
               dialog.setContentView(R.layout.logout_layout);
               text_desctxt =  dialog.findViewById(R.id.text_desc);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.setCancelable(false);
               yes1 =  dialog.findViewById(R.id.yes_1);
               no1 =  dialog.findViewById(R.id.no_1);
               popup_headingtxt =  dialog.findViewById(R.id.popup_heading);


               try {
                   lngObject = new JSONObject(sessionManager.getRegId("language"));
                   text_desctxt.setText(lngObject.getString("AreyousureyouwanttoLogout"));
                   yes1.setText(lngObject.getString("Confirm"));
                   no1.setText(lngObject.getString("Cancel"));
                   popup_headingtxt.setText(lngObject.getString("Logout"));

               } catch (JSONException e) {
                   e.printStackTrace();
               }


               close_layout =  dialog.findViewById(R.id.close_layout);
               no1.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });
               close_layout.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });
               yes1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       sessionManager.logoutUser();
                       getActivity().finish();
                       dialog.dismiss();
                   }
               });


               dialog.show();

           }
       });

        help_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HelpandSupportFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });
        abt_frmpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = AboutfarmpeFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });
        acc_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = UpdateAccDetailsFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });
       noti_setting.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectedFragment = Notification_Recyc_Fragment.newInstance();
               FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.frame_layout, selectedFragment);
               transaction.addToBackStack("setting");
               transaction.commit();
           }
       });
        refer_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ReferAndEarncopy.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = FeedbackFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });

        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ChangeLanguageFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status","setting_privacy");
                selectedFragment = PrivacyPolicyFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                selectedFragment.setArguments(bundle);
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("setting");
                transaction.commit();
            }
        });

        your_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId",sessionManager.getRegId("userId"));
                    jsonObject.put("PickUpFrom",pickUPFrom);
                    System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));
                    System.out.println("ggggggggggaaaaaaa"+jsonObject);

                    Crop_Post.crop_posting(getActivity(), Urls.Get_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("ggggggggggaaaaaaa"+result);

                            try{
                                get_address_array = result.getJSONArray("UserAddressDetails");

                                if(get_address_array.length()== 0){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("navigation_from","SETTING_FRAG");
                                    selectedFragment = Add_New_Address_Fragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    selectedFragment.setArguments(bundle);
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    transaction.addToBackStack("setting");
                                    transaction.commit();

                                }else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("navigation_from","SETTING_FRAG1");
                                    selectedFragment = You_Address_Fragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                    selectedFragment.setArguments(bundle);
                                    transaction.addToBackStack("setting");
                                    transaction.commit();

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
        });
        return view;
    }



}
