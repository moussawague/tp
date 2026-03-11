package com.example.projet.entities;

import java.util.List;

public class Habitat {

    private int id;
    private int floor;
    private int user_id;
    private List<Appliance> appliances;

    public int getId() { return id; }
    public int getFloor() { return floor; }
    public int getIdUser(){ return user_id; }
    public List<Appliance> getAppliances(){
        return appliances;
    }
    public int getNbAppliances(){
        return appliances.size();
    }
    public void setAppliances(List<Appliance> appliances) {this.appliances = appliances; }
}