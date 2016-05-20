package com.example.ali2nat.v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;

import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity {

    private ListView mListSalle, mListPref;
    private List<Salle> sallesPref, sallesGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);

        // on récupère les listes view
        mListPref = (ListView) findViewById(R.id.listPref);
        mListSalle = (ListView) findViewById(R.id.listSalleGlob);

        // On initialise les listes d'éléments
        sallesPref = new ArrayList();
        sallesGlob = new ArrayList();
        genererSallesPref();
        genererSallesGlob();

        // on gère les adapteurs
        SalleAdapteur adapteurPref = new SalleAdapteur(this, sallesPref);
        SalleAdapteur adapteurGlob = new SalleAdapteur(this, sallesGlob);

        mListSalle.setAdapter(adapteurGlob);
        mListPref.setAdapter(adapteurPref);

    }

    private void genererSallesPref() {
        for (int i = 0; i < 5; i++) {
            sallesPref.add(new Salle("Salle Pref " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }

    private void genererSallesGlob() {
        for (int i = 0; i < 10; i++) {
            sallesGlob.add(new Salle("Salle " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }
}
