package com.example.projet.entities;

import java.util.List;

public class Resident {

    private int id;
    private String firstname;
    private String lastname;
    private Habitat habitat;
    private List<Appliance> appliances;
    private String email;
    private String password;

    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public Habitat getHabitat() { return habitat; }
    public List<Appliance> getAppliances() { return appliances; }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat=habitat;
    }
}