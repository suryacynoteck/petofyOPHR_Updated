package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;
import com.cynoteck.petofyvet.response.getWorkingHoursResponse.GetorkingHoursResponseModel;
import com.cynoteck.petofyvet.response.getWorkingHoursResponse.WorkingHoursResponse;
import com.cynoteck.petofyvet.response.staffPermissionListResponse.StaffPermissionResponseModel;
import com.cynoteck.petofyvet.utils.OperatingHoursClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

public class StaffPermissionListAdapter extends RecyclerView.Adapter<StaffPermissionListAdapter.MyViewHolder>  {

    Context context;
    List<StaffPermissionResponseModel> staffPermissionResponseModels;
    private OperatingHoursClickListener operatingHoursClickListener;


    public StaffPermissionListAdapter(Context context, List<StaffPermissionResponseModel> staffPermissionResponseModels, OperatingHoursClickListener operatingHoursClickListener) {
        this.context = context;
        this.staffPermissionResponseModels = staffPermissionResponseModels;
        this.operatingHoursClickListener = operatingHoursClickListener;
    }

    @NonNull
    @Override
    public StaffPermissionListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.operating_hours_adapter, parent, false);
        StaffPermissionListAdapter.MyViewHolder vh = new StaffPermissionListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull StaffPermissionListAdapter.MyViewHolder holder, int position) {

    holder.permission_name.setText(staffPermissionResponseModels.get(position).getPermissionName());
    if(staffPermissionResponseModels.get(position).getIsSelected().equals("true"))
    holder.permission_switch.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return staffPermissionResponseModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView permission_name;
        SwitchCompat permission_switch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            permission_name = itemView.findViewById(R.id.permission_name);
            permission_switch = itemView.findViewById(R.id.permission_switch);


            permission_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        operatingHoursClickListener.onViewSetTime(getAdapterPosition(),staffPermissionResponseModels.get(getAdapterPosition()).getId(),"true");
                    } else {
                        // The toggle is disabled
                        operatingHoursClickListener.onViewSetTime(getAdapterPosition(),staffPermissionResponseModels.get(getAdapterPosition()).getId(),"false");

                    }
                }
            });
        }
    }
}
