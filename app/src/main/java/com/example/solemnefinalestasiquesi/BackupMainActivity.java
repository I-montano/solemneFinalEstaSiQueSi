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

public class BackupMainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnRegistrar;
    TextView tvIngresa;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        // Será necesario poner esto ?
        // .getReference("solemneFinalEstaSiQueSi"); ??
        etEmail=findViewById(R.id.editEmail);
        etPassword=findViewById(R.id.editPassword);
        btnRegistrar=findViewById(R.id.btnRegistrar1);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(MainActivity.this,"Ambos campos están vacios" ,Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "No se completó el registro", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,Inicio.class));
                            }
                        }

                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "¡Ocurrió un error inesperado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvIngresa=findViewById(R.id.tvIngresar1);
        tvIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Ingresar.class);
                startActivity(i);
            }
        });
    }

}
