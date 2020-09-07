package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;
import com.cynoteck.petofyvet.response.getWorkingHoursResponse.GetorkingHoursResponseModel;
import com.cynoteck.petofyvet.response.getWorkingHoursResponse.WorkingHoursResponse;
import com.cynoteck.petofyvet.utils.OperatingHoursClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OperatingHoursAdaptear extends RecyclerView.Adapter<OperatingHoursAdaptear.MyViewHolder>  {

    Context context;
    List<GetorkingHoursResponseModel> getorkingHoursResponseModels;
    private OperatingHoursClickListener operatingHoursClickListener;


    public OperatingHoursAdaptear(Context context, List<GetorkingHoursResponseModel> getorkingHoursResponseModels, OperatingHoursClickListener operatingHoursClickListener) {
        this.context = context;
        this.getorkingHoursResponseModels = getorkingHoursResponseModels;
        this.operatingHoursClickListener = operatingHoursClickListener;
    }

    @NonNull
    @Override
    public OperatingHoursAdaptear.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.operating_hours_adapter, parent, false);
        OperatingHoursAdaptear.MyViewHolder vh = new OperatingHoursAdaptear.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull OperatingHoursAdaptear.MyViewHolder holder, int position) {

        if(getorkingHoursResponseModels.get(position).getDayId().equals("1.0"))
            holder.day_name.setText("MonDay");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("2.0"))
            holder.day_name.setText("TuesDay");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("3.0"))
            holder.day_name.setText("Wednesday");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("4.0"))
            holder.day_name.setText("Thursday");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("5.0"))
            holder.day_name.setText("FriDay");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("6.0"))
            holder.day_name.setText("Saturday");
        if(getorkingHoursResponseModels.get(position).getDayId().equals("7.0"))
            holder.day_name.setText("Sunday");



        holder.day_start_time.setText(getorkingHoursResponseModels.get(position).getStartTime());
        holder.day_close_time.setText(getorkingHoursResponseModels.get(position).getEndTime());



    }

    @Override
    public int getItemCount() {
        return getorkingHoursResponseModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView day_name, day_start_time,day_close_time;
        CheckBox day_tfhrs_chkbox,day_close_chkbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            day_name = itemView.findViewById(R.id.day_name);
            day_start_time = itemView.findViewById(R.id.day_start_time);
            day_close_time = itemView.findViewById(R.id.day_close_time);
            day_tfhrs_chkbox=itemView.findViewById(R.id.day_tfhrs_chkbox);
            day_close_chkbox = itemView.findViewById(R.id.day_close_chkbox);


            day_start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (operatingHoursClickListener!=null){
                        operatingHoursClickListener.onViewSetTime(getAdapterPosition(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getId(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getDayId(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getStartTime(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getEndTime(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getIsClosed(),
                                 getorkingHoursResponseModels.get(getAdapterPosition()).getAllDayOpen(),"startTime");
                    }
                }
            });
            day_close_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (operatingHoursClickListener!=null){
                        operatingHoursClickListener.onViewSetTime(getAdapterPosition(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getId(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getDayId(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getStartTime(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getEndTime(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getIsClosed(),
                                getorkingHoursResponseModels.get(getAdapterPosition()).getAllDayOpen(),"EndTime");
                    }
                }
            });
        }
    }
}
