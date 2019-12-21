package com.example.solemnefinalestasiquesi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, btnRegisterFromLogin;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarLoading = findViewById(R.id.progressBarLoading);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterFromLogin = findViewById(R.id.btnRegisterFromLogin);
        progressBarLoading.setVisibility(ProgressBar.INVISIBLE);

        btnLogin.setOnClickListener(v -> handleLogin(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString()
        ));

        btnRegisterFromLogin.setOnClickListener(v -> redirectToRegisterActivity());
    }

    private void handleLogin(String email, String password){
        if(email.isEmpty()){
            Toast.makeText(this, "Profavor, ingresa el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Profavor, ingresa la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        handleFirebaseAuth(email, password);
    }

    public void handleFirebaseAuth(String email, String pass) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(auth -> {
                    progressBarLoading.setVisibility(ProgressBar.VISIBLE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                })
                .addOnFailureListener(auth -> {
                    Toast.makeText(LoginActivity.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                    System.out.println("Error de autenticación de Firebase: " + auth.getMessage());
                });
    }

    public void redirectToRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}


