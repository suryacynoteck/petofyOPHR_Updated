package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getMyVisitedPetRecordResponse.GetMyVisitPetRecordPetClinicVisitList;

import java.util.List;

public class AllVisitsAdapter extends RecyclerView.Adapter<AllVisitsAdapter.MyViewHolder> {

    Context context;
    List<GetMyVisitPetRecordPetClinicVisitList> getMyVisitPetRecordPetClinicVisitLists;

    public AllVisitsAdapter(Context context, List<GetMyVisitPetRecordPetClinicVisitList> getMyVisitPetRecordPetClinicVisitLists) {
        this.context = context;
        this.getMyVisitPetRecordPetClinicVisitLists = getMyVisitPetRecordPetClinicVisitLists;
    }

    @NonNull
    @Override
    public AllVisitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_visit_report, parent, false);
        AllVisitsAdapter.MyViewHolder vh = new AllVisitsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AllVisitsAdapter.MyViewHolder holder, int position) {
        holder.pet_parent_name_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getPetName());
        holder.pet_reg__id_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getPetUniqueId());
        holder.pet_parent_name_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getPetParentName());
        holder.pet_phone_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getContactNumber());
        holder.pet_nature_of_visit_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getNatureOfVisit().getNature());
        holder.pet_remakrs_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getTreatmentRemarks());
        holder.pet_nature_of_visit_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getVisitDate());
        holder.follow_up_date_TV.setText(getMyVisitPetRecordPetClinicVisitLists.get(position).getFollowUpDate());


    }

    @Override
    public int getItemCount() {
        return getMyVisitPetRecordPetClinicVisitLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pet_reg_name_TV, pet_reg__id_TV,pet_parent_name_TV,pet_phone_TV,pet_nature_of_visit_TV,pet_remakrs_TV,VisitDate_TV,follow_up_date_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_reg_name_TV = itemView.findViewById(R.id.pet_reg_name_TV);
            pet_reg__id_TV = itemView.findViewById(R.id.pet_reg__id_TV);
            pet_parent_name_TV = itemView.findViewById(R.id.pet_parent_name_TV);
            pet_phone_TV = itemView.findViewById(R.id.pet_phone_TV);
            pet_nature_of_visit_TV = itemView.findViewById(R.id.pet_nature_of_visit_TV);
            pet_remakrs_TV = itemView.findViewById(R.id.pet_remakrs_TV);
            VisitDate_TV = itemView.findViewById(R.id.VisitDate_TV);
            follow_up_date_TV = itemView.findViewById(R.id.follow_up_date_TV);

        }
    }
}
