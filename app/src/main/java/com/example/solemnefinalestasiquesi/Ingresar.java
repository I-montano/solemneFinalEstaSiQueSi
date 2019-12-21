package com.example.solemnefinalestasiquesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Ingresar extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnIngresar;
    TextView tvRegistrar;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        mFirebaseAuth=FirebaseAuth.getInstance();
        etEmail=findViewById(R.id.editEmail);
        etPassword=findViewById(R.id.editPassword);
        btnIngresar=findViewById(R.id.btnIngresar1);
        tvRegistrar=findViewById(R.id.tvRegistrar1);


        mAuthStateListener = firebaseAuth -> {
            FirebaseUser mFireBaseUser = mFirebaseAuth.getCurrentUser();
            if(mFireBaseUser != null) {
                Toast.makeText(Ingresar.this,"Ya estás logeado",Toast.LENGTH_SHORT);
                Intent i = new Intent(Ingresar.this,Inicio.class);
                startActivity(i);
            }
            else{
                Toast.makeText(Ingresar.this,"Por favor ingresa",Toast.LENGTH_SHORT);
            }
        };

        btnIngresar.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String pass = etPassword.getText().toString();
            if (email.isEmpty()) {
                etEmail.setError("Ingrese un correo por favor");
                etEmail.requestFocus();
            }
            else if (pass.isEmpty()){
                etPassword.setError("Ingrese su contraseña por favor");
                etPassword.requestFocus();
            }
            else if (email.isEmpty() && pass.isEmpty()){
                Toast.makeText(Ingresar.this,"Ambos campos están vacios" ,Toast.LENGTH_SHORT).show();
            }
            else if (!(email.isEmpty() && pass.isEmpty())){
                mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Ingresar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Ingresar.this, "Error al ingresar, por favor intentelo de nuevo", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intToHome = new Intent (Ingresar.this,Inicio.class);
                            startActivity(intToHome);
                        }
                    }
                });
            }
            else{
                Toast.makeText(Ingresar.this, "¡Ocurrió un error inesperado!", Toast.LENGTH_SHORT).show();
            }
    });

        tvRegistrar.setOnClickListener(view -> {
            Intent intRegistrar=new Intent (Ingresar.this,MainActivity.class);
            startActivity(intRegistrar);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
