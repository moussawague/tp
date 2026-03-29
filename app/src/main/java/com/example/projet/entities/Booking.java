package com.example.projet.entities;

public class Booking {
    private int order;
    private int timeslot_id; // Pour faire le lien avec le TimeSlot
    private String bookedAt;
    private Appliance appliance;

    public Booking() {}

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public int getTimeSlotId() { return timeslot_id; }
    public void setTimeSlotId(int timeslot_id) { this.timeslot_id = timeslot_id; }

    public String getBookedAt() { return bookedAt; }
    public void setBookedAt(String bookedAt) { this.bookedAt = bookedAt; }

    public Appliance getAppliance() { return appliance; }
    public void setAppliance(Appliance appliance) { this.appliance = appliance; }
}