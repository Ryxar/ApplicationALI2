package com.example.ali2nat.v1.Modele;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexi on 12/05/2016.
 */
public class Periode {
    // -- Variable Globale--
    private String nomPeriode;
    private Intensite int1;
    private Intensite int2;


    public Periode(String nomPeriode, Intensite int1,Intensite int2){
        this.nomPeriode = nomPeriode;
        this.int1 = int1;
        this.int2 = int2;
    }

    public String getNomPeriode() {
        return nomPeriode;
    }

    public void setNomPeriode(String nomPeriode) {
        this.nomPeriode = nomPeriode;
    }

    public Intensite getInt1() {
        return int1;
    }

    public void setInt1(Intensite int1) {
        this.int1 = int1;
    }

    public Intensite getInt2() {
        return int2;
    }

    public void setInt2(Intensite int2) {
        this.int2 = int2;
    }
}
