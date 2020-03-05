package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText user_name,user_email,user_password,user_password_conf,user_phone_number,user_location,donation_date;

    Button register_button;
    Spinner district_spinner,spinner_blood_group;
    ProgressDialog progressDialog;

    String[] blood_group={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    String[] zilla={"Magura","Dhaka","Jashore","Barguna",
            "Barishal","Bhola","Jhalokati","Patuakhali","Pirojpur",
            "Bandarban","Brahmanbaria","Chadpur","Chattogram",
            "Cumilla","Cox's Bazar","Feni","Khagrachhari",
            "Lakshmipur","Noakhali","Rangamati","Faridpur",
            "Gazipir","Gopalganj","Kishoregonj","Madaripur"
            ,"Manikgonj","Munshigonj","Narayangonj","Narshingdi",
            "Rajbari","Shariatpur","Tangail","Bagerhat","Chuadanga",
            "Jhenaidah","Khulna","Kushtia","Meherpur","Narail","Satkhira"
            ,"Jamalpur","Mymensingh","Netrokona","Sherpur","Bogura","Joypurhat",
            "Naogaon","Nator","Chapainawabganj","Pabna","Rajshahi","Shirajganj",
            "Dianjpur","Gaibandha","Kurigram","Lalmonirhat","Nilphamari","Panchagarh",
            "Rangpur","Thakurgaon","Habiganj","Moulvibazar","Sunamganj","Sylhet"

    };

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");


        user_name=findViewById(R.id.reg_name);
        user_email=findViewById(R.id.reg_email);
        user_password=findViewById(R.id.reg_password);
        user_password_conf=findViewById(R.id.reg_password2);
        user_phone_number=findViewById(R.id.reg_phone);
        user_location=findViewById(R.id.reg_location);
        donation_date=findViewById(R.id.reg_last_donation);

        register_button=findViewById(R.id.register_id);

        spinner_blood_group=findViewById(R.id.reg_blood_group);
        district_spinner=findViewById(R.id.reg_district);

        progressDialog=new ProgressDialog(this);

        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,blood_group);
        spinner_blood_group.setAdapter(adapter);

        final ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,zilla);
        district_spinner.setAdapter(adapter1);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = user_name.getText().toString();
                final String email=user_email.getText().toString();
                final String password=user_password.getText().toString();
                final String password2=user_password_conf.getText().toString();
                final String phoneNumber=user_phone_number.getText().toString();
                final String location=user_location.getText().toString();
                final String donationDate=donation_date.getText().toString();
                final String group=spinner_blood_group.getSelectedItem().toString();
                final String district=district_spinner.getSelectedItem().toString();


                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty() || phoneNumber.isEmpty() || location.isEmpty() || !password.equals(password2)){
                    Toast.makeText(MainActivity.this,"Please Enter All Fields...",Toast.LENGTH_SHORT).show();


                }else if (phoneNumber.length()>11 || phoneNumber.length()<11){
                    Toast.makeText(getApplicationContext(),"Please enter your 11 digit phone number",Toast.LENGTH_SHORT).show();
                }else if (password.length()<6 ){
                    Toast.makeText(getApplicationContext(),"Please enter minimum 6 digit password",Toast.LENGTH_SHORT).show();
                }
                else {
                    //key=databaseReference.push().getKey();

                    //createAccount(key,name,email,password,password2,phoneNumber,location,donationDate,group,district);
                    createAccount(email,password);


                    //User user=new User(key,name,email,password,phoneNumber,location,donationDate,group,district);
                    //databaseReference.child(key).setValue(user);


                }
            }
        });
    }

    private void createAccount(final String email, final String password) {
        progressDialog.setMessage("Please wait......");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser mUser=mAuth.getCurrentUser();

                            String uid=mUser.getUid();
                            String email=mUser.getEmail();
                            String password=user_password.getText().toString().trim();
                            String phone=user_phone_number.getText().toString().trim();
                            String nAME=user_name.getText().toString().trim();
                            String lOCATION=user_location.getText().toString();
                            final String donationDATE=donation_date.getText().toString();
                            final String blood_group=spinner_blood_group.getSelectedItem().toString();
                            final String dISTRICT=district_spinner.getSelectedItem().toString();


                            User user=new User(uid,email,password,phone,nAME,lOCATION,donationDATE,blood_group,dISTRICT);
                            databaseReference.child(uid).setValue(user);

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Account create successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),VerifyAccount.class));
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Account Created Failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
        super.onBackPressed();
    }
}
