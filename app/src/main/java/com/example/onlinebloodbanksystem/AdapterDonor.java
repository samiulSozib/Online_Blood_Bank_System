package com.example.onlinebloodbanksystem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterDonor extends ArrayAdapter<Donor1> {
    private Activity context;
    private List<Donor1> donorList;

    public AdapterDonor(Activity context, List<Donor1> donorList) {
        super(context, R.layout.donor_sample, donorList);
        this.context = context;
        this.donorList = donorList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater= context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.donor_sample,null,false);

        Donor1 donor=donorList.get(position);
        TextView name=view.findViewById(R.id.donor_name_text_view_id);
        TextView group=view.findViewById(R.id.donor_blood_text_view_id);
        TextView phone=view.findViewById(R.id.donor_phone_view_id);
        TextView location=view.findViewById(R.id.donor_location_text_view_id);

        name.setText(" Name : "+donor.getDonorName());
        group.setText(" Blood group : "+donor.getDonorGroup());
        phone.setText(" Phone : +88"+donor.getDonorPhone());
        location.setText(" Location : "+donor.getDonorLocation());


        return view;
    }
}
