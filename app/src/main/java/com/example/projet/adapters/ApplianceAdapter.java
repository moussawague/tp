package com.example.projet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.R;
import com.example.projet.entities.Appliance;

import java.util.List;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.ViewHolder> {

    private final List<Appliance> appliances;

    public ApplianceAdapter(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_appliances, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Appliance appliance = appliances.get(position);

        if (appliance != null) {
            holder.icon.setImageResource(
                    getIconForAppliance(appliance.getName())
            );
        }
    }

    @Override
    public int getItemCount() {
        return appliances != null ? appliances.size() : 0;
    }

    // 🔥 MÉTHODE AVEC TON SWITCH
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}