package com.example.ali2nat.v1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali2nat.v1.Adapteur.PeriodeAdapteur;
import com.example.ali2nat.v1.Adapteur.StatistiqueAdaptateur;
import com.example.ali2nat.v1.Modele.Intensite;
import com.example.ali2nat.v1.Modele.Periode;
import com.example.ali2nat.v1.Modele.Profil;
import com.example.ali2nat.v1.Modele.Statistique;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ListView lvPeriode, lvStat;
    private static final String PROFIL_KEY = "profil_key";
    private Profil profil;
    private List<Periode> lPeriode;
    private List<Statistique> lStats;

    private TextView tvNom, tvAdresse, tvType;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Profil profil){
        MainFragment fragment  = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROFIL_KEY, profil);
        fragment.setArguments(bundle);

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profil = (Profil) getArguments().getSerializable(PROFIL_KEY);
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        lvPeriode = (ListView) v.findViewById(R.id.lvPeriode);
        lvStat = (ListView) v.findViewById(R.id.lvStat);

        lPeriode = new ArrayList();
        lStats = new ArrayList();

        tvNom = (TextView )v.findViewById(R.id.tvNom);
        tvAdresse =  (TextView )v.findViewById(R.id.tvAdresse);
        tvType = (TextView )v.findViewById(R.id.tvType);


        tvNom.setText(profil.getNom());
        tvAdresse.setText(profil.getAdresse());
        tvType.setText(profil.typeString());


        genererPeriode();
        genererStat();

        PeriodeAdapteur adapteur_periode = new PeriodeAdapteur(getActivity(), lPeriode);
        StatistiqueAdaptateur adapteur_stat = new StatistiqueAdaptateur(getActivity(), lStats);

        lvStat.setAdapter(adapteur_stat);
        lvPeriode.setAdapter(adapteur_periode);
        return v;
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
