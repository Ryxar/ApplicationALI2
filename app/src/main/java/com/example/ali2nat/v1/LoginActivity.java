package com.example.ali2nat.v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;*/

import com.example.ali2nat.v1.Modele.Profil;

import java.io.File;
import java.io.FileOutputStream;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;
    final private String access="accessok";
    private String id;
    private File myFile,myDir;
    private FileOutputStream output;
    private StringBuilder text=new StringBuilder();
    int duration= Toast.LENGTH_SHORT;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    final private String serveraddress="192.168.0.2";
    //private SigninActivity SA;

    private Profil profil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);
        //Check if user already register

        // on initialise le profil
        profil = new Profil("Hubert Bonnisseur de la batte", 1);

    }



    public void login(View view){
        //When Button Login Click
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("profil", profil);
        startActivity(intent);
        finish();

       /* String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        //If Network OK
        if(TestConnection.isConnectedInternet(LoginActivity.this))
        {
            SA=new SigninActivity(this);
            SA.execute(username, password,id);
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
            }, 40000);
        }
        else//If Network NOK
        {
            String NoCon="Pas de connexion détecté";
            Context context=getApplicationContext();
            Toast toast= Toast.makeText(context,NoCon,duration);
            toast.show();
        }


    }
    public void tryresult (String result){

        if(result.equals(access)){
            String success="Authentication Successful";
            Context context=getApplicationContext();
            Toast toast= Toast.makeText(context,success,duration);
            toast.show();
            try {
                output = new FileOutputStream(myFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                output.write(id.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Context context=getApplicationContext();
            String text="Access Refused";
            int duration= Toast.LENGTH_SHORT;
            Toast toast= Toast.makeText(context,text,duration);
            toast.show();
        }
    }
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


/*class SigninActivity  extends AsyncTask<String,Void,String> {
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
            String phone=arg0[2];
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
            return sb.toString();
        }

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }


    }

    @Override
    protected void onPostExecute(String result){

        final String rs=result;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                tryresult(rs);

            }
        }, 2000);
    }
}*/
}}
