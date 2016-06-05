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
import android.widget.ListView;
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

public class    SalleActivity extends AppCompatActivity implements SalleListeFragment.OnSalleSelectedListener, OnMapReadyCallback{

    public static final String NATURE_KEY = "nature_key";
    public static final String SALLES_KEY = "salles_key";
    public static final String SALLES_NUM_KEY = "salles_";

    private ListView mListSalle, mListPref;
    private ArrayList<Salle> sallesPref, sallesR;

    private Fragment fragPref;
    private Fragment fragRech;

    //private MapFragment mapFragment;
    private SupportMapFragment mapF;

    private Button bRech, bMap;
    private EditText etRech;

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
        Bundle bundle = genererBundle("preferer", sallesPref);
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
        final Intent intent = new Intent(this, MapsActivity.class);



    }

    public void onArticleSelected(int position, String type){

        if(type == "recherche"){

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

    public void recherche(String recherche){
        ArrayList<Salle> listRech = new ArrayList<>();
        for (int i = 0; i < sallesR.size(); i++) {
            if(sallesR.get(i).getNom().contains(recherche)){
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

       LatLng address = getLocationFromAddress("Montrouge");
        map.addMarker(new MarkerOptions().position(address).title("Marker chez moi"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(address,12));


    }
}
