package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Activity.LoginActivity;
import com.FarmPe.Farmer.Activity.SignUpActivity;
import com.FarmPe.Farmer.Bean.SelectLanguageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class SelectLanguageAdapter_SignUP extends RecyclerView.Adapter<SelectLanguageAdapter_SignUP.MyViewHolder>  {
    private List<SelectLanguageBean> productList;
    Activity activity;
    Fragment selectedFragment;
    Date o_date;
    SessionManager sessionManager;
    public static int selected_position=0;

    public static CardView cardView;
    public SelectLanguageAdapter_SignUP(Activity activity, List<SelectLanguageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView language_name;
        public LinearLayout language;
        public ImageView right_img;


        public MyViewHolder(View view) {
            super(view);
            language_name=view.findViewById(R.id.lang_text);
            language=view.findViewById(R.id.language);
            right_img=view.findViewById(R.id.right_img);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_item_2, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SelectLanguageBean products1 = productList.get(position);


        holder.language_name.setText(products1.getVendor());
        System.out.print("iiidddddd" + products1.getLanguageid());

        holder.language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.saveLanguage_name(products1.getVendor());
                getLang(products1.getLanguageid());
                SignUpActivity.change_lang.setText(products1.getVendor());
                SignUpActivity.dialog.dismiss();
            }
        });
    }

    private void getLang(int id) {

        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id",id);


            System.out.print("iiidddddd"+ id);

            Crop_Post.crop_posting(activity, Urls.CHANGE_LANGUAGE, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("qqqqqqvv" + result);

                    try{

                        sessionManager.saveLanguage(result.toString());



                        String log_name = result.getString("FullName");
                        String log_mobile = result.getString("PhoneNo");
                        String log_password = result.getString("Password");
                        String log_register = result.getString("Register");
                        String lang_title1 = result.getString("ChangeLanguage");



                        SignUpActivity.popup_heading.setText(lang_title1);
                        SignUpActivity.sign_name.setHint(log_name);
                        SignUpActivity.create_acc.setText(log_register);
                        SignUpActivity.sign_mobile.setHint(log_mobile);
                        SignUpActivity.sign_pass.setHint(log_password);
                        SignUpActivity.continue_sign_up.setText(log_register);

                        SignUpActivity.mob_toast = result.getString("Entervalidmobilenumber");
                        SignUpActivity.passwrd_toast = result.getString("Enterpasswordoflength6characters");
                        SignUpActivity.minimum_character_toast = result.getString("NameShouldContainMinimum2Characters");
                        SignUpActivity.enter_all_toast = result.getString("EnterAllTextFields");
                        SignUpActivity.name_toast= result.getString("Enteryourname");
                        SignUpActivity.mobile_registered_toast = result.getString("Thismobilehasalreadyregistered");
                        SignUpActivity.toast_internet = result.getString("GoodConnectedtoInternet");
                        SignUpActivity.toast_nointernet = result.getString("NoInternetConnection");



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}