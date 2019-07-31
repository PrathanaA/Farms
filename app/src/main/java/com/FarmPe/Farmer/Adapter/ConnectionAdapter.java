package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Bean.ConnectionBean;
import com.FarmPe.Farmer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder>  {
    private List<ConnectionBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static int WHATSAPP_NUMMBER=1;
    public LinearLayout linearLayout;
   public static LinearLayout next_arw;
    public static String first;
    public static CardView cardView;
    public static String contact;
    public ConnectionAdapter(Activity activity, List<ConnectionBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
//        session=new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,normal_call,whatsapp_call;
        public LinearLayout item;
        public TextView farmer_name,profession,location;




        public MyViewHolder(View view) {
            super(view);
            farmer_name=view.findViewById(R.id.farmer_name);
            image=view.findViewById(R.id.farmer_img);
            location=view.findViewById(R.id.location);
            profession=view.findViewById(R.id.profession);
            normal_call=view.findViewById(R.id.normal_call);
            whatsapp_call=view.findViewById(R.id.whatsapp_call);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.connections_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ConnectionBean products = productList.get(position);
      //holder.agri_text.setText(products.getAgri_text());
        holder.farmer_name.setText(products.getName());
        holder.profession.setText(products.getProffesion());
        holder.location.setText(products.getLocation() + " , " + products.getState());

        holder.normal_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = products.getPhone_no();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                activity.startActivity(intent);

            }
        });

 holder.whatsapp_call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        String waNumber = products.getPhone_no();
        sendIntent.putExtra("jid", waNumber + "@s.whatsapp.net");
        activity.startActivity(sendIntent);
    }
});


        Glide.with(activity).load(products.getImage())

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }







}