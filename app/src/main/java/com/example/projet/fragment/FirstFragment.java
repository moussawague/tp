package com.example.projet.fragment;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.projet.R;
import com.example.projet.ResidentAdapter;
import com.example.projet.entities.Appliance;
import com.example.projet.entities.Resident;
import com.example.projet.entities.Habitat;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private ListView listView;
    private ArrayList<Resident> residentList = new ArrayList<>();
    private ArrayList<Habitat> habitatList = new ArrayList<>();
    private ArrayList<Appliance> appliancesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
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
                    Log.d("ION_USERS", "Users chargés : " + residentList.size());

                    loadHabitats(); // on charge les habitats après
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
                    Log.d("ION_HABITAT", "Habitats chargés : " + habitatList.size());

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
                        Log.e("ION_HABITAT", "Erreur habitat", e);
                        return;
                    }

                    appliancesList = result;
                    Log.d("ION_HABITAT", "Habitats chargés : " + appliancesList.size());

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

        Log.d("ION_FINAL", "Association terminée");
    }

    private void linkHabitatsWithAppliances() {

        for (Habitat habitat : habitatList) {
            List<Appliance> appliances = new ArrayList<Appliance>();
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

        Log.d("ION_FINAL", "Association terminée");
    }
}