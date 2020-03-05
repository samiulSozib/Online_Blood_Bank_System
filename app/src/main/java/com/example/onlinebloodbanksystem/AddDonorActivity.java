package com.example.onlinebloodbanksystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDonorActivity extends AppCompatActivity {
    private EditText donor_name,donor_blood_group,donor_phone,donor_location;
    private Button add_donor;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        donor_name=findViewById(R.id.donor_name);
        donor_blood_group=findViewById(R.id.donor_blood_group);
        donor_phone=findViewById(R.id.donor_phone_number);
        donor_location=findViewById(R.id.donor_location);

        add_donor=findViewById(R.id.add_donor);


        databaseReference= FirebaseDatabase.getInstance().getReference("Donor");

        add_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (donor_name.getText().toString().isEmpty() || donor_blood_group.getText().toString().isEmpty() || donor_phone.getText().toString().isEmpty() || donor_location.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter all field",Toast.LENGTH_SHORT).show();
                }else {
                    save_donor();
                }

            }
        });
    }
    private void save_donor() {
        String donorName=donor_name.getText().toString().trim();
        String donorGroup=donor_blood_group.getText().toString().trim();
        String donorPhone=donor_phone.getText().toString().trim();
        String donorLocation=donor_location.getText().toString().trim();



        String key=databaseReference.push().getKey();

        Donor1 donor=new Donor1(donorName,donorGroup,donorPhone,donorLocation);

        databaseReference.child(key).setValue(donor);
        Toast.makeText(getApplicationContext(),"Donor Add Successfully",Toast.LENGTH_LONG).show();
        donor_name.setText("");
        donor_blood_group.setText("");
        donor_phone.setText("");
        donor_location.setText("");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
        super.onBackPressed();
    }
}
