package com.example.ali2nat.v1;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.ali2nat.v1.Modele.Profil;




public class LoginActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;
    private Profil profil;
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
        //pour l'instant ça lance la mainActivity direct il faut le changer avec le service appellant l'API
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("profil", profil);
        startActivity(intent);
        finish();}
}