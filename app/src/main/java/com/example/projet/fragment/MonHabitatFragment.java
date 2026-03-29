package com.example.projet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.adapters.ApplianceAdapter1;
import com.example.projet.R;
import com.example.projet.entities.Appliance;
import com.example.projet.entities.Habitat;
import com.example.projet.entities.Resident;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MonHabitatFragment extends Fragment {

    private TextView txtFloor, txtArea, txtConsumption;
    private RecyclerView recyclerAppliances;

    private ApplianceAdapter1 applianceAdapter;
    private List<Appliance> applianceList = new ArrayList<>();

    private List<Resident> allResidents = new ArrayList<>();
    private List<Habitat> allHabitats = new ArrayList<>();
    private List<Appliance> allAppliances = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mon_habitat, container, false);

        txtFloor = view.findViewById(R.id.txtFloor);
        txtArea = view.findViewById(R.id.txtArea);
        txtConsumption = view.findViewById(R.id.txtConsumption);
        recyclerAppliances = view.findViewById(R.id.recyclerAppliances);

        recyclerAppliances.setLayoutManager(new LinearLayoutManager(getContext()));
        applianceAdapter = new ApplianceAdapter1(applianceList);
        recyclerAppliances.setAdapter(applianceAdapter);

        loadResidents();

        return view;
    }

    private void loadResidents() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/resident.php")
                .as(new TypeToken<ArrayList<Resident>>() {})
                .setCallback((e, result) -> {
                    if (e != null) return;
                    allResidents = result;
                    loadHabitats();
                });
    }

    // 2. Charger tous les habitats
    private void loadHabitats() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/habitat.php")
                .as(new TypeToken<ArrayList<Habitat>>() {})
                .setCallback((e, result) -> {
                    if (e != null) return;
                    allHabitats = result;
                    loadAppliances();
                });
    }

    private void loadAppliances() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/appliance.php")
                .as(new TypeToken<ArrayList<Appliance>>() {})
                .setCallback((e, result) -> {
                    if (e != null) return;
                    allAppliances = result;

                    processData();
                });
    }

    private void processData() {
        int myUserId = 1; // L'ID de l'utilisateur connecté (Moussa)
        Resident myResident = null;

        for (Resident r : allResidents) {
            for (Habitat h : allHabitats) {
                if (h.getIdUser() == r.getId()) {
                    r.setHabitat(h);
                }
            }
            if (r.getId() == myUserId) {
                myResident = r;
            }
        }

        for (Habitat h : allHabitats) {
            List<Appliance> appliancesForThisHabitat = new ArrayList<>();
            for (Appliance a : allAppliances) {
                if (a.getIdHabitat() == h.getId()) {
                    appliancesForThisHabitat.add(a);
                }
            }
            h.setAppliances(appliancesForThisHabitat);
        }

        if (myResident != null && myResident.getHabitat() != null) {
            Habitat myHabitat = myResident.getHabitat();

            txtFloor.setText("Étage : " + myHabitat.getFloor());
            txtArea.setText("Surface : " +myHabitat.getArea()+" m²");

            if (myHabitat.getAppliances() != null) {
                applianceList.clear();
                applianceList.addAll(myHabitat.getAppliances());
                applianceAdapter.notifyDataSetChanged();

                // Calcul de la consommation totale
                int totalWatts = 0;
                for (Appliance a : myHabitat.getAppliances()) {
                    totalWatts += a.getWattage();
                }
                txtConsumption.setText(totalWatts + " W");
            }
        }
    }
}