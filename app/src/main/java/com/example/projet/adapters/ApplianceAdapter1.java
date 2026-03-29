package com.example.projet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.R;
import com.example.projet.entities.Appliance;

import java.util.List;

public class ApplianceAdapter1 extends RecyclerView.Adapter<ApplianceAdapter1.ViewHolder> {

    private final List<Appliance> appliances;

    public ApplianceAdapter1(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Liaison avec le layout XML du design amélioré
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appliance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appliance appliance = appliances.get(position);

        if (appliance != null) {
            // Affichage du nom et de la puissance
            holder.tvName.setText(appliance.getName());
            holder.tvWattage.setText(appliance.getWattage() + " W");

            // Gestion de l'image via ton switch
            holder.icon.setImageResource(getIconForAppliance(appliance.getName()));
        }
    }

    @Override
    public int getItemCount() {
        return appliances != null ? appliances.size() : 0;
    }

    // Ta logique de sélection d'icônes
    private int getIconForAppliance(String name) {
        if (name == null) return R.drawable.ic_fer_a_repasser;

        switch (name) {
            case "Climatiseur":
                return R.drawable.ic_climatiseur;
            case "Aspirateur":
                return R.drawable.ic_aspirateur;
            case "Machine à laver":
                return R.drawable.ic_machine;
            default:
                return R.drawable.ic_fer_a_repasser;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView tvName, tvWattage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // On récupère les IDs du fichier item_appliance.xml
            icon = itemView.findViewById(R.id.imgApplianceIcon);
            tvName = itemView.findViewById(R.id.tvApplianceName);
            tvWattage = itemView.findViewById(R.id.tvApplianceWattage);
        }
    }
}