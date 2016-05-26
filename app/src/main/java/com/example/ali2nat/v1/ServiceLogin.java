package com.example.ali2nat.v1;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Adrien.D on 26/05/2016.
 */
/*

//Je sais pas ou mettre le code tu verra avec le cours pour l'instant je l'ai mis dans OnHandleIntent
public class ServiceLogin extends IntentService {

public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
private SigninActivity SA;
 private DownloadFileAsync DF;

    public ServiceLogin(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        if(TestConnection.isConnectedInternet())
        {
        //If Network OK lance la tache Asyn SA

            SA=new SigninActivity(this);
            //On donne en parametre le nom d'utilisateur et le password
            SA.execute(username, password);
            //On bloque l'application pendant 40 sec le temps du dl aprés 40 sec TimeOut
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
                        mProgressDialog.dismiss();
                    }
                }
            }, 400000);
        }
        else//If Network NOK on envoie un toast comme quoi il n'y a pas de connexion
        {

            String NoCon="Pas de connexion détecté";
            Context context=getApplicationContext();
            Toast toast= Toast.makeText(context,NoCon,duration);
            toast.show();
        }


    }
    public void Authentification (String result){
    //Si on a un bon résultat de l'API on lance le télechargement

        if(result.equals(access)){
            String success="Authentication Successful";
            Context context=getApplicationContext();
            Toast toast= Toast.makeText(context,success,duration);
            toast.show();

        }
        else{
        //Sinon on affiche que l'accée est refusé
            Context context=getApplicationContext();
            String text="Access Refused";
            int duration= Toast.LENGTH_SHORT;
            Toast toast= Toast.makeText(context,text,duration);
            toast.show();
        }
    }
    //On affiche une fenetre le temps du DL
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Authentication..");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SA.cancel(true);
                        dialog.dismiss();
                    }
                });
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

//AsynTask avec comme parametre les string "nomd'utilisateur" et "password"

    class SigninActivity  extends AsyncTask<String,Void,String> {
        private Context context;
        public SigninActivity(Context context) {
            this.context = context;
        }

        protected void onPreExecute(){
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);

        }

        @Override
        protected String doInBackground(String... arg0) {

            try{
                String username = arg0[0];
                String password = arg0[1];
                // Les fonctions sont dépréciées donc il faut mettre les nouvelles que t'as vu en cours
                String link = "http://"+serveraddress+"/authentification.php?username="+ URLEncoder.encode(username, "UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&phoneid="+URLEncoder.encode(phone,"UTF-8");

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                //Le return est récupéré en parametre de la fonction OnPostExecute
                return sb.toString();
            }

            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }


        }

        @Override
        protected void onPostExecute(String result){

        //Le result est le retour de l'api

            final String rs=result;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                    //On envoie le retour de l'API a la fonction
                    Authentification(rs);

                }
            }, 2000);
        }
    }

    }
    protected void Toast(String msg) {
        //Display Toast with the message as parameter
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    //L'asynTask permettant de télécharger
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            //Call Pop-up Display
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOADFILE_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/ALI2");
            //Create ALI2 folder if doesn't exist
            if (dir.exists() == false) {
                dir.mkdirs();
            }

            try {
                //Call server to donwload  file
                //Il faut changer ici car c'est déprécié
                String pathurl = ("http://" + serveraddress + "/" + aurl[0] + ".pdf");

                URL url = new URL(pathurl);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(dir + "/" + aurl[1]);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            //On affiche l'avancer du téléchargement
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //Close Pop-up
                    dismissDialog(DIALOG_DOWNLOADFILE_PROGRESS);
                    //Open downloaded file
                    openfile();
                }
            }, 2000);

        }
    }
    //La fonction qui affiche la pop-up avec la progression
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Authentication..");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SA.cancel(true);
                        dialog.dismiss();
                    }
                });
                mProgressDialog.show();
                return mProgressDialog;
                case DIALOG_DOWNLOADFILE_PROGRESS:
                //Function Who display a pop-up with the progress of Download
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DF.cancel(true);
                        dialog.dismiss();
                    }
                });
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }
}*/
