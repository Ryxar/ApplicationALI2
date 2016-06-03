package com.example.ali2nat.v1;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali2nat.v1.Modele.Profil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    public void tryresult(String resultat){
        if(resultat.equals("KO")||resultat==null){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Erreur d'identification", duration);
            toast.show();

        }
        else{
        try {
            Context context = getApplicationContext();
            JSONObject jsonObject = new JSONObject(resultat);
            Toast toast = Toast.makeText(context, (CharSequence) jsonObject.get("name"), duration);
            toast.show();
            if(jsonObject.get("email")!=null&&jsonObject.get("id_GS")!=null&& jsonObject.get("auth")!=null&&jsonObject.get("name")!=null)
            {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("profil", profil);
                intent.putExtra("Json", jsonObject.toString());
                startActivity(intent);
                finish();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }

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
            tryresult(result);

        }
    }
}