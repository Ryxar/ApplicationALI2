package com.example.ali2nat.v1.Modele;

/**
 * Created by alexi on 12/05/2016.
 */
public class Statistique {
    private String nom;
    private String description;
    private int avancement;
    private int total;

    public Statistique(String nom, int avancement, int total) {
        this.nom = nom;
        this.avancement = avancement;
        this.total = total;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvancement() {
        return avancement;
    }

    public void setAvancement(int avancement) {
        this.avancement = avancement;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
