package com.example.ali2nat.v1.Salle;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.R;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity implements SalleListeFragment.OnSalleSelectedListener, OnMapReadyCallback{

    public static final String NATURE_KEY = "nature_key";
    public static final String SALLES_KEY = "salles_key";
    public static final String SALLES_NUM_KEY = "salles_";

    private ListView mListSalle, mListPref;
    private ArrayList<Salle> sallesPref, sallesR;
    private Salle salleSelectionnee;
    private String nature;

    private Fragment fragPref;
    private Fragment fragRech;

    //private MapFragment mapFragment;
    private SupportMapFragment mapF;

    private Button bRech, bAction;
    private EditText etRech;
    private TextView tvNomSalle, tvAdresse;
    private ImageView ivIcone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);

        nature = "vide";

        tvNomSalle = (TextView) findViewById(R.id.tvNomSalle);
        tvAdresse = (TextView) findViewById(R.id.tvAdresse);
        ivIcone = (ImageView) findViewById(R.id.ivIcone);
        tvNomSalle.setVisibility(View.GONE);
        tvAdresse.setVisibility(View.GONE);
        ivIcone.setVisibility(View.GONE);


        // On initialise les listes d'éléments
        sallesPref = new ArrayList();
        sallesR = new ArrayList();
        salleSelectionnee  = null;


        genererSalles();
        // -- -- --  Fragment
        fragPref = new SalleListeFragment();
        fragRech = new SalleListeFragment();

        // Fragment pour les salles préférées
        Log.d("tag", "pref");
        Bundle bundle = genererBundle("favori", sallesPref);
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
        Bundle bundleRech = genererBundle("recherche", sallesR);
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

        // -- Map --
        GoogleMap googleMap;

        mapF = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapF.getMapAsync(this);

        bRech = (Button) findViewById(R.id.bRech);
        etRech = (EditText) findViewById(R.id.etRech) ;
        bRech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yolo", "onClick: ");
                String rech =  etRech.getText().toString();
                recherche(rech);
            }
        });
        bAction = (Button) findViewById(R.id.bAction);
        bAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nvFrag = new SalleListeFragment();
                if(nature == "recherche"){
                    sallesPref.add(salleSelectionnee);
                    // -- Update des listes -- //
                    Bundle bundleRech = genererBundle("favori", sallesPref);
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
                }else if(nature == "favori"){
                    sallesPref.remove(salleSelectionnee);
                    // -- Update des listes -- //
                    Bundle bundleRech = genererBundle(nature, sallesPref);
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
                }else{

                }
            }
        });




    }

    public void onArticleSelected(int position, String type){

        tvAdresse.setVisibility(View.VISIBLE);
        tvNomSalle.setVisibility(View.VISIBLE);
        ivIcone.setVisibility(View.VISIBLE);
        nature = type;
//        ajoutMarker();
        if(type == "recherche"){

            salleSelectionnee = sallesR.get(position);
            ivIcone.setImageResource(R.drawable.icone_fav_vide);
            tvNomSalle.setText(salleSelectionnee.getNom());
            tvAdresse.setText(salleSelectionnee.getAdresse());
        }
        else {

            salleSelectionnee = sallesPref.get(position);
            ivIcone.setImageResource(R.drawable.icone_fav_plein);
            tvNomSalle.setText(salleSelectionnee.getNom());
            tvAdresse.setText(salleSelectionnee.getAdresse());
        }




    }

    public void recherche(String recherche){
        ArrayList<Salle> listRech = new ArrayList<>();
        for (int i = 0; i < sallesR.size(); i++) {
            if(sallesR.get(i).getNom().contains(recherche)){
                listRech.add(sallesR.get(i));

            }
            if(sallesR.get(i).getAdresse().contains(recherche)){
                listRech.add(sallesR.get(i));
            }
        }

        Fragment nvFrag = new SalleListeFragment();
        Bundle bundleRech = genererBundle("recherche", listRech);
        // Create new fragment and transaction
        FragmentTransaction transactionR = getFragmentManager().beginTransaction();
        // on envoit le bundle
        nvFrag.setArguments(bundleRech);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transactionR.replace(R.id.llSalleRech, nvFrag);
        transactionR.addToBackStack(null);
        // Commit the transaction
        transactionR.commit();
        fragPref = nvFrag;
    }








    private Bundle genererBundle(String type, List<Salle> lSalles){
        Bundle bundle = new Bundle();
        Log.d("type", "type : "+type);

        bundle.putString(NATURE_KEY, type);
        bundle.putInt(SALLES_KEY, lSalles.size());
        for (int i = 0 ; i < lSalles.size() ; i++)
            bundle.putParcelable(SALLES_NUM_KEY + i, lSalles.get(i));

        return bundle;
    }




    private void genererSalles() {
        sallesR.add(new Salle("Paris 9 Bergère","5 rue Bergère Paris 75009"));
        sallesR.add(new Salle("Paris 4 Saint Merri","16 rue du Renard Paris 75004"));
        sallesR.add(new Salle("Paris 7 Saint Jean","11 rue Pierre Villey Paris 75007"));
        sallesR.add(new Salle("Paris 6 Stanislas - G2","Collège Stanislas - 6 rue du Montparnasse Paris 75006"));
        sallesR.add(new Salle("Paris 7 Camou","35 avenue de la Bourdonnais Paris 75007"));
        sallesR.add(new Salle("Paris 9 Grange Batelière","Ecole maternelle Grange Batelière, 11 rue Grange Batelière Paris 75009"));
        sallesR.add(new Salle("Paris 13 Thomas Mann","Rue Cadets de la France Libre Paris 75013"));
        sallesR.add(new Salle("Paris 13 Glacière","88 rue de la Glacière Paris 75013"));
        sallesR.add(new Salle("Paris 13 Rentiers","184 rue du Château des Rentiers Paris 75013"));
        sallesR.add(new Salle("Paris 13 Blanqui","26 bd Auguste Blanqui"));

    }

    public LatLng getLocationFromAddress(String strAdress){
        Geocoder coder = new Geocoder(this);
        List<android.location.Address> address;
        LatLng p1 = null;
        try{
            address = coder.getFromLocationName(strAdress, 5);
            if(address == null){
                return null;
            }
            android.location.Address locaction = address.get(0);
           p1 = new LatLng( locaction.getLatitude(), locaction.getLongitude());

        }catch (Exception e){
            Log.e("MAP", "getLocationFromAddress: "+ e.toString());
        }

        return p1;
    }

    @Override
    public void onMapReady(GoogleMap map) {

       LatLng address = getLocationFromAddress("3 bis rue Lakanal Sceaux");
        map.addMarker(new MarkerOptions().position(address).title("Marker chez moi"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(address,12));


    }

    public void ajoutMarker(){
        LatLng latLng = getLocationFromAddress(salleSelectionnee.getAdresse());
        GoogleMap googleMap = mapF.getMap();
        googleMap.addMarker(new MarkerOptions().position(latLng).title(salleSelectionnee.getNom()));

    }
}
