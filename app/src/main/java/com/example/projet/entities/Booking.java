package com.example.projet.entities;

import java.time.LocalDateTime;

public class Booking {
    private int order;
    private LocalDateTime bookedAt;

    private Appliance appliance;
    private TimeSlot timeSlot;

    public Booking() {}

    public Booking(int order, LocalDateTime bookedAt) {
        this.order = order;
        this.bookedAt = bookedAt;
    }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

    public Appliance getAppliance() { return appliance; }
    public void setAppliance(Appliance appliance) { this.appliance = appliance; }

    public TimeSlot getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }
}
