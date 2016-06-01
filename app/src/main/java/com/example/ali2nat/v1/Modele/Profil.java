package com.example.ali2nat.v1.Modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alexis Pentori on 09/05/2016.
 */
public class Profil implements Serializable {
    private String nom;
    private int type; // 1=ambassadeur, 2=animateur, 3=gestionnaire
    private String adresse; // Pour enregistrer l'adresse de référence du profil
    private ArrayList<Integer> intensites;
    private ArrayList<Salle> salles;

    public Profil (String nom, int type){
        this.nom = nom;
        this.type = type;
    }

    public String typeString()
    {
        String type_u;
        switch (type){
            case 1:
                type_u = "Ambassadeur";
                break;
            case 2:
                type_u="Animateur";
                break;
            case 3 :
                type_u = "Gestionnaire";
                break;
            default :
                type_u="no type";
                break;

        }
        return type_u;
    }
    // GETTER && SETTER

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ArrayList<Integer> getIntensites() {
        return intensites;
    }

    public void setIntensites(ArrayList<Integer> intensites) {
        this.intensites = intensites;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }

    public void setSalles(ArrayList<Salle> salles) {
        this.salles = salles;
    }
}
