package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getPetHospitalizationResponse.getHospitalizationListResponse.PetHospitalizationsList;
import com.cynoteck.petofyvet.utils.ViewAndUpdateClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImmunazationVaccineAdopter extends RecyclerView.Adapter<ImmunazationVaccineAdopter.MyViewHolder> {

    Context context;
    ArrayList<String> petHospitalizationsLists;

    public ImmunazationVaccineAdopter(Context context, ArrayList<String> petClinicVisitLists) {
        this.context = context;
        this.petHospitalizationsLists = petClinicVisitLists;
    }

    @NonNull
    @Override
    public ImmunazationVaccineAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.immunization_add_layout, parent, false);
        ImmunazationVaccineAdopter.MyViewHolder vh = new ImmunazationVaccineAdopter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImmunazationVaccineAdopter.MyViewHolder holder, int position) {
        StringTokenizer st = new StringTokenizer(petHospitalizationsLists.get(position), ",");
        String brandType = st.nextToken();
        String vaccine_name = st.nextToken();

        holder.vaccine_brand_type.setText(brandType);
        holder.vaccine_name.setText(vaccine_name);

        holder.vaccine_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.vaccine_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.vaccine_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return petHospitalizationsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vaccine_edit,vaccine_save,vaccine_delete;
        TextView vaccine_brand_type,vaccine_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vaccine_brand_type = itemView.findViewById(R.id.vaccine_brand_type);
            vaccine_name = itemView.findViewById(R.id.vaccine_name);
            vaccine_edit = itemView.findViewById(R.id.vaccine_edit);
            vaccine_save = itemView.findViewById(R.id.vaccine_save);
            vaccine_delete = itemView.findViewById(R.id.vaccine_delete);

        }
    }
}
