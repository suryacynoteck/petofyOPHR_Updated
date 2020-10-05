package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getVaccinationResponse.GetVaccineResponseModel;
import com.cynoteck.petofyvet.response.immuniztionHistory.ImmunizationHistorymodel;
import com.cynoteck.petofyvet.utils.ImmunizationOnclickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.immunization_history_adopter, parent, false);
        ImmunizationHistoryAdopter.MyViewHolder vh = new ImmunizationHistoryAdopter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImmunizationHistoryAdopter.MyViewHolder holder, int position) {

       Log.d("aanannanan",""+getImmunizationHistory.get(position).getPetVaccinationDetail().size());
        for(int i=0;i<getImmunizationHistory.get(position).getPetVaccinationDetail().size();i++)
        {
            holder.immunization_dt.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getImmunizationDate());
            holder.vaccine_cls.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getVaccineType());
            holder.vaccine.setText(getImmunizationHistory.get(position).getPetVaccinationDetail().get(i).getVaccine());
        }
        holder.nxt_due_dt.setText(getImmunizationHistory.get(position).getFollowUpDate());

    }

    @Override
    public int getItemCount() {
        return getImmunizationHistory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView immunization_dt,vaccine_cls,vaccine,nxt_due_dt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            immunization_dt =itemView.findViewById(R.id.immunization_dt);
            vaccine_cls = itemView.findViewById(R.id.vaccine_cls);
            vaccine = itemView.findViewById(R.id.vaccine);
            nxt_due_dt = itemView.findViewById(R.id.nxt_due_dt);

        }
    }
}
