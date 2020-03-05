package com.example.onlinebloodbanksystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private EditText editText;
    private Button adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editText = findViewById(R.id.admin_code_id);
        adminButton = findViewById(R.id.admin_code_button);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=editText.getText().toString().trim();
                if (value.equals("samiulcse2018@gmail.com")){
                    startActivity(new Intent(AdminActivity.this,AddDonorActivity.class));
                    finish();
                }else if (!value.equals("samiulcse2018@gmail.com")){
                    Toast.makeText(getApplicationContext(),"Enter correct code",Toast.LENGTH_LONG).show();
                }
                editText.setText("");

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
