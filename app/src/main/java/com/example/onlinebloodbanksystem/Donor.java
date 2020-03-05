package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Donor extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<Donor1> donorList;
    AdapterDonor adapterDonor;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);


        searchView=findViewById(R.id.search_view);


        databaseReference= FirebaseDatabase.getInstance().getReference("Donor");
        donorList=new ArrayList<>();

        adapterDonor=new AdapterDonor(Donor.this,donorList);



        listView=findViewById(R.id.donor_list_id);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!TextUtils.isEmpty(query.trim())){
                    searchDonor(query);
                }else {

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (!TextUtils.isEmpty(query.trim())){
                    searchDonor(query);
                }else {

                }
                return false;
            }
        });
    }


    private void searchDonor(final String string) {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Donor1 donor1=ds.getValue(Donor1.class);

                    if (donor1.getDonorGroup().toLowerCase().contains(string.toLowerCase())){
                        donorList.add(donor1);
                    }
                    adapterDonor=new AdapterDonor(Donor.this,donorList);
                    adapterDonor.notifyDataSetChanged();
                    listView.setAdapter(adapterDonor);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Donor1 donor=dataSnapshot1.getValue(Donor1.class);
                    donorList.add(donor);
                }

                listView.setAdapter(adapterDonor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
        super.onBackPressed();
    }
}
