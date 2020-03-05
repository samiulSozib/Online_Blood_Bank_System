package com.example.onlinebloodbanksystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder>{

    Context context;
    List<User> userList;

    public AdapterUser(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    @NonNull
    @Override
    public AdapterUser.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_view,parent,false);
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterUser.MyHolder holder, int position) {

        String userName=userList.get(position).getnAME();
        //String userEmail=userList.get(position).getEmail();
        String userPhone=userList.get(position).getPhone();
        String userLocation=userList.get(position).getlOCATION();
        String userDate=userList.get(position).getDonationDATE();
        String userGroup=userList.get(position).getBlood_group();

        holder.name.setText(" Name : "+userName);
        //holder.email.setText(" Email : "+userEmail);
        holder.phone.setText(" Phone : +88"+userPhone);
        holder.location.setText(" Location : "+userLocation);
        holder.date.setText(" Donation Date : "+userDate);
        holder.group.setText(" Blood Group : "+userGroup);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView name,email,phone,location,date,group;
        public MyHolder(@NonNull View itemView) {
            super(itemView);


            name=itemView.findViewById(R.id.name_user);
            //email=itemView.findViewById(R.id.email_user);
            phone=itemView.findViewById(R.id.phone_user);
            location=itemView.findViewById(R.id.location_user);
            date=itemView.findViewById(R.id.date_user);
            group=itemView.findViewById(R.id.group_user);
        }
    }
}
