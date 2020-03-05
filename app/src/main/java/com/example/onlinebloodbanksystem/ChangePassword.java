package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePassword extends AppCompatActivity {

    EditText change_password;
    Button change;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        change_password=findViewById(R.id.change_password_edit_text);
        change=findViewById(R.id.change_password);
        progressDialog=new ProgressDialog(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                if (user!=null){
                    progressDialog.setMessage("Changing Password! Please wait....");
                    progressDialog.show();

                    final String value=change_password.getText().toString().trim();
                    //if (value.length()<6){
                    //  Toast.makeText(getApplicationContext(),"Please enter minimum 6 digit password",Toast.LENGTH_SHORT).show();
                    //}

                    user.updatePassword(value)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"password successfully changed",Toast.LENGTH_SHORT).show();

                                        HashMap<String,Object> result=new HashMap<>();
                                        result.put("password",value);

                                        databaseReference.child(user.getUid()).updateChildren(result)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });

                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"password does not changed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                change_password.setText("");

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        super.onBackPressed();
    }
}
