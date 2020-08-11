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
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;
import com.cynoteck.petofyvet.utils.StaffListClickListener;

import java.util.List;

public class AllStaffAdapter extends RecyclerView.Adapter<AllStaffAdapter.MyViewHolder> {

    Context context;
    List<GetAllStaffData> getAllStaffData;
    private StaffListClickListener onProductItemClickListner;

    public AllStaffAdapter(Context context, List<GetAllStaffData> getAllStaffData, StaffListClickListener onProductItemClickListner) {
        this.context = context;
        this.getAllStaffData = getAllStaffData;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public AllStaffAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_staff_list, parent, false);
        AllStaffAdapter.MyViewHolder vh = new AllStaffAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AllStaffAdapter.MyViewHolder holder, int position) {

        if (getAllStaffData.get(position).getIsActive().equals("false")){
            holder.staff_status_BT.setBackgroundResource(R.drawable.deactivate_status);
            holder.staff_status_BT.setText("Deactivated");

        }else {

            holder.staff_status_BT.setBackgroundResource(R.drawable.activated_status);
            holder.staff_status_BT.setText("Activated");

        }
        holder.staff_name_TV.setText(getAllStaffData.get(position).getFirstName()+" "+getAllStaffData.get(position).getLastName());
        holder.staff_email_TV.setText(getAllStaffData.get(position).getEmail());
        holder.staff_phone_number_TV.setText(getAllStaffData.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return getAllStaffData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView staff_name_TV, staff_email_TV,staff_phone_number_TV;
        Button view_details_BT,staff_status_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            staff_email_TV = itemView.findViewById(R.id.staff_email_TV);
            staff_name_TV = itemView.findViewById(R.id.staff_name_TV);
            view_details_BT = itemView.findViewById(R.id.view_details_BT);
            staff_status_BT=itemView.findViewById(R.id.staff_status_BT);
            staff_phone_number_TV = itemView.findViewById(R.id.staff_phone_number_TV);


            view_details_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner!=null){
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }
                }
            });
            staff_status_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner!=null){
                        onProductItemClickListner.onStausClick(getAdapterPosition(),staff_status_BT);
                    }
                }
            });
        }
    }
}
