package com.example.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.entities.Resident;
import com.example.projet.entities.Habitat;

import java.util.ArrayList;
import java.util.List;

public class ResidentAdapter extends ArrayAdapter<Resident> {

    private final Context context;
    private final int resource;
    private final List<Resident> residents;

    public ResidentAdapter(@NonNull Context context,
                           int resource,
                           @Nullable List<Resident> residents) {

        super(context, resource, residents != null ? residents : new ArrayList<>());
        this.context = context;
        this.resource = resource;
        this.residents = residents != null ? residents : new ArrayList<>();
    }

    @Override
    public int getCount() {
        return residents.size();
    }

    @Nullable
    @Override
    public Resident getItem(int position) {
        return residents.get(position);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvFloor = convertView.findViewById(R.id.tvFloor);
            holder.tvNbAppliances = convertView.findViewById(R.id.tvApplianceCount);
            holder.recyclerView = convertView.findViewById(R.id.recyclerView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Resident resident = getItem(position);

        if (resident != null) {

            // Nom
            String firstname = resident.getFirstname() != null ? resident.getFirstname() : "";
            String lastname = resident.getLastname() != null ? resident.getLastname() : "";
            holder.tvName.setText(firstname + " " + lastname);

            Habitat habitat = resident.getHabitat();

            if (habitat != null) {

                // Étage
                holder.tvFloor.setText(String.valueOf(habitat.getFloor()));

                // Appliances
                if (habitat.getAppliances() != null) {

                    if (habitat.getNbAppliances()==1){
                        holder.tvNbAppliances.setText(
                                String.valueOf(habitat.getAppliances().size()+"equipement")
                        );
                    }
                    else {
                        holder.tvNbAppliances.setText(
                                String.valueOf(habitat.getAppliances().size()+" équipements")
                        );
                    }


                    ApplianceAdapter applianceAdapter =
                            new ApplianceAdapter(habitat.getAppliances());

                    holder.recyclerView.setLayoutManager(
                            new LinearLayoutManager(context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false)
                    );

                    holder.recyclerView.setAdapter(applianceAdapter);

                } else {
                    holder.tvNbAppliances.setText("0");
                }

            } else {
                holder.tvFloor.setText("-");
                holder.tvNbAppliances.setText("0");
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvFloor;
        TextView tvNbAppliances;
        RecyclerView recyclerView;
    }
}