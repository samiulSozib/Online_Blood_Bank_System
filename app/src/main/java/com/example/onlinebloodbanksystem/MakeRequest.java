package com.example.onlinebloodbanksystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MakeRequest extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;


    EditText req_location,req_phone,req_blood,req_date,req_need_dets;
    Button req_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        mAuth=FirebaseAuth.getInstance();

        /////

        req_location=findViewById(R.id.request_location);
        req_phone=findViewById(R.id.request_phone);
        req_blood=findViewById(R.id.request_group);
        req_date=findViewById(R.id.request_date);
        req_need_dets=findViewById(R.id.request_need_details);
        req_button=findViewById(R.id.request_make_button_id);


        user=mAuth.getCurrentUser();


        req_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location=req_location.getText().toString();
                String phone=req_phone.getText().toString();
                String blood=req_blood.getText().toString();
                String date=req_date.getText().toString();
                String need=req_need_dets.getText().toString();

                if (location.isEmpty() || phone.isEmpty() || blood.isEmpty() || date.isEmpty() || need.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter All fields....",Toast.LENGTH_SHORT).show();
                }
                else {

                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    //put data in database "User"
                    DatabaseReference reference=database.getReference("Request_Blood");


                    //String request_id=user.getUid();
                    String request_id=reference.push().getKey();
                    HashMap<Object, String> hashMap=new HashMap<>();
                    hashMap.put("request_id",request_id);
                    hashMap.put("request_location",location);
                    hashMap.put("request_phone",phone);
                    hashMap.put("request_blood_group",blood);
                    hashMap.put("request_date",date);
                    hashMap.put("request_need_details",need);


                    reference.child(request_id).setValue(hashMap);

                    Toast.makeText(getApplicationContext(),"Request Created....",Toast.LENGTH_SHORT).show();
                    req_location.setText("");
                    req_phone.setText("");
                    req_blood.setText("");
                    req_date.setText("");
                    req_need_dets.setText("");

                }

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
