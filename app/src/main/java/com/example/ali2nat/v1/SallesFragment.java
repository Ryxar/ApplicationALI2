package com.example.ali2nat.v1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SallesFragment extends Fragment {
    private ListView mListSalle, mListPref;
    private List<Salle> sallesPref, sallesGlob;

    public SallesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_salles, container, false);


        mListPref = (ListView) v.findViewById(R.id.listPref);
        mListSalle = (ListView) v.findViewById(R.id.listSalleGlob);

        // On initialise les listes d'éléments
        sallesPref = new ArrayList();
        sallesGlob = new ArrayList();
        genererSallesPref();
        genererSallesGlob();

        // on gère les adapteurs
        SalleAdapteur adapteurPref = new SalleAdapteur(getActivity(), sallesPref);
        SalleAdapteur adapteurGlob = new SalleAdapteur(getActivity(), sallesGlob);

        mListSalle.setAdapter(adapteurGlob);
        mListPref.setAdapter(adapteurPref);

        mListSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(),"salle n#" +position, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        mListPref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(),"salle n#" +position, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        

        return v;
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
