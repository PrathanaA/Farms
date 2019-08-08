package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.FarmPe.Farmer.Bean.List_Farm_Bean;
import com.FarmPe.Farmer.Bean.Notification_recy_bean;

import com.FarmPe.Farmer.Fragment.ListYourFarmsSecond;
import com.FarmPe.Farmer.R;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;


public class List_Farm_Adapter2 extends RecyclerView.Adapter<List_Farm_Adapter2.MyViewHolder>  {

    private List<List_Farm_Bean> productList;
    Activity activity;
    Fragment selectedFragment;
    public LinearLayout linearLayout;
    public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public static String farm_type_id;
    public static int position_1;

    public List_Farm_Adapter2(Activity activity, List<List_Farm_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView actninfo;
        public RadioButton list_farm1;
        public LinearLayout farm_type_id_layout;


        public MyViewHolder(View view) {
            super(view);
            actninfo=view.findViewById(R.id.actninfo);

            image=view.findViewById(R.id.image);
            list_farm1=view.findViewById(R.id.list_farm1);
            farm_type_id_layout=view.findViewById(R.id.linear_layout);

            //linearLayout=view.findViewById(R.id.dialog_list);
            //confirmbutton=view.findViewById(R.id.delivery2);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_farm_first_layout_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final List_Farm_Bean products = productList.get(position);



        holder.list_farm1.setText(products.getFarm_list_name());


        holder.farm_type_id_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farm_type_id=products.getFarm_list_id();
            }
        });

        boolean selectedval= products.isIsselected()? true : false;

        holder.list_farm1.setChecked(selectedval);

        if (!ListYourFarmsSecond.selectedRadio.equals("") && products.getFarm_list_name().equals(ListYourFarmsSecond.selectedRadio)){
            holder.list_farm1.setChecked(true);



        }

       else{
            holder.list_farm1.setChecked(false);
        }


        holder.list_farm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ListYourFarmsSecond.selectedRadio = holder.list_farm1.getText().toString();

                farm_type_id=products.getFarm_list_id();


                ListYourFarmsSecond.selected=true;
                boolean selectedval= products.isIsselected()? false : true;

                holder.list_farm1.setChecked(selectedval);
                position_1=position;
            //  farm_listid = products.getFarm_list_id();

                for(int i = 0;i<productList.size();i++) {

                    if (i == position) {
                        products.setIsselected(true);

                    } else {
                        productList.get(i).setIsselected(false);
                    }
                }

                notifyDataSetChanged();
            }
        });





      /*  if (Notification_Recyc_Fragment.list.get(position).equals(products.getNoti_id())){
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(true);
            FirebaseMessaging.getInstance().subscribeToTopic(products.getNoti_code());

        }else {
            holder.actninfo.setText(products.getNoti_txt());
            holder.switch1.setChecked(false);


        }*/

//
//        JSONObject jsonObject = null;
//        try{
//
//           jsonObject = new JSONObject();
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        Crop_Post.crop_posting(activity, Urls.GET_NOTIFICATION, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("ffffffnn" + result);
//
//                    try{
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//





    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }

}