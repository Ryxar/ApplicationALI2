package com.example.ali2nat.v1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.ali2nat.v1.Modele.Profil;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.Salle.SalleActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SemaineTypeFragment.OnCoursSelectedListener {

    // clef de communication bundle
        // bundle Planning
    public static final String EVENT_KEY = "event_key";
    public  static final String EVENT_NUM_TYPE = "event_";
        // Bundle Salle
    public static final String LSALLE_KEY = "lsalle_key";
    public static final String LSALLE_NUM_TYPE = "lsalle_";
        // Bundle Profil

    NavigationView navigationView=null;
    Toolbar toolbar=null;

    private Fragment frag;
    private String profile,salles;
    JSONObject profileJSON;


    // Profil de l'utilisateur
    Profil profil ;//= new Profil("Hubert Bonnisseur de la batte", 1);
    // Liste des salles de l'utilisateur
    ArrayList<Salle> mesSalles;
    // Liste des salles (tout)
    ArrayList<Salle> lesSalles;

    ArrayList<WeekViewEvent> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView nom=(TextView)  headerView.findViewById(R.id.textViewNom);
        TextView role=(TextView)headerView.findViewById(R.id.textViewrole);
        ImageView imagenav=(ImageView)headerView.findViewById(R.id.profile_image);

        profil = (Profil) getIntent().getSerializableExtra("profil");
        profile = getIntent().getStringExtra("JsonProfil");

        // recuperation SAlle
        int taille = getIntent().getIntExtra(LoginActivity.LSALLE_KEY, 0);
        for (int i = 0; i < taille; i++) {
            lesSalles.add((Salle) getIntent().getParcelableExtra(LoginActivity.LSALLE_NUM_TYPE+i));
        }
        File imgFile = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files","profil.jpg");

        try {
            profileJSON = new JSONObject(profile);

            imagenav.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            nom.setText((String) profileJSON.get("name"));
            role.setText((String) profileJSON.get("auth"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainFragment fragment= MainFragment.newInstance(profil);
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisation du profil


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



    }
    public JSONObject getJsonProfil(){return profileJSON;}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }
    public void deconnexion(MenuItem item){
        finish();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment myFragment=null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
            MainFragment fragment;

                 fragment = MainFragment.newInstance(profil);

                 //fragment = new MainFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_planning) {
            PlanningFragment fragment=new PlanningFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_semaine_type) {
            genererPlanning();
            Bundle bundleSemaineType= genererBundlePlanning();
            SemaineTypeFragment fragment=new SemaineTypeFragment();
            fragment.setArguments(bundleSemaineType);
            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        }/*else if (id == R.id.nav_gestion) {
            GestionFragment fragment=new GestionFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } */else if(id == R.id.nav_salle_act){
            Intent intent = new Intent(this, SalleActivity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Transmission de données au fragment planning
    public Bundle genererBundlePlanning(){
        Log.d("yolo", "genererBundle: ");
        Bundle bundle = new Bundle();



        bundle.putInt(EVENT_KEY, events.size());
        for (int i = 0 ; i < events.size() ; i++)
            bundle.putParcelable(EVENT_NUM_TYPE + i, events.get(i));

        return bundle;
    }

    private  void genererPlanning(){

        events = new ArrayList<>();
        Calendar startTime = Calendar.getInstance();
        WeekViewEvent event;
        for (int i = 0; i < 20; i++) {


            startTime.set(2016,6,(i+1), 8+(i+1)%7, 0 );
            /*
            startTime.set(Calendar.HOUR_OF_DAY, 8+(i%5));
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, 6);
            startTime.set(Calendar.YEAR, 2016);
            */
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 10);
            event = new WeekViewEvent(i, "Yolo","NomYoloSalle", startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

        }


    }

    @Override
    public void onArticleSelected(WeekViewEvent event) {
        events.add(event);

        Log.d("yolo", "On est dans MainA: ");
        //
        genererPlanning();
        Bundle bundleSemaineType= genererBundlePlanning();
        SemaineTypeFragment fragment=new SemaineTypeFragment();
        fragment.setArguments(bundleSemaineType);
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

    }

}
