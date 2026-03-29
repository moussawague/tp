package com.example.projet.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton; // Ajouté pour le bouton
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projet.R;
import com.example.projet.entities.Resident;
import com.example.projet.fragment.CalendrierFragment;
import com.example.projet.fragment.HabitatsFragment;
import com.example.projet.fragment.MonHabitatFragment;
import com.example.projet.fragment.NotificationFragment;
import com.example.projet.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.koushikdutta.ion.Ion;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerDL;
    private NavigationView navNV;
    private FragmentManager fm;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. SUPPRIMER LA BARRE DU HAUT (ACTIONBAR)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        userId = getIntent().getIntExtra("user_id", -1);

        loadResidentHeader();

        drawerDL = findViewById(R.id.drawer);
        navNV = findViewById(R.id.nav_view);
        fm = getSupportFragmentManager();

        // 2. CONFIGURER LE BOUTON DE MENU PERSONNALISÉ
        // Assure-toi que l'ID dans ton XML est bien "btnOpenDrawer"
        ImageButton btnOpenDrawer = findViewById(R.id.btnOpenDrawer);
        btnOpenDrawer.setOnClickListener(v -> {
            // Ouvre le menu latéral manuellement
            drawerDL.openDrawer(GravityCompat.START);
        });

        navNV.setNavigationItemSelectedListener(this);

        // Ouvre le premier fragment par défaut
        navNV.getMenu().performIdentifierAction(R.id.nav_first, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.nav_first) {
            fragment = new HabitatsFragment();
        } else if (item.getItemId() == R.id.nav_second) {
            MonHabitatFragment monHabitatFragment = new MonHabitatFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", userId);
            monHabitatFragment.setArguments(bundle);
            fragment = monHabitatFragment;
        } else if (item.getItemId() == R.id.nav_fifth) {
            afficherAPropos();
        } else if (item.getItemId() == R.id.nav_sixth) {
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", userId);
            profileFragment.setArguments(bundle);
            fragment = profileFragment;
        } else if (item.getItemId() == R.id.nav_third) {
            CalendrierFragment calendarFragment = new CalendrierFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", userId);
            calendarFragment.setArguments(bundle);
            fragment = calendarFragment;
        }
        else if (item.getItemId() == R.id.nav_fourth) {
            NotificationFragment notificationFragment = new NotificationFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", userId);
            notificationFragment.setArguments(bundle);
            fragment = notificationFragment;
        }

        if (fragment != null) {
            fm.beginTransaction()
                    .replace(R.id.contentFL, fragment)
                    .commit();
        }

        drawerDL.closeDrawer(GravityCompat.START);
        return true;
    }

    private void afficherAPropos() {
        String message = "Version 1.0<br><br>" +
                "Développé par Moussa et Ilan<br><br>" +
                "GitHub : <a href='https://github.com/moussawague/tp.git'>Lien du projet</a><br><br>" +
                "Merci pour votre utilisation";

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("PowerHome")
                .setMessage(android.text.Html.fromHtml(message))
                .setPositiveButton("OK", null)
                .create();
        dialog.show();

        ((TextView) dialog.findViewById(android.R.id.message))
                .setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    private void loadResidentHeader() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/residentById.php?id=" + userId)
                .as(Resident.class)
                .setCallback((e, result) -> {
                    if (e != null || result == null) return;
                    View headerView = navNV.getHeaderView(0);
                    TextView tvEmail = headerView.findViewById(R.id.tvEmail);
                    tvEmail.setText(result.getEmail());
                });
    }
}