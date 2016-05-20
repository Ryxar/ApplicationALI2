package com.example.ali2nat.v1.Modele;

/**
 * Created by Alexis Pentori on 09/05/2016.
 */
public class Salle {

    private String nom;
    private String adresse;


    public Salle(String nom, String adresse){
        this.nom = nom;
        this.adresse = adresse;

    }


    // GETTER && SETTER
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
