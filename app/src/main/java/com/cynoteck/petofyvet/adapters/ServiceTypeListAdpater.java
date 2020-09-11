package com.cynoteck.petofyvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.response.getPetDetailsResponse.PetTypeList;
import com.cynoteck.petofyvet.response.updateProfileResponse.ServiceTypeList;

import java.util.List;

public class ServiceTypeListAdpater extends RecyclerView.Adapter<ServiceTypeListAdpater.MyViewHolder> {
    Context context;
    List<ServiceTypeList> serviceTypeLists;

    public ServiceTypeListAdpater(Context context, List<ServiceTypeList> serviceTypeLists) {
        this.context = context;
        this.serviceTypeLists = serviceTypeLists;
    }

    @NonNull
    @Override
    public ServiceTypeListAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_type_date, parent, false);
        ServiceTypeListAdpater.MyViewHolder vh = new ServiceTypeListAdpater.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTypeListAdpater.MyViewHolder holder, int position) {
        holder.service_type_TV.setText(serviceTypeLists.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return serviceTypeLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView service_type_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service_type_TV=itemView.findViewById(R.id.service_data);
        }
    }
}
