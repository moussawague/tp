package com.example.projet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.projet.R;
import com.example.projet.activity.MainActivity;
import com.example.projet.entities.Resident;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

public class ProfileFragment extends Fragment {

    private Resident resident;
    private EditText editNom, editPrenom, editEmail;
    private Button btnUpdate, btnDeco;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 🔹 Récup userId
        if (getArguments() != null) {
            userId = getArguments().getInt("user_id", -1);
        }

        // 🔹 Init views
        editNom = view.findViewById(R.id.editNom);
        editPrenom = view.findViewById(R.id.editPrenom);
        editEmail = view.findViewById(R.id.editEmail);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDeco = view.findViewById(R.id.btnDeco);

        // 🔹 Click update
        btnUpdate.setOnClickListener(v -> updateProfile());

        // 🔹 Click déconnexion
        btnDeco.setOnClickListener(v -> deconnection());

        // 🔹 Charger données
        loadResident();

        return view;
    }

    private void updateProfile() {

        String nom = editNom.getText().toString();
        String prenom = editPrenom.getText().toString();
        String email = editEmail.getText().toString();

        Ion.with(getContext())
                .load("POST", "http://192.168.1.118/powerhome_server/updateProfile.php")
                .setBodyParameter("id", String.valueOf(userId))
                .setBodyParameter("firstname", prenom)
                .setBodyParameter("lastname", nom)
                .setBodyParameter("email", email)
                .asString()
                .setCallback((e, result) -> {

                    if (e != null) {
                        Toast.makeText(getContext(), "Erreur", Toast.LENGTH_SHORT).show();
                        Log.e("ION_UPDATE", "Erreur", e);
                        return;
                    }

                    Log.d("ION_UPDATE", "Réponse : " + result);
                    Toast.makeText(getContext(), "Profil mis à jour", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadResident() {

        if (userId == -1) {
            Log.e("PROFILE", "userId invalide");
            return;
        }

        Ion.with(getContext())
                .load("http://192.168.1.118/powerhome_server/residentById.php?id=" + userId)
                .as(new TypeToken<Resident>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_USERS", "Erreur", e);
                        return;
                    }

                    resident = result;

                    if (resident != null) {
                        editNom.setText(resident.getLastname());
                        editPrenom.setText(resident.getFirstname());
                        editEmail.setText(resident.getEmail());
                    }

                    Log.d("ION_USERS", "Resident chargé");
                });
    }

    private void deconnection() {

        new AlertDialog.Builder(getContext())
                .setTitle("Déconnexion")
                .setMessage("Voulez-vous vraiment vous déconnecter ?")
                .setPositiveButton("Oui", (dialog, which) -> {

                    // 🔥 Retour login (recommandé)
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                    // fermer l'activité actuelle
                    getActivity().finish();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}