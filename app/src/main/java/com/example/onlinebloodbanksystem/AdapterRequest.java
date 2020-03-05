package com.example.onlinebloodbanksystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.MyHolder> {

    private Context context;
    private List<ModelRequestOne> requestList;
    public AdapterRequest(Context context, List<ModelRequestOne> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public AdapterRequest.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_request,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRequest.MyHolder holder, int position) {
        String location_requ=requestList.get(position).getRequest_location();
        String phone=requestList.get(position).getRequest_phone();
        String date=requestList.get(position).getRequest_date();
        String group=requestList.get(position).getRequest_blood_group();
        String details=requestList.get(position).getRequest_need_details();

        holder.location_r.setText(" Location : "+location_requ);
        holder.phone.setText(" Phone : +88"+phone);
        holder.date.setText(" Date : "+date);
        holder.group.setText(" Blood Group : "+group);
        //holder.details.setText(details);
        holder.details.setText(" Need For : "+details);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView location_r,phone,date,group,details;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            location_r=itemView.findViewById(R.id.location_req);
            phone=itemView.findViewById(R.id.phone_req);
            date=itemView.findViewById(R.id.date_req);
            group=itemView.findViewById(R.id.group_req);
            details=itemView.findViewById(R.id.details_req);
        }
    }
}
