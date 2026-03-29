package com.example.projet.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projet.R;
import com.example.projet.adapters.ResidentAdapter;
import com.example.projet.entities.Appliance;
import com.example.projet.entities.Resident;
import com.example.projet.entities.Habitat;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class HabitatsFragment extends Fragment {

    private ListView listView;
    private ArrayList<Resident> residentList = new ArrayList<>();
    private ArrayList<Habitat> habitatList = new ArrayList<>();
    private ArrayList<Appliance> appliancesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_habitats, container, false);
        listView = view.findViewById(R.id.list_users);

        loadUsers();

        return view;
    }

    private void loadUsers() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/resident.php")
                .as(new TypeToken<ArrayList<Resident>>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_USERS", "Erreur users", e);
                        return;
                    }

                    residentList = result;
                    loadHabitats();
                });
    }

    private void loadHabitats() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/habitat.php")
                .as(new TypeToken<ArrayList<Habitat>>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_HABITAT", "Erreur habitat", e);
                        return;
                    }

                    habitatList = result;
                    loadAppliances();
                    linkUsersWithHabitats();
                });
    }

    private void loadAppliances() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/appliance.php")
                .as(new TypeToken<ArrayList<Appliance>>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_APPLIANCE", "Erreur appliance", e);
                        return;
                    }

                    appliancesList = result;
                    linkHabitatsWithAppliances();
                });
    }

    private void linkUsersWithHabitats() {
        for (Resident resident : residentList) {
            for (Habitat habitat : habitatList) {
                if (habitat.getIdUser() == resident.getId()) {
                    resident.setHabitat(habitat);
                }
            }
        }
    }

    private void linkHabitatsWithAppliances() {

        for (Habitat habitat : habitatList) {
            List<Appliance> appliances = new ArrayList<>();

            for (Appliance appliance : appliancesList) {
                if (appliance.getIdHabitat() == habitat.getId()) {
                    appliances.add(appliance);
                }
            }

            habitat.setAppliances(appliances);
        }

        ResidentAdapter adapter =
                new ResidentAdapter(getContext(),
                        R.layout.item,
                        residentList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Resident resident = residentList.get(position);

            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_resident, null);

            TextView tvName = dialogView.findViewById(R.id.tvName);
            TextView tvFloor = dialogView.findViewById(R.id.tvFloor);
            TextView tvAppliances = dialogView.findViewById(R.id.tvAppliances);
            TextView tvTotal = dialogView.findViewById(R.id.tvTotal);

            tvName.setText(resident.getFirstname() + " " + resident.getLastname());

            Habitat habitat = resident.getHabitat();

            StringBuilder appliancesText = new StringBuilder();
            int totalConso = 0;

            if (habitat != null) {

                tvFloor.setText("Étage : " + habitat.getFloor());

                if (habitat.getAppliances() != null) {

                    for (Appliance appliance : habitat.getAppliances()) {
                        appliancesText.append("• ")
                                .append(appliance.getName())
                                .append(" : ")
                                .append(appliance.getWattage())
                                .append(" W\n");

                        totalConso += appliance.getWattage();
                    }

                } else {
                    appliancesText.append("Aucun appareil");
                }

            } else {
                tvFloor.setText("Étage : -");
                appliancesText.append("Aucun appareil");
            }

            tvAppliances.setText(appliancesText.toString());
            tvTotal.setText("Total : " + totalConso + " W");

            new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .setPositiveButton("Fermer", null)
                    .show();
        });
    }
}