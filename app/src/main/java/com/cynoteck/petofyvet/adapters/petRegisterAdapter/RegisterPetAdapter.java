package com.cynoteck.petofyvet.adapters.petRegisterAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getPetListResponse.PetList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RegisterPetAdapter extends RecyclerView.Adapter<RegisterPetAdapter.MyViewHolder> {
    Context context;
    List<PetList> profileList;
    private RegisterRecyclerViewClickListener onProductItemClickListner;

    public RegisterPetAdapter(Context context, List<PetList> profileList, RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;
    }

    @NonNull
    @Override
    public RegisterPetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_register_list, parent, false);
        RegisterPetAdapter.MyViewHolder vh = new RegisterPetAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterPetAdapter.MyViewHolder holder, int position) {
        holder.pet_reg__id_TV.setText(profileList.get(position).getPetUniqueId());
        holder.pet_reg_date_of_birth_TV.setText(profileList.get(position).getDateOfBirth());
        holder.pet_reg_name_TV.setText(profileList.get(position).getPetName());
        holder.pet_reg_gender_TV.setText(profileList.get(position).getPetSex());

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView petRegImage_IV;
        TextView pet_reg__id_TV,pet_reg_date_of_birth_TV,pet_reg_name_TV,pet_reg_gender_TV;
        Button view_reg_pet_details_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            petRegImage_IV = itemView.findViewById(R.id.petRegImage_IV);
            pet_reg__id_TV = itemView.findViewById(R.id.pet_reg__id_TV);
            pet_reg_date_of_birth_TV = itemView.findViewById(R.id.pet_reg_date_of_birth_TV);
            pet_reg_name_TV = itemView.findViewById(R.id.pet_reg_name_TV);
            pet_reg_gender_TV = itemView.findViewById(R.id.pet_reg_gender_TV);
            view_reg_pet_details_BT = itemView.findViewById(R.id.view_reg_pet_details_BT);

            view_reg_pet_details_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onProductItemClickListner!=null){
                        onProductItemClickListner.onProductClick(getAdapterPosition());
                    }
                }
            });

            }

        }
    }
