package com.example.solemnefinalestasiquesi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBarLoadingRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        progressBarLoadingRegister = findViewById(R.id.progressBarLoadingRegister);
        progressBarLoadingRegister.setVisibility(ProgressBar.INVISIBLE);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> RegisterActivity.this.handleRegister(
                editTextEmailRegister.getText().toString(),
                editTextPasswordRegister.getText().toString()
        ));

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void handleRegister(String email, String password) {
        if (password.length() >= 6){
            progressBarLoadingRegister.setVisibility(ProgressBar.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            handleSuccessfulTask();
                        } else {
                            displayExceptionMessage(task);
                        }
                    });
        } else {
            Toast.makeText(
                    RegisterActivity.this,
                    "Error: La password debe contenter al menos seis catactéres",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleSuccessfulTask() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(
                    RegisterActivity.this,
                    LoginActivity.class
            );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, 1500);
    }

    private void displayExceptionMessage(Task<AuthResult> task) {
        String exceptionMessage = task.getException().toString();
        Toast.makeText(
                RegisterActivity.this,
                "Error: " + exceptionMessage,
                Toast.LENGTH_SHORT)
                .show();
    }
}
