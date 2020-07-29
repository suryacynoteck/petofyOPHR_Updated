package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.petRegisterAdapter.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyvet.response.getPetReportsResponse.PetList;

import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {
    Context context;
    List<PetList> profileList;
    RegisterRecyclerViewClickListener onProductItemClickListner;
    public ReportsAdapter(Context context, List<PetList> profileList,RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pet_id_TV.setText(profileList.get(position).getPetUniqueId());
        holder.date_of_birth_TV.setText(profileList.get(position).getDateOfBirth());
        holder.pet_name_TV.setText(profileList.get(position).getPetName());
        holder.pet_color_TV.setText(profileList.get(position).getPetColor());

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pet_id_TV,date_of_birth_TV,pet_name_TV,pet_color_TV;
        Button view_pet_details_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_id_TV = itemView.findViewById(R.id.pet_id_TV);
            date_of_birth_TV = itemView.findViewById(R.id.date_of_birth_TV);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);
            pet_color_TV = itemView.findViewById(R.id.pet_color_TV);
            view_pet_details_BT=itemView.findViewById(R.id.view_pet_details_BT);

            view_pet_details_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner!=null){
                        onProductItemClickListner.onProductClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}
