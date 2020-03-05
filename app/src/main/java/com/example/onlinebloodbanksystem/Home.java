package com.example.onlinebloodbanksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    private Button all_user,donor,all_request,make_request,profile;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /////////
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////
        setContentView(R.layout.activity_home);

        all_user=findViewById(R.id.all_user_id);
        donor=findViewById(R.id.donor_id);
        all_request=findViewById(R.id.all_request_id);
        make_request=findViewById(R.id.make_request_id);
        profile=findViewById(R.id.profile_id);




        all_user.setOnClickListener(this);
        donor.setOnClickListener(this);
        all_request.setOnClickListener(this);
        make_request.setOnClickListener(this);
        profile.setOnClickListener(this);



        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.all_user_id:
                startActivity(new Intent(getApplicationContext(), AllUser.class));
                finish();
                break;
            case R.id.donor_id:
                startActivity(new Intent(getApplicationContext(), Donor.class));
                finish();
                break;
            case R.id.all_request_id:
                startActivity(new Intent(getApplicationContext(), AllRequest.class));
                finish();
                break;
            case R.id.make_request_id:
                startActivity(new Intent(getApplicationContext(), MakeRequest.class));
                finish();
                break;
            case R.id.profile_id:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();
                break;
            case R.id.nav_log_out:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;


            default:
                break;

        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_main,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_admin: startActivity(new Intent(getApplicationContext(),AdminActivity.class)); finish(); break;
                case R.id.action_logout: startActivity(new Intent(getApplicationContext(),LoginActivity.class)); finish(); break;

                default: break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int item=menuItem.getItemId();

            if (item==R.id.nav_log_out){
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }


            else if (item==R.id.nav_change_password){
                startActivity(new Intent(getApplicationContext(),ChangePassword.class));
                finish();
            }
            else if (item==R.id.nav_deactive_account){
                startActivity(new Intent(getApplicationContext(),DeactiveAccount.class));
                finish();
            }



            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
}
