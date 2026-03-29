package com.example.projet.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Habitat {

    private int id;
    private int floor;

    // Correction 1 : Utiliser double pour accepter les valeurs comme "85.5"
    private double area;

    // Correction 2 : Utiliser SerializedName pour faire le lien avec "user_id" du JSON
    @SerializedName("user_id")
    private int idUser;

    private List<Appliance> appliances;

    // Constructeur par défaut
    public Habitat() {}

    public int getId() { return id; }

    public int getFloor() { return floor; }

    public int getIdUser() { return idUser; }

    public double getArea() { return area; }

    public List<Appliance> getAppliances() {
        return appliances;
    }

    // Correction 3 : Sécuriser le calcul de la taille pour éviter le crash
    public int getNbAppliances() {
        if (appliances == null) {
            return 0;
        }
        return appliances.size();
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }
}