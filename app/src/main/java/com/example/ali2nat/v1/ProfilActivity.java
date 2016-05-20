package com.example.ali2nat.v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ali2nat.v1.Adapteur.PeriodeAdapteur;
import com.example.ali2nat.v1.Adapteur.StatistiqueAdaptateur;
import com.example.ali2nat.v1.Modele.Intensite;
import com.example.ali2nat.v1.Modele.Periode;
import com.example.ali2nat.v1.Modele.Statistique;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    private ListView lvPeriode, lvStat;
    private List<Periode> lPeriode;
    private List<Statistique> lStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        lvPeriode = (ListView) findViewById(R.id.lvPeriode);
        lvStat = (ListView) findViewById(R.id.lvStat);

        lPeriode = new ArrayList<>();
        genererPeriode();
        lStats = new ArrayList<>();
        genererStat();

        PeriodeAdapteur adapteur_periode = new PeriodeAdapteur(this, lPeriode);
        lvPeriode.setAdapter(adapteur_periode);
        StatistiqueAdaptateur adapteur_stat = new StatistiqueAdaptateur(this, lStats);
        lvStat.setAdapter(adapteur_stat);

    }

    private void genererPeriode() {
        String nomPeriode;
        Intensite int1, int2;
        for (int i = 0; i < 5; i++) {
            if(i==0){
                nomPeriode = "Periode de saisie";
                int1 = new Intensite("Tonic", 0, 40);
                int2 = new Intensite("Dance", 0, 50);
                lPeriode.add(new Periode(nomPeriode, int1 ,int2));
            }
            else if(i == 1){
                nomPeriode = "Periode en cours ";
                int1 = new Intensite("Tonic", 37, 50);
                int2 = new Intensite("Dance", 23, 30);
                lPeriode.add(new Periode(nomPeriode, int1 ,int2));
            }
            else if(i == 2){
                nomPeriode = "Periode précédente ";
                int1 = new Intensite("Tonic", 48, 48);
                int2 = new Intensite("Dance", 43, 43);
                lPeriode.add(new Periode(nomPeriode, int1 ,int2));
            }
            else {
                nomPeriode = "Periode antérieure ";
                int1 = new Intensite("moyen", 20, 20);
                int2 = new Intensite("Dance", 10, 10);
                lPeriode.add(new Periode(nomPeriode, int1 ,int2));
            }
        }

    }

    private void genererStat(){
        for (int i = 0; i < 10; i++) {
            lStats.add(new Statistique("Stat "+ (i+1), (i+1)*7, 100));
        }

    }
}
