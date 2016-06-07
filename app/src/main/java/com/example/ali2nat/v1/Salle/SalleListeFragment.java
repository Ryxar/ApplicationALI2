package com.example.ali2nat.v1.Salle;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ali2nat.v1.Adapteur.SalleAdapteur;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.R;

import java.util.ArrayList;

/**
 * Created by Alexis on 30/05/2016.
 */
public class SalleListeFragment extends Fragment {

    public static final String SALLES_KEY = "salles_key";


    private TextView tvTitre;
    private ListView lvSalle;


    // objet
    private ArrayList<Salle> salles;
    private String recherche;

    // Interface pour le callback vers l'activité
    OnSalleSelectedListener mCallback;

    public SalleListeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_salle_liste, container, false);
        Intent i = getActivity().getIntent();

        salles = new ArrayList<>();
        //recherche = false;


        Bundle bundle = this.getArguments();
         recherche = bundle.getString(SalleActivity.NATURE_KEY);


        int taille = bundle.getInt(SalleActivity.SALLES_KEY);
        for (int t = 0; t< taille; t++)
            salles.add((Salle) bundle.getParcelable(SalleActivity.SALLES_NUM_KEY + t));


        tvTitre = (TextView) v.findViewById(R.id.tvTitre);

        lvSalle = (ListView) v.findViewById(R.id.listSalle);
        if(recherche == "recherche"){
            tvTitre.setText(R.string.liste_salle_rech);
        }
        else{
            tvTitre.setText(R.string.liste_salle_pref);
        }


        SalleAdapteur adapteur = new SalleAdapteur(getActivity(), salles);
        lvSalle.setAdapter(adapteur);
        final Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.colorchange);

       lvSalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("type", "type : " + recherche);
                final int pos = position;

                final String titre;
                final String text ;
                mCallback.onArticleSelected(pos, recherche);



                // si recherche = false; on le suprrime de la liste en demandant la confirmation



            }
        });




        return v;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSalleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void startColorAnimation(View v) {
        int colorStart = v.getSolidColor();
        int colorEnd =0xFFFF0000;
        if(recherche == "recherche"){
            colorEnd = 0xFFFF0000;
        }

        ValueAnimator colorAnim = ObjectAnimator.ofInt(v,
                "backgroundColor", colorStart, colorEnd);

        colorAnim.setDuration(2000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim.start();
    }

    private void startColorAnimationReverse(View v) {
        int colorStart = v.getSolidColor();
        int colorEnd = R.color.vert;
        if(recherche == "recherche"){
            colorEnd = R.color.red;
        }

        ValueAnimator colorAnim = ObjectAnimator.ofInt(v,
                "backgroundColor", colorEnd, colorStart);
        colorAnim.setRepeatCount(1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();


        colorAnim.start();
    }



    private void genererSalles() {
        for (int i = 0; i < 10; i++) {
            salles.add(new Salle("Salle " + (i+1), "Adresse de la salle" + (i+1)));
        }

    }



    // Container Activity must implement this interface
    public interface OnSalleSelectedListener {
        public void onArticleSelected(int position, String type);
        // le type est pour dire si liste de recherche ou liste préf
    }






}
