package com.example.andr_advanced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Home_fragment fragment = new Home_fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frg_home,fragment)
                .commit();

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

    }

    private Toolbar initToolbar(){
        return findViewById(R.id.toolbar);
    }
    private void initDrawer(Toolbar toolbar){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

       drawer.addDrawerListener(toggle);
       toggle.syncState();
       navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}