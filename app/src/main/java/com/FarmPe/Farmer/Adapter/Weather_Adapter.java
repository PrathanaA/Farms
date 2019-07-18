package com.FarmPe.Farmer.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.FarmPe.Farmer.Bean.StateBean;
import com.FarmPe.Farmer.Fragment.Add_New_Address_Fragment;
import com.FarmPe.Farmer.Fragment.ListYourFarmsThird;
import com.FarmPe.Farmer.R;
import java.util.List;


public class Weather_Adapter extends RecyclerView.Adapter<Weather_Adapter.HoblisMyViewHolder> {

    List<StateBean>stateBeans;
    public static String villageid;
    Activity activity;



    public Weather_Adapter(List<StateBean> stateBeans, Activity activity) {
        this.stateBeans = stateBeans;
        this.activity = activity;
    }



    @NonNull
    @Override
    public HoblisMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview=LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_adapter_class,parent,false);

        return new HoblisMyViewHolder(stateview);
    }


    public class HoblisMyViewHolder extends RecyclerView.ViewHolder{
        TextView city_name;
        LinearLayout state_name_layout;
        public HoblisMyViewHolder(View itemView) {
            super(itemView);
            city_name=itemView.findViewById(R.id.city_name);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final HoblisMyViewHolder holder, int position) {
        final StateBean stateBean = stateBeans.get(position);

        holder.city_name.setText(stateBean.getName());

    }

    @Override
    public int getItemCount() {
        return stateBeans.size();
    }


}
