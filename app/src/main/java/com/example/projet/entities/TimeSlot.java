package com.example.projet.entities;

import java.time.LocalDateTime;
import java.util.List;

public class TimeSlot {
    private int id;
    private LocalDateTime begin;
    private LocalDateTime end;
    private int maxWattage;

    private List<Booking> bookings;

    public TimeSlot() {}

    public TimeSlot(int id, LocalDateTime begin, LocalDateTime end, int maxWattage) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.maxWattage = maxWattage;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getBegin() { return begin; }
    public void setBegin(LocalDateTime begin) { this.begin = begin; }

    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }

    public int getMaxWattage() { return maxWattage; }
    public void setMaxWattage(int maxWattage) { this.maxWattage = maxWattage; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
