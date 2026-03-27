package com.example.projet.fragment;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projet.R;
import com.example.projet.entities.Resident;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private Resident resident;
    private EditText editNom, editPrenom, editEmail;
    private Button btnUpdate;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            userId = getArguments().getInt("user_id");
        }

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editNom = view.findViewById(R.id.editNom);
        editPrenom = view.findViewById(R.id.editPrenom);
        editEmail = view.findViewById(R.id.editEmail);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> updateProfile());

        loadResident(); // 👈 on appelle après init des views

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

    private void loadResident(){
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/residentById.php?id="+userId)
                .as(new TypeToken<Resident>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_USERS", "Erreur users", e);
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
}