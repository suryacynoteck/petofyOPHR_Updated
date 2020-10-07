package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.immuniztionHistory.ImmunizationHistorymodel;

import java.util.ArrayList;

public class ImmunizationHistoryAdopter extends RecyclerView.Adapter<ImmunizationHistoryAdopter.MyViewHolder> {
    Context context;
    ArrayList<ImmunizationHistorymodel> getImmunizationHistory;

    public ImmunizationHistoryAdopter(Context context, ArrayList<ImmunizationHistorymodel> getImmunizationHistory) {
        this.context = context;
        this.getImmunizationHistory = getImmunizationHistory;
    }

    @NonNull
    @Override
    public ImmunizationHistoryAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.immunization_chart_list, parent, false);
        ImmunizationHistoryAdopter.MyViewHolder vh = new ImmunizationHistoryAdopter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImmunizationHistoryAdopter.MyViewHolder holder, int position) {

       Log.d("aanannanan",""+getImmunizationHistory.get(position).getPetVaccinationDetail().size());
        for(int i=0;i<getImmunizationHistory.get(position).getPetVaccinationDetail().size();i++)
        {
            holder.immunization_dt.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getImmunizationDate().substring(0,getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getImmunizationDate().length()-8));
            holder.vaccine_cls.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getVaccineType());
            holder.vaccine.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getVaccine());
        }
        holder.nxt_due_dt.setText(getImmunizationHistory.get(position).getFollowUpDate());
        holder.down_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.up_IV.setVisibility(View.VISIBLE);
                holder.down_IV.setVisibility(View.GONE);
                holder.vaccine_type_expand_LL.setVisibility(View.VISIBLE);

            }
        });

        holder.up_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.up_IV.setVisibility(View.GONE);
                holder.down_IV.setVisibility(View.VISIBLE);
                holder.vaccine_type_expand_LL.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getImmunizationHistory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView immunization_dt,vaccine_cls,vaccine,nxt_due_dt;
        LinearLayout vaccine_type_expand_LL;
        ImageView up_IV, down_IV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            immunization_dt =itemView.findViewById(R.id.immunization_dt);
            vaccine_cls = itemView.findViewById(R.id.vaccine_cls);
            vaccine = itemView.findViewById(R.id.vaccine);
            nxt_due_dt = itemView.findViewById(R.id.nxt_due_dt);
            down_IV = itemView.findViewById(R.id.down_IV);
            up_IV = itemView.findViewById(R.id.up_IV);
            vaccine_type_expand_LL = itemView.findViewById(R.id.vaccine_type_expand_LL);
        }
    }
}
