package com.example.projet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projet.adapters.DayAdapter;
import com.example.projet.R;
import com.example.projet.entities.Booking;
import com.example.projet.entities.TimeSlot;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendrierFragment extends Fragment {
    private RecyclerView recyclerView;
    private DayAdapter adapter;
    private List<TimeSlot> dailyProcessed = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);
        recyclerView = view.findViewById(R.id.calendarRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        // On initialise l'adapter une seule fois
        adapter = new DayAdapter(dailyProcessed);
        recyclerView.setAdapter(adapter);

        loadTimeSlots();
        return view;
    }

    private void loadTimeSlots() {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/timeslot.php")
                .as(new TypeToken<ArrayList<TimeSlot>>() {})
                .setCallback((e, slots) -> {
                    if (slots != null) loadBookings(slots);
                });
    }

    private void loadBookings(List<TimeSlot> slots) {
        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/booking.php")
                .as(new TypeToken<ArrayList<Booking>>() {})
                .setCallback((e, bookings) -> {
                    if (bookings != null) {
                        for (TimeSlot slot : slots) {
                            List<Booking> matchingBookings = new ArrayList<>();
                            for (Booking b : bookings) {
                                if (b.getTimeSlotId() == slot.getId()) matchingBookings.add(b);
                            }
                            slot.setBookings(matchingBookings);
                        }
                        displayData(slots);
                    }
                });
    }

    private void displayData(List<TimeSlot> slots) {
        List<TimeSlot> result = processDailyData(slots);
        dailyProcessed.clear();
        dailyProcessed.addAll(result);
        adapter.notifyDataSetChanged(); // Rafraîchit les couleurs !
    }

    private List<TimeSlot> processDailyData(List<TimeSlot> allSlots) {
        Map<Integer, Integer> dailyMax = new HashMap<>();
        for (TimeSlot slot : allSlots) {
            try {
                // Extraction du jour depuis "YYYY-MM-DD"
                int day = Integer.parseInt(slot.getBegin().substring(8, 10));
                int currentWatts = slot.getTotalBookingWattage();

                if (!dailyMax.containsKey(day) || currentWatts > dailyMax.get(day)) {
                    dailyMax.put(day, currentWatts);
                }
            } catch (Exception e) {
                Log.e("DEBUG", "Erreur parsing date: " + slot.getBegin());
            }
        }

        List<TimeSlot> result = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            int watts = dailyMax.getOrDefault(i, 0);
            TimeSlot ts = new TimeSlot();
            ts.setId(i);
            ts.setBegin("2026-04-" + String.format("%02d", i));
            ts.setWattage(watts); // <-- IMPORTANT : On définit le wattage calculé
            result.add(ts);
        }
        return result;
    }
}