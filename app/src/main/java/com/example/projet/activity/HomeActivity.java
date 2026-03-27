package com.example.projet.activity;

// MainActivity.java

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projet.R;
import com.example.projet.entities.Resident;
import com.example.projet.fragment.FirstFragment;
import com.example.projet.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.koushikdutta.ion.Ion;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerDL;
    private NavigationView navNV;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fm;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userId = getIntent().getIntExtra("user_id", -1); // ✅ récupéré ici

        loadResidentHeader();

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
            ProfileFragment profileFragment = new ProfileFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("user_id", userId);

            profileFragment.setArguments(bundle);

            fragment = profileFragment;
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Déconnexion")
                    .setMessage("Voulez-vous vraiment vous déconnecter ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        finish();
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
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

    private void loadResidentHeader() {

        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/residentById.php?id=" + userId)
                .as(Resident.class)
                .setCallback((e, result) -> {

                    if (e != null || result == null) return;

                    View headerView = navNV.getHeaderView(0);
                    TextView tvEmail = headerView.findViewById(R.id.tvEmail);

                    tvEmail.setText(result.getEmail()); // ✅ ici magie
                });
    }
}