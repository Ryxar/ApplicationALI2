package com.example.ali2nat.v1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.Salle.SalleListeFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity {


    public static final String SALLES_KEY = "salles_key";
    public static final String SALLES_NUM_KEY = "salles_";

    private ListView mListSalle, mListPref;
    private ArrayList<Salle> sallesPref, salles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);




        // On initialise les listes d'éléments
        sallesPref = new ArrayList();
        salles = new ArrayList();

        genererSallesPref();
        genererSalles();


        // on rempli le bundle pour envoyer les infos
        Bundle bundle = new Bundle();
        bundle.putInt("NB_SALLES", salles.size());
        for (int i = 0 ; i < salles.size() ; i++)
            bundle.putParcelable("SALLE_ID" + i, salles.get(i));

        //bundle.putParcelableArrayList(SALLES_KEY, salles);

        // Create new fragment and transaction

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment newFragment = new SalleListeFragment();

        // on envoit le bundle
        newFragment.setArguments(bundle);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.list, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();



        // on récupère les listes view
        mListPref = (ListView) findViewById(R.id.listSallePref);
        //mListSalle = (ListView) findViewById(R.id.listSalleGlob);

        SalleAdapteur adapteurPref = new SalleAdapteur(this, sallesPref);



        mListPref.setAdapter(adapteurPref);

    }
    public List<Salle> getListeRech(){
        return salles;
    }


    private void genererSallesPref() {
        for (int i = 0; i < 5; i++) {
            sallesPref.add(new Salle("Salle Pref " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }

    private void genererSalles() {
        for (int i = 0; i < 10; i++) {
            salles.add(new Salle("Salle R " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }
}
