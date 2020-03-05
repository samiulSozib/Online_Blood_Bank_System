package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    private TextView name,phone,bloodGroup,locaton,lastDonationDate,district;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Button update_button;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.profile_name);
        phone=findViewById(R.id.profile_phone);
        bloodGroup=findViewById(R.id.profile_blood_group);
        locaton=findViewById(R.id.profile_location);
        lastDonationDate=findViewById(R.id.profile_donation_date);
        district=findViewById(R.id.profile_district);
        update_button=findViewById(R.id.update_profile);
        progressDialog=new ProgressDialog(this);



        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String user_name=""+dataSnapshot1.child("nAME").getValue();
                    String user_phone=""+dataSnapshot1.child("phone").getValue();
                    String user_blood_group=""+dataSnapshot1.child("blood_group").getValue();
                    String user_locaton=""+dataSnapshot1.child("lOCATION").getValue();
                    String user_district=""+dataSnapshot1.child("dISTRICT").getValue();
                    String user_date=""+dataSnapshot1.child("donationDATE").getValue();


                    name.setText("Name : "+user_name);
                    phone.setText("Phone Number : "+user_phone);
                    bloodGroup.setText("Blood Group : "+user_blood_group);
                    locaton.setText("Location : "+user_locaton);
                    lastDonationDate.setText("Last Donation Date : "+user_date);
                    district.setText("District : "+user_district);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

    }

    private void showEditProfileDialog() {
        String option[]={"Edit Name","Edit Phone","Edit District","Edit Location","Edit Last Donation Date","Edit blood Group"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose Option");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    //Edit name
                    progressDialog.setMessage("Updating Name");
                    showNamePhoneOthersDialog("nAME");
                }else if (i==1){
                    //Edit phone
                    progressDialog.setMessage("Updating Phone");
                    showNamePhoneOthersDialog("phone");
                }else if (i==2){
                    //Edit District
                    progressDialog.setMessage("Updating District");
                    showNamePhoneOthersDialog("dISTRICT");
                }else if (i==3){
                    //Edit location
                    progressDialog.setMessage("Updating Location");
                    showNamePhoneOthersDialog("lOCATION");
                }else if (i==4){
                    //Edit last donation date
                    progressDialog.setMessage("Updating Donation Date");
                    showNamePhoneOthersDialog("donationDATE");
                }else if (i==5){
                    //Edit blood group
                    progressDialog.setMessage("Updating Blood Group");
                    showNamePhoneOthersDialog("blood_group");
                }
            }
        });
        builder.create().show();

    }

    private void showNamePhoneOthersDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        final EditText editText = new EditText(this);
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value=editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    progressDialog.show();
                    HashMap<String,Object> res=new HashMap<>();
                    res.put(key,value);



                    //databaseReference.child(user.getUid()).updateChildren(res)
                    databaseReference.child(user.getUid()).updateChildren(res)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Updated...",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter "+key,Toast.LENGTH_SHORT).show();

                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.dismiss();
            }
        });
        builder.create().show();


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        super.onBackPressed();
    }
}
