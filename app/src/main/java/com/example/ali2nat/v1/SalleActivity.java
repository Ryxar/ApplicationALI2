package com.example.ali2nat.v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.Salle.ListeSalleFragment;

import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity {

    private ListView mListSalle, mListPref;
    private List<Salle> sallesPref, salles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);




        // On initialise les listes d'éléments
        sallesPref = new ArrayList();

        genererSallesPref();
        genererSalles();


        // fragment
        ListeSalleFragment fragment= new ListeSalleFragment();
                // faire une new instance pour passer en argument des trucs
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
        // on gère les adapteurs

        // on récupère les listes view
        mListPref = (ListView) findViewById(R.id.listPref);
        mListSalle = (ListView) findViewById(R.id.listSalleGlob);

        SalleAdapteur adapteurPref = new SalleAdapteur(this, sallesPref);



        mListPref.setAdapter(adapteurPref);

    }


    private void genererSallesPref() {
        for (int i = 0; i < 5; i++) {
            sallesPref.add(new Salle("Salle Pref " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }

    private void genererSalles() {
        for (int i = 0; i < 10; i++) {
            salles.add(new Salle("Salle " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }
}
