package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyAccount extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Boolean verified;
    String msg;
    TextView textView;
    Button verify_button,nxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        textView=findViewById(R.id.email_id);
        nxt=findViewById(R.id.next_activity);

        verified=mUser.isEmailVerified();

        msg="Email is not verify";

        textView.setText(mUser.getEmail());

        verify_button=findViewById(R.id.verify_email_button_id);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        nxt.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Email sent, Please check",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                nxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUser.reload()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            if (mUser.isEmailVerified()){
                                                startActivity(new Intent(getApplicationContext(), Home.class));
                                                finish();
                                            }
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }
}
