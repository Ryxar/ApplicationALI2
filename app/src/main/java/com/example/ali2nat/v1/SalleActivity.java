package com.example.ali2nat.v1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.Salle.SalleListeFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity implements SalleListeFragment.OnSalleSelectedListener{


    public static final String SALLES_KEY = "salles_key";
    public static final String SALLES_NUM_KEY = "salles_";

    private ListView mListSalle, mListPref;
    private ArrayList<Salle> sallesPref, sallesR;

    private Fragment fragPref;
    private Fragment fragRech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);




        // On initialise les listes d'éléments
        sallesPref = new ArrayList();
        sallesR = new ArrayList();

        genererSallesPref();
        genererSalles();
        // -- -- --  Fragment
        fragPref = new SalleListeFragment();
        fragRech = new SalleListeFragment();

        // Fragment pour les salles préférées
        Log.d("tag", "pref");
        Bundle bundle = genererBundle(false, sallesPref);
        Log.d("type", "Bundle:  "+bundle);
        // Create new fragment and transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // on envoit le bundle
        fragPref.setArguments(bundle);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.llSallePref, fragPref);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();





        Log.d("tag", "rech");
        // Fragment pour les salles recherché
        Bundle bundleRech = genererBundle(true, sallesR);
        // Create new fragment and transaction
        FragmentTransaction transactionR = getFragmentManager().beginTransaction();
        // on envoit le bundle

        Log.d("type", "Bundle:  "+bundleRech);
        fragRech.setArguments(bundleRech);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transactionR.replace(R.id.llSalleRech, fragRech);
        transactionR.addToBackStack(null);
        // Commit the transaction
        transactionR.commit();





    }

    public void onArticleSelected(int position, boolean type){

        if(type == true){

            Salle salle = sallesR.get(position);
            sallesPref.add(salle);
            Toast.makeText((this),
                    "ajout de salle " +salle.getNom() + "à liste pref", Toast.LENGTH_LONG)
                    .show();
        }
        else{

            Salle salle = sallesPref.get(position);
            Toast.makeText((this),
                    "retrait de " +salle.getNom() + "à liste pref", Toast.LENGTH_LONG)
                    .show();
            sallesPref.remove(salle);

        }

        Fragment nvFrag = new SalleListeFragment();
        Bundle bundleRech = genererBundle(type, sallesPref);
        // Create new fragment and transaction
        FragmentTransaction transactionR = getFragmentManager().beginTransaction();
        // on envoit le bundle
        nvFrag.setArguments(bundleRech);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transactionR.replace(R.id.llSallePref, nvFrag);
        transactionR.addToBackStack(null);
        // Commit the transaction
        transactionR.commit();
        fragPref = nvFrag;


    }



/*                      || Non utilisé ||
    private void setFragment(boolean type){
        int idLayout;
        Fragment frag;
        ArrayList<Salle> lSalles;
        if(type == true){
            idLayout = R.id.llSalleRech;
            lSalles = sallesR;
            frag = fragRech;
        }
        else{
            idLayout = R.id.llSallePref;
            lSalles = sallesPref;
            frag = fragPref;
        }
        // Fragment pour les salles préférées
        Bundle bundle = genererBundle(true, lSalles);
        // Create new fragment and transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // on envoit le bundle
        fragRech.setArguments(bundle);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(idLayout, frag);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

    }
*/





    private Bundle genererBundle(boolean type, List<Salle> lSalles){
        Bundle bundle = new Bundle();
        Log.d("type", "type : "+type);

        bundle.putBoolean("NATURE", type);
        bundle.putInt("NB_SALLES", lSalles.size());
        for (int i = 0 ; i < lSalles.size() ; i++)
            bundle.putParcelable("SALLE_ID" + i, lSalles.get(i));

        return bundle;
    }


    private void genererSallesPref() {
        for (int i = 0; i < 5; i++) {
            sallesPref.add(new Salle("Salle Pref " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }

    private void genererSalles() {
        for (int i = 0; i < 10; i++) {
            sallesR.add(new Salle("Salle R " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }
}
