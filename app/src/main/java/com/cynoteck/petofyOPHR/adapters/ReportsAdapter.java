package com.cynoteck.petofyOPHR.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyOPHR.utils.RegisterRecyclerViewClickListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {
    Context context;
    ArrayList<PetList> profileList;
   /* List<PetList> filterProfileList;*/

    RegisterRecyclerViewClickListener onProductItemClickListner;
    public ReportsAdapter(Context context, ArrayList<PetList> profileList,RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;
       /* filterProfileList = new ArrayList<>(profileList);*/

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
        holder.setIsRecyclable(false);
        holder.pet_id_TV.setText(profileList.get(position).getPetUniqueId());
        holder.date_of_birth_TV.setText(profileList.get(position).getDateOfBirth());
        holder.pet_name_TV.setText(profileList.get(position).getPetName().substring(0, 1).toUpperCase() + profileList.get(position).getPetName().substring(1));
        holder.pet_reg_gender_TV.setText(profileList.get(position).getPetSex());

        try {
            Glide.with(context)
                    .load(new URL(profileList.get(position).getPetProfileImageUrl()))
                    .placeholder(R.drawable.pet_image)
                    .into(holder.pet_profile_IV);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pet_id_TV,date_of_birth_TV,pet_name_TV,pet_reg_gender_TV;
        Button view_pet_details_BT;
        ImageView pet_profile_IV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_id_TV = itemView.findViewById(R.id.pet_id_TV);
            date_of_birth_TV = itemView.findViewById(R.id.date_of_birth_TV);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);
            pet_reg_gender_TV = itemView.findViewById(R.id.pet_reg_gender_TV);
            view_pet_details_BT=itemView.findViewById(R.id.view_pet_details_BT);
            pet_profile_IV=itemView.findViewById(R.id.petImage_IV);
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
