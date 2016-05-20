package com.example.ali2nat.v1.Modele;

/**
 * Created by alexi on 12/05/2016.
 */
public class Intensite {

    private String nom;
    private int hEffectue;
    private int hRestant;

    public Intensite (String nom,int hEffectue, int hRestant){
        this.nom = nom;
        this.hEffectue = hEffectue;
        this.hRestant = hRestant;
    }

    public String getText(){
        return nom+" "+ hEffectue+"h effectué"+ hRestant + "h restante";

    }
}
