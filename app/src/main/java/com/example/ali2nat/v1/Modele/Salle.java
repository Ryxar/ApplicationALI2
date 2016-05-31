package com.example.ali2nat.v1.Modele;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Alexis Pentori on 09/05/2016.
 */
public class Salle implements Parcelable{

    private String nom;
    private String adresse;


    public Salle(String nom, String adresse){
        this.nom = nom;
        this.adresse = adresse;

    }


    protected Salle(Parcel in) {
        nom = in.readString();
        adresse = in.readString();
    }

    public static final Creator<Salle> CREATOR = new Creator<Salle>() {
        @Override
        public Salle createFromParcel(Parcel in) {
            return new Salle(in);
        }

        @Override
        public Salle[] newArray(int size) {
            return new Salle[size];
        }
    };


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(adresse);
    }
}
