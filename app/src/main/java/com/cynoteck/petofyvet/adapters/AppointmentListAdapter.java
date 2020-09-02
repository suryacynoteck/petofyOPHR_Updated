package com.cynoteck.petofyvet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.appointmentResponse.AppointmentList;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {

    ArrayList<AppointmentList> appointmentList;

    public AppointmentListAdapter(ArrayList<AppointmentList> appointmentList) {
        this.appointmentList = appointmentList;

    }

    @NonNull
    @Override
    public AppointmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        AppointmentListAdapter.MyViewHolder vh = new AppointmentListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentListAdapter.MyViewHolder holder, int position)
    {
        if (appointmentList.get(position).getIsApproved().equals("true")){
            holder.join_BT.setVisibility(View.VISIBLE);
        }else {
            holder.approve_BT.setVisibility(View.VISIBLE);
        }

        holder.appoint_subject_TV.setText(appointmentList.get(position).getSubject());
        holder.timing_TV.setText(appointmentList.get(position).getStartDateString()+"-"+appointmentList.get(position).getEndDateString());
        holder.pet_parent_TV.setText(appointmentList.get(position).getPetParentName());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appoint_subject_TV,timing_TV,pet_parent_TV;
        Button approve_BT,join_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appoint_subject_TV = itemView.findViewById(R.id.appoint_subject_TV);
            timing_TV = itemView.findViewById(R.id.timing_TV);
            pet_parent_TV = itemView.findViewById(R.id.pet_parent_TV);
            approve_BT = itemView.findViewById(R.id.approve_BT);
            join_BT = itemView.findViewById(R.id.join_BT);

            approve_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            join_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
