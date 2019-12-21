package com.example.solemnefinalestasiquesi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, btnRegisterFromLogin;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarLoading = findViewById(R.id.progressBarLoading);
        mHandler = new Handler();
        doubleBackToExitPressedOnce = false;
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterFromLogin = findViewById(R.id.btnRegisterFromLogin);
        progressBarLoading.setVisibility(ProgressBar.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.handleLogin(
                        editTextEmail.getText().toString(),
                        editTextPassword.getText().toString()
                );
            }
        });

        btnRegisterFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.redirectToRegisterActivity();
            }
        });
    }

    private void handleLogin(String email, String password){
        if(email.isEmpty()){
            Toast.makeText(this, "Ingresa el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Ingresa la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        handleFirebaseAuth(email, password);
    }

    public void handleFirebaseAuth(String email, String pass) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult auth) {
                        progressBarLoading.setVisibility(ProgressBar.VISIBLE);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception auth) {
                        Toast.makeText(LoginActivity.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                        System.out.println("Error de autenticación de Firebase: " + auth.getMessage());
                    }
                });
    }

    public void redirectToRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}


