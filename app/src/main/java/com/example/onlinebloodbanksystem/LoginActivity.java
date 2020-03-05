package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener{

    private Button new_registration,login_button,help_button,forget_button;
    EditText login_email_edit,login_password_edit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    CheckBox checkBox;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME="pref";
    private static final String KEY_REMEMBER="remember";
    private static final String KEY_USERNAME="user_name";
    private static final String KEY_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////

        setContentView(R.layout.activity_login);

        new_registration=findViewById(R.id.login_register_id);
        login_button=findViewById(R.id.login_button_id);
        help_button=findViewById(R.id.help_id);
        forget_button=findViewById(R.id.forget_password_id);

        login_email_edit=findViewById(R.id.login_email_id);
        login_password_edit=findViewById(R.id.login_password_id);

        checkBox=findViewById(R.id.checkbox_id);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

/////////////////////////////
        sharedPreferences=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getBoolean(KEY_REMEMBER,false)){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        login_email_edit.setText(sharedPreferences.getString(KEY_USERNAME,""));
        login_password_edit.setText(sharedPreferences.getString(KEY_PASSWORD,""));

        login_email_edit.addTextChangedListener(this);
        login_password_edit.addTextChangedListener(this);
        checkBox.setOnCheckedChangeListener(this);

        /////////////////////////////

        new_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=login_email_edit.getText().toString().trim();
                String password=login_password_edit.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
                }
                else if (mAuth!=null){
                    //sign in method

                    progressDialog.setMessage("Logging in....");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        ////////////////code for late
                                        if (mUser!=null && mUser.isEmailVerified()){
                                            startActivity(new Intent(getApplicationContext(), Home.class));
                                            finish();
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"you don not verify your email",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),VerifyAccount.class));
                                            finish();
                                        }

                                    }else {
                                        progressDialog.dismiss();
                                        //Toast.makeText(getApplicationContext(),"Please register first",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

//////////////////////////forget button method
        forget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_for_reset=login_email_edit.getText().toString().trim();

                //if (email_for_reset.isEmpty()){
                //  Toast.makeText(getApplicationContext(),"Enter your registered email address",Toast.LENGTH_SHORT).show();
                //}
                if (TextUtils.isEmpty(email_for_reset)){
                    Toast.makeText(getApplicationContext(),"Enter your registered email address",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Progress running....");
                progressDialog.show();

                mAuth.sendPasswordResetEmail(email_for_reset)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"We have sent instruction in your email, please check",Toast.LENGTH_SHORT).show();

                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Failed to sent email",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });



            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }
    public void managePrefs(){
        if (checkBox.isChecked()){
            editor.putString(KEY_USERNAME,login_email_edit.getText().toString().trim());
            editor.putString(KEY_PASSWORD,login_password_edit.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER,true);
            editor.apply();
        }else {
            editor.putBoolean(KEY_REMEMBER,false);
            editor.remove(KEY_USERNAME);
            editor.remove(KEY_PASSWORD);
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.alert);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage("Do you want to exit?");

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();

        //super.onBackPressed();
    }
}
