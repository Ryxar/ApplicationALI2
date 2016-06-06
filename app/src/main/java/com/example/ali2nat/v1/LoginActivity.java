package com.example.ali2nat.v1;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ali2nat.v1.Modele.Profil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;


public class
        LoginActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;
    private Profil profil;
    private SigninActivity SA;
    private int duration = Toast.LENGTH_SHORT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);

        // on initialise le profil
        profil = new Profil("Hubert Bonnisseur de la batte", 1);
    }



    public void login(View view){
        //When Button Login Click

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        if(TestConnection.isConnectedInternet(LoginActivity.this))
        {
            SA=new SigninActivity(this);
            SA.execute(username, password);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SA.getStatus() == AsyncTask.Status.RUNNING) {
                        SA.cancel(true);
                        String NoCon="Time Out";
                        Context context=getApplicationContext();
                        Toast toast= Toast.makeText(context,NoCon,duration);
                        toast.show();
                    }
                }
            }, 40000);
        }
        else//If Network NOK
        {
            String NoCon="Vous n'etes pas connecté";
            Context context=getApplicationContext();
            Toast toast= Toast.makeText(context,NoCon,duration);
            toast.show();
        }
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



    public void downloadfini(JSONObject jsonObject){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("profil", profil);
        intent.putExtra("Json", jsonObject.toString());
        startActivity(intent);
        finish();

    }


    class SigninActivity  extends AsyncTask<String,Void,String> {
        private Context context;
        private ProgressDialog dialog;
        public SigninActivity(Context context) {
            this.context = context;
        }

        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement...");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {

            try{
                String username = arg0[0];
                String password = arg0[1];

                String link =  "https://alimentation.herokuapp.com/loginURL?email=anim@gs.fr&password="+URLEncoder.encode(password,"UTF-8");

                URL url= new URL(link);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();
                InputStream inputStream= urlConnection.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = input.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                input.close();
                return sb.toString();
            }

            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }


        }

        @Override
        protected void onPostExecute(String result){
            dialog.dismiss();
            if(result.equals("KO")||result==null){
                Toast toast = Toast.makeText(context, "Erreur d'identification", duration);
                toast.show();

            }
            else{
                File imgFile = new File(Environment.getExternalStorageDirectory()
                        + "/Android/data/"
                        + context.getPackageName()
                        + "/Files","profil.jpg");
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    Toast toast = Toast.makeText(context, (CharSequence) jsonObject.get("name"), duration);
                    toast.show();
                    if(jsonObject.get("email")!=null&&jsonObject.get("id_GS")!=null&& jsonObject.get("auth")!=null&&jsonObject.get("name")!=null)
                    { if(!imgFile.exists()){
                        new DownloadImageTask(context,jsonObject).execute((String)jsonObject.get("photo"));}
                        else {
                        downloadfini(jsonObject);}


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private Context context;
        private ProgressDialog dialog;
        private JSONObject jsonObject;

        public DownloadImageTask(Context context,JSONObject jsonObject) {
            this.jsonObject=jsonObject;
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
            storeImage(result);
            downloadfini(jsonObject);
        }
    }
    class DownloadSallesActivity  extends AsyncTask<Void,Void,String> {
        private Context context;
        private ProgressDialog dialog;
        public DownloadSallesActivity(Context context) {
            this.context = context;
        }


        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement...");
            dialog.show();

        }

        protected String doInBackground(Void... voids) {

            try{


                String link =  "http://ws.gymsuedoise.com/api/V2/classroom&country=FR&apikey=XE2uG449BC";

                URL url= new URL(link);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();
                InputStream inputStream= urlConnection.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = input.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                input.close();
                return sb.toString();
            }

            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }



        }

        @Override
        protected void onPostExecute(String result){
            dialog.dismiss();
        }
}
}