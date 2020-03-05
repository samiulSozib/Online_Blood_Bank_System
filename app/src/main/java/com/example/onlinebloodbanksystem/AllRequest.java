package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllRequest extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterRequest adapterRequest;
    List<ModelRequestOne> requestList;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_request);


        mAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.request_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllRequest.this));
        requestList=new ArrayList<>();

        getAllRequest();
    }
    private void getAllRequest() {

        final FirebaseUser firebaseUser=mAuth.getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Request_Blood");
        //DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Request_Blood");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //requestList.clear();


                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    ModelRequestOne modelRequest = ds.getValue(ModelRequestOne.class);
                    requestList.add(modelRequest);

                    adapterRequest=new AdapterRequest(AllRequest.this,requestList);
                    recyclerView.setAdapter(adapterRequest);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
        super.onBackPressed();
    }
}
