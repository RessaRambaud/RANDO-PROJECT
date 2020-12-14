package com.RandoDam.rando.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.RandoDam.rando.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Hamburger_fragment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //For Fragments
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //Enabling Hamburger
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        //sinking the open and close drawer
        actionBarDrawerToggle.syncState();

        //to load default fragments
       fragmentManager= getSupportFragmentManager();
       fragmentTransaction=fragmentManager.beginTransaction();
       fragmentTransaction.add(R.id.container_fragment,new MainFragment_Acceuil());
       fragmentTransaction.commit();

   }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId()==R.id.Apropos)
        {
            fragmentManager= getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_Apropos());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Settings)
        {
            fragmentManager= getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_Settings());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Contact)
        {
            fragmentManager= getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_Contact());
            fragmentTransaction.commit();


        }

       if(menuItem.getItemId()==R.id.menu_Logout)
        {
        FirebaseAuth.getInstance().signOut();
        fragmentManager= getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment,new MainFragment_Acceuil());
        fragmentTransaction.commit();
        }
       if(menuItem.getItemId()==R.id.menu_Profile)
        {

            fragmentManager= getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_Profile());

            fragmentTransaction.commit();
        }
        if(menuItem.getItemId()==R.id.Acceuil)
        {
            fragmentManager= getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MainFragment_Acceuil());
            fragmentTransaction.commit();
        }

        return true;
    }


}