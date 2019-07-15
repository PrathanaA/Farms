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

public class VillageAdapter1 extends RecyclerView.Adapter<VillageAdapter1.HoblisMyViewHolder> {

    List<StateBean>stateBeans;
    public static String villageid;
    Activity activity;



    public VillageAdapter1(List<StateBean> stateBeans, Activity activity) {
        this.stateBeans = stateBeans;
        this.activity = activity;
    }



    @NonNull
    @Override
    public HoblisMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview=LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);


        return new HoblisMyViewHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final HoblisMyViewHolder holder, int position) {
        final StateBean stateBean=stateBeans.get(position);
        holder.statename.setText(stateBean.getName());

        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               villageid = stateBean.getId();
                if (ListYourFarmsThird.list_farms_village==null) {
                    Add_New_Address_Fragment.village.setText(holder.statename.getText().toString());
                    Add_New_Address_Fragment.drawer.closeDrawers();
                    // Add_New_Address_Fragment.grade_dialog.dismiss();
                }else{
                    ListYourFarmsThird.village.setText(holder.statename.getText().toString());
                    ListYourFarmsThird.grade_dialog.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateBeans.size();
    }

    public class HoblisMyViewHolder extends RecyclerView.ViewHolder{
        TextView statename;
        LinearLayout state_name_layout;
        public HoblisMyViewHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);

        }
    }
}
