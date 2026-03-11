package com.example.projet.activity;

// MainActivity.java

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projet.R;
import com.example.projet.fragment.FirstFragment;
import com.example.projet.fragment.SecondFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerDL;
    private NavigationView navNV;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerDL = findViewById(R.id.drawer);
        navNV = findViewById(R.id.nav_view);
        fm = getSupportFragmentManager();

        toggle = new ActionBarDrawerToggle(
                this,
                drawerDL,
                R.string.open,
                R.string.close
        );

        drawerDL.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navNV.setNavigationItemSelectedListener(this);

        // Ouvre le premier fragment par défaut
        navNV.getMenu().performIdentifierAction(R.id.nav_first, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if (item.getItemId() == R.id.nav_first) {
            fragment = new FirstFragment();

        } else if (item.getItemId() == R.id.nav_second) {
            fragment = new SecondFragment();
        }

        if (fragment != null) {
            fm.beginTransaction()
                    .replace(R.id.contentFL, fragment)
                    .commit();
        }

        drawerDL.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}