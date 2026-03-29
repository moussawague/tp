package com.example.projet.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.R;
import com.example.projet.entities.TimeSlot;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<TimeSlot> days;

    public DayAdapter(List<TimeSlot> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        TimeSlot dayData = days.get(position);

        // On affiche le numéro du jour (stocké dans l'ID pour le résumé)
        holder.tvDayNumber.setText(String.valueOf(dayData.getId()));

        float limiteResidence = 10000f;
        float actuel = (float) dayData.getTotalBookingWattage();
        int percentage = (int) ((actuel / limiteResidence) * 100);

        Log.d("moussa", "Jour " + dayData.getId() + " | wattage : " + actuel + " (" + percentage + "%)");

        // Application des couleurs
        if (percentage <= 30) {
            holder.cardDay.setCardBackgroundColor(Color.parseColor("#4CAF50")); // VERT
        } else if (percentage <= 70) {
            holder.cardDay.setCardBackgroundColor(Color.parseColor("#FF9800")); // ORANGE
        } else {
            holder.cardDay.setCardBackgroundColor(Color.parseColor("#F44336")); // ROUGE
        }
    }

    @Override
    public int getItemCount() {
        return (days != null) ? days.size() : 0;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayNumber;
        CardView cardDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayNumber = itemView.findViewById(R.id.tvDayNumber);
            cardDay = itemView.findViewById(R.id.cardDay);
        }
    }
}