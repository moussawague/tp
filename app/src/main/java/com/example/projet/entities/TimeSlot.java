package com.example.projet.entities;

import java.util.List;

public class TimeSlot {
    private int id;
    private String begin;
    private String end;
    private int maxWattage; // Puissance max autorisée (seuil)
    private int currentWattage; // Puissance consommée réellement
    private List<Booking> bookings;

    public TimeSlot() {}

    public TimeSlot(int id, String begin, String end, int maxWattage) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.maxWattage = maxWattage;
    }

    // Méthode MISE À JOUR : Elle regarde d'abord si on a forcé un wattage (pour le calendrier)
    // Sinon, elle calcule la somme des bookings
    public int getTotalBookingWattage() {
        // Si on a défini un wattage manuellement (via setWattage)
        if (this.currentWattage > 0) {
            return this.currentWattage;
        }

        // Sinon, calcul classique via les bookings
        int total = 0;
        if (bookings != null) {
            for (Booking b : bookings) {
                // On vérifie b.getAppliance() ou b.getWattage() selon ta structure
                if (b.getAppliance() != null) {
                    total += b.getAppliance().getWattage();
                }
            }
        }
        return total;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBegin() { return begin; }
    public void setBegin(String begin) { this.begin = begin; }

    public int getMaxWattage() { return maxWattage; }
    public void setMaxWattage(int maxWattage) { this.maxWattage = maxWattage; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    // Remplissage du setter que tu as demandé
    public void setWattage(int watts) {
        this.currentWattage = watts;
    }
}