package com.example.solemnefinalestasiquesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.solemnefinalestasiquesi.API.ChatApi;
import com.example.solemnefinalestasiquesi.Models.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSend,btnOut;
    private EditText editTextMessage;
    private EditText multiLineTextMessages;
    private TextView textViewWelcome;
    private DatabaseReference mFirebaseDatabaseReference;

    private boolean doubleBackToExitPressedOnce;
    private Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doubleBackToExitPressedOnce = false;
        handler = new Handler();

        textViewWelcome = findViewById(R.id.textViewWelcome);
        btnOut = findViewById(R.id.btnOut);
        multiLineTextMessages = findViewById(R.id.multiLineTextMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.btnSend);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("solemneFinalEstaSiQUeSi");

        FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();
        if(logged_user != null) {
            handleLoggedUser(logged_user);
        } else {
            redirectToLoginActivity();
        }
    }

    private void handleLoggedUser(FirebaseUser logged_user) {
        textViewWelcome.append(" "+logged_user.getEmail());
        btnOut.setOnClickListener(v -> handleLogoutEvent());
        btnSend.setOnClickListener(v -> handleSendMessage());
        handleHistoricMessages();
    }

    private void redirectToLoginActivity() {
        // Handler scopedHandler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, 1500);
    }

    private void handleLogoutEvent(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleSendMessage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getEmail();
        String messageToSend = editTextMessage.getText().toString();
        mFirebaseDatabaseReference.child("mensaje_user").push().setValue(name+": "+messageToSend);
        editTextMessage.setText("");
    }

    private void handleHistoricMessages() {
        mFirebaseDatabaseReference.child("mensaje_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    Toast.makeText(MainActivity.this,"Conversación vacía.",Toast.LENGTH_SHORT).show();
                }
                else{
                    fillMessagesHistory(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error en la base de datos: "+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillMessagesHistory(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> messagesHistory = dataSnapshot.getChildren();
        ArrayList<String> messages = new ArrayList<>();

        for(DataSnapshot message : messagesHistory){
            String m = message.getValue().toString();
            messages.add(m);
        }

        ChatApi chatApi = new ChatApi();
        chatApi.deleteAllMessages();
        chatApi.recreateAllMessages(messages);
        List<Text> historyOfMessages = chatApi.getAllMessages();

        if(!historyOfMessages.isEmpty()){
            if(multiLineTextMessages.getText().length() == 0){
                for(int i = 0; i < historyOfMessages.size(); i++){
                    multiLineTextMessages.append(historyOfMessages.get(i)+"\n");
                }
            } else {
                multiLineTextMessages.append(historyOfMessages.get(historyOfMessages.size()-1)+"\n");
            }
        }
        else{
            chatApi.createNewMessage(messages.get(messages.size()-1));

            if(multiLineTextMessages.getText().length() == 0){
                for(int i = 0; i < historyOfMessages.size(); i++) {
                    multiLineTextMessages.append(historyOfMessages.get(i)+"\n");
                }
            }

            else{
                multiLineTextMessages.append(historyOfMessages.get(historyOfMessages.size()-1)+"\n");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private final Runnable runnable = () -> doubleBackToExitPressedOnce = false;

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Doble click para salir", Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable, 2000);
    }
}
