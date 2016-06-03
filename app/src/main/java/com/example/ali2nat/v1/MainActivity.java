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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
    private String profile;


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

        profil = (Profil) getIntent().getSerializableExtra("profil");
        profile = getIntent().getStringExtra("Json");
        try {
            JSONObject profileJSON = new JSONObject(profile);
            Toast toast = Toast.makeText(this, (CharSequence) profileJSON.get("name"), Toast.LENGTH_LONG);
            toast.show();
            new DownloadImageTask((ImageView) headerView.findViewById(R.id.profile_image),this).execute((String)profileJSON.get("photo"));
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
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name

        File mediaFile;
        String mImageName="profil.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


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

        } else if (id == R.id.nav_salles) {
            SallesFragment fragment=new SallesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_planning) {
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

        }else if(id == R.id.nav_prof_act){
            Intent intent = new Intent(this, ProfilActivity.class);
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

        WeekViewEvent event;
        for (int i = 0; i < 20; i++) {
            Calendar startTime = Calendar.getInstance();

            startTime.set(Calendar.DAY_OF_WEEK, (i+1)%7);
            startTime.set(Calendar.HOUR_OF_DAY, 8);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, 6);
            startTime.set(Calendar.YEAR, 2016);
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
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        private Context context;
        private ProgressDialog dialog;

        public DownloadImageTask(ImageView bmImage,Context context) {
            this.bmImage = bmImage;
            this.context=context;
        }
        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement...");
            dialog.show();

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            dialog.dismiss();
            bmImage.setImageBitmap(result);
            storeImage(result);
        }
    }
}
