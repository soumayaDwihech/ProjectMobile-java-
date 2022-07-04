package com.example.camunda.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.camunda.Data.DataAdapter;
import com.example.camunda.Domain.Profile;
import com.example.camunda.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Button ButtonLogin;
    public Intent HomeIntent;
    public String USERNAME;
    public  String PASSWORD;
    String UrlProfileInfo;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    private DataAdapter profileAdapter;
    private Profile profilelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        HomeIntent=new Intent(this, HomeActivity.class); //sena3 intent jedida bech tehezou mil login lil home

        ButtonLogin=findViewById(R.id.LoginButton);

        ButtonLogin.setOnClickListener(new Button.OnClickListener()  {
            @Override
            public void onClick(View view) {
                USERNAME=((TextView)(findViewById(R.id.Email))).getText().toString();
                PASSWORD=((TextView)(findViewById(R.id.Password))).getText().toString();
                if(USERNAME.length()!=0 && PASSWORD.length()!=0)
                {
                    try {
                        String result;
                        UrlProfileInfo="http://digitalisi.tn:8080/engine-rest/user/"+USERNAME+"/profile";
                        profileAdapter=new DataAdapter();
                        System.out.println("Connexion : "+USERNAME);
                        System.out.println("url Connexion : "+UrlProfileInfo);
                        result = profileAdapter.getData(UrlProfileInfo,USERNAME,PASSWORD);
                        Gson g = new Gson(); //TRAITEMENT FILE JSON



                        if(!result.equals("No Data"))
                        {
                            profilelog = g.fromJson(result, Profile.class);
                            System.out.println("profile from login :"+ profilelog);
                            HomeIntent.putExtra("ProfileLog", profilelog);
                            HomeIntent.putExtra("username", USERNAME);
                            HomeIntent.putExtra("password", PASSWORD);
                            startActivity(HomeIntent);
                        }
                        else {
                            // Username or password false, display and an error
                            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());

                            dlgAlert.setMessage("Verify password or username");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();

                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());

                    dlgAlert.setMessage("Username And Password Empty!");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }


            }

            Fragment temp;
            Bundle bundle = new Bundle();

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_processes:
                        temp = new processesfragment(profilelog);
                        bundle.putString("username", getIntent().getStringExtra("username"));
                        bundle.putString("password", getIntent().getStringExtra("password"));
                        temp.setArguments(bundle);
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

}
