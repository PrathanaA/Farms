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

public class TalukAdapter1 extends RecyclerView.Adapter<TalukAdapter1.TalukMyViewHolder> {

    List<StateBean>stateBeanList;
    public static String talukid;
    Activity activity;


    public TalukAdapter1(List<StateBean> stateBeanList,Activity activity) {
        this.stateBeanList = stateBeanList;
        this.activity=activity;
    }



    @NonNull
    @Override
    public TalukMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview=LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);
        return new TalukMyViewHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final TalukMyViewHolder holder, int position) {
        final StateBean stateBean=stateBeanList.get(position);

        holder.statename.setText(stateBean.getName());

        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("checkingggggg");
                talukid=stateBean.getId();


                    ListYourFarmsThird.taluk.setText(holder.statename.getText().toString());
                    ListYourFarmsThird.drawer.closeDrawers();
                ListYourFarmsThird.search.setText("");



            }
        });


    }

    @Override
    public int getItemCount() {
        return stateBeanList.size();
    }

    public class TalukMyViewHolder extends RecyclerView.ViewHolder {
        TextView statename;
        LinearLayout state_name_layout;

        public TalukMyViewHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);
        }
    }
}
