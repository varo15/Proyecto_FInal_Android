package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextPass;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    //declaramos un objeto Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos el objeto Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = findViewById(R.id.textoEmail);
        TextPass = findViewById(R.id.textoPass);

        btnRegistrar = findViewById(R.id.button);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener((View.OnClickListener) this);
    }

    private void registrarUsuario(){
        //Obtenemos el email y la contrasenia desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String pass = TextPass.getText().toString().trim();

        //verificamos que las cajas no esten vacias
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Se debe ingresar una contrasenia", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando el registro");
        progressDialog.show();

        //Creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Check if succes
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Se ha registrado el email", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(MainActivity.this,"No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    public void onClick(View view){
        //Invocamos el metodo
        registrarUsuario();
    }
}
