package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.immunizationListResponse.ImmunizationScheduleScheduleList;
import com.cynoteck.petofyvet.utils.ImmunizationOnclick;

import java.util.List;

public class ImmunizationChartAdapter extends RecyclerView.Adapter<ImmunizationChartAdapter.MyViewHolder> {
    Context context;
    List<ImmunizationScheduleScheduleList> immunizationScheduleScheduleLists;
    private ImmunizationOnclick immunizationOnclick;

    public ImmunizationChartAdapter(Context context, List<ImmunizationScheduleScheduleList> immunizationScheduleScheduleLists, ImmunizationOnclick immunizationOnclick) {
        this.context = context;
        this.immunizationScheduleScheduleLists = immunizationScheduleScheduleLists;
        this.immunizationOnclick = immunizationOnclick;
    }


    @NonNull
    @Override
    public ImmunizationChartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.immunization_chart_list, parent, false);
        ImmunizationChartAdapter.MyViewHolder vh = new ImmunizationChartAdapter.MyViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(@NonNull ImmunizationChartAdapter.MyViewHolder holder, int position) {
        String min_age="",max_age;
        min_age = immunizationScheduleScheduleLists.get(position).getMinimunAge().substring(0,immunizationScheduleScheduleLists.get(position).getMinimunAge().length()-3);
        max_age = immunizationScheduleScheduleLists.get(position).getMaximunAge().substring(0,immunizationScheduleScheduleLists.get(position).getMaximunAge().length()-3);

        holder.age_group_TV.setText(min_age+"-"+max_age+"Days");
        holder.vaccine_name_TV.setText(immunizationScheduleScheduleLists.get(position).getRecommendedVaccinations());

    }

    @Override
    public int getItemCount() {
        return immunizationScheduleScheduleLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView age_group_TV,vaccine_name_TV;
        ImageView delete_vaccine_IV,edit_vaccine_IV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            age_group_TV = itemView.findViewById(R.id.age_group_TV);
            vaccine_name_TV = itemView.findViewById(R.id.vaccine_name_TV);
            delete_vaccine_IV = itemView.findViewById(R.id.delete_vaccine_IV);
            edit_vaccine_IV = itemView.findViewById(R.id.edit_vaccine_IV);

            delete_vaccine_IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (immunizationOnclick!=null){
                        immunizationOnclick.onDeleteButton(getAdapterPosition());
                    }
                }
            });
            edit_vaccine_IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (immunizationOnclick!=null){
                        immunizationOnclick.onEditButton(getAdapterPosition());
                    }
                }
            });

        }
    }
}
