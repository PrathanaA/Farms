package com.FarmPe.Farms.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farms.Bean.StateBean;
import com.FarmPe.Farms.Fragment.Add_New_Address_Fragment;
import com.FarmPe.Farms.R;

import java.util.List;

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.HoblisMyViewHolder> {

    List<StateBean>stateBeans;
    public static String villageid;
    Activity activity;



    public VillageAdapter(List<StateBean> stateBeans, Activity activity) {
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
                Add_New_Address_Fragment.village.setText(holder.statename.getText().toString());
                Add_New_Address_Fragment.drawer.closeDrawers();

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
