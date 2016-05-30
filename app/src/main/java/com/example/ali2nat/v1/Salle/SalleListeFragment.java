package com.example.ali2nat.v1.Salle;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.R;
import com.example.ali2nat.v1.SalleActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 30/05/2016.
 */
public class SalleListeFragment extends Fragment {

    public static final String SALLES_KEY = "salles_key";


    private TextView tvTitre;
    private ListView lvSalle;


    // objet
    private ArrayList<Salle> salles;

    public SalleListeFragment() {
        // Required empty public constructor
    }
    public static SalleListeFragment newInstance(List<Salle> salles){
        SalleListeFragment salleListeFragment = new SalleListeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SALLES_KEY, (Serializable) salles);

        salleListeFragment.setArguments(bundle);
        return salleListeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_salle_liste, container, false);
        Intent i = getActivity().getIntent();
        Bundle bundle = this.getArguments();

        salles = new ArrayList<>();
        salles = savedInstanceState.getParcelable(SalleActivity.SALLES_KEY);
        /*
        int taille = bundle.getInt(SalleActivity.SALLES_KEY);
        for (int t = 0; t< taille; t++){
            salles.add((Salle) bundle.getSerializable(SalleActivity.SALLES_NUM_KEY + t));
        }*/

        tvTitre = (TextView) v.findViewById(R.id.tvTitre);
        lvSalle = (ListView) v.findViewById(R.id.listSalle);

        tvTitre.setText("Résultat de la recherche");


        //genererSalles();

        // adapteur
        try{
            SalleAdapteur adapteur = new SalleAdapteur(getActivity(), salles);
            lvSalle.setAdapter(adapteur);
        }catch(NullPointerException e){
            Log.d("yolo", "prble adapteur vide");
        }







        return v;
    }

    private void genererSalles() {
        for (int i = 0; i < 10; i++) {
            salles.add(new Salle("Salle " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }

}
