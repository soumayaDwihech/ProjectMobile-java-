package com.example.camunda.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.camunda.Domain.Profile;
import com.example.camunda.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
{
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    public Intent LoginIntent;
    private Profile profilelog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profilelog=(Profile) getIntent().getSerializableExtra("ProfileLog");
        System.out.println("profile from home :"+ profilelog);

        LoginIntent=new Intent(this, LoginActivity.class);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        processesfragment fragment = new processesfragment(profilelog);
        Bundle bundle=new Bundle();
        bundle.putString("username",getIntent().getStringExtra("username"));
        bundle.putString("password",getIntent().getStringExtra("password"));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        nav.setCheckedItem(R.id.menu_home);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment temp;
            Bundle bundle=new Bundle();

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :
                        temp=new processesfragment(profilelog);

                        bundle.putString("username",getIntent().getStringExtra("username"));
                        bundle.putString("password",getIntent().getStringExtra("password"));
                        temp.setArguments(bundle);
                        break;
                    case R.id.menu_processes :

                        temp=new tasksfragment();
                        bundle.putString("username",getIntent().getStringExtra("username"));
                        bundle.putString("password",getIntent().getStringExtra("password"));
                        temp.setArguments(bundle);
                        break;

                    case R.id.menu_profile :
                        temp=new profilefragment(profilelog);
                        break;
                    case R.id.menu_logout :
                        startActivity(LoginIntent);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


}
